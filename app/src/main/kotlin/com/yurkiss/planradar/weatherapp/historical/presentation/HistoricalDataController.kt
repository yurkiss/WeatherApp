package com.yurkiss.planradar.weatherapp.historical.presentation

import com.airbnb.epoxy.EpoxyController
import com.yurkiss.planradar.weatherapp.common.domain.Labels
import com.yurkiss.planradar.weatherapp.common.domain.LabelsRepository
import com.yurkiss.planradar.weatherapp.common.loadingHolder
import com.yurkiss.planradar.weatherapp.common.messageHolder
import javax.inject.Inject
import kotlin.properties.Delegates.observable

class HistoricalDataController @Inject constructor(
    private val labelRepository: LabelsRepository
) : EpoxyController() {

    var viewState: HistoricalDataScreenState by observable(
        HistoricalDataScreenState.NoData
    ) { _, _, _ -> requestModelBuild() }

    override fun buildModels() {
        viewState.let { state ->
            when (state) {
                is HistoricalDataScreenState.Loading -> loadingHolder(labelRepository.getLabel(Labels.LoadingData))
                is HistoricalDataScreenState.NoData -> messageHolder(labelRepository.getLabel(Labels.NoData))
                is HistoricalDataScreenState.Loaded -> state.cities.forEach(::buildLoaded)
                is HistoricalDataScreenState.Error -> {
                    val message = labelRepository.getLabel(Labels.GeneralError)
                    //TODO: handle different error types
                    messageHolder(message)
                }
            }
        }
    }

    private fun buildLoaded(item: UiHistoricalItem) {
        historicalDataItemHolder {
            //TODO: check uniqueness of dateTime
            id(item.dateTime)
            dateTime(item.dateTime)
            description("${item.description}, ${item.temperature.toInt()} \u2103")
            weatherIcon(item.weatherIcon)
        }
    }

    fun clear() {
        cancelPendingModelBuild()
    }
}