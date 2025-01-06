package com.yurkiss.planradar.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.yurkiss.planradar.weatherapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "room_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCitiesDao(appDatabase: AppDatabase) = appDatabase.favoriteCitiesDao()

    @Provides
    @Singleton
    fun provideCityWeatherDao(appDatabase: AppDatabase) = appDatabase.cityWeatherDao()

}