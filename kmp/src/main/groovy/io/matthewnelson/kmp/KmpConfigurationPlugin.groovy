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
package io.matthewnelson.kmp

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Add to module's build.gradle file:
 *
 * plugins {
 *     id 'kmp-configuration' // does nothing but give access to classes
 * }
 *
 * KmpConfigurationPlugin.setupMultiplatform(
 *     project,
 *     [
 *         // list all kmp targets that the module will utilize
 *         KmpTarget.ANDROID
 *             .setBuildTools("30.0.3")
 *             .setCompileSdk(30)
 *             .setMinSdk(16)
 *         ,
 *         KmpTarget.JS_IR,
 *         KmpTarget.JS_LEGACY,
 *         KmpTarget.JVM,
 *         ...
 *     ]
 * )
 *
 * Add to your machine's global gradle.properties (ex. /home/$USER/.gradle/gradle.properties):
 * KMP_TARGETS=ANDROID,JS_IR,JS_LEGACY,JVM,.....
 *
 * This will enable those KMP targets for that machine.
 *
 * Also, you can append to your build command -PKMP_TARGETS=ANDROID,JS_IR,JS_LEGACY,JVM,.....
 *
 * Shout out to Arkivanov for his work on Decompose and MVIKotlin which heavily influenced this
 *  - https://github.com/arkivanov/Decompose
 *  - https://github.com/arkivanov/MVIKotlin
 * */
@SuppressWarnings('unused')
class KmpConfigurationPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) { /* no-op */ }

    static boolean setupMultiplatform(Project project, List<KmpTarget> targets) {
        if (project == null || targets == null || targets.isEmpty()) {
            return false
        }

        Set<KmpTarget> enabledTargets = getEnabledTargets(project)
        KmpTarget android = null

        if (enabledTargets.contains(KmpTarget.ANDROID)) {
            for (KmpTarget target : targets) {
                if (target == KmpTarget.ANDROID) {
                    android = target
                    break
                }
            }

            if (android != null) {
                project.getPlugins().apply("com.android.library")
            }
        }

        project.getPlugins().apply("kotlin-multiplatform")

        setupMultiplatformCommon(project)

        if (android != null) {
            setupMultiplatformAndroid(project)
            setupAndroid(project, android)
        }

        if (enabledTargets.contains(KmpTarget.IOS_ARM32) && targets.contains(KmpTarget.IOS_ARM32)) {
            setupMultiplatformIosArm32(project)
        }
        if (enabledTargets.contains(KmpTarget.IOS_ARM64) && targets.contains(KmpTarget.IOS_ARM64)) {
            setupMultiplatformIosArm64(project)
        }
        if (enabledTargets.contains(KmpTarget.IOS_X64) && targets.contains(KmpTarget.IOS_X64)) {
            setupMultiplatformIosX64(project)
        }

        if (enabledTargets.contains(KmpTarget.JS_IR) || enabledTargets.contains(KmpTarget.JS_LEGACY)) {
            if (targets.contains(KmpTarget.JS_IR) || targets.contains(KmpTarget.JS_LEGACY)) {
                setupMultiplatformJs(project, targets.contains(KmpTarget.JS_IR), targets.contains(KmpTarget.JS_LEGACY))
            }
        }

        if (enabledTargets.contains(KmpTarget.JVM) && targets.contains(KmpTarget.JVM)) {
            setupMultiplatformJvm(project)
        }

        if (enabledTargets.contains(KmpTarget.LINUX_ARM32_HFP) && targets.contains(KmpTarget.LINUX_ARM32_HFP)) {
            setupMultiplatformLinuxArm32Hfp(project)
        }
        if (enabledTargets.contains(KmpTarget.LINUX_MIPS32) && targets.contains(KmpTarget.LINUX_MIPS32)) {
            setupMultiplatformLinuxMips32(project)
        }
        if (enabledTargets.contains(KmpTarget.LINUX_MIPSEL32) && targets.contains(KmpTarget.LINUX_MIPSEL32)) {
            setupMultiplatformLinuxMipsel32(project)
        }
        if (enabledTargets.contains(KmpTarget.LINUX_X64) && targets.contains(KmpTarget.LINUX_X64)) {
            setupMultiplatformLinuxX64(project)
        }

        if (enabledTargets.contains(KmpTarget.MACOS_X64) && targets.contains(KmpTarget.MACOS_X64)) {
            setupMultiplatformMacosX64(project)
        }

        if (enabledTargets.contains(KmpTarget.MINGW_X64) && targets.contains(KmpTarget.MINGW_X64)) {
            setupMultiplatformMingwX64(project)
        }
        if (enabledTargets.contains(KmpTarget.MINGW_X86) && targets.contains(KmpTarget.MINGW_X86)) {
            setupMultiplatformMingwX86(project)
        }

        if (enabledTargets.contains(KmpTarget.TVOS_ARM64) && targets.contains(KmpTarget.TVOS_ARM64)) {
            setupMultiplatformTvosArm64(project)
        }
        if (enabledTargets.contains(KmpTarget.TVOS_X64) && targets.contains(KmpTarget.TVOS_X64)) {
            setupMultiplatformTvosX64(project)
        }

        if (enabledTargets.contains(KmpTarget.WATCHOS_ARM32) && targets.contains(KmpTarget.WATCHOS_ARM32)) {
            setupMultiplatformWatchosArm32(project)
        }
        if (enabledTargets.contains(KmpTarget.WATCHOS_ARM64) && targets.contains(KmpTarget.WATCHOS_ARM64)) {
            setupMultiplatformWatchosArm64(project)
        }
        if (enabledTargets.contains(KmpTarget.WATCHOS_X64) && targets.contains(KmpTarget.WATCHOS_X64)) {
            setupMultiplatformWatchosX64(project)
        }
        if (enabledTargets.contains(KmpTarget.WATCHOS_X86) && targets.contains(KmpTarget.WATCHOS_X86)) {
            setupMultiplatformWatchosX86(project)
        }

        return true
    }

    private static void setupMultiplatformCommon(Project project) {
        project.kotlin {
            sourceSets {
                commonMain {

                }

                commonTest {
                    dependencies {
                        implementation kotlin("test-common")
                        implementation kotlin("test-annotations-common")
                    }
                }

                nonAndroidMain {
                    dependsOn commonMain
                }

                nonAndroidTest {
                    dependsOn commonTest
                }

                nativeMain {
                    dependsOn commonMain
                }

                nativeTest {
                    dependsOn commonTest
                }

                nonNativeMain {
                    dependsOn commonMain
                }

                nonNativeTest {
                    dependsOn commonTest
                }
            }
        }
    }

    private static void setupMultiplatformAndroid(Project project) {
        project.kotlin {
            android()

            sourceSets {
                androidMain {
                    dependsOn nonNativeMain
                }

                androidTest {
                    dependsOn nonNativeTest

                    dependencies {
                        implementation kotlin("test-junit")
                    }
                }
            }
        }
    }

    private static void setupAndroid(Project project, KmpTarget android) {
        project.android {
            compileSdkVersion android.getCompileSdk()
            buildToolsVersion android.getBuildTools()

            defaultConfig {
                minSdkVersion android.getMinSdk()
                targetSdkVersion android.getCompileSdk()
            }

            compileOptions {
                sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
            }

            kotlinOptions {
                jvmTarget = '1.8'
            }
        }
    }

    private static void setupMultiplatformIosArm32(Project project) {
        project.kotlin {
            iosArm32()

            sourceSets {
                iosArm32Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                iosArm32Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformIosArm64(Project project) {
        project.kotlin {
            iosArm64()

            sourceSets {
                iosArm64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                iosArm64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformIosX64(Project project) {
        project.kotlin {
            iosX64()

            sourceSets {
                iosX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                iosX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformJs(
            Project project,
            boolean isIr,
            boolean isLegacy
    ) {
        project.kotlin {
            js(isIr ? (isLegacy ? BOTH : IR) : LEGACY) {
                browser()
                nodejs()
            }

            sourceSets {
                jsMain {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                jsTest {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest

                    dependencies {
                        implementation kotlin("test-js")
                    }
                }
            }
        }
    }

    private static void setupMultiplatformJvm(Project project) {
        project.kotlin {
            jvm()

            sourceSets {
                jvmMain {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                jvmTest {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest

                    dependencies {
                        implementation kotlin("test-junit")
                    }
                }
            }
        }
    }

    private static void setupMultiplatformLinuxArm32Hfp(Project project) {
        project.kotlin {
            linuxArm32Hfp()

            sourceSets {
                linuxArm32HfpMain {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                linuxArm32HfpTest {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    private static void setupMultiplatformLinuxMips32(Project project) {
        project.kotlin {
            linuxMips32()

            sourceSets {
                linuxMips32Main {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                linuxMips32Test {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    @SuppressWarnings('SpellCheckingInspection')
    private static void setupMultiplatformLinuxMipsel32(Project project) {
        project.kotlin {
            linuxMipsel32()

            sourceSets {
                linuxMipsel32Main {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                linuxMipsel32Test {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    private static void setupMultiplatformLinuxX64(Project project) {
        project.kotlin {
            linuxX64()

            sourceSets {
                linuxX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                linuxX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    private static void setupMultiplatformMacosX64(Project project) {
        project.kotlin {
            macosX64()

            sourceSets {
                macosX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                macosX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformMingwX64(Project project) {
        project.kotlin {
            mingwX64()

            sourceSets {
                mingwX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                mingwX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    private static void setupMultiplatformMingwX86(Project project) {
        project.kotlin {
            mingwX86()

            sourceSets {
                mingwX86Main {
                    dependsOn nonAndroidMain
                    dependsOn nonNativeMain
                }

                mingwX86Test {
                    dependsOn nonAndroidTest
                    dependsOn nonNativeTest
                }
            }
        }
    }

    private static void setupMultiplatformTvosArm64(Project project) {
        project.kotlin {
            tvosArm64()

            sourceSets {
                tvosArm64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                tvosArm64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void  setupMultiplatformTvosX64(Project project) {
        project.kotlin {
            tvosX64()

            sourceSets {
                tvosX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                tvosX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformWatchosArm32(Project project) {
        project.kotlin {
            watchosArm32()

            sourceSets {
                watchosArm32Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                watchosArm32Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformWatchosArm64(Project project) {
        project.kotlin {
            watchosArm64()

            sourceSets {
                watchosArm64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                watchosArm64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformWatchosX64(Project project) {
        project.kotlin {
            watchosX64()

            sourceSets {
                watchosX64Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                watchosX64Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static void setupMultiplatformWatchosX86(Project project) {
        project.kotlin {
            watchosX86()

            sourceSets {
                watchosX86Main {
                    dependsOn nonAndroidMain
                    dependsOn nativeMain
                }

                watchosX86Test {
                    dependsOn nonAndroidTest
                    dependsOn nativeTest
                }
            }
        }
    }

    private static Set<KmpTarget> getEnabledTargets(Project project) {

        Object propertyTargets = project.findProperty("KMP_TARGETS")

        if (propertyTargets != null && !((String) propertyTargets).isEmpty()) {
            String[] targets = ((String) propertyTargets).split(",")
            HashSet<KmpTarget> set = new HashSet<>(targets.length)

            for (String target : targets) {
                try {
                    set.add(KmpTarget.valueOf(target))
                } catch (Exception e) {
                    e.printStackTrace()
                }
            }

            if (set.isEmpty()) {
                throw new IllegalArgumentException(
                        "KMP_TARGETS property is set but did not contain any matching values"
                )
            }

            return set
        } else {
            System.out.println(
                    "\nWARNING: KMP_TARGETS property not found..." +
                    "\n         Enabling all targets for project '" + project.name + "'\n"
            )
            return EnumSet.allOf(KmpTarget.class)
        }
    }
}
