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

        private val ALL_ENV_PROPERTY_TARGETS: Set<String> = setOf(
            // jvm
            KmpTarget.JVM.JVM.envPropertyValue,
            KmpTarget.JVM.ANDROID.envPropertyValue,

            // js
            KmpTarget.JS.Browser.envPropertyValue,
            KmpTarget.JS.Node.envPropertyValue,

            // native.unix.darwin
            KmpTarget.IOS.ARM32.envPropertyValue,
            KmpTarget.IOS.ARM64.envPropertyValue,
            KmpTarget.IOS.X64.envPropertyValue,

            KmpTarget.MACOS.X64.envPropertyValue,

            KmpTarget.TVOS.ARM64.envPropertyValue,
            KmpTarget.TVOS.X64.envPropertyValue,

            KmpTarget.WATCHOS.ARM32.envPropertyValue,
            KmpTarget.WATCHOS.ARM64.envPropertyValue,
            KmpTarget.WATCHOS.X64.envPropertyValue,
            KmpTarget.WATCHOS.X86.envPropertyValue,

            // native.unix.linux
            KmpTarget.LINUX.ARM32HFP.envPropertyValue,
            KmpTarget.LINUX.MIPS32.envPropertyValue,
            KmpTarget.LINUX.MIPSEL32.envPropertyValue,
            KmpTarget.LINUX.X64.envPropertyValue,

            // native.mingw
            KmpTarget.MINGW.X64.envPropertyValue,
            KmpTarget.MINGW.X86.envPropertyValue,
        )
    }

    @Suppress("unused")
    fun setupMultiplatform(targets: List<KmpTarget>): Boolean {
        if (targets.isEmpty()) {
            return false
        }

        val enabledEnvironmentTargets: Set<String> = getEnabledEnvironmentTargets(project)

        var android: KmpTarget.JVM.ANDROID? = null
        if (enabledEnvironmentTargets.contains(KmpTarget.JVM.ANDROID.envPropertyValue)) {
            for (target in targets) {
                if (target is KmpTarget.JVM.ANDROID) {
                    android = target
                    break
                }
            }

            if (android != null) {
                project.plugins.apply("com.android.library")
            }
        }

        project.plugins.apply("org.jetbrains.kotlin.multiplatform")

        setupMultiplatformCommon(project, targets)

        android?.setupMultiplatform(project)

        for (target in targets) {
            if (target !is KmpTarget.JVM.ANDROID && enabledEnvironmentTargets.contains(target.envPropertyValue)) {
                target.setupMultiplatform(project)
            }
        }

        return true
    }

    private fun setupMultiplatformCommon(project: Project, targets: List<KmpTarget>) {
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

                if (targets.filterIsInstance<KmpTarget.JVM>().isNotEmpty()) {
                    maybeCreate(KmpTarget.JVM.COMMON_JVM_MAIN).dependsOn(getByName(COMMON_MAIN))
                    maybeCreate(KmpTarget.JVM.COMMON_JVM_TEST).dependsOn(getByName(COMMON_TEST))
                }
            }
        }
    }

    private fun getEnabledEnvironmentTargets(project: Project): Set<String> {
        val propertyTargets: Any? = project.findProperty("KMP_TARGETS")

        return if (propertyTargets != null && propertyTargets is String && propertyTargets.isNotEmpty()) {

            val enabledTargets: Set<String> = propertyTargets.split(",").mapNotNull { propertyTarget ->
                if (ALL_ENV_PROPERTY_TARGETS.contains(propertyTarget)) {
                    propertyTarget
                } else {
                    println(
                        "\nWARNING: KMP_TARGET environment property '$propertyTarget' not recognized..."
                    )
                    null
                }
            }.toSet()

            if (enabledTargets.isEmpty()) {
                throw IllegalArgumentException(
                    "KMP_TARGETS environment variable is set, but did not contain any recognized values"
                )
            }

            enabledTargets
        } else {
            println(
                "\nWARNING: KMP_TARGETS environment variable not set... " +
                "Enabling all targets for project '" + project.name + "'\n"
            )
            ALL_ENV_PROPERTY_TARGETS
        }
    }
}
