package com.yurkiss.planradar.weatherapp.historical.domain

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.CityWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.UseCase
import com.yurkiss.planradar.weatherapp.historical.domain.model.HistoricalItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoricalDataUseCase @Inject constructor(
    private val repository: CityWeatherRepository,
) : UseCase<Flow<Outcome<List<HistoricalItem>>>, City>() {
    override suspend fun run(params: City): Flow<Outcome<List<HistoricalItem>>> {
        return repository.observeAll(params)
    }
}