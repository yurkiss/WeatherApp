package com.yurkiss.planradar.weatherapp.historical.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.util.Failure
import com.yurkiss.planradar.weatherapp.historical.domain.GetHistoricalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class UiHistoricalItem(
    val dateTime: String,
    val description: String,
    val temperature: Double,
    val weatherIcon: String
)

sealed interface HistoricalDataScreenState {
    data object Loading : HistoricalDataScreenState
    data object NoData : HistoricalDataScreenState
    data class Loaded(val cities: List<UiHistoricalItem>) : HistoricalDataScreenState
    data class Error(val failure: Failure) : HistoricalDataScreenState
}

sealed interface HistoricalDataActions {
    data class RequestData(val city: City) : HistoricalDataActions
}


@HiltViewModel
class HistoricalDataViewModel @Inject constructor(
    private val getHistoricalDataUseCase: GetHistoricalDataUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<HistoricalDataScreenState>(HistoricalDataScreenState.NoData)
    val state: StateFlow<HistoricalDataScreenState> = _state.asStateFlow()

    private var loadJob: Job? = null

    init {
        Timber.d("CitySelectorViewModel created")
    }

    fun submit(action: HistoricalDataActions) {
        when (action) {
            is HistoricalDataActions.RequestData -> {
                if (action.city.name.isBlank() or action.city.country.isBlank()) {
                    _state.update { HistoricalDataScreenState.NoData }
                }

                loadJob?.cancel()
                loadJob = viewModelScope.launch {
                    loadData(action.city, this)
                }
            }
        }
    }

    private fun loadData(city: City, scope: CoroutineScope) {
        getHistoricalDataUseCase(city, scope) { flow ->
            flow.onEach { outcome ->
                outcome.fold({ failure ->
                    _state.update {
                        Timber.e(failure.exception)
                        HistoricalDataScreenState.Error(failure)
                    }
                }, { data ->
                    _state.update {
                        HistoricalDataScreenState.Loaded(data.map {
                            UiHistoricalItem(
                                dateTime = it.dateTime,
                                description = it.description,
                                temperature = it.temperature,
                                weatherIcon = it.weatherIcon
                            )
                        })
                    }
                })
            }.onStart {
                _state.update { HistoricalDataScreenState.Loading }
            }.launchIn(scope)
        }
    }
}


