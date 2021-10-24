/*
 *  Copyright 2021 Matthew Nelson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * */
@file:Suppress("SpellCheckingInspection")

package io.matthewnelson.kotlin.components.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/**
 * Configures source sets for a Kotlin Multiplatform project in an opinionated fashion.
 *
 * Declare a Set of all [KmpTarget]s for the module and pass them to
 * [KmpConfigurationExtension.setupMultiplatform] via the `kmpConfiguration` block. The
 * passed targets are then filtered by what is enabled via the `KMP_TARGETS` environment
 * property set for the given maching (more detials below). This allows you to declare
 * in the module's build.gradle.kts (or build.gradle) file all of the desired targets
 * while only setting up and building those that are enabled for the given
 * machine (linux, macos, windows).
 *
 * Add to module's build.gradle.kts file:
 *
 * ```
 * import io.matthewnelson.kotlin.components.kmp.KmpTarget
 *
 * plugins {
 *     id("kmp-configuration")
 * }
 *
 * kmpConfiguration {
 *     setupMultiplatform(
 *         setOf(
 *             // list all kmp targets that the module will utilize
 *             KmpTarget.Jvm.Android(
 *                 buildTools = "30.0.3",
 *                 compileSdk = 30,
 *                 minSdk = 16,
 *                 targetSdk = 30,
 *
 *                 // Optional argument to provide path to manifest
 *                 // if it is not locasted in `src/main`
 *                 manifestPath = "src/androidMain/AndroidManifest.xml",
 *
 *                 // Optional argument to apply platform specific plugins
 *                 // for when the target is enabled
 *                 pluginIds = setOf("android.specific.plugin1", "android.specific.plugin2"),
 *
 *                 // Optional lambda to apply android block configurations
 *                 androidConfig = {
 *                     buildFeatures.viewBinding = true
 *                 },
 *
 *                 // Optional lambda for accessing the KotlinAndroidTarget
 *                 target = {
 *                     ...
 *                 },
 *
 *                 // Option lambda for accessing the androidMain source set
 *                 mainSourceSet = {
 *                     dependencies {
 *                         implementation("androidx.......")
 *                     }
 *                 },
 *
 *                 // Option lambda for accessing the androidTest source set
 *                 testSourceSet = {
 *                     dependencies {
 *                         implementation(kotlin(test-junit))
 *                     }
 *                 },
 *
 *             ),
 *             KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm32(
 *                 target = {
 *                     ...
 *                 },
 *                 mainSourceSet = {
 *                     dependencies {
 *                         ...
 *                     }
 *                 }
 *             ),
 *
 *             // Optionally, utilize the `DEFAULT`s provided (no callbacks)
 *             KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm64.DEFAULT,
 *             ...
 *         ),
 *
 *         // Option argument for applying additional, platform non-specific, plugins.
 *         commonPluginIds = setOf("my.plugin1", "my.plugin2"),
 *
 *         commonMainSourceSet = {
 *             dependencies {
 *                 api(project(":my-other-project-module"))
 *             }
 *         },
 *         commonTestSourceSet = {
 *             dependencies {
 *                 implementation(kotlin("test-common"))
 *                 implementation(kotlin("test-annotations-common"))
 *             }
 *         }
 *     )
 * }
 * ```
 *
 * Alternatively, import the [io.matthewnelson.kotlin.components.kmp.util.kotlin] extension function
 * and configure things further after the `kmpConfiguration` block.
 *
 * ```
 * import io.matthewnelson.kotlin.components.kmp.KmpTarget
 * import io.matthewnelson.kotlin.components.kmp.kotlin
 *
 * plugins {
 *     id("kmp-configuration")
 * }
 *
 * kmpConfiguration {
 *    ...
 * }
 *
 * kotlin {
 *     sourceSets {
 *         getByName(KmpTarget.SetNames.COMMON_MAIN) {
 *             dependencies {
 *                 implementation(project(":my-other-project-module"))
 *             }
 *         }
 *         getByName(KmpTarget.SetNames.MACOS_X64_MAIN) {
 *             dependencies {
 *                 implementation(project(":my-other-macos-only-project"))
 *             }
 *         }
 *     }
 *
 *     targets.getByName(KmpTarget.NonJvm.JS.TARGET_NAME) {
 *         ...
 *     }
 * }
 * ```
 *
 * Append args when building from command line for the given machine (linux, macos, windows):
 *  - ex: `$ ./gradlew build -PKMP_TARGETS=ANDROID,JS,JVM,LINUX_X64,...`
 *
 * Or add to the global gradle.properties file (ex: ~/.gradle/gradle.properties)
 * the desired KMP_TARGETS values to enable for that machine (linux, macos, windows)
 *
 * If no KMP_TARGETS property is set, all targets that are passed to
 * [KmpConfigurationExtension.setupMultiplatform] will be enabled
 *
 * Full list of KMP_TARGETS property arguments:
 *
 *   ANDROID,
 *   JVM,
 *   JS,
 *   LINUX_ARM32HFP,LINUX_MIPS32,LINUX_MIPSEL32,LINUX_X64,
 *   IOS_ALL,IOS_ARM32,IOS_ARM64,IOS_X64,IOS_SIMULATOR_ARM64,
 *   MACOS_ARM64,MACOS_X64,
 *   TVOS_ALL,TVOS_ARM64,TVOS_X64,TVOS_SIMULATOR_ARM64,
 *   WATCHOS_ALL,WATCHOS_ARM32,WATCHOS_ARM64,WATCHOS_X64,WATCHOS_X86,WATCHOS_SIMULATOR_ARM64,
 *   MINGW_X64,MINGW_X86,
 *
 * Depending on the [KmpTarget]s passed, as well as what is enabled (as mentioned above),
 * the structure of source sets will be as depicted below (diagram shamelessly stolen from
 * Jesse Wilson: https://github.com/square/okio/blob/master/okio/build.gradle.kts).
 *
 * common
 *   |-- jvmCommon
 *   |      |-- jvm
 *   |      '-- android
 *   '-- nonJvm
 *          |-- js
 *          '-- nativeCommon
 *                  |-- unixCommon
 *                  |       |-- darwinCommon
 *                  |       |        |-- iosArm32
 *                  |       |        |-- iosArm64
 *                  |       |        |-- iosX64
 *                  |       |        |-- iosSimulatorArm64
 *                  |       |        |-- macosArm64
 *                  |       |        |-- macosX64
 *                  |       |        |-- tvosArm64
 *                  |       |        |-- tvosX64
 *                  |       |        |-- tvosSimulatorArm64
 *                  |       |        |-- watchosArm32
 *                  |       |        |-- watchosArm64
 *                  |       |        |-- watchosX64
 *                  |       |        '-- watchosSimulatorArm64
 *                  |       '-- linuxCommon
 *                  |                |-- linuxArm32Hfp
 *                  |                |-- linuxMips32
 *                  |                |-- linuxMipsel32
 *                  |                '-- linuxX64
 *                  '-- mingwCommon
 *                          |-- mingwX64
 *                          '-- mingwX86
 *
 * Shout out to Arkivanov for his work on Reaktive, Decompose and MVIKotlin which heavily influenced this
 *  - https://github.com/badoo/Reaktive
 *  - https://github.com/arkivanov/Decompose
 *  - https://github.com/arkivanov/MVIKotlin
 *
 *
 * @see [KmpConfigurationExtension]
 * @see [KmpTarget]
 * @see [io.matthewnelson.kotlin.components.kmp.util.kotlin]
 * */
@Suppress("unused")
class KmpConfigurationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create<KmpConfigurationExtension>("kmpConfiguration", target)
    }
}
