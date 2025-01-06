package com.yurkiss.planradar.weatherapp.common

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.ModelCollector
import com.google.android.material.textview.MaterialTextView
import com.yurkiss.planradar.weatherapp.R

private const val MessageHolderId = "messageHolderId"

@EpoxyModelClass(layout = R.layout.holder_message)
abstract class MessageHolder : EpoxyModelWithHolder<MessageHolder.Holder>() {

    @EpoxyAttribute
    var message: String? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.messageView.text = message
    }

    class Holder : BaseEpoxyHolder() {
        val messageView by bind<MaterialTextView>(R.id.message_view)
    }
}

fun ModelCollector.messageHolder(text: String) =
    add(
        MessageHolder_()
            .apply { id(MessageHolderId); message(text) }
    )
