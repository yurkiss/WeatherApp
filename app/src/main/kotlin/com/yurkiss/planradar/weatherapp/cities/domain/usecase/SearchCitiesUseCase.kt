package com.yurkiss.planradar.weatherapp.cities.domain.usecase

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.OpenWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.UseCase
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repo: OpenWeatherRepository,
) : UseCase<Outcome<List<City>>, String>() {
    override suspend fun run(params: String): Outcome<List<City>> {
        return repo.getCities(params)
    }
}