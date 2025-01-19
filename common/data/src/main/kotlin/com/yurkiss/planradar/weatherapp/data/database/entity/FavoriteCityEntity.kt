package com.yurkiss.planradar.weatherapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = FavoriteCityEntity.TABLE_NAME,
    indices = [Index(
        value = [FavoriteCityEntity.COLUMN_NAME, FavoriteCityEntity.COLUMN_COUNTRY],
        unique = true
    )]
)
data class FavoriteCityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_COUNTRY)
    val country: String,

) {
    companion object {
        const val TABLE_NAME = "favorite_cities"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
    }
}