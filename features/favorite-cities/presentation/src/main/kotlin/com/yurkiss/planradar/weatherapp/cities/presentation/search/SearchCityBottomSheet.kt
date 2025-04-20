package com.yurkiss.planradar.weatherapp.cities.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yurkiss.planradar.weatherapp.cities.presentation.CityItem
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.common.presentation.ErrorContent
import com.yurkiss.planradar.weatherapp.common.presentation.NoDataContent
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.common.util.NetworkException
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.R
import timber.log.Timber

@Composable
internal fun SearchCityBottomSheet(
    onDismiss: () -> Unit,
    displaySnackbar: suspend (String) -> Unit = {},
) {
    val viewModel: SearchCitiesViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()
    val eventsState by viewModel.eventsState.collectAsStateWithLifecycle()
    SearchCityBottomSheetContent(
        state = state,
        searchQuery = searchQuery,
        onQueryChanged = { viewModel.submit(SearchCitiesActions.SearchCity(it)) },
        onCityClicked = { city -> viewModel.submit(SearchCitiesActions.AddCity(city)) },
    )
    LaunchedEffect(eventsState) {
        when (val ev = eventsState) {
            is SearchCitiesScreenEventsState.CityAdded -> onDismiss()
            is SearchCitiesScreenEventsState.ShowError -> displaySnackbar(ev.failure.message)
            SearchCitiesScreenEventsState.Empty -> {}
        }
        viewModel.submit(SearchCitiesActions.EventsSateConsumed)
    }
}


@Composable
private fun SearchCityBottomSheetContent(
    state: State<SearchCitiesScreenState>,
    searchQuery: State<String>,
    onCityClicked: (UiCity) -> Unit,
    onQueryChanged: (String) -> Unit,
) {

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SearchCityTextField(
            modifier = Modifier.focusRequester(focusRequester),
            searchQuery = searchQuery,
            onQueryChanged = onQueryChanged,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray),
        )

        SearchResultsContent(state, onCityClicked)

    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun ColumnScope.SearchResultsContent(
    state: State<SearchCitiesScreenState>,
    onCityClicked: (UiCity) -> Unit,
) {

    val topCenter = Modifier
        .align(Alignment.CenterHorizontally)
        .padding(top = 16.dp)

    when (val searchResult = state.value) {
        is SearchCitiesScreenState.Error -> ErrorContent(topCenter)
        is SearchCitiesScreenState.Loaded -> SearchCityResultContent(searchResult, onCityClicked)
        is SearchCitiesScreenState.Loading -> CircularProgressIndicator(topCenter)
        is SearchCitiesScreenState.NoData -> NoDataContent(topCenter)
    }

}


@Composable
private fun SearchCityTextField(
    modifier: Modifier = Modifier,
    searchQuery: State<String>,
    onQueryChanged: (String) -> Unit,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

//    val textFieldState = rememberTextFieldState()

//    BasicTextField(textFieldState)
//    textFieldState.setTextAndPlaceCursorAtEnd()

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onQueryChanged(it.text)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        placeholder = { Text(stringResource(R.string.lbl_search_for_cities)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.content_description_search_24px),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
        ),
        singleLine = true,
    )

    LaunchedEffect(Unit) {
        Timber.d("SearchCityTextField: LaunchedEffect(Unit), searchQuery=$searchQuery")
        textFieldValue = textFieldValue.copy(
            text = searchQuery.value,
            selection = TextRange(searchQuery.value.length),
        )
    }

}


@Composable
private fun SearchCityResultContent(
    state: SearchCitiesScreenState.Loaded,
    onCityClicked: (UiCity) -> Unit,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
    ) {
        items(state.cities.size) {
            CityItem(
                city = state.cities[it],
                onItemClick = onCityClicked,
                withInfoIcon = false,
            )
        }
    }
}

class SearchCityBottomSheetPreviewParameterProvider : PreviewParameterProvider<SearchCitiesScreenState> {
    override val values = sequenceOf(
        SearchCitiesScreenState.Loaded(
            listOf(
                UiCity("Paris", "Paris", "France"),
                UiCity("London", "London", "UK"),
                UiCity("Berlin", "Berlin", "Germany"),
            ),
        ),
        SearchCitiesScreenState.Error(NetworkException("", null)),
        SearchCitiesScreenState.Loading,
        SearchCitiesScreenState.NoData,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchCityBottomSheetPreview(
    @PreviewParameter(SearchCityBottomSheetPreviewParameterProvider::class) state: SearchCitiesScreenState,
) {
    WeatherAppTheme {
        SearchCityBottomSheetContent(
            state = remember { mutableStateOf(state) },
            searchQuery = remember { mutableStateOf("par") },
            onCityClicked = {},
            onQueryChanged = {},
        )
    }
}
