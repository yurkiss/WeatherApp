/*
 * CT Mobile, mobile client for Salesforce.com (TM)
 *
 * Copyright (C) 2007-2019 Customertimes Corp.
 * 3 Columbus Circle, 15th Floor, #1513
 * New York, NY 10019
 * mailto:support@customertimes.com
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.yurkiss.planradar.weatherapp.common

import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.ModelCollector
import com.google.android.material.textview.MaterialTextView
import com.yurkiss.planradar.weatherapp.R

private const val VerticalLoadingHolderId = "vertical_loading_holder"

@EpoxyModelClass(layout = R.layout.holder_vertical_loading_more)
abstract class LoadingHolder : EpoxyModelWithHolder<LoadingHolder.Holder>() {

    @EpoxyAttribute
    var loadingText: String? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.loading.isVisible = loadingText != null
        holder.loading.text = loadingText
    }

    class Holder : BaseEpoxyHolder() {
        val loading: MaterialTextView by bind(R.id.loading_text)
    }
}

fun ModelCollector.loadingHolder(text: String) =
    add(
        LoadingHolder_()
            .apply { id(VerticalLoadingHolderId); loadingText(text) }
    )