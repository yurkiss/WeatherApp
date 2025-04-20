package com.yurkiss.planradar.weatherapp.cities.domain.usecase

import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.repository.OpenWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.FlowUseCase
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repo: OpenWeatherRepository,
) : FlowUseCase<Outcome<List<City>>, String>() {
    override fun run(params: String): Flow<Outcome<List<City>>> {
        return flow {
            emit(repo.getCities(params))
        }
    }
}