plugins {
    id("local.android.library")
}

android {
    namespace = "com.yurkiss.planradar.weatherapp.favorite_cities.data"
}

dependencies {

    implementation(project(":common:domain"))
    implementation(project(":common:data"))
    implementation(project(":features:favorite-cities:domain"))

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    ksp(libs.hilt.compiler)

    // Logging
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}