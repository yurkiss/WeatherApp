package com.yurkiss.planradar.weatherapp.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.yurkiss.planradar.weatherapp.R
import com.yurkiss.planradar.weatherapp.common.domain.Labels
import com.yurkiss.planradar.weatherapp.common.domain.LabelsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourcesLabelsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : LabelsRepository {
    override fun getLabel(label: Labels): String {
        val resId = when (label) {
            Labels.NoData -> R.string.lbl_no_data
            Labels.LoadingData -> R.string.lbl_loading
            Labels.GeneralError -> R.string.lbl_general_error
            Labels.ErrorAddingCity -> R.string.lbl_error_adding_favorite_city
        }
        return ContextCompat.getString(context, resId)
    }

}