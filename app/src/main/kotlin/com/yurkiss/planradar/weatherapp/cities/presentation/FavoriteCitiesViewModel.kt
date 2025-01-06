package com.yurkiss.planradar.weatherapp.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.ObserverFavoriteCitiesUseCase
import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.BiDiMapping
import com.yurkiss.planradar.weatherapp.common.util.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

data class UiCity(val id: String, val title: String, val country: String)

sealed interface FavoriteCitiesScreenState {
    data object Loading : FavoriteCitiesScreenState
    data object NoData : FavoriteCitiesScreenState
    data class Loaded(val cities: List<UiCity>) : FavoriteCitiesScreenState
    data class Error(val failure: Failure) : FavoriteCitiesScreenState
}

sealed interface FavoriteCitiesActions {
    data class OpenWeatherDetails(val city: UiCity) : FavoriteCitiesActions
    data class OpenHistoricalData(val city: UiCity) : FavoriteCitiesActions
}

@HiltViewModel
class FavoriteCitiesViewModel @Inject constructor(
    favoriteCitiesUseCase: ObserverFavoriteCitiesUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<FavoriteCitiesScreenState>(FavoriteCitiesScreenState.NoData)
    val state: StateFlow<FavoriteCitiesScreenState> = _state.asStateFlow()

    init {
        Timber.d("CitySelectorViewModel created")
        val mapper = UiCityMapper()

        favoriteCitiesUseCase { flow ->
            flow.onEach { outcome ->
                outcome.fold({ failure ->
                    _state.update {
                        Timber.e(failure.exception)
                        FavoriteCitiesScreenState.Error(failure)
                    }
                }, { data ->
                    _state.update {
                        FavoriteCitiesScreenState.Loaded(data.map(mapper::invoke))
                    }
                })
            }.onStart {
                _state.update { FavoriteCitiesScreenState.Loading }
            }.launchIn(viewModelScope)
        }

    }

    fun submit(action: FavoriteCitiesActions) {
        Timber.d("CitySelectorViewModel submitted action $action")
    }
}

internal fun UiCityMapper() = BiDiMapping<City, UiCity>(
    asOut = { UiCity(id = it.name, title = it.name, country = it.country) },
    asIn = { City(name = it.title, country = it.country) }
)
