package com.yurkiss.planradar.weatherapp.data.repository

import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.domain.OpenWeatherRepository
import com.yurkiss.planradar.weatherapp.common.util.NetworkException
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.runCatching
import com.yurkiss.planradar.weatherapp.data.remote.OpenWeatherApi
import com.yurkiss.planradar.weatherapp.data.remote.OpenWeatherHistoricalApi
import com.yurkiss.planradar.weatherapp.details.domain.CityWeather
import com.yurkiss.planradar.weatherapp.di.OpenWeatherApiKey
import com.yurkiss.planradar.weatherapp.historical.domain.model.HistoricalItem
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val historicalApi: OpenWeatherHistoricalApi,
    @OpenWeatherApiKey private val apiKey: String
) : OpenWeatherRepository {

    override suspend fun getCities(query: String): Outcome<List<City>> {
        return runCatching(handler = {
            NetworkException(it.message ?: "Network error", it)
        }) {
            api.queryCities(query, 5, apiKey).map {
                City(it.name, it.country)
            }
        }
    }

    override suspend fun getCityWeather(city: City): Outcome<CityWeather> {
        return runCatching(handler = {
            NetworkException(it.message ?: "Network error", it)
        }) {
            val cityWeatherJson =
                api.queryWeather(city = "${city.name},${city.country}", apiKey = apiKey)

            val weatherInfo = cityWeatherJson.weather.first()
            CityWeather(
                cityWeatherJson.dateTime,
                cityWeatherJson.name,
                cityWeatherJson.main.temp,
                cityWeatherJson.main.humidity,
                cityWeatherJson.wind.speed,
                weatherInfo.description,
                cityWeatherJson.dateTime,
                weatherInfo.icon,
            )
        }
    }

    override suspend fun getHistoricalData(city: City): Outcome<List<HistoricalItem>> {
        return runCatching(handler = {
            NetworkException(it.message ?: "Network error", it)
        }) {
            val historicalDataJson = historicalApi.queryHistoricalData(
                city = "${city.name},${city.country}",
                apiKey = apiKey
            )
            val epochSeconds = historicalDataJson.items.first().dateTime
            val time = Instant.fromEpochSeconds(epochSeconds)
            val dateTime = time.toLocalDateTime(TimeZone.currentSystemDefault())

            historicalDataJson.items.map {
                val weatherInfo = it.weather.first()
                HistoricalItem(
                    epochSeconds,
                    dateTime.toString(),
                    weatherInfo.description,
                    it.main.temp,
                    weatherInfo.icon
                )
            }
        }
    }

}

