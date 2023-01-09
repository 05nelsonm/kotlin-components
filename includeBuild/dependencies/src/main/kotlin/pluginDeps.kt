/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
@file:Suppress("SpellCheckingInspection", "unused", "ClassName")

/**
 * Gradle Plugin dependencies
 * */
object pluginDeps {

    object android {
        const val gradle                    = "com.android.tools.build:gradle:${versions.gradle.android}"
    }

    object androidx {
        object navigation {
            const val safeArgs              = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.gradle.navigation}"
        }
    }

    object google {
        const val hilt                      = "com.google.dagger:hilt-android-gradle-plugin:${versions.gradle.hilt}"
    }

    const val gradleVersions                = "com.github.ben-manes:gradle-versions-plugin:${versions.gradle.gradleVersions}"
    const val intellij                      = "gradle.plugin.org.jetbrains.intellij.plugins:gradle-intellij-plugin:${versions.gradle.intellij}"

    object kotlin {
        const val atomicfu                  = "org.jetbrains.kotlinx:atomicfu-gradle-plugin:${versions.gradle.atomicfu}"
        const val dokka                     = "org.jetbrains.dokka:dokka-gradle-plugin:${versions.gradle.dokka}"
        const val gradle                    = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.gradle.kotlin}"
        const val serialization             = "org.jetbrains.kotlin:kotlin-serialization:${versions.gradle.serialization}"
    }

    const val mavenPublish                  = "com.vanniktech:gradle-maven-publish-plugin:${versions.gradle.mavenPublish}"

    // 3.0.0+ change to         >>>            dev.petuska:npm-publish-gradle-plugin
    const val npmPublish                    = "dev.petuska:npm-publish:${versions.gradle.npmPublish}"

    object square {
        const val sqlDelight                = "com.squareup.sqldelight:gradle-plugin:${versions.gradle.sqlDelight}"
    }

}
