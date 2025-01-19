package com.yurkiss.planradar.weatherapp.cities.domain.repository

import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import kotlinx.coroutines.flow.Flow

interface FavoriteCitiesRepository {
    fun getAll(): List<City>
    fun observeAll(): Flow<Outcome<List<City>>>
    suspend fun put(city: City): Outcome<City>
}

