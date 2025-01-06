package com.yurkiss.planradar.weatherapp.common.domain

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.details.domain.CityWeather
import com.yurkiss.planradar.weatherapp.historical.domain.model.HistoricalItem

interface OpenWeatherRepository {
    suspend fun getCities(query: String): Outcome<List<City>>
    suspend fun getHistoricalData(city: City): Outcome<List<HistoricalItem>>
    suspend fun getCityWeather(city: City): Outcome<CityWeather>
}