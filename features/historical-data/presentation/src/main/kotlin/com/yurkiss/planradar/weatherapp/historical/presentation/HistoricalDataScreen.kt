@file:OptIn(ExperimentalMaterial3Api::class)

package com.yurkiss.planradar.weatherapp.historical.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.yurkiss.planradar.weatherapp.common.presentation.ErrorContent
import com.yurkiss.planradar.weatherapp.common.presentation.NoDataContent
import com.yurkiss.planradar.weatherapp.common.presentation.ScaffoldScreen
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.common.util.NetworkException
import com.yurkiss.planradar.weatherapp.historical_data.presentation.R

@Composable
fun HistoricalDataScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: HistoricalDataViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    HistoricalDataScreenContent(state, navigateBack)
}

@Composable
internal fun HistoricalDataScreenContent(state: HistoricalDataScreenState, navigateBack: () -> Unit) {

    ScaffoldScreen(
        title = stringResource(R.string.historical_data_label, state.title),
        onBack = navigateBack,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
        ) {

            val topCenter = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)

            when (val res = state.loadingResult) {
                is HistoricalDataLoadingResult.Error -> ErrorContent(topCenter)
                is HistoricalDataLoadingResult.Loaded -> HistoricalDataContent(
                    records = { res.historicalRecords },
                )

                HistoricalDataLoadingResult.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                HistoricalDataLoadingResult.NoData -> NoDataContent(topCenter)
            }
        }
    }

}

@Composable
internal fun HistoricalDataContent(records: () -> List<UiHistoricalItem>) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        val list = records()
        items(list.size) {
            HistoricalItem(list[it].dateTime, list[it].description, list[it].weatherIcon)
        }
    }
}

@Composable
internal fun HistoricalItem(
    dateTime: String,
    weatherDescription: String,
    weatherIcon: String,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),

        ) {

        Image(
            painter = rememberAsyncImagePainter(model = weatherIcon),
            contentDescription = "City Icon",
            modifier = Modifier.size(48.dp),
        )

        Spacer(Modifier.size(32.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = dateTime,
                fontSize = 12.sp,
            )
            Text(
                text = weatherDescription,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
        }
    }

}

internal class HistoricalDataScreenPreviewParameterProvider : PreviewParameterProvider<HistoricalDataScreenState> {
    override val values = sequenceOf(
        HistoricalDataScreenState(
            "Paris",
            HistoricalDataLoadingResult.Loaded(
                listOf(
                    UiHistoricalItem("2024-09-15 12:00", "Cloudy", 32.0, "https://openweathermap.org/img/w/02d.png"),
                    UiHistoricalItem("2024-09-16 10:00", "Cloudy", 28.0, "https://openweathermap.org/img/w/01d.png"),
                ),
            ),
        ),
        HistoricalDataScreenState("Paris", HistoricalDataLoadingResult.NoData),
        HistoricalDataScreenState("Paris", HistoricalDataLoadingResult.Loading),
        HistoricalDataScreenState("Paris", HistoricalDataLoadingResult.Error(NetworkException("", null))),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun HistoricalDataScreenPreview(
    @PreviewParameter(HistoricalDataScreenPreviewParameterProvider::class) state: HistoricalDataScreenState,
) {
    WeatherAppTheme {
        HistoricalDataScreenContent(
            state = state,
            {},
        )
    }
}