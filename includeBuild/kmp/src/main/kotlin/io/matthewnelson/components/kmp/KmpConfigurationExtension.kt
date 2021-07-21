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
package io.matthewnelson.components.kmp

import io.matthewnelson.components.kmp.KmpTarget.Companion.COMMON_MAIN
import io.matthewnelson.components.kmp.KmpTarget.Companion.COMMON_TEST
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import javax.inject.Inject

open class KmpConfigurationExtension @Inject constructor(private val project: Project) {

    companion object {
        private val ANDROID_INSTANCE = KmpTarget.ANDROID("30.0.3", 30, 23, 30)
        private val ALL_TARGETS: Set<KmpTarget> = setOf(
            ANDROID_INSTANCE,

            KmpTarget.IOS.ARM32,
            KmpTarget.IOS.ARM64,
            KmpTarget.IOS.X64,

            KmpTarget.JS.IR,
            KmpTarget.JS.LEGACY,

            KmpTarget.JVM,

            KmpTarget.LINUX.ARM32HFP,
            KmpTarget.LINUX.MIPS32,
            KmpTarget.LINUX.MIPSEL32,
            KmpTarget.LINUX.X64,

            KmpTarget.MACOS.X64,

            KmpTarget.MINGW.X64,
            KmpTarget.MINGW.X86,

            KmpTarget.TVOS.ARM64,
            KmpTarget.TVOS.X64,

            KmpTarget.WATCHOS.ARM32,
            KmpTarget.WATCHOS.ARM64,
            KmpTarget.WATCHOS.X64,
            KmpTarget.WATCHOS.X86,
        )
    }

    @Suppress("unused")
    fun setupMultiplatform(targets: List<KmpTarget>): Boolean {
        if (targets.isEmpty()) {
            return false
        }

        val enabledTargets: Set<KmpTarget> = getEnabledTargets(project)

        var android: KmpTarget.ANDROID? = null
        if (enabledTargets.contains(ANDROID_INSTANCE)) {
            for (target in targets) {
                if (target is KmpTarget.ANDROID) {
                    android = target
                    break
                }
            }

            if (android != null) {
                project.plugins.apply("com.android.library")
            }
        }

        project.plugins.apply("org.jetbrains.kotlin.multiplatform")

        setupMultiplatformCommon(project)

        android?.setupMultiplatform(project)

        for (target in targets) {
            if (target !is KmpTarget.ANDROID && target !is KmpTarget.JS && enabledTargets.contains(target)) {
                target.setupMultiplatform(project)
            }
        }

        if (enabledTargets.contains(KmpTarget.JS.IR) || enabledTargets.contains(KmpTarget.JS.LEGACY)) {
            if (targets.contains(KmpTarget.JS.IR) || targets.contains(KmpTarget.JS.LEGACY)) {
                setupMultiplatformJs(
                    project = project,
                    isIr = targets.contains(KmpTarget.JS.IR),
                    isLegacy = targets.contains(KmpTarget.JS.LEGACY),
                )
            }
        }

        return true
    }

    private fun setupMultiplatformCommon(project: Project) {
        project.kotlin {
            sourceSets {

                getByName(COMMON_MAIN) {

                }

                getByName(COMMON_TEST) {
                    dependencies {
                        implementation(kotlin("test-common"))
                        implementation(kotlin("test-annotations-common"))
                    }
                }

                // TODO: Setup default platform specific structures
//                    maybeCreate("exampleMain").dependsOn(getByName(COMMON_MAIN))
//                    maybeCreate("exampleTest").dependsOn(getByName(COMMON_TEST))
            }
        }
    }

    private fun setupMultiplatformJs(project: Project, isIr: Boolean, isLegacy: Boolean) {
        project.kotlin {
            js(
                if (isIr && isLegacy) {
                    BOTH
                } else if (isIr && !isLegacy) {
                    IR
                } else {
                    LEGACY
                }
            ) {
                browser()
                nodejs()
            }

            sourceSets {
                // Both JS_IR and JS_LEGACY main/test names are the same
                maybeCreate(KmpTarget.JS.IR.sourceSetMainName)
                maybeCreate(KmpTarget.JS.IR.sourceSetTestName).dependencies {
                    implementation(kotlin("test-js"))
                }
            }
        }
    }

    private fun getEnabledTargets(project: Project): Set<KmpTarget> {
        val propertyTargets: Any? = project.findProperty("KMP_TARGETS")

        return if (propertyTargets != null && propertyTargets is String && propertyTargets.isNotEmpty()) {

            val map: Map<String, KmpTarget> = ALL_TARGETS.associateBy { it.envPropertyValue }

            val set: Set<KmpTarget> = propertyTargets
                .split(",")
                .mapNotNull { propertyTarget ->
                    map[propertyTarget].let { target ->
                        if (target == null) {
                            println("\nWARNING: KMP_TARGET property value '$propertyTarget' not recognized...")
                        }

                        target
                    }
                }
                .toSet()

            if (set.isEmpty()) {
                throw IllegalArgumentException("KMP_TARGETS property is set but did not contain any matching values")
            }

            set
        } else {
            println(
                "\nWARNING: KMP_TARGETS property not found..." +
                        "\n         Enabling all targets for project '" + project.name + "'\n"
            )
            ALL_TARGETS
        }
    }
}
