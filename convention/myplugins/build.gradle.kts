@file:Suppress("Annotator")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(plugin(libs.plugins.kotlin.android))
    implementation(plugin(libs.plugins.hilt.android))
    implementation(plugin(libs.plugins.jetbrains.kotlin.jvm))
    implementation(plugin(libs.plugins.jetbrains.kotlin.kapt))
    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.spotless))
    implementation(plugin(libs.plugins.detekt))
    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

}

fun plugin(plugin: Provider<PluginDependency>) = plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

private val projectJavaVersion: JavaVersion = JavaVersion.VERSION_11

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}