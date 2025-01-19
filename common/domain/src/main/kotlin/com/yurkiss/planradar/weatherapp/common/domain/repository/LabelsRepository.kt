package com.yurkiss.planradar.weatherapp.common.domain.repository

interface LabelsRepository {
    fun getLabel(label: Labels): String
}

enum class Labels {
    NoData, LoadingData, GeneralError, ErrorAddingCity
}