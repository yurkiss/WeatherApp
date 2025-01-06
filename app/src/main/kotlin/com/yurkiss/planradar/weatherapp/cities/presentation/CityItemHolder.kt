package com.yurkiss.planradar.weatherapp.cities.presentation

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.NoGetter
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textview.MaterialTextView
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.common.BaseEpoxyHolder

@EpoxyModelClass(layout = R.layout.holder_city_item)
abstract class CityItemHolder : EpoxyModelWithHolder<CityItemHolder.Holder>() {

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute(value = [NoGetter])
    var withInfoIcon: Boolean = true

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: View.OnClickListener? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var infoIconClickListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.titleView.text = title
        holder.itemContainer.setOnClickListener { itemClickListener?.onClick(it) }
        holder.infoIconView.visibility = if (withInfoIcon) View.VISIBLE else View.GONE
        if (withInfoIcon) {
            holder.infoIconView.setOnClickListener { infoIconClickListener?.onClick(it) }
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.titleView.text = null
        holder.itemContainer.setOnClickListener(null)
        holder.infoIconView.setOnClickListener(null)

    }

    class Holder : BaseEpoxyHolder() {
        val itemContainer by bind<LinearLayout>(R.id.city_item_container)
        val titleView by bind<MaterialTextView>(R.id.city_name)
        val infoIconView by bind<ImageView>(R.id.info_icon)
    }
}
