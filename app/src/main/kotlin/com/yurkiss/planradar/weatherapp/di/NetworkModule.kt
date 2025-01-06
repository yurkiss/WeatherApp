package com.yurkiss.planradar.weatherapp.di

import com.squareup.moshi.Moshi
import com.yurkiss.planradar.weatherapp.BuildConfig
import com.yurkiss.planradar.weatherapp.data.remote.OpenWeatherApi
import com.yurkiss.planradar.weatherapp.data.remote.OpenWeatherHistoricalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * A qualifier annotation used to distinguish the API keys.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenWeatherApiKey

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @OpenWeatherApiKey
    fun providesOpenWeatherApiKey() = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun providesMoshi() = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(
        moshi: Moshi
    ): OpenWeatherApi {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.openweathermap.org/")
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideOpenWeatherHistoricalApi(
        moshi: Moshi
    ): OpenWeatherHistoricalApi {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://history.openweathermap.org/")
            .build()
            .create()
    }

}



