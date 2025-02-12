package com.yurkiss.planradar.weatherapp.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.Failure
import com.yurkiss.planradar.weatherapp.common.util.formatToLocalDateTime
import com.yurkiss.planradar.weatherapp.details.domain.GetCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

data class UiCityWeather(
    val title: String,
    val id: Long,
    val name: String,
    val temperature: String,
    val humidity: String,
    val windSpeed: String,
    val description: String,
    val dateTime: String,
    val icon: String,
)

sealed interface CityWeatherScreenState {
    data object Loading : CityWeatherScreenState
    data object NoData : CityWeatherScreenState
    data class Loaded(val cityWeather: UiCityWeather) : CityWeatherScreenState
    data class Error(val failure: Failure) : CityWeatherScreenState
}

sealed interface CityWeatherActions {
    data class RequestData(val city: City) : CityWeatherActions
}


@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<CityWeatherScreenState>(CityWeatherScreenState.NoData)
    val state: StateFlow<CityWeatherScreenState> = _state.asStateFlow()

    private val city = savedStateHandle.toRoute<CityWeatherRoute>()

    init {
        Timber.d("CitySelectorViewModel created")
        submit(CityWeatherActions.RequestData(City(city.city, city.country)))
    }

    fun submit(action: CityWeatherActions) {
        when (action) {
            is CityWeatherActions.RequestData -> {
                if (action.city.name.isBlank() or action.city.country.isBlank()) {
                    _state.update { CityWeatherScreenState.NoData }
                }
                _state.update { CityWeatherScreenState.Loading }
                getCityWeatherUseCase(action.city, viewModelScope) { outcome ->
                    outcome.fold(
                        { failure ->
                            _state.update {
                                Timber.e(failure.exception)
                                CityWeatherScreenState.Error(failure)
                            }
                        },
                        { data ->
                            val uiCityWeather = UiCityWeather(
                                title = action.city.name,
                                id = data.id,
                                name = data.name,
                                temperature = "${data.temperature.toInt()} \u2103",
                                humidity = "${data.humidity} %",
                                windSpeed = "${data.windSpeed} km/h",
                                description = data.description,
                                dateTime = formatToLocalDateTime(data.dateTime),
                                icon = "https://openweathermap.org/img/w/${data.icon}.png",
                            )
                            _state.update {
                                CityWeatherScreenState.Loaded(uiCityWeather)
                            }
                        },
                    )

                }
            }
        }
    }

}


