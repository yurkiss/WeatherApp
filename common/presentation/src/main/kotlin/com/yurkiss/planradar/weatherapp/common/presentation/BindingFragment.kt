package com.yurkiss.planradar.weatherapp.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<Binding: ViewBinding> (
    private val inflater: (LayoutInflater, ViewGroup?) -> Binding
): Fragment() {

    // This property is only valid between onCreateView and onDestroyView.
    protected var _binding: Binding? = null
        private set

    protected fun requireBinding(): Binding = _binding!!

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater(inflater, container).let {
            _binding = it
            it.root
        }
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.let {
            setupGUI(it, savedInstanceState)
            setupObserve()
        }
    }

    final override fun onDestroyView() {
        super.onDestroyView()
        _binding?.let {
            destroyGUI(it)
        }
        _binding = null
    }

    open fun setupObserve() {}

    open fun setupGUI(binding: Binding, savedInstanceState: Bundle?) {}

    open fun destroyGUI(binding: Binding) {}

    protected inline fun binding(block: Binding.() -> Unit) {
        _binding?.block()
    }
}
