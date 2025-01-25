package com.yurkiss.planradar.weatherapp.cities.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.TextProp
import com.yurkiss.planradar.weatherapp.favorite_cities.presentation.databinding.HolderCityItemBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CityItemHolder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        HolderCityItemBinding.inflate(LayoutInflater.from(this.context), this, true)

    @set:TextProp
    var title: CharSequence? = null

    @set:ModelProp
    var withInfoIcon: Boolean = true

    @set:CallbackProp
    var itemClickListener: View.OnClickListener? = null

    @set:CallbackProp
    var infoIconClickListener: View.OnClickListener? = null

    @AfterPropsSet
    fun postBindSetup() {
        binding.cityName.text = title
        binding.cityItemContainer.setOnClickListener { itemClickListener?.onClick(it) }
        binding.infoIcon.visibility = if (withInfoIcon) View.VISIBLE else View.GONE
        if (withInfoIcon) {
            binding.infoIcon.setOnClickListener { infoIconClickListener?.onClick(it) }
        }
    }

    @OnViewRecycled
    fun clear() {
        binding.cityName.text = null
        binding.cityItemContainer.setOnClickListener (null)
        binding.infoIcon.visibility = View.VISIBLE
        binding.infoIcon.setOnClickListener(null)
    }

}
