package com.yurkiss.planradar.weatherapp.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yurkiss.planradar.weatherapp.common.presentation.theme.WeatherAppTheme
import com.yurkiss.planradar.weatherapp.designsystem.theme.backgroundLight

@ExperimentalMaterial3Api
@Composable
fun ScaffoldScreen(
    title: String,
    onBack: (() -> Unit)?,
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    WeatherAppBackground {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = Color.Transparent,
            topBar = {
                LargeTopAppBar(
                    title = {
                        Text(
                            text = title,
                            modifier = Modifier
                                .windowInsetsPadding(
                                    WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal),
                                ),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                    navigationIcon = {
                        onBack?.let {
                            IconButton(onClick = it) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                )
            },
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
            content = content,
        )
    }
}

//@ExperimentalMaterial3Api
//@Composable
//fun BottomSheetScaffoldScreen(
//    title: String,
//    onBack: (() -> Unit)?,
//    floatingActionButton: @Composable () -> Unit = {},
//    snackbarHostState: SnackbarHostState? = null,
//    content: @Composable (PaddingValues) -> Unit,
//) {
//    WeatherAppBackground {
//        BottomSheetScaffold(
//            contentWindowInsets = WindowInsets.safeDrawing,
//            containerColor = Color.Transparent,
//            topBar = {
//                LargeTopAppBar(
//                    title = {
//                        Text(
//                            text = title,
//                            modifier = Modifier
//                                .windowInsetsPadding(
//                                    WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
//                                ),
//                            style = MaterialTheme.typography.headlineMedium,
//                        )
//                    },
//                    navigationIcon = {
//                        onBack?.let {
//                            IconButton(onClick = it) {
//                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                            }
//                        }
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    ),
//                )
//            },
//            floatingActionButton = floatingActionButton,
//            floatingActionButtonPosition = FabPosition.End,
//            snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
//            content = content,
//        )
//    }
//}


/**
 * The main background for the app.
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun WeatherAppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.group), // Replace with your image
                contentDescription = stringResource(id = R.string.group_image_description),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Adjust height as needed
                    .alpha(0.4f)
                    .drawBehind {
                        val brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFD6D3DE), backgroundLight),
                            start = Offset(size.width / 2, size.height * 0.47f),
                            end = Offset(size.width / 2, size.height * 0f),
                            tileMode = TileMode.Clamp,
                        )
                        drawRect(brush)
                    }, // Apply tint color
                contentScale = ContentScale.FillWidth,
            )

            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppBackgroundPreview(modifier: Modifier = Modifier) {
    WeatherAppTheme {
        WeatherAppBackground(modifier) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScaffoldScreenPreview(modifier: Modifier = Modifier) {
    WeatherAppTheme {
        ScaffoldScreen(
            "Hello",
            onBack = {},
            floatingActionButton = {
                Button(onClick = {}) {
                    Text(text = "Hello")
                }
            },
        ) {

        }
    }
}