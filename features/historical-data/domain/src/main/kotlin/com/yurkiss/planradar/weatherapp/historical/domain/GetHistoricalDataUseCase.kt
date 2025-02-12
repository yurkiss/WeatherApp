package com.yurkiss.planradar.weatherapp.historical.domain

import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.model.HistoricalItem
import com.yurkiss.planradar.weatherapp.common.domain.repository.CityWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.FlowUseCase
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoricalDataUseCase @Inject constructor(
    private val repository: CityWeatherRepository,
) : FlowUseCase<Outcome<List<HistoricalItem>>, City>() {
    override fun run(params: City): Flow<Outcome<List<HistoricalItem>>> {
        return repository.observeAll(params)
    }
}