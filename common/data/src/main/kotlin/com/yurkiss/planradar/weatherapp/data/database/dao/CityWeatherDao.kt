package com.yurkiss.planradar.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yurkiss.planradar.weatherapp.data.database.entity.CityWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CityWeatherEntity): Long

    @Query("SELECT count(*) FROM ${CityWeatherEntity.TABLE_NAME}")
    suspend fun count(): Int

    @Query("SELECT * FROM ${CityWeatherEntity.TABLE_NAME} " +
            "WHERE ${CityWeatherEntity.COLUMN_NAME} = :name AND ${CityWeatherEntity.COLUMN_COUNTRY} = :country " +
            "ORDER BY ${CityWeatherEntity.COLUMN_DATE_TIME} DESC"
    )
    fun observe(name: String, country: String): Flow<List<CityWeatherEntity>>

    @Query(
        "SELECT * FROM ${CityWeatherEntity.TABLE_NAME} " +
                "WHERE ${CityWeatherEntity.COLUMN_NAME} = :name AND ${CityWeatherEntity.COLUMN_COUNTRY} = :country " +
                "ORDER BY ${CityWeatherEntity.COLUMN_DATE_TIME} DESC LIMIT 1"
    )
    suspend fun queryLast(name: String, country: String): CityWeatherEntity?
}
