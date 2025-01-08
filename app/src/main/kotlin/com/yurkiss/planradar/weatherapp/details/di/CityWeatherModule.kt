package com.yurkiss.planradar.weatherapp.details.di

import com.yurkiss.planradar.weatherapp.cities.data.LocalFavoriteCitiesRepository
import com.yurkiss.planradar.weatherapp.cities.domain.repository.FavoriteCitiesRepository
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
    abstract fun bindsFavoriteCitiesRepository(repo: LocalFavoriteCitiesRepository): FavoriteCitiesRepository

}