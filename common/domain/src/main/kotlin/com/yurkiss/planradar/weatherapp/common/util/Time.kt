package com.yurkiss.planradar.weatherapp.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val dateTimeFormater by lazy {
    LocalDateTime.Format {
        dayOfMonth()
        char('-')
        monthNumber()
        char('-')
        year()
        chars(" - ")
        hour()
        char(':')
        minute()
    }
}

fun formatToLocalDateTime(epoch: Long): String {
    val dateTime = Instant.fromEpochSeconds(epoch).toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTime.format(dateTimeFormater)
}
