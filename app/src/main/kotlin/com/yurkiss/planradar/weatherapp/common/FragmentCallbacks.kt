package com.yurkiss.planradar.weatherapp.common

import androidx.fragment.app.FragmentActivity

interface FragmentCallbacks {
    fun displayFab()
    fun hideFab()
    fun setTitle(title: String)
}

val FragmentActivity.fragmentCallbacks: FragmentCallbacks?
    get() = this as? FragmentCallbacks
