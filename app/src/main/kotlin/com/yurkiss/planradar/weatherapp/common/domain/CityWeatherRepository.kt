package com.yurkiss.planradar.weatherapp.common.domain

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.details.domain.CityWeather
import com.yurkiss.planradar.weatherapp.historical.domain.model.HistoricalItem
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {
    suspend fun put(city: City, weather: CityWeather): Outcome<Unit>
    fun observeAll(city: City): Flow<Outcome<List<HistoricalItem>>>
}