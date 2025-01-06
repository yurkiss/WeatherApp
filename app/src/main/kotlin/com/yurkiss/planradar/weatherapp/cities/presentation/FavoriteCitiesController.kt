package com.yurkiss.planradar.weatherapp.cities.presentation

import com.airbnb.epoxy.EpoxyController
import com.yurkiss.planradar.weatherapp.common.domain.Labels
import com.yurkiss.planradar.weatherapp.common.domain.LabelsRepository
import com.yurkiss.planradar.weatherapp.common.loadingHolder
import com.yurkiss.planradar.weatherapp.common.messageHolder
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.properties.Delegates.observable

class FavoriteCitiesController @AssistedInject constructor(
    private val labelRepository: LabelsRepository
) : EpoxyController() {

    interface Callback {
        fun onItemClicked(record: UiCity)
        fun onInfoClicked(record: UiCity)
    }

    @AssistedFactory
    interface Factory {
        fun create(): FavoriteCitiesController
    }

    var viewState: FavoriteCitiesScreenState by observable(
        FavoriteCitiesScreenState.NoData
    ) { _, _, _ -> requestModelBuild() }

    var callback: Callback? by observable(null)
    { _, _, _ -> requestModelBuild() }

    override fun buildModels() {
        viewState.let { state ->
            when (state) {
                is FavoriteCitiesScreenState.Loading -> loadingHolder(labelRepository.getLabel(Labels.LoadingData))
                is FavoriteCitiesScreenState.NoData -> messageHolder(labelRepository.getLabel(Labels.NoData))
                is FavoriteCitiesScreenState.Loaded -> {
                    state.cities.forEach(::buildLoaded)
                }

                is FavoriteCitiesScreenState.Error -> {
                    val message = labelRepository.getLabel(Labels.GeneralError)
                    //TODO: handle different error types
                    messageHolder(message)
                }
            }
        }
    }

    private fun buildLoaded(city: UiCity) {
        cityItemHolder {
            id(city.id)
            title(city.title)
            itemClickListener { _ ->
                this@FavoriteCitiesController.callback?.onItemClicked(city)
            }
            infoIconClickListener { _ ->
                this@FavoriteCitiesController.callback?.onInfoClicked(city)
            }
        }
    }

    fun clear() {
        callback = null
        cancelPendingModelBuild()
    }
}