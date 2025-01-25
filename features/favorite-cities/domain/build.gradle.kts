plugins {
    id("local.domain.library")
}

dependencies {
    implementation(project(":common:domain"))
    implementation(libs.bundles.domain.deps)
    implementation(libs.kotlinx.datetime)
}
