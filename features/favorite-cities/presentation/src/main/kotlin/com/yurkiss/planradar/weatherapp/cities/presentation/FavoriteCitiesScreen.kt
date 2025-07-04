@file:OptIn(ExperimentalMaterial3Api::class)

package com.yurkiss.planradar.weatherapp.cities.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yurkiss.planradar.weatherapp.cities.presentation.search.SearchCityBottomSheet
import com.yurkiss.planradar.weatherapp.common.presentation.ErrorContent
import com.yurkiss.planradar.weatherapp.common.presentation.NoDataContent
import com.yurkiss.planradar.weatherapp.common.presentation.ScaffoldScreen
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.common.util.NetworkException
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.R
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR

@Composable
fun FavoriteCitiesScreen(
    onItemClick: (UiCity) -> Unit,
    onInfoClick: (UiCity) -> Unit,
) {
    val viewModel: FavoriteCitiesViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavoriteCitiesScreenContent(state, onItemClick, onInfoClick)
}

@Composable
internal fun FavoriteCitiesScreenContent(
    state: FavoriteCitiesScreenState,
    onItemClick: (UiCity) -> Unit,
    onInfoClick: (UiCity) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    ScaffoldScreen(
        title = stringResource(R.string.favorite_cities_fragment_label),
        onBack = null,
        floatingActionButton = {
            AddFavoriteCityButton(onClick = { showBottomSheet = true })
        },
        snackbarHostState = snackbarHostState,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {

            val topCenter = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)

            when (state) {
                is FavoriteCitiesScreenState.Error -> ErrorContent(topCenter)
                is FavoriteCitiesScreenState.Loaded -> FavoriteCitiesContent(
                    cities = { state.cities },
                    onItemClick = onItemClick,
                    onInfoClick = onInfoClick,
                )

                FavoriteCitiesScreenState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                FavoriteCitiesScreenState.NoData -> NoDataContent(topCenter)
            }

            if (showBottomSheet) {
                AddCityBottomSheet(onDismiss = { showBottomSheet = false })
            }
        }
    }
}

@Composable
fun AddCityBottomSheet(onDismiss: () -> Unit = {}) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    ModalBottomSheet(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxHeight(),
        sheetState = sheetState,
        shape = RectangleShape,
        dragHandle = {},
        onDismissRequest = onDismiss,
    ) {
        SearchCityBottomSheet(onDismiss = onDismiss)
    }

}


@Composable
internal fun AddFavoriteCityButton(onClick: () -> Unit = {}) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.lbl_add_city)) },
        text = { Text(text = stringResource(id = R.string.lbl_add_city)) },
    )
}


@Composable
internal fun FavoriteCitiesContent(
    cities: () -> List<UiCity>,
    onItemClick: (UiCity) -> Unit = {},
    onInfoClick: (UiCity) -> Unit = {},
) {

    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        val list = cities()
        items(list.size, key = { list[it].title }) {
            CityItem(
                city = list[it],
                onItemClick = onItemClick,
                onInfoClick = onInfoClick,
            )
        }
    }
}

@Composable
internal fun CityItem(
    city: UiCity,
    withInfoIcon: Boolean = true,
    onItemClick: (UiCity) -> Unit = {},
    onInfoClick: (UiCity) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable { onItemClick(city) }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = comR.drawable.location_city_24px),
            contentDescription = stringResource(id = comR.string.city_icon_description),
            modifier = Modifier.size(24.dp),
        )

        Spacer(Modifier.size(32.dp))

        Text(
            text = city.title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
        )

        if (withInfoIcon) {
            Image(
                painter = painterResource(id = comR.drawable.info_24px),
                contentDescription = stringResource(id = comR.string.info_icon_description),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onInfoClick(city) },
            )
        }
    }
}


class FavoriteCitiesScreenPreviewParameterProvider : PreviewParameterProvider<FavoriteCitiesScreenState> {
    override val values = sequenceOf(
        FavoriteCitiesScreenState.Loaded(
            listOf(
                UiCity("Paris_France", "Paris", "France"),
                UiCity("London_UK", "London", "UK"),
                UiCity("Berlin_Germany", "Berlin", "Germany"),
            ),
        ),
        FavoriteCitiesScreenState.Error(NetworkException("", null)),
        FavoriteCitiesScreenState.Loading,
        FavoriteCitiesScreenState.NoData,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoriteCitiesScreenPreview(
    @PreviewParameter(FavoriteCitiesScreenPreviewParameterProvider::class) state: FavoriteCitiesScreenState,
) {
    WeatherAppTheme {
        FavoriteCitiesScreenContent(
            state = state,
            onItemClick = {},
            onInfoClick = {},
        )
    }
}

