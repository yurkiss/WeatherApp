@file:OptIn(ExperimentalMaterial3Api::class)

package com.yurkiss.planradar.weatherapp

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yurkiss.planradar.weatherapp.cities.presentation.FavoriteCitiesRoute
import com.yurkiss.planradar.weatherapp.cities.presentation.FavoriteCitiesScreen
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.details.presentation.CityWeatherRoute
import com.yurkiss.planradar.weatherapp.details.presentation.CityWeatherScreen
import com.yurkiss.planradar.weatherapp.historical.presentation.HistoricalDataRoute
import com.yurkiss.planradar.weatherapp.historical.presentation.HistoricalDataScreen
import dagger.hilt.android.AndroidEntryPoint
import com.yurkiss.planradar.weatherapp.common.presentation.R as comR

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppTheme {
                WeatherApp()
            }
        }

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(getColor(comR.color.black_10)))

    }

}


@ExperimentalMaterial3Api
@Composable
fun WeatherApp() {
    WeatherAppTheme {
        val navController = rememberNavController()
        NavHost(
            navController,
            startDestination = FavoriteCitiesRoute,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<FavoriteCitiesRoute> {
                FavoriteCitiesScreen(
                    navigateToDetails = { navController.navigate(CityWeatherRoute(it.city, it.country)) },
                    navigateToWeatherHistory = { navController.navigate(HistoricalDataRoute(it.city, it.country)) },
                )
            }
            composable<CityWeatherRoute> {
                CityWeatherScreen(
                    navigateBack = { navController.navigateUp() },
                )
            }
            composable<HistoricalDataRoute> {
                HistoricalDataScreen(
                    navigateBack = { navController.navigateUp() },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        WeatherApp()
    }
}
