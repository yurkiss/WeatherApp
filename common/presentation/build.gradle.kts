plugins {
    id("local.android.library")
    alias(libs.plugins.compose)
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.common.presentation"
}

dependencies {

    implementation(project(":common:domain"))
    api(project(":common:designsystem"))

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    ksp(libs.hilt.compiler)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Logging
    implementation(libs.timber)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}