package com.yurkiss.planradar.weatherapp.cities.data

import com.yurkiss.planradar.weatherapp.cities.domain.repository.FavoriteCitiesRepository
import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.DatabaseException
import com.yurkiss.planradar.weatherapp.data.database.dao.FavoriteCitiesDao
import com.yurkiss.planradar.weatherapp.common.util.Outcome
import com.yurkiss.planradar.weatherapp.common.util.map
import com.yurkiss.planradar.weatherapp.common.util.runCatching
import com.yurkiss.planradar.weatherapp.common.util.toLeft
import com.yurkiss.planradar.weatherapp.common.util.toRight
import com.yurkiss.planradar.weatherapp.data.database.entity.FavoriteCityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFavoriteCitiesRepository @Inject constructor(
    private val favoriteCitiesDao: FavoriteCitiesDao
) : FavoriteCitiesRepository {

    override fun getAll(): List<City> = listOf()

    override fun observeAll(): Flow<Outcome<List<City>>> {
        return favoriteCitiesDao.observerAll()
            .map { list ->
                list.map { City(it.name, it.country) }.toRight() as Outcome<List<City>>
            }
            .catch {
                emit(DatabaseException(it.message ?: "General database exception", it).toLeft())
            }
    }

    override suspend fun put(city: City): Outcome<City> {
        return runCatching({
            DatabaseException(it.message ?: "Failed to add City to database", it)
        }) {
            favoriteCitiesDao.insert(FavoriteCityEntity(name = city.name, country = city.country))
        }.map { city }
    }
}