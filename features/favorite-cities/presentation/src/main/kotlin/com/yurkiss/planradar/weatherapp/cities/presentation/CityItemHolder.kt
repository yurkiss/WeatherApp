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
//@EpoxyModelClass(layout = R.layout.holder_city_item)
//abstract class CityItemHolder : EpoxyModelWithHolder<CityItemHolder.Holder>() {

    private val binding =
        HolderCityItemBinding.inflate(LayoutInflater.from(this.context), this, true)

//    @EpoxyAttribute
    @set:TextProp
    var title: CharSequence? = null
//
//    @EpoxyAttribute(value = [NoGetter])
    @set:ModelProp
    var withInfoIcon: Boolean = true
//
//    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    @set:CallbackProp
    var itemClickListener: View.OnClickListener? = null
//
//    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
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

//    override fun bind(holder: Holder) {
//        super.bind(holder)
//        holder.titleView.text = title
//        holder.itemContainer.setOnClickListener { itemClickListener?.onClick(it) }
//        holder.infoIconView.visibility = if (withInfoIcon) View.VISIBLE else View.GONE
//        if (withInfoIcon) {
//            holder.infoIconView.setOnClickListener { infoIconClickListener?.onClick(it) }
//        }
//    }
//
//    override fun unbind(holder: Holder) {
//        super.unbind(holder)
//        holder.titleView.text = null
//        holder.itemContainer.setOnClickListener(null)
//        holder.infoIconView.setOnClickListener(null)
//
//    }
//
//    class Holder : BaseEpoxyHolder() {
//        val itemContainer by bind<LinearLayout>(R.id.city_item_container)
//        val titleView by bind<MaterialTextView>(R.id.city_name)
//        val infoIconView by bind<ImageView>(R.id.info_icon)
//    }
}
