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

@Suppress("NOTHING_TO_INLINE")
inline fun Project.kotlin(action: Action<KotlinMultiplatformExtension>) {
    extensions.configure(KotlinMultiplatformExtension::class, action)
}

inline fun KotlinMultiplatformExtension.sourceSetCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetJvmCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetJvmCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetJvmMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetJvmTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_TEST)

inline fun KotlinMultiplatformExtension.sourceSetJvmJsCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmJsCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_JS_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetJvmJsCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJvmTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJvmJsCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JVM_JS_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetAndroidMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetAndroidMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetAndroidTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetAndroidTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetAndroidTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.ANDROID_TEST)

inline fun KotlinMultiplatformExtension.sourceSetNonJvmMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetNonJvmMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetNonJvmMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NON_JVM_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetNonJvmTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetNonJvmTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetNonJvmTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NON_JVM_TEST)

inline fun KotlinMultiplatformExtension.sourceSetJsMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJsMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJsMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JS_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetJsTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetJsTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetJsTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.JS_TEST)

inline fun KotlinMultiplatformExtension.sourceSetNativeCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetNativeCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetNativeCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NATIVE_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetNativeCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetNativeCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetNativeCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.NATIVE_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetUnixCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetUnixCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetUnixCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.UNIX_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetUnixCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetUnixCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetUnixCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.UNIX_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetDarwinCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetDarwinCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetDarwinCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.DARWIN_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetDarwinCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetDarwinCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetDarwinCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.DARWIN_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetIosMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetIosTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_TEST)

inline fun KotlinMultiplatformExtension.sourceSetIosArm32Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm32Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosArm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM32_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetIosArm32Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm32Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosArm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM32_TEST)

inline fun KotlinMultiplatformExtension.sourceSetIosArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetIosArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetIosX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetIosX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosSimulatorArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_SIMULATOR_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetIosSimulatorArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetIosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.IOS_SIMULATOR_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetMacosArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMacosCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMacosCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetMacosArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_ARM64_TEST)


inline fun KotlinMultiplatformExtension.sourceSetMacosX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMacosX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMacosX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMacosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MACOS_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetTvosMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetTvosTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_TEST)

inline fun KotlinMultiplatformExtension.sourceSetTvosArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetTvosArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetTvosX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetTvosX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosSimulatorArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_SIMULATOR_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetTvosSimulatorArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetTvosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.TVOS_SIMULATOR_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetWatchosMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetWatchosTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_TEST)

inline fun KotlinMultiplatformExtension.sourceSetWatchosArm32Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm32Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosArm32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM32_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetWatchosArm32Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm32Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosArm32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM32_TEST)

inline fun KotlinMultiplatformExtension.sourceSetWatchosArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetWatchosArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetWatchosX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetWatchosX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosSimulatorArm64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_SIMULATOR_ARM64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetWatchosSimulatorArm64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetWatchosSimulatorArm64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.WATCHOS_SIMULATOR_ARM64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetLinuxCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetLinuxCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetLinuxArm32HfpMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm32HfpMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxArm32HfpMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM32HFP_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetLinuxArm32HfpTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxArm32HfpTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxArm32HfpTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_ARM32HFP_TEST)

inline fun KotlinMultiplatformExtension.sourceSetLinuxMips32Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMips32Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxMips32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPS32_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetLinuxMips32Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMips32Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxMips32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPS32_TEST)

inline fun KotlinMultiplatformExtension.sourceSetLinuxMipsel32Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMipsel32Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxMipsel32Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPSEL32_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetLinuxMipsel32Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxMipsel32Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxMipsel32Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_MIPSEL32_TEST)

inline fun KotlinMultiplatformExtension.sourceSetLinuxX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetLinuxX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetLinuxX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetLinuxX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.LINUX_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetMingwCommonMain(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwCommonMain?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwCommonMain: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_COMMON_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMingwCommonTest(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwCommonTest?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwCommonTest: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_COMMON_TEST)

inline fun KotlinMultiplatformExtension.sourceSetMingwX64Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX64Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwX64Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X64_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMingwX64Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX64Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwX64Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X64_TEST)

inline fun KotlinMultiplatformExtension.sourceSetMingwX86Main(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX86Main?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwX86Main: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X86_MAIN)

inline fun KotlinMultiplatformExtension.sourceSetMingwX86Test(crossinline action: KotlinSourceSet.() -> Unit) {
    sourceSetMingwX86Test?.let { action.invoke(it) }
}

inline val KotlinMultiplatformExtension.sourceSetMingwX86Test: KotlinSourceSet?
    get() = sourceSets.findByName(KmpTarget.SetNames.MINGW_X86_TEST)
