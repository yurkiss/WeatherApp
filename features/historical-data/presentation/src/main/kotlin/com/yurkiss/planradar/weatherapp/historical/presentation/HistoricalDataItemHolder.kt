package com.yurkiss.planradar.weatherapp.historical.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import coil3.load
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.TextProp
import com.yurkiss.planradar.weatherapp.historical_data.presentation.databinding.HolderHistoricalDataItemBinding



@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HistoricalDataItemHolder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        HolderHistoricalDataItemBinding.inflate(LayoutInflater.from(this.context), this, true)

    @set:TextProp
    var dateTime: CharSequence? = null

    @set:TextProp
    var description: CharSequence? = null

    @set:ModelProp(value = [ModelProp.Option.DoNotHash, ModelProp.Option.GenerateStringOverloads])
    var weatherIcon: CharSequence? = null

    @AfterPropsSet
    fun postBindSetup() {
        binding.dateTime.text = dateTime
        binding.weatherDescription.text = description
        weatherIcon?.let {
            binding.weatherIcon.load("https://openweathermap.org/img/w/$it.png")
        }
    }

    @OnViewRecycled
    fun clear() {
        binding.dateTime.text = null
        binding.weatherDescription.text = null
        binding.weatherIcon.setImageDrawable(null)
    }

}
