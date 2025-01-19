package com.yurkiss.planradar.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yurkiss.planradar.weatherapp.data.database.entity.FavoriteCityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Dao
interface FavoriteCitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteCityEntity): Long

    @Query("SELECT count(*) FROM ${FavoriteCityEntity.TABLE_NAME}")
    suspend fun count(): Int

    @Query("SELECT * FROM ${FavoriteCityEntity.TABLE_NAME}")
    fun observerAll(): Flow<List<FavoriteCityEntity>>

    @Query("SELECT * FROM ${FavoriteCityEntity.TABLE_NAME} WHERE id = :id")
    suspend fun query(id: Long): FavoriteCityEntity?
}

class ErrorFavoriteCitiesDao @Inject constructor() : FavoriteCitiesDao {
    override suspend fun insert(item: FavoriteCityEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun count(): Int {
        TODO("Not yet implemented")
    }

    override fun observerAll(): Flow<List<FavoriteCityEntity>> {
        return flow {
            TODO("Not yet implemented")
        }
    }

    override suspend fun query(id: Long): FavoriteCityEntity? {
        TODO("Not yet implemented")
    }

}