package com.yurkiss.planradar.weatherapp.cities.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
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
import com.yurkiss.planradar.weatherapp.cities.presentation.search.SearchCitiesBottomSheet
import com.yurkiss.planradar.weatherapp.common.BindingFragment
import com.yurkiss.planradar.weatherapp.common.FavoriteCitiesToCityWeatherArgs
import com.yurkiss.planradar.weatherapp.common.FavoriteCitiesToHistoricalDataArgs
import com.yurkiss.planradar.weatherapp.common.fragmentCallbacks
import com.yurkiss.planradar.weatherapp.databinding.FavoriteCitiesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FavoriteCitiesFragment : BindingFragment<FavoriteCitiesFragmentBinding>(::inflater) {

    @Inject
    lateinit var favoriteCitiesControllerFactory: FavoriteCitiesController.Factory

    private val viewModel: FavoriteCitiesViewModel by hiltNavGraphViewModels(R.id.nav_graph)

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

    override fun onResume() {
        super.onResume()
        activity?.fragmentCallbacks?.displayFab()
    }

    override fun destroyGUI(binding: FavoriteCitiesFragmentBinding) {
        favoriteCitiesController.clear()
    }

    private val callback = object : FavoriteCitiesController.Callback {
        override fun onItemClicked(record: UiCity) {
            val bundle = bundleOf(
                FavoriteCitiesToCityWeatherArgs.CITY to record.title,
                FavoriteCitiesToCityWeatherArgs.COUNTRY to record.country
            )
            findNavController().navigate(
                R.id.action_FavoriteCitiesFragment_to_CityWeatherFragment,
                bundle
            )
        }

        override fun onInfoClicked(record: UiCity) {
            val bundle = bundleOf(
                FavoriteCitiesToHistoricalDataArgs.CITY to record.title,
                FavoriteCitiesToHistoricalDataArgs.COUNTRY to record.country
            )
            findNavController().navigate(
                R.id.action_FavoriteCitiesFragment_to_HistoricalDataFragment,
                bundle
            )
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