package com.yurkiss.planradar.weatherapp.historical.presentation

import android.widget.ImageView
import android.widget.LinearLayout
import coil3.load
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textview.MaterialTextView
import com.yurkiss.planradar.weatherapp.R

@EpoxyModelClass(layout = R.layout.holder_historycal_data_item)
abstract class HistoricalDataItemHolder : EpoxyModelWithHolder<HistoricalDataItemHolder.Holder>() {

    @EpoxyAttribute
    var dateTime: String? = null

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute(value = [DoNotHash])
    var weatherIcon: String? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.dateTimeView.text = dateTime
        holder.weatherDescriptionView.text = description
        weatherIcon?.let {
            holder.weatherIconView.load("https://openweathermap.org/img/w/$it.png")
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.dateTimeView.text = null
        holder.weatherDescriptionView.text = null
        holder.weatherIconView.setImageDrawable(null)
    }

    class Holder : com.yurkiss.planradar.weatherapp.common.presentation.BaseEpoxyHolder() {
        val itemContainer by bind<LinearLayout>(R.id.historical_data_item_container)
        val dateTimeView by bind<MaterialTextView>(R.id.dateTime)
        val weatherDescriptionView by bind<MaterialTextView>(R.id.weatherDescription)
        val weatherIconView by bind<ImageView>(R.id.weatherIcon)
    }
}
