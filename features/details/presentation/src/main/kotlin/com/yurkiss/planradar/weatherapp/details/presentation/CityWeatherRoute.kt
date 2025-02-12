package com.yurkiss.planradar.weatherapp.details.presentation

import com.yurkiss.planradar.weatherapp.common.presentation.AppRoutes
import kotlinx.serialization.Serializable

@Serializable
data class CityWeatherRoute(val city: String, val country: String) : AppRoutes
