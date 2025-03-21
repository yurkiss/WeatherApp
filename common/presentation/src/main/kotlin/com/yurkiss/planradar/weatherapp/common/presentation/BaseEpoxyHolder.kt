package com.yurkiss.planradar.weatherapp.common.presentation

import android.view.View
import androidx.annotation.CallSuper

import com.airbnb.epoxy.EpoxyHolder

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Creating a base holder class allows us to leverage easy view binding for all subclasses via property delegates.
 * This makes subclasses much cleaner, and is a highly recommended pattern.
 */
abstract class BaseEpoxyHolder : EpoxyHolder() {

    private lateinit var view: View

    @CallSuper
    override fun bindView(itemView: View) {
        view = itemView
    }

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<BaseEpoxyHolder, V> =
        Lazy { holder: BaseEpoxyHolder, prop ->
            holder.view.findViewById<V>(id)
                ?: throw IllegalStateException("View ID $id for '${prop.name}' not found.")
        }

    private class Lazy<V>(
        private val initializer: (BaseEpoxyHolder, KProperty<*>) -> V
    ) : ReadOnlyProperty<BaseEpoxyHolder, V> {

        private object EMPTY

        private var value: Any? = EMPTY

        override fun getValue(thisRef: BaseEpoxyHolder, property: KProperty<*>): V {
            if (value == EMPTY) value = initializer(thisRef, property)
            @Suppress("UNCHECKED_CAST")
            return value as V
        }
    }
}
