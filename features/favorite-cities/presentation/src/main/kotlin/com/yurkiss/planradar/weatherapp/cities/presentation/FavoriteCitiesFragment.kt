package com.yurkiss.planradar.weatherapp.cities.presentation

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
import com.yurkiss.planradar.weatherapp.cities.presentation.search.SearchCitiesBottomSheet
import com.yurkiss.planradar.weatherapp.common.presentation.BindingFragment
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.R
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.databinding.FavoriteCitiesFragmentBinding
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FavoriteCitiesFragment : BindingFragment<FavoriteCitiesFragmentBinding>(::inflater) {

    @Inject
    lateinit var favoriteCitiesControllerFactory: FavoriteCitiesController.Factory

    @Inject
    lateinit var navigationCallback: Lazy<FavoriteCitiesNavigation>

    private val viewModel: FavoriteCitiesViewModel by hiltNavGraphViewModels(comR.id.nav_graph)

    private val favoriteCitiesController: FavoriteCitiesController by lazy {
        favoriteCitiesControllerFactory.create()
    }

    override fun setupGUI(binding: FavoriteCitiesFragmentBinding, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val toolbar = binding.toolbar
        (requireActivity() as? AppCompatActivity)?.let {
            it.setSupportActionBar(toolbar)
            NavigationUI.setupActionBarWithNavController(it, navController)
            it.supportActionBar?.title = getString(R.string.favorite_cities_fragment_label)
        }

        binding.recyclerViewFavoriteCities.apply {
            setController(favoriteCitiesController)
            itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = true }
            LinearLayoutManager(context)
                .also {
                    it.recycleChildrenOnDetach = true
                    layoutManager = it
                }
        }
        favoriteCitiesController.callback = callback

        binding.fab.setOnClickListener {
            val modalBottomSheet = SearchCitiesBottomSheet()
            modalBottomSheet.show(parentFragmentManager, SearchCitiesBottomSheet.TAG)

        }
    }

    override fun setupObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { value ->
                    favoriteCitiesController.viewState = value
                }
            }
        }
    }

    override fun destroyGUI(binding: FavoriteCitiesFragmentBinding) {
        favoriteCitiesController.clear()
    }

    private val callback = object : FavoriteCitiesController.Callback {
        override fun onItemClicked(record: UiCity) {
            navigationCallback.get().onCityClicked(record)

        }

        override fun onInfoClicked(record: UiCity) {
            navigationCallback.get().onInfoClicked(record)
        }
    }

    companion object {
        fun inflater(
            layoutInflater: LayoutInflater,
            container: ViewGroup?
        ): FavoriteCitiesFragmentBinding {
            return FavoriteCitiesFragmentBinding.inflate(layoutInflater, container, false)
        }
    }
}