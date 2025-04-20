@file:OptIn(ExperimentalMaterial3Api::class)

package com.yurkiss.planradar.weatherapp

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoaded.value.not()
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(getColor(comR.color.black)),
        )

        setContent {
            WeatherAppTheme {
                WeatherApp()
            }
        }

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
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
        ) {
            composable<FavoriteCitiesRoute> {
                FavoriteCitiesScreen(
                    onItemClick = { navController.navigate(CityWeatherRoute(it.city, it.country)) },
                    onInfoClick = { navController.navigate(HistoricalDataRoute(it.city, it.country)) },
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
