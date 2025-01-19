package com.yurkiss.planradar.weatherapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeatherJson(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "weather") val weather: List<WeatherInfo>,
    @Json(name = "main") val main: Main,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "dt") val dateTime: Long,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Double,
)

@JsonClass(generateAdapter = true)
data class WeatherInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)
