package com.yurkiss.planradar.weatherapp.cities.presentation.search

import com.airbnb.epoxy.EpoxyController
import com.yurkiss.planradar.weatherapp.cities.presentation.UiCity
import com.yurkiss.planradar.weatherapp.cities.presentation.cityItemHolder
import com.yurkiss.planradar.weatherapp.common.domain.Labels
import com.yurkiss.planradar.weatherapp.common.domain.LabelsRepository
import com.yurkiss.planradar.weatherapp.common.loadingHolder
import com.yurkiss.planradar.weatherapp.common.messageHolder
import javax.inject.Inject
import kotlin.properties.Delegates.observable

class SearchCitiesController @Inject constructor(
    private val labelRepository: LabelsRepository
) : EpoxyController() {

    interface Callback {
        fun onItemClicked(record: UiCity)
    }

    var viewState: SearchCitiesScreenState by observable(
        SearchCitiesScreenState.NoData
    ) { _, _, _ -> requestModelBuild() }

    var callback: Callback? by observable(null)
    { _, _, _ -> requestModelBuild() }

    override fun buildModels() {
        viewState.let { state ->
            when (state) {
                is SearchCitiesScreenState.Loading -> loadingHolder(labelRepository.getLabel(Labels.LoadingData))
                is SearchCitiesScreenState.NoData -> messageHolder(labelRepository.getLabel(Labels.NoData))
                is SearchCitiesScreenState.Loaded -> {
                    state.cities.forEach(::buildLoaded)
                }

                is SearchCitiesScreenState.Error -> {
                    val message = labelRepository.getLabel(Labels.GeneralError)
                    //TODO: handle different error types
                    messageHolder(message)
                }
            }
        }
    }

    private fun buildLoaded(city: UiCity) {
        cityItemHolder {
            id("${city.title}-${city.country}")
            title("${city.title}, ${city.country}")
            withInfoIcon(false)
            itemClickListener { _ ->
                this@SearchCitiesController.callback?.onItemClicked(city)
            }
        }
    }

    fun clear() {
        callback = null
        cancelPendingModelBuild()
    }
}