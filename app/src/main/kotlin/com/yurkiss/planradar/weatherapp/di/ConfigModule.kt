package com.yurkiss.planradar.weatherapp.di

import com.yurkiss.planradar.weatherapp.BuildConfig
import com.yurkiss.planradar.weatherapp.common.di.OpenWeatherApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    @Singleton
    @OpenWeatherApiKey
    fun providesOpenWeatherApiKey() = BuildConfig.API_KEY
}