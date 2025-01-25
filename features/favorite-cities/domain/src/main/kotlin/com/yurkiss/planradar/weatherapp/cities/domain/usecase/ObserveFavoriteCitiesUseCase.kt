package com.yurkiss.planradar.weatherapp.cities.domain.usecase

import com.yurkiss.planradar.weatherapp.cities.domain.repository.FavoriteCitiesRepository
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.NoParamsUseCase
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteCitiesUseCase @Inject constructor(
    private val observableLocal: FavoriteCitiesRepository,
) : NoParamsUseCase<Flow<Outcome<List<City>>>>() {
    override suspend fun run(params: Unit): Flow<Outcome<List<City>>> {
        return observableLocal.observeAll()
    }
}