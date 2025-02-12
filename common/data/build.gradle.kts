plugins {
    id("local.android.library")
    alias(libs.plugins.room)
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.common.data"
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {

    implementation(project(":common:domain"))

    // Kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    ksp(libs.hilt.compiler)

    // Network & Serialization
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.okhttp3.logging.interceptor)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}