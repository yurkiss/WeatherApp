package com.yurkiss.planradar.weatherapp.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.yurkiss.planradar.weatherapp.designsystem.theme.WeatherAppTypography
import com.yurkiss.planradar.weatherapp.designsystem.theme.WeatherShapes
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.errorLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseOnSurfaceLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inversePrimaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.inverseSurfaceLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onBackgroundLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onErrorLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onPrimaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSecondaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onSurfaceVariantLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.onTertiaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.outlineVariantLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.primaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.scrimLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.secondaryLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceBrightLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerHighestLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceContainerLowestLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceDimLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.surfaceVariantLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryContainerLightMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryDark
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryDarkHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryDarkMediumContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryLight
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryLightHighContrast
import com.yurkiss.planradar.weatherapp.designsystem.theme.tertiaryLightMediumContrast

@Composable
private fun lightScheme() = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified,
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit,
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> darkScheme
        else -> lightScheme()
    }

    MaterialTheme(
        colorScheme = lightScheme(),
        typography = WeatherAppTypography,
        shapes = WeatherShapes,
        content = content,
    )
}

