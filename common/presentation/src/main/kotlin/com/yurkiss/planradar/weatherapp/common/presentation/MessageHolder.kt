package com.yurkiss.planradar.weatherapp.common.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelCollector
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.OnViewRecycled
import com.airbnb.epoxy.TextProp
import com.yurkiss.planradar.weatherapp.common.presentation.databinding.HolderMessageBinding


private const val MessageHolderId = "messageHolderId"

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        HolderMessageBinding.inflate(LayoutInflater.from(this.context), this, true)

    @set:TextProp
    var message: CharSequence? = null


    @AfterPropsSet
    fun postBindSetup() {
        binding.messageView.text = message
    }

    @OnViewRecycled
    fun clear() {
        binding.messageView.text = null
    }


}

fun ModelCollector.messageHolder(text: String) =
    add(
        MessageViewModel_()
            .id(MessageHolderId)
            .message(text)
    )
