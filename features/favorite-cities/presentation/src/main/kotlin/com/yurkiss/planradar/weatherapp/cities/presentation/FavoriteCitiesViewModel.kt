package com.yurkiss.planradar.weatherapp.cities.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.ObserveFavoriteCitiesUseCase
import com.yurkiss.planradar.weatherapp.common.domain.model.City
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

data class UiCity(val city: String, val title: String, val country: String)

sealed interface FavoriteCitiesScreenState {
    data object Loading : FavoriteCitiesScreenState
    data object NoData : FavoriteCitiesScreenState
    data class Loaded(val cities: List<UiCity>) : FavoriteCitiesScreenState
    data class Error(val failure: Failure) : FavoriteCitiesScreenState
}

sealed interface FavoriteCitiesActions

@HiltViewModel
class FavoriteCitiesViewModel @Inject constructor(
    favoriteCitiesUseCase: ObserveFavoriteCitiesUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<FavoriteCitiesScreenState>(FavoriteCitiesScreenState.NoData)
    val state: StateFlow<FavoriteCitiesScreenState> = _state.asStateFlow()

    init {
        Timber.d("CitySelectorViewModel created")
        val mapper = uiCityMapper()

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

internal fun uiCityMapper() = BiDiMapping<City, UiCity>(
    asOut = { UiCity(city = it.name, title = "${it.name}, ${it.country}", country = it.country) },
    asIn = { City(name = it.title, country = it.country) },
)
