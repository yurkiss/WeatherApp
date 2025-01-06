package com.yurkiss.planradar.weatherapp.data.remote

import com.yurkiss.planradar.weatherapp.data.remote.model.CityJson
import com.yurkiss.planradar.weatherapp.data.remote.model.CityWeatherJson
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A Retrofit interface for fetching weather data from the OpenWeatherMap API.
 */
interface OpenWeatherApi {

    /**
     * Queries cities by name using the OpenWeatherMap Geocoding API.
     *
     * @param city The name of the city to query.
     * @param limit The maximum number of cities to return.
     * @param apiKey Your OpenWeatherMap API key.
     * @return A list of [CityJson] objects representing the matching cities.
     */
    @GET(DIRECT_GEO_ENDPOINT_URL)
    suspend fun queryCities(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityJson>

    /**
     * Queries current weather data for a city using the OpenWeatherMap Current Weather API.
     *
     * @param city The name of the city to query.
     * @param apiKey Your OpenWeatherMap API key.
     * @return A [CityWeatherJson] object representing the current weather data for the city.
     */
    @GET(WEATHER_ENDPOINT_URL)
    suspend fun queryWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): CityWeatherJson


    companion object {
        /**
         * URL for the direct geocoding API.
         */
        const val DIRECT_GEO_ENDPOINT_URL = "geo/1.0/direct"

        /**
         * URL for the current weather data API.
         */
        const val WEATHER_ENDPOINT_URL = "data/2.5/weather?units=metric"
    }

}

