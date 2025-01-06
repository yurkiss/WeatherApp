package com.yurkiss.planradar.weatherapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = CityWeatherEntity.TABLE_NAME,
    indices = [Index(
        value = [
            CityWeatherEntity.COLUMN_NAME,
            CityWeatherEntity.COLUMN_COUNTRY,
            CityWeatherEntity.COLUMN_DATE_TIME
        ],
        unique = true
    )]
)
data class CityWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_COUNTRY)
    val country: String,

    @ColumnInfo(name = COLUMN_TEMPERATURE)
    val temperature: Double,

    @ColumnInfo(name = COLUMN_HUMIDITY)
    val humidity: Int,

    @ColumnInfo(name = COLUMN_WIND_SPEED)
    val windSpeed: Double,

    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String,

    @ColumnInfo(name = COLUMN_DATE_TIME)
    val dateTime: Long,

    @ColumnInfo(name = COLUMN_ICON)
    val icon: String

) {
    companion object {
        const val TABLE_NAME = "city_weather_requests"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_TEMPERATURE = "temperature"
        const val COLUMN_HUMIDITY = "humidity"
        const val COLUMN_WIND_SPEED = "wind_speed"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE_TIME = "date_time"
        const val COLUMN_ICON = "icon"
    }
}