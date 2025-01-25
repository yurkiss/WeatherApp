plugins {
    id("local.android.library")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.details.presentation"
}

dependencies {

    implementation(project(":common:presentation"))
    implementation(project(":common:domain"))

    implementation(project(":features:details:domain"))

    // Kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Image loading
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}