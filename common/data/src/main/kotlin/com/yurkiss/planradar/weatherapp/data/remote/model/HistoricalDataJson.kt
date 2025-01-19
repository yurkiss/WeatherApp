package com.yurkiss.planradar.weatherapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoricalDataJson(
    @Json(name = "cod") val cod: String,
    @Json(name = "city_id") val cityId: Int,
    @Json(name = "cnt") val count: Int,
    @Json(name = "list") val items: List<WeatherItem>
)

@JsonClass(generateAdapter = true)
data class WeatherItem(
    @Json(name = "main") val main: MainInfo,
    @Json(name = "weather") val weather: List<WeatherInfo>,
    @Json(name = "dt") val dateTime: Long
)

@JsonClass(generateAdapter = true)
data class MainInfo(
    @Json(name = "temp") val temp: Double,
)
