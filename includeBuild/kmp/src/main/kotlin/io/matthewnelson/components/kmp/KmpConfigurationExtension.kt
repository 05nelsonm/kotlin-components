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

            KmpTarget.JS.Browser.DEFAULT,
            KmpTarget.JS.Node.DEFAULT,

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

        val enabledEnvironmentTargets: Set<String> = getEnabledEnvironmentTargets(project)

        var android: KmpTarget.ANDROID? = null
        if (enabledEnvironmentTargets.contains(ANDROID_INSTANCE.envPropertyValue)) {
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
            if (target !is KmpTarget.ANDROID && enabledEnvironmentTargets.contains(target.envPropertyValue)) {
                target.setupMultiplatform(project)
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

    private fun getEnabledEnvironmentTargets(project: Project): Set<String> {
        val propertyTargets: Any? = project.findProperty("KMP_TARGETS")

        val allEnvPropertyValues: Set<String> = ALL_TARGETS.map { it.envPropertyValue }.toSet()

        return if (propertyTargets != null && propertyTargets is String && propertyTargets.isNotEmpty()) {

            val enabledTargets: Set<String> = propertyTargets.split(",").mapNotNull { propertyTarget ->
                if (allEnvPropertyValues.contains(propertyTarget)) {
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
            allEnvPropertyValues
        }
    }
}
