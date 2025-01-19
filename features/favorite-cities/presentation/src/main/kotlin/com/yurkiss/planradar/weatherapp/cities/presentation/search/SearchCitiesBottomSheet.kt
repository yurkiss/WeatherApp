package com.yurkiss.planradar.weatherapp.cities.presentation.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.common.domain.repository.Labels
import com.yurkiss.planradar.weatherapp.common.domain.repository.LabelsRepository
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.databinding.AddCityBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchCitiesBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: SearchCitiesViewModel by viewModels()

    @Inject
    lateinit var controller: SearchCitiesController

    @Inject
    lateinit var labelsRepository: LabelsRepository

    private var _binding: AddCityBottomSheetBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                val layoutParams = it.layoutParams
                layoutParams.height = getWindowHeight() / 2
                it.layoutParams = layoutParams
                behavior.peekHeight = getWindowHeight()
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddCityBottomSheetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(requireDialog().window!!.decorView) { view, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            val bottomSheet =
                dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            val layoutParams = bottomSheet?.layoutParams
            layoutParams?.height =
                (getWindowHeight() / 2 + imeHeight).coerceAtMost(getWindowHeight())
            bottomSheet?.layoutParams = layoutParams
            bottomSheet?.setPadding(0, 0, 0, imeHeight)

            WindowInsetsCompat.CONSUMED
        }

        // Configure EpoxyRecyclerView and EpoxyController
        _binding?.apply {
            recyclerViewCities.apply {
                setController(controller)
                itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = true }
                LinearLayoutManager(context)
                    .also {
                        it.recycleChildrenOnDetach = true
                        layoutManager = it
                    }
            }
            controller.setFilterDuplicates(true)
            controller.callback = callback

            inputSearchForCity.doAfterTextChanged {
                viewModel.submit(SearchCitiesActions.SearchCity(it.toString()))
            }

        }

        // Observe ViewModel state
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { value ->
                        controller.viewState = value
                    }
                }

                launch {
                    viewModel.events.collectLatest { event ->
                        when (event) {
                            is SearchCitiesScreenEvents.CityAdded -> dismiss()
                            is SearchCitiesScreenEvents.ShowError -> {
                                Snackbar.make(
                                    view,
                                    labelsRepository.getLabel(Labels.ErrorAddingCity),
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAnchorView(com.google.android.material.R.id.design_bottom_sheet)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        controller.clear()
    }

    private val callback = object : SearchCitiesController.Callback {
        override fun onItemClicked(record: UiCity) {
            viewModel.submit(SearchCitiesActions.AddCity(record))
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}