@file:Suppress("SpellCheckingInspection")

import io.matthewnelson.kotlin.components.dependencies.plugins

plugins {
    `kotlin-dsl`
    id("dependencies")
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

System.setProperty("GRADLE_ANDROID", Plugins.android.gradle)
System.setProperty("GRADLE_ANDROIDX_NAVIGATION_SAFEARGS", Plugins.androidx.navigation.safeArgs)
System.setProperty("GRADLE_GOOGLE_HILT", Plugins.google.hilt)
System.setProperty("GRADLE_VERSIONS", Plugins.gradleVersions)
System.setProperty("GRADLE_INTELLIJ", Plugins.intellijGradle)
System.setProperty("GRADLE_KOTLIN_DOKKA", Plugins.kotlin.dokka)
System.setProperty("GRADLE_KOTLIN", Plugins.kotlin.gradle)
System.setProperty("GRADLE_SERIALIZATION", Plugins.kotlin.serialization)
System.setProperty("GRADLE_MAVEN_PUBLISH", Plugins.mavenPublish)
System.setProperty("GRADLE_SQUARE_EXHAUSTIVE", Plugins.square.exhaustive)
System.setProperty("GRADLE_SQUARE_SQLDELIGHT", Plugins.square.sqlDelight)
