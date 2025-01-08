package com.yurkiss.planradar.weatherapp.di

import com.yurkiss.planradar.weatherapp.common.ResourcesLabelsRepository
import com.yurkiss.planradar.weatherapp.common.domain.CityWeatherRepository
import com.yurkiss.planradar.weatherapp.common.domain.LabelsRepository
import com.yurkiss.planradar.weatherapp.common.domain.OpenWeatherRepository
import com.yurkiss.planradar.weatherapp.data.repository.LocalCityWeatherRepository
import com.yurkiss.planradar.weatherapp.data.repository.OpenWeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsLabelsRepository(repo: ResourcesLabelsRepository): LabelsRepository

    @Binds
    @Singleton
    abstract fun bindsOpenWeatherRepository(repo: OpenWeatherRepositoryImpl): OpenWeatherRepository

    @Binds
    @Singleton
    abstract fun bindsLocalCityWeatherRepository(repo: LocalCityWeatherRepository): CityWeatherRepository

}