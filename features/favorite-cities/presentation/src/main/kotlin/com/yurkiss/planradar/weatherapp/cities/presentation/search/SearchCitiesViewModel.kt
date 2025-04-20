package com.yurkiss.planradar.weatherapp.cities.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.AddCityUseCase
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.SearchCitiesUseCase
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.cities.presentation.uiCityMapper
import com.yurkiss.planradar.weatherapp.common.util.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject


sealed interface SearchCitiesScreenState {
    data object Loading : SearchCitiesScreenState
    data object NoData : SearchCitiesScreenState
    data class Loaded(val cities: List<UiCity>) : SearchCitiesScreenState
    data class Error(val failure: Failure) : SearchCitiesScreenState
}

sealed interface SearchCitiesScreenEventsState {
    data class ShowError(val failure: Failure) : SearchCitiesScreenEventsState
    data object CityAdded : SearchCitiesScreenEventsState
    data object Empty : SearchCitiesScreenEventsState
}

sealed interface SearchCitiesActions {
    data class SearchCity(val query: String) : SearchCitiesActions
    data class AddCity(val city: UiCity) : SearchCitiesActions
    data object EventsSateConsumed : SearchCitiesActions

}

@HiltViewModel
class SearchCitiesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val addCityUseCase: AddCityUseCase,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    val state: StateFlow<SearchCitiesScreenState> = searchQuery.debounce(500).flatMapLatest { query ->
        if (query.trim().length < SEARCH_QUERY_MIN_LENGTH) {
            flowOf(SearchCitiesScreenState.NoData)
        } else {
            searchCitiesUseCase(query).map { outcome ->
                outcome.fold(
                    { failure ->
                        Timber.e(failure.exception)
                        SearchCitiesScreenState.Error(failure)
                    },
                    { data ->
                        if (data.isEmpty()) {
                            SearchCitiesScreenState.NoData
                        } else {
                            SearchCitiesScreenState.Loaded(data.map(cityMapper::invoke))
                        }
                    },
                )
            }.onStart { emit(SearchCitiesScreenState.Loading) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchCitiesScreenState.Loading,
    )

    private val eventChannel = Channel<SearchCitiesScreenEventsState>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    private val _eventsState = MutableStateFlow<SearchCitiesScreenEventsState>(SearchCitiesScreenEventsState.Empty)
    val eventsState = _eventsState.asStateFlow()

    private val cityMapper = uiCityMapper()

    init {
        Timber.d("SearchCitiesViewModel created")
    }

    fun submit(action: SearchCitiesActions) {
        Timber.d("CitySelectorViewModel submitted action $action")
        when (action) {
            is SearchCitiesActions.SearchCity -> {
                savedStateHandle[SEARCH_QUERY] = action.query
            }

            is SearchCitiesActions.EventsSateConsumed -> {
                _eventsState.update { SearchCitiesScreenEventsState.Empty }
            }

            is SearchCitiesActions.AddCity -> addCityUseCase(cityMapper(action.city)) { outcome ->
                outcome.fold(
                    { failure ->
                        Timber.e(failure.exception)
                        _eventsState.update { SearchCitiesScreenEventsState.ShowError(failure) }
                    },
                    {
                        _eventsState.update { SearchCitiesScreenEventsState.CityAdded }
                        savedStateHandle[SEARCH_QUERY] = ""
                    },
                )
            }
        }
    }

}

/** Minimum length where search query is considered as [SearchCitiesScreenState.NoData] */
private const val SEARCH_QUERY_MIN_LENGTH = 3

private const val SEARCH_QUERY = "searchQuery"

