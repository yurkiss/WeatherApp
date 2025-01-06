package com.yurkiss.planradar.weatherapp.data.remote

import com.yurkiss.planradar.weatherapp.data.remote.model.HistoricalDataJson
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A Retrofit interface for fetching historical weather data from the OpenWeatherMap API.
 * @see <a href="https://openweathermap.org/history">OpenWeatherMap History API</a>
 */
interface OpenWeatherHistoricalApi {

    /**
     * This function is used to retrieve historical weather data for a specified city.
     *
     * @param city The name of the city to query.
     * @param apiKey Your OpenWeatherMap API key.
     */
    @GET(HISTORICAL_API_URL)
    suspend fun queryHistoricalData(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
    ): HistoricalDataJson

    companion object {
        const val HISTORICAL_API_URL = "data/2.5/history/city"
    }
}