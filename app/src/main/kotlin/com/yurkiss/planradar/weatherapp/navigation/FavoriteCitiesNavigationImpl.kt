package com.yurkiss.planradar.weatherapp.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.cities.presentation.FavoriteCitiesNavigation
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.common.presentation.FavoriteCitiesToCityWeatherArgs
import com.yurkiss.planradar.weatherapp.common.presentation.FavoriteCitiesToHistoricalDataArgs
import javax.inject.Inject

class FavoriteCitiesNavigationImpl @Inject constructor(
    private val navController: NavController
) : FavoriteCitiesNavigation {
    override fun onCityClicked(city: UiCity) {
        val bundle = bundleOf(
            FavoriteCitiesToCityWeatherArgs.CITY to city.title,
            FavoriteCitiesToCityWeatherArgs.COUNTRY to city.country
        )
        navController.navigate(
            R.id.action_FavoriteCitiesFragment_to_CityWeatherFragment,
            bundle
        )
    }

    override fun onInfoClicked(city: UiCity) {
        val bundle = bundleOf(
            FavoriteCitiesToHistoricalDataArgs.CITY to city.title,
            FavoriteCitiesToHistoricalDataArgs.COUNTRY to city.country
        )
        navController.navigate(
            R.id.action_FavoriteCitiesFragment_to_HistoricalDataFragment,
            bundle
        )
    }
}