package com.yurkiss.planradar.weatherapp.historical.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.presentation.BindingFragment
import com.yurkiss.planradar.weatherapp.common.presentation.FavoriteCitiesToHistoricalDataArgs
import com.yurkiss.planradar.weatherapp.databinding.HistoricalDataFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HistoricalDataFragment : BindingFragment<HistoricalDataFragmentBinding>(::inflater) {

    @Inject
    lateinit var historicalDataController: HistoricalDataController

    private val viewModel: HistoricalDataViewModel by hiltNavGraphViewModels(comR.id.nav_graph)

    override fun setupGUI(binding: HistoricalDataFragmentBinding, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val toolbar = binding.toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(activity, navController)
        arguments?.let {
            val name = it.getString(FavoriteCitiesToHistoricalDataArgs.CITY, "")
            activity.supportActionBar?.title = getString(R.string.historical_data_label, name)
        }

        binding.recyclerViewHistoricalData.apply {
            setController(historicalDataController)
            itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = true }
            LinearLayoutManager(context)
                .also {
                    it.recycleChildrenOnDetach = true
                    layoutManager = it
                }
        }

    }

    override fun setupObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { value ->
                    historicalDataController.viewState = value
                }
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.lifecycle.withResumed {
//                arguments?.let {
//                    val name = it.getString(FavoriteCitiesToHistoricalDataArgs.CITY, "")
//                    activity?.fragmentCallbacks?.run {
//                        setTitle(getString(R.string.historical_data_label, name))
//                        hideFab()
//                    }
//                }
//            }
//        }

        arguments?.let {
            val name = it.getString(FavoriteCitiesToHistoricalDataArgs.CITY, "")
            val country = it.getString(com.yurkiss.planradar.weatherapp.common.presentation.FavoriteCitiesToHistoricalDataArgs.COUNTRY, "")
            viewModel.submit(HistoricalDataActions.RequestData(City(name, country)))
        }
    }

    override fun destroyGUI(binding: HistoricalDataFragmentBinding) {
        historicalDataController.clear()
    }

    companion object {
        fun inflater(
            layoutInflater: LayoutInflater,
            container: ViewGroup?
        ): HistoricalDataFragmentBinding {
            return HistoricalDataFragmentBinding.inflate(layoutInflater, container, false)
        }
    }
}