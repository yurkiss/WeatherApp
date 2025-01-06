package com.yurkiss.planradar.weatherapp.details.domain

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.OpenWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.UseCase
import com.yurkiss.planradar.weatherapp.common.util.coMap
import com.yurkiss.planradar.weatherapp.data.repository.LocalCityWeatherRepository
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val repository: OpenWeatherRepository,
    private val localRepository: LocalCityWeatherRepository,
) : UseCase<Outcome<CityWeather>, City>() {
    override suspend fun run(params: City): Outcome<CityWeather> {
        return repository.getCityWeather(params).coMap {
            localRepository.put(params, it)
            it
        }
    }
}
