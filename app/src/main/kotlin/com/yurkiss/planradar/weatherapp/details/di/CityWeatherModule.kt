package com.yurkiss.planradar.weatherapp.details.di

import com.yurkiss.planradar.weatherapp.common.domain.repository.CityWeatherRepository
import com.yurkiss.planradar.weatherapp.data.repository.LocalCityWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CityWeatherModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsCityWeatherRepository(repo: LocalCityWeatherRepository): CityWeatherRepository

}