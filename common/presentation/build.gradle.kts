plugins {
    id("local.android.library")
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.common.presentation"
}

dependencies {

    implementation(project(":common:domain"))

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    kapt(libs.hilt.compiler)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Recycler View
    implementation(libs.epoxy)
    kapt(libs.epoxy.processor)

    // Logging
    implementation(libs.timber)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}