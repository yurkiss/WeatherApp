package com.yurkiss.planradar.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurkiss.planradar.weatherapp.data.database.dao.CityWeatherDao
import com.yurkiss.planradar.weatherapp.data.database.dao.FavoriteCitiesDao
import com.yurkiss.planradar.weatherapp.data.database.entity.CityWeatherEntity
import com.yurkiss.planradar.weatherapp.data.database.entity.FavoriteCityEntity

@Database(entities = [FavoriteCityEntity::class, CityWeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCitiesDao(): FavoriteCitiesDao
    abstract fun cityWeatherDao(): CityWeatherDao
}