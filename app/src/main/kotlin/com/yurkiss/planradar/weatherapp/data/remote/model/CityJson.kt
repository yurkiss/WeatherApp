package com.yurkiss.planradar.weatherapp.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityJson(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null // State is optional
)