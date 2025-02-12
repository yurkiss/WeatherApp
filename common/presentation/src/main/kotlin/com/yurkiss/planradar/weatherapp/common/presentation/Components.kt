package com.yurkiss.planradar.weatherapp.common.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorContent(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.lbl_general_error),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
    )
}

@Composable
fun NoDataContent(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.lbl_no_data),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
    )
}