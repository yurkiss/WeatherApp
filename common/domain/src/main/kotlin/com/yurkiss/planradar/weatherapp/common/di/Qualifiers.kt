package com.yurkiss.planradar.weatherapp.common.di

import javax.inject.Qualifier

/**
 * A qualifier annotation used to distinguish the API keys.
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class OpenWeatherApiKey