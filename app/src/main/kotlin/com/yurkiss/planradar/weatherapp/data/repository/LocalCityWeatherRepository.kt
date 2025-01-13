package com.yurkiss.planradar.weatherapp.data.repository

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.CityWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.DatabaseException
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.formatToLocalDateTime
import com.yurkiss.planradar.weatherapp.common.util.runCatching
import com.yurkiss.planradar.weatherapp.common.util.toLeft
import com.yurkiss.planradar.weatherapp.common.util.toRight
import com.yurkiss.planradar.weatherapp.data.database.dao.CityWeatherDao
import com.yurkiss.planradar.weatherapp.data.database.entity.CityWeatherEntity
import com.yurkiss.planradar.weatherapp.details.domain.CityWeather
import com.yurkiss.planradar.weatherapp.historical.domain.model.HistoricalItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class LocalCityWeatherRepository @Inject constructor(
    private val cityWeatherDao: CityWeatherDao,
) : CityWeatherRepository {

    init {
        Timber.d("LocalCityWeatherRepository initialized")
    }

    override fun observeAll(city: City): Flow<Outcome<List<HistoricalItem>>> {
        return cityWeatherDao.observe(city.name, city.country)
            .map { list ->
                list.map {
                    HistoricalItem(
                        id = it.id,
                        dateTime = formatToLocalDateTime(it.dateTime),
                        description = it.description,
                        temperature = it.temperature,
                        weatherIcon = it.icon
                    )
                }.toRight() as Outcome<List<HistoricalItem>>
            }
            .catch {
                emit(DatabaseException(it.message ?: "General database exception", it).toLeft())
            }
    }

    override suspend fun put(city: City, weather: CityWeather): Outcome<Unit> {
        return runCatching({
            DatabaseException(it.message ?: "Failed to add City weather to database", it)
        }) {
            cityWeatherDao.insert(
                CityWeatherEntity(
                    id = weather.id,
                    name = city.name,
                    country = city.country,
                    temperature = weather.temperature,
                    humidity = weather.humidity,
                    windSpeed = weather.windSpeed,
                    description = weather.description,
                    dateTime = weather.dateTime,
                    icon = weather.icon
                )
            )
        }
    }
}