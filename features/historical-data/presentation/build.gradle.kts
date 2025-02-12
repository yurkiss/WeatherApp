plugins {
    id("local.android.library")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.yurkiss.planradar.weatherapp.historical_data.presentation"

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":common:presentation"))
    implementation(project(":common:domain"))

    implementation(project(":features:historical-data:domain"))

    // Kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Image loading
    implementation(libs.coil.compose)

    // Logging
    implementation(libs.timber)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.compose)

}