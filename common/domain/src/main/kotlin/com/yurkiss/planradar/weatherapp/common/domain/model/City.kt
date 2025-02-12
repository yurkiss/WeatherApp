package com.yurkiss.planradar.weatherapp.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class City(val name: String, val country: String)