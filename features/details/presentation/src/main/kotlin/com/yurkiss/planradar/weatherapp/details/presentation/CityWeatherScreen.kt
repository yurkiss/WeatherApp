package com.yurkiss.planradar.weatherapp.details.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.yurkiss.planradar.weatherapp.common.presentation.ErrorContent
import com.yurkiss.planradar.weatherapp.common.presentation.NoDataContent
import com.yurkiss.planradar.weatherapp.common.presentation.ScaffoldScreen
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.common.util.NetworkException
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR


@Composable
fun CityWeatherScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: CityWeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    CityWeatherScreenContent(state, navigateBack)
//    LaunchedEffect(Unit) {
//        viewModel.submit(CityWeatherActions.RequestData(City(city, country)))
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherScreenContent(
    state: CityWeatherScreenState,
    onBack: () -> Unit,
) {

    ScaffoldScreen("", onBack) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .zIndex(10f),
        ) {

            val mod: ColumnScope.() -> Modifier = {
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            }

            when (state) {
                is CityWeatherScreenState.Error -> CityWeatherCardContent {
                    ErrorContent(
                        mod(),
                    )
                }

                is CityWeatherScreenState.Loaded -> {
                    CityWeatherCardContent {
                        val weather = state.cityWeather
                        WeatherContent(
                            weather.title,
                            weather.description,
                            weather.temperature,
                            weather.humidity,
                            weather.windSpeed,
                            weather.icon,
                        )
                    }
                    Footer(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        footerText = stringResource(
                            comR.string.lbl_footer,
                            state.cityWeather.name,
                            state.cityWeather.dateTime,
                        ),
                    )
                }

                CityWeatherScreenState.Loading -> CityWeatherCardContent { LoadingContent() }
                CityWeatherScreenState.NoData -> CityWeatherCardContent { NoDataContent(mod()) }
            }

        }
    }

}

@Composable
internal fun CityWeatherCardContent(
    content: @Composable ColumnScope.() -> Unit,
) {
    // Weather Card
    Card(
        elevation = CardDefaults.cardElevation(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f)
            .offset(y = (-50).dp)
            .padding(horizontal = 32.dp),
    ) {
        content()
    }
}


@Composable
internal fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(id = comR.string.lbl_loading), // Use your string resource
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
internal fun WeatherContent(
    cityName: String,
    description: String,
    temperature: String,
    humidity: String,
    windspeed: String,
    iconUrl: String,
) {
    Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 26.dp)) {

        // City Name
        Text(
            cityName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
        )

        // Weather Icon
        Image(
            painter = rememberAsyncImagePainter(model = iconUrl),
            contentDescription = stringResource(id = comR.string.city_icon_description),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop, // or .Fit
        )


        // Description
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.lbl_description),
                fontSize = 14.sp,
            )
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.Blue,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
            )
        }

        // Temperature
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.lbl_temperature),
                fontSize = 14.sp,
            )
            Text(
                text = temperature,
                fontSize = 16.sp,
                color = Color.Blue,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
            )
        }

        // Humidity
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.lbl_humidity),
                fontSize = 14.sp,
            )
            Text(
                text = humidity,
                fontSize = 16.sp,
                color = Color.Blue,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
            )
        }

        // Windspeed
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.lbl_windspeed),
                fontSize = 14.sp,
            )
            Text(
                text = windspeed,
                fontSize = 16.sp,
                color = Color.Blue,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
            )
        }
    }
}


@Composable
fun Footer(footerText: String, modifier: Modifier) {
    Text(
        text = footerText,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),

        )
}


class CityWeatherScreenPreviewParameterProvider : PreviewParameterProvider<CityWeatherScreenState> {
    override val values = sequenceOf(
        CityWeatherScreenState.Loaded(
            UiCityWeather(
                title = "London",
                description = "Cloudy",
                temperature = "15°C",
                humidity = "60%",
                windSpeed = "10 km/h",
                icon = "https://openweathermap.org/img/w/02d.png",
                dateTime = "2023-09-15 12:00",
                id = 1,
                name = "London",
            ),
        ),
        CityWeatherScreenState.Error(NetworkException("", null)),
        CityWeatherScreenState.Loading,
        CityWeatherScreenState.NoData,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview(
    @PreviewParameter(CityWeatherScreenPreviewParameterProvider::class) state: CityWeatherScreenState,
) {
    WeatherAppTheme {
        CityWeatherScreenContent(state) {}
    }
}

