plugins {
    id("local.android.library")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.favorite_cities.presentation"
}

dependencies {

    implementation(project(":common:presentation"))
    implementation(project(":common:domain"))

    implementation(project(":features:favorite-cities:domain"))

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

    // Recycler View
    implementation(libs.epoxy)
    kapt(libs.epoxy.processor)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}