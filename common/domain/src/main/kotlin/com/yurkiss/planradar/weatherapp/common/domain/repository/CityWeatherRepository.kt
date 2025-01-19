package com.yurkiss.planradar.weatherapp.common.domain.repository

import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.model.CityWeather
import com.yurkiss.planradar.weatherapp.common.domain.model.HistoricalItem
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {
    suspend fun put(city: City, weather: CityWeather): Outcome<Unit>
    fun observeAll(city: City): Flow<Outcome<List<HistoricalItem>>>
}