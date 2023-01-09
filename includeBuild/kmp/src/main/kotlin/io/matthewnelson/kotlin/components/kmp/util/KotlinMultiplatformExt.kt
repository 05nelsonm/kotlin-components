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
@file:Suppress("unused")

package io.matthewnelson.kotlin.components.kmp.util

import io.matthewnelson.kotlin.components.kmp.KmpTarget
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun Project.kotlin(action: Action<KotlinMultiplatformExtension>) {
    extensions.configure(KotlinMultiplatformExtension::class, action)
}

fun KotlinMultiplatformExtension.sourceSetCommonMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetCommonMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.COMMON_MAIN)

fun KotlinMultiplatformExtension.sourceSetCommonTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetCommonTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.COMMON_TEST)

// Jvm/Android
fun KotlinMultiplatformExtension.sourceSetJvmAndroidMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmAndroidMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmAndroidMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_ANDROID_MAIN)

fun KotlinMultiplatformExtension.sourceSetJvmAndroidTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmAndroidTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmAndroidTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_ANDROID_TEST)

// Jvm
fun KotlinMultiplatformExtension.sourceSetJvmMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_MAIN)

fun KotlinMultiplatformExtension.sourceSetJvmTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_TEST)

// Jvm/Js
fun KotlinMultiplatformExtension.sourceSetJvmJsMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmJsMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_JS_MAIN)

fun KotlinMultiplatformExtension.sourceSetJvmJsTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJvmJsTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_JS_TEST)

// Android
fun KotlinMultiplatformExtension.sourceSetAndroidMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidUnitTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidUnitTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidUnitTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_TEST_UNIT)

fun KotlinMultiplatformExtension.sourceSetAndroidInstrumentedTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidInstrumentedTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidInstrumentedTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_TEST_INSTRUMENTED)

// Non-Jvm
fun KotlinMultiplatformExtension.sourceSetNonJvmMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetNonJvmMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetNonJvmMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NON_JVM_MAIN)

fun KotlinMultiplatformExtension.sourceSetNonJvmTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetNonJvmTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetNonJvmTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NON_JVM_TEST)

// JS
fun KotlinMultiplatformExtension.sourceSetJsMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetJsMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJsMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JS_MAIN)

fun KotlinMultiplatformExtension.sourceSetJsTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetJsTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetJsTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JS_TEST)

// Native
fun KotlinMultiplatformExtension.sourceSetNativeMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetNativeMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetNativeMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NATIVE_MAIN)

fun KotlinMultiplatformExtension.sourceSetNativeTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetNativeTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetNativeTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NATIVE_TEST)

// Unix
fun KotlinMultiplatformExtension.sourceSetUnixMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetUnixMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetUnixMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.UNIX_MAIN)

fun KotlinMultiplatformExtension.sourceSetUnixTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetUnixTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetUnixTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.UNIX_TEST)

// Darwin
fun KotlinMultiplatformExtension.sourceSetDarwinMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetDarwinMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetDarwinMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.DARWIN_MAIN)

fun KotlinMultiplatformExtension.sourceSetDarwinTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetDarwinTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetDarwinTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.DARWIN_TEST)

// Darwin [ iOS ]
fun KotlinMultiplatformExtension.sourceSetIosMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_MAIN)

fun KotlinMultiplatformExtension.sourceSetIosTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_TEST)

fun KotlinMultiplatformExtension.sourceSetIosArm32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosArm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM32_MAIN)

fun KotlinMultiplatformExtension.sourceSetIosArm32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosArm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM32_TEST)

fun KotlinMultiplatformExtension.sourceSetIosArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetIosArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM64_TEST)

fun KotlinMultiplatformExtension.sourceSetIosX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetIosX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_X64_TEST)

fun KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosSimulatorArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_SIMULATOR_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetIosSimulatorArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_SIMULATOR_ARM64_TEST)

// Darwin [ macOS ]
fun KotlinMultiplatformExtension.sourceSetMacosArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetMacosMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_MAIN)

fun KotlinMultiplatformExtension.sourceSetMacosTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_TEST)

fun KotlinMultiplatformExtension.sourceSetMacosArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_ARM64_TEST)


fun KotlinMultiplatformExtension.sourceSetMacosX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetMacosX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMacosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_X64_TEST)

// Darwin [ tvOS ]
fun KotlinMultiplatformExtension.sourceSetTvosMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_MAIN)

fun KotlinMultiplatformExtension.sourceSetTvosTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_TEST)

fun KotlinMultiplatformExtension.sourceSetTvosArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetTvosArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_ARM64_TEST)

fun KotlinMultiplatformExtension.sourceSetTvosX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetTvosX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_X64_TEST)

fun KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosSimulatorArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_SIMULATOR_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosSimulatorArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_SIMULATOR_ARM64_TEST)

// Darwin [ watchOS ]
fun KotlinMultiplatformExtension.sourceSetWatchosMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_MAIN)

fun KotlinMultiplatformExtension.sourceSetWatchosTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_TEST)

fun KotlinMultiplatformExtension.sourceSetWatchosArm32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosArm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM32_MAIN)

fun KotlinMultiplatformExtension.sourceSetWatchosArm32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosArm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM32_TEST)

fun KotlinMultiplatformExtension.sourceSetWatchosArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetWatchosArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM64_TEST)

fun KotlinMultiplatformExtension.sourceSetWatchosX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetWatchosX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_X64_TEST)

fun KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosSimulatorArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_SIMULATOR_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosSimulatorArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_SIMULATOR_ARM64_TEST)

// Linux
fun KotlinMultiplatformExtension.sourceSetLinuxMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_TEST)

fun KotlinMultiplatformExtension.sourceSetLinuxArm32HfpMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm32HfpMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxArm32HfpMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM32HFP_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxArm32HfpTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm32HfpTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxArm32HfpTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM32HFP_TEST)

fun KotlinMultiplatformExtension.sourceSetLinuxArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM64_TEST)

fun KotlinMultiplatformExtension.sourceSetLinuxMips32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMips32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxMips32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPS32_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxMips32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMips32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxMips32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPS32_TEST)

fun KotlinMultiplatformExtension.sourceSetLinuxMipsel32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMipsel32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxMipsel32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPSEL32_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxMipsel32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMipsel32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxMipsel32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPSEL32_TEST)

fun KotlinMultiplatformExtension.sourceSetLinuxX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetLinuxX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetLinuxX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_X64_TEST)

// Mingw
fun KotlinMultiplatformExtension.sourceSetMingwMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_MAIN)

fun KotlinMultiplatformExtension.sourceSetMingwTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_TEST)

fun KotlinMultiplatformExtension.sourceSetMingwX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetMingwX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X64_TEST)

fun KotlinMultiplatformExtension.sourceSetMingwX86Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX86Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwX86Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X86_MAIN)

fun KotlinMultiplatformExtension.sourceSetMingwX86Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX86Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetMingwX86Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X86_TEST)

// Wasm
fun KotlinMultiplatformExtension.sourceSetWasmMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetWasmMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWasmMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WASM_MAIN)

fun KotlinMultiplatformExtension.sourceSetWasmTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetWasmTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWasmTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WASM_TEST)

fun KotlinMultiplatformExtension.sourceSetWasm32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetWasm32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWasm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WASM_32_MAIN)

fun KotlinMultiplatformExtension.sourceSetWasm32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetWasm32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetWasm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WASM_32_TEST)

// Android Native
fun KotlinMultiplatformExtension.sourceSetAndroidNativeMain(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeMain?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_NATIVE_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeTest(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeTest?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_NATIVE_TEST)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeArm32Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeArm32Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeArm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_ARM32_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeArm32Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeArm32Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeArm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_ARM32_TEST)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeArm64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeArm64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_ARM64_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeArm64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeArm64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_ARM64_TEST)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeX64Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeX64Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_X64_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeX64Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeX64Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_X64_TEST)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeX86Main(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeX86Main?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeX86Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_X86_MAIN)

fun KotlinMultiplatformExtension.sourceSetAndroidNativeX86Test(action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidNativeX86Test?.let { action.invoke(it) }
}

val KotlinMultiplatformExtension.sourceSetAndroidNativeX86Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_X86_TEST)
