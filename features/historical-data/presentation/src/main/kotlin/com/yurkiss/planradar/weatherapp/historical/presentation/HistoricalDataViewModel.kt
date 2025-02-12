package com.yurkiss.planradar.weatherapp.historical.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yurkiss.planradar.weatherapp.common.domain.model.City
import com.yurkiss.planradar.weatherapp.common.presentation.ANR_TIMEOUT
import com.yurkiss.planradar.weatherapp.common.util.Failure
import com.yurkiss.planradar.weatherapp.historical.domain.GetHistoricalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

data class UiHistoricalItem(
    val dateTime: String,
    val description: String,
    val temperature: Double,
    val weatherIcon: String,
)

internal data class HistoricalDataScreenState(
    val title: String,
    val loadingResult: HistoricalDataLoadingResult,
)

internal sealed interface HistoricalDataLoadingResult {
    data object Loading : HistoricalDataLoadingResult
    data object NoData : HistoricalDataLoadingResult
    data class Loaded(val historicalRecords: List<UiHistoricalItem>) : HistoricalDataLoadingResult
    data class Error(val failure: Failure) : HistoricalDataLoadingResult
}

@HiltViewModel
class HistoricalDataViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getHistoricalDataUseCase: GetHistoricalDataUseCase,
) : ViewModel() {

    internal val state: StateFlow<HistoricalDataScreenState>

    init {
        Timber.d("CitySelectorViewModel created")
        val route = savedStateHandle.toRoute<HistoricalDataRoute>()
        state = getHistoricalDataUseCase(City(route.city, route.country)).map { outcome ->
            outcome.fold(
                { failure ->
                    Timber.e(failure.exception)
                    HistoricalDataScreenState(route.city, loadingResult = HistoricalDataLoadingResult.Error(failure))
                },
                { data ->
                    HistoricalDataScreenState(
                        route.city,
                        HistoricalDataLoadingResult.Loaded(
                            data.map {
                                UiHistoricalItem(
                                    dateTime = it.dateTime,
                                    description = it.description,
                                    temperature = it.temperature,
                                    weatherIcon = "https://openweathermap.org/img/w/${it.weatherIcon}.png",
                                )
                            },
                        ),
                    )
                },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(ANR_TIMEOUT),
            initialValue = HistoricalDataScreenState(route.city, HistoricalDataLoadingResult.Loading),
        )
    }
}


