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

package com.yurkiss.planradar.weatherapp.common.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelCollector
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.TextProp
import com.yurkiss.planradar.weatherapp.common.presentation.databinding.HolderVerticalLoadingMoreBinding

private const val VerticalLoadingHolderId = "vertical_loading_holder"

//@EpoxyModelClass(layout = R.layout.holder_vertical_loading_more)
@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class LoadingHolder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    @set:TextProp
    var loadingText: CharSequence? = null

    private val binding =
        HolderVerticalLoadingMoreBinding.inflate(LayoutInflater.from(this.context), this, true)


    @AfterPropsSet
    fun postBindSetup() {
        binding.loadingText.text = loadingText
        binding.loadingText.isVisible = loadingText != null
    }

    @OnViewRecycled
    fun clear() {
        binding.loadingText.text = null
    }

//    init {
//        inflate(context, R.layout.holder_vertical_loading_more, this)
//    }

//    @EpoxyAttribute
//    var loadingText: String? = null
//
//    override fun bind(holder: Holder) {
//        super.bind(holder)
//        holder.loading.isVisible = loadingText != null
//        holder.loading.text = loadingText
//    }
//
//    class Holder : BaseEpoxyHolder() {
//        val loading: MaterialTextView by bind(R.id.loading_text)
//    }
}

fun ModelCollector.loadingHolder(text: String) =
    add(
        LoadingHolderModel_()
            .apply { id(VerticalLoadingHolderId); loadingText(text) }
    )