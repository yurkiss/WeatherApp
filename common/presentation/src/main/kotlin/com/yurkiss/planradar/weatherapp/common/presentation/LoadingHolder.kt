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

}

fun ModelCollector.loadingHolder(text: String) =
    add(
        LoadingHolderModel_()
            .apply { id(VerticalLoadingHolderId); loadingText(text) }
    )