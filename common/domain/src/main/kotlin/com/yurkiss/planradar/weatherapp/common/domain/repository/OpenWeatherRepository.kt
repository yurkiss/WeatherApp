package com.yurkiss.planradar.weatherapp.common.domain.repository

import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.model.CityWeather
import com.yurkiss.planradar.weatherapp.common.domain.model.HistoricalItem
import com.yurkiss.planradar.weatherapp.common.util.Outcome

interface OpenWeatherRepository {
    suspend fun getCities(query: String): Outcome<List<City>>
    suspend fun getHistoricalData(city: City): Outcome<List<HistoricalItem>>
    suspend fun getCityWeather(city: City): Outcome<CityWeather>
}