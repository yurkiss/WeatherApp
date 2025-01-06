package com.yurkiss.planradar.weatherapp.cities.domain.usecase

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.cities.domain.repository.FavoriteCitiesRepository
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.UseCase
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val repo: FavoriteCitiesRepository,
) : UseCase<Outcome<City>, City>() {
    override suspend fun run(params: City): Outcome<City> {
        return repo.put(params)
    }
}