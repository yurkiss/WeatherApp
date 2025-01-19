package com.yurkiss.planradar.weatherapp.cities.presentation

interface FavoriteCitiesNavigation {
    fun onCityClicked(city: UiCity)
    fun onInfoClicked(city: UiCity)

}