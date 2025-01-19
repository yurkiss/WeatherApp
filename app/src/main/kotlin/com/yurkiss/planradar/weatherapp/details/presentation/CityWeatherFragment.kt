package com.yurkiss.planradar.weatherapp.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import coil3.load
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.repository.Labels
import com.yurkiss.planradar.weatherapp.common.domain.repository.LabelsRepository
import com.yurkiss.planradar.weatherapp.common.presentation.BindingFragment
import com.yurkiss.planradar.weatherapp.common.presentation.FavoriteCitiesToCityWeatherArgs
import com.yurkiss.planradar.weatherapp.databinding.WeatherDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CityWeatherFragment : BindingFragment<WeatherDetailsFragmentBinding>(::inflater) {

    @Inject
    lateinit var labelsRepository: LabelsRepository

    private val viewModel: CityWeatherViewModel by hiltNavGraphViewModels(comR.id.nav_graph)

    override fun setupGUI(binding: WeatherDetailsFragmentBinding, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val toolbar = binding.toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(activity, navController)
        arguments?.let {
            val name = it.getString(FavoriteCitiesToCityWeatherArgs.CITY, "")
            activity.supportActionBar?.title = null
        }

        arguments?.let {
            val name = it.getString(FavoriteCitiesToCityWeatherArgs.CITY, "")
            val country = it.getString(FavoriteCitiesToCityWeatherArgs.COUNTRY, "")
            viewModel.submit(CityWeatherActions.RequestData(City(name, country)))
        }
    }

    override fun setupObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is CityWeatherScreenState.Loaded -> {
                            binding {
                                weatherIcon.load("https://openweathermap.org/img/w/${state.cities.icon}.png")
                                cityName.text = state.cities.name
                                description.text = state.cities.description
                                temperature.text = "${state.cities.temperature.toInt()} \u2103"
                                humidity.text = "${state.cities.humidity} %"
                                windspeed.text = "${state.cities.windSpeed} km/h"
                                footer.text = getString(
                                    R.string.lbl_footer,
                                    state.cities.name,
                                    state.cities.dateTime
                                )

                                loadingLayout.visibility = View.GONE
                                weatherCard.visibility = View.VISIBLE
                                weatherCardContent.visibility = View.VISIBLE

                            }
                        }

                        CityWeatherScreenState.Loading -> binding {
                            loadingLayout.visibility = View.VISIBLE
                            weatherCard.visibility = View.VISIBLE
                            weatherCardContent.visibility = View.GONE
                        }

                        is CityWeatherScreenState.Error -> binding {
                            displayMessage(labelsRepository.getLabel(Labels.GeneralError))
                        }

                        CityWeatherScreenState.NoData -> binding {
                            displayMessage(labelsRepository.getLabel(Labels.NoData))
                        }
                    }
                }
            }
        }

    }

    private fun displayMessage(message: String) = binding {
        loadingText.text = message
        loadingLayout.visibility = View.VISIBLE
        loadingProgress.visibility = View.GONE
        weatherCardContent.visibility = View.GONE
        weatherCard.visibility = View.VISIBLE
    }

    companion object {
        fun inflater(
            layoutInflater: LayoutInflater,
            container: ViewGroup?
        ): WeatherDetailsFragmentBinding {
            return WeatherDetailsFragmentBinding.inflate(layoutInflater, container, false)
        }
    }
}