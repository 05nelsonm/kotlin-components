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

package io.matthewnelson.components.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Add to module's build.gradle.kts file:
 *
 * plugins {
 *     id("kmp-configuration")
 * }
 *
 * kmpConfiguration {
 *     setupMultiplatform(
 *         listOf(
 *             // list all kmp targets that the module will utilize
 *             KmpTarget.ANDROID(
 *                 "30.0.3",                                // buildToolsVersion
 *                 30,                                      // compileSdk
 *                 16,                                      // minSdk
 *                 30,                                      // targetSdk
 *                 "src/androidMain/AndroidManifest.xml"    // OPTIONAL arg to specify manifest path
 *             ),
 *             KmpTarget.IOS.ARM32,
 *             KmpTarget.IOS.ARM64,
 *             KmpTarget.IOS.X64,
 *             ...
 *         )
 *     )
 * }
 *
 * Append args when building from command line for the given machine (linux, macos, windows):
 *  - ex: `$ ./gradlew build -PKMP_TARGETS=ANDROID,JS_BROWSER,JS_NODE,JVM,LINUX_X64,...`
 *
 * Full list of KMP_TARGET property arguments:
 *
 *   ANDROID,JS_BROWSER,JS_NODE,JVM,LINUX_ARM32HFP,LINUX_MIPS32,LINUX_MIPSEL32,
 *   LINUX_X64,IOS_ARM32,IOS_ARM64,IOS_X64,MACOS_X64,MINGW_X64,MINGW_X86,
 *   TVOS_ARM64,TVOS_X64,WATCHOS_ARM32,WATCHOS_ARM64,WATCHOS_X64,WATCHOS_X86
 *
 * Shout out to Arkivanov for his work on Reaktive, Decompose and MVIKotlin which heavily influenced this
 *  - https://github.com/badoo/Reaktive
 *  - https://github.com/arkivanov/Decompose
 *  - https://github.com/arkivanov/MVIKotlin
 * */
@Suppress("unused")
class KmpConfigurationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("kmpConfiguration", KmpConfigurationExtension::class.java, target)
    }
}
