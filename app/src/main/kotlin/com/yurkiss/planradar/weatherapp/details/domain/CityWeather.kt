package com.yurkiss.planradar.weatherapp.details.domain


data class CityWeather(
    val id: Long,
    val name: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val dateTime: Long,
    val icon: String
)