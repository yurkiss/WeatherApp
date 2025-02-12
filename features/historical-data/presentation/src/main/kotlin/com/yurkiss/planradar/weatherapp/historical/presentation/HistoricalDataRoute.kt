package com.yurkiss.planradar.weatherapp.historical.presentation

import com.yurkiss.planradar.weatherapp.common.presentation.AppRoutes
import kotlinx.serialization.Serializable

@Serializable
data class HistoricalDataRoute(val city: String, val country: String) : AppRoutes
