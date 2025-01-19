package com.yurkiss.planradar.weatherapp.cities.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.AddCityUseCase
import com.yurkiss.planradar.weatherapp.cities.domain.usecase.SearchCitiesUseCase
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCityMapper
import com.yurkiss.planradar.weatherapp.common.util.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


sealed interface SearchCitiesScreenState {
    data object Loading : SearchCitiesScreenState
    data object NoData : SearchCitiesScreenState
    data class Loaded(val cities: List<UiCity>) : SearchCitiesScreenState
    data class Error(val failure: Failure) : SearchCitiesScreenState
}

sealed interface SearchCitiesScreenEvents {
    data class ShowError(val failure: Failure) : SearchCitiesScreenEvents
    data object CityAdded : SearchCitiesScreenEvents
}

sealed interface SearchCitiesActions {
    data class SearchCity(val query: String) : SearchCitiesActions
    data class AddCity(val city: UiCity) : SearchCitiesActions
}

@HiltViewModel
class SearchCitiesViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val addCityUseCase: AddCityUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<SearchCitiesScreenState>(SearchCitiesScreenState.NoData)
    val state: StateFlow<SearchCitiesScreenState> = _state.asStateFlow()

    private val eventChannel = Channel<SearchCitiesScreenEvents>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    private val searchQuery = MutableStateFlow<String?>(null)
    private val cityMapper = UiCityMapper()

    private var searchJob: Job? = null

    init {
        Timber.d("SearchCitiesViewModel created")
        searchQuery.debounce(500)
            .filterNot { it.isNullOrBlank() }
            .onEach {
                Timber.d("Search query changed to $it")
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(100)
                    _state.update { SearchCitiesScreenState.Loading }
                    performSearch(this, it!!)
                }
            }
            .launchIn(viewModelScope)
    }

    fun submit(action: SearchCitiesActions) {
        Timber.d("CitySelectorViewModel submitted action $action")
        when (action) {
            is SearchCitiesActions.SearchCity -> searchQuery.update { action.query }
            is SearchCitiesActions.AddCity -> addCityUseCase(cityMapper(action.city)) { outcome ->
                outcome.fold({
                    Timber.e(it.exception)
                    eventChannel.trySend(SearchCitiesScreenEvents.ShowError(it))
                }, {
                    eventChannel.trySend(SearchCitiesScreenEvents.CityAdded)
                })
            }
        }
    }

    private fun performSearch(scope: CoroutineScope, query: String) =
        searchCitiesUseCase(query, scope) { outcome ->
            outcome.fold({ failure ->
                Timber.e(failure.exception)
                _state.update {
                    SearchCitiesScreenState.Error(failure)
                }
            }, { data ->
                _state.update {
                    SearchCitiesScreenState.Loaded(data.map(cityMapper::invoke))
                }
            })
        }

}

