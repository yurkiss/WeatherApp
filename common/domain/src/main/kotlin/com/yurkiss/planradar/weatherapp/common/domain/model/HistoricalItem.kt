package com.yurkiss.planradar.weatherapp.common.domain.model

data class HistoricalItem(
    val id: Long,
    val dateTime: String,
    val description: String,
    val temperature: Double,
    val weatherIcon: String
)