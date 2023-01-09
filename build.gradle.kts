import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(pluginDeps.android.gradle)
        classpath(pluginDeps.androidx.navigation.safeArgs)
        classpath(pluginDeps.google.hilt)
        classpath(pluginDeps.gradleVersions)
        classpath(pluginDeps.intellij)
        classpath(pluginDeps.kotlin.dokka)
        classpath(pluginDeps.kotlin.gradle)
        classpath(pluginDeps.kotlin.serialization)
        classpath(pluginDeps.mavenPublish)
        classpath(pluginDeps.npmPublish)
        classpath(pluginDeps.square.sqlDelight)

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
    id(pluginId.kotlin.binaryCompat) version(versions.gradle.binaryCompat) apply(false)
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
