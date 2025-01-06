package com.yurkiss.planradar.weatherapp.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.cities.domain.model.City
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.ObserverFavoriteCitiesUseCase
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.common.util.Failure
import com.yurkiss.planradar.weatherapp.common.util.formatToLocalDateTime
import com.yurkiss.planradar.weatherapp.details.domain.GetCityWeatherUseCase
import com.yurkiss.planradar.weatherapp.historical.domain.GetHistoricalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

data class UiCityWeather(
    val id: Long,
    val name: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val dateTime: String,
    val icon: String
)

sealed interface CityWeatherScreenState {
    data object Loading : CityWeatherScreenState
    data object NoData : CityWeatherScreenState
    data class Loaded(val cities: UiCityWeather) : CityWeatherScreenState
    data class Error(val failure: Failure) : CityWeatherScreenState
}

sealed interface CityWeatherActions {
    data class RequestData(val city: City) : CityWeatherActions
}


@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<CityWeatherScreenState>(CityWeatherScreenState.NoData)
    val state: StateFlow<CityWeatherScreenState> = _state.asStateFlow()

    init {
        Timber.d("CitySelectorViewModel created")
    }

    fun submit(action: CityWeatherActions) {
        when (action) {
            is CityWeatherActions.RequestData -> {
                if (action.city.name.isBlank() or action.city.country.isBlank()) {
                    _state.update { CityWeatherScreenState.NoData }
                }
                _state.update { CityWeatherScreenState.Loading }
                getCityWeatherUseCase(action.city, viewModelScope) { outcome ->
                    outcome.fold({ failure ->
                        _state.update {
                            Timber.e(failure.exception)
                            CityWeatherScreenState.Error(failure)
                        }
                    }, { data ->
                        val uiCityWeather = UiCityWeather(
                            id = data.id,
                            name = data.name,
                            temperature = data.temperature,
                            humidity = data.humidity,
                            windSpeed = data.windSpeed,
                            description = data.description,
                            dateTime = formatToLocalDateTime(data.dateTime),
                            icon = data.icon
                        )
                        _state.update {
                            CityWeatherScreenState.Loaded(uiCityWeather)
                        }
                    })

                }
            }
        }
    }

}


