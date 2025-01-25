import java.util.Properties

plugins {
    id("local.app")
}

android {
    namespace = "com.yurkiss.planradar.weatherapp"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.yurkiss.planradar.weatherapp"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] =
                    "$projectDir/schemas"
            }
        }

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        properties.load(localPropertiesFile.inputStream())
        val apiKey = properties.getProperty("API_KEY") ?: throw IllegalStateException("API_KEY not found in local.properties")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(project(":common:domain"))
    implementation(project(":common:data"))
    implementation(project(":common:presentation"))

    implementation(project(":features:favorite-cities:presentation"))
    implementation(project(":features:favorite-cities:domain"))
    implementation(project(":features:favorite-cities:data"))

    implementation(project(":features:details:presentation"))
    implementation(project(":features:details:domain"))

    implementation(project(":features:historical-data:presentation"))
    implementation(project(":features:historical-data:domain"))


    // Kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    // DI: Hilt
    implementation(libs.hilt.android.deps)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.fragment)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Recycler View
    implementation(libs.epoxy)
    kapt(libs.epoxy.processor)

    // Network & Serialization
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.okhttp3.logging.interceptor)
    kapt(libs.moshi.kotlin.codegen)
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // Room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Logging
    implementation(libs.timber)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}