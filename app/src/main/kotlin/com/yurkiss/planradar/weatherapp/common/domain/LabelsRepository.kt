package com.yurkiss.planradar.weatherapp.common.domain

interface LabelsRepository {
    fun getLabel(label: Labels): String
}

enum class Labels {
    NoData, LoadingData, GeneralError, ErrorAddingCity
}