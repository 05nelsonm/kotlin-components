import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.android.gradle)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.androidx.navigation.safeArgs)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.google.hilt)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.gradleVersions)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.intellij)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.kotlin.dokka)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.kotlin.gradle)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.kotlin.serialization)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.mavenPublish)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.square.exhaustive)
        classpath(io.matthewnelson.kotlin.components.dependencies.plugins.square.sqlDelight)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io") // Needed for Requery
    }
}

task<Delete>("clean") {
    delete(project.buildDir)
}

plugins {
    val vBinaryCompat = io.matthewnelson.kotlin.components.dependencies.versions.gradle.binaryCompat

    id(pluginId.kotlin.binaryCompat) version(vBinaryCompat) apply(false)
}

// Gradle Versions: https://github.com/ben-manes/gradle-versions-plugin
plugins.apply(pluginId.gradleVersions)

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    // Example 1: reject all non stable versions
    rejectVersionIf {
        isNonStable(candidate.version)
    }

    // Example 2: disallow release candidates as upgradable versions from stable versions
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

    // Example 3: using the full syntax
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}
