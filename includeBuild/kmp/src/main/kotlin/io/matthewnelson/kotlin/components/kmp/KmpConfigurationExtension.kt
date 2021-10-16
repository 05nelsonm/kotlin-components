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
package io.matthewnelson.kotlin.components.kmp

import io.matthewnelson.kotlin.components.kmp.KmpTarget.Companion.COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Companion.COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.JVM.Companion.JVM_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.JVM.Companion.JVM_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.Companion.NON_JVM_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.Companion.NON_JVM_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.Companion.NATIVE_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.Companion.NATIVE_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.MINGW.Companion.MINGW_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.MINGW.Companion.MINGW_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.Companion.UNIX_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.Companion.UNIX_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.Companion.DARWIN_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.Companion.DARWIN_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.Companion.LINUX_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.Companion.LINUX_COMMON_TEST
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import javax.inject.Inject

/**
 * See [KmpConfigurationPlugin]
 * */
open class KmpConfigurationExtension @Inject constructor(private val project: Project) {

    companion object {

        private val ALL_ENV_PROPERTY_TARGETS: Set<String> = setOf(
            // jvm
            KmpTarget.JVM.JVM.ENV_PROPERTY_VALUE,
            KmpTarget.JVM.ANDROID.ENV_PROPERTY_VALUE,

            // js
            KmpTarget.NON_JVM.JS.ENV_PROPERTY_VALUE,

            // darwin
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ALL.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM32.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.ARM64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.IOS.SIMULATOR_ARM64.ENV_PROPERTY_VALUE,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.MACOS.ARM64.ENV_PROPERTY_VALUE,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ALL.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.ARM64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.TVOS.SIMULATOR_ARM64.ENV_PROPERTY_VALUE,

            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ALL.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM32.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.ARM64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.X86.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN.WATCHOS.SIMULATOR_ARM64.ENV_PROPERTY_VALUE,

            // linux
            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.ARM32HFP.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPS32.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.MIPSEL32.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.UNIX.LINUX.X64.ENV_PROPERTY_VALUE,

            // mingw
            KmpTarget.NON_JVM.NATIVE.MINGW.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NON_JVM.NATIVE.MINGW.X86.ENV_PROPERTY_VALUE
        )
    }

    @Suppress("unused")
    fun setupMultiplatform(
        targets: Set<KmpTarget>,
        commonPluginIds: Set<String>? = null,
        commonMainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        commonTestSourceSet: (KotlinSourceSet.() -> Unit)? = null
    ): Boolean {
        if (targets.isEmpty()) {
            return false
        }

        val enabledEnvironmentTargets: Set<String> = getEnabledEnvironmentTargets(project)
        val enabledTargets: List<KmpTarget> = targets.filter { enabledEnvironmentTargets.contains(it.envPropertyValue) }

        var android: KmpTarget.JVM.ANDROID? = null
        for (target in enabledTargets) {
            if (target is KmpTarget.JVM.ANDROID) {
                android = target
                break
            }
        }

        if (android != null) {
            project.plugins.apply("com.android.library")
        }

        project.plugins.apply("org.jetbrains.kotlin.multiplatform")

        commonPluginIds?.let { ids ->
            for (id in ids) {
                project.plugins.apply(id)
            }
        }

        setupMultiplatformCommon(project, enabledTargets, commonMainSourceSet, commonTestSourceSet)

        android?.setupMultiplatform(project)

        for (target in enabledTargets) {
            if (target !is KmpTarget.JVM.ANDROID) {
                target.setupMultiplatform(project)
            }
        }

        return true
    }

    private fun setupMultiplatformCommon(
        project: Project,
        enabledTargets: List<KmpTarget>,
        commonMainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        commonTestSourceSet: (KotlinSourceSet.() -> Unit)? = null
    ) {
        project.kotlin {
            sourceSets {

                all {
                    languageSettings.apply {
                        optIn("kotlin.RequiresOptIn")
                    }
                }

                getByName(COMMON_MAIN) sourceSetMain@ {
                    commonMainSourceSet?.invoke(this@sourceSetMain)
                }

                getByName(COMMON_TEST) sourceSetTest@ {
                    commonTestSourceSet?.invoke(this@sourceSetTest)
                }

                val jvmTargets = enabledTargets.filterIsInstance<KmpTarget.JVM>()
                if (jvmTargets.isNotEmpty()) {
                    maybeCreate(JVM_COMMON_MAIN).apply {
                        dependsOn(getByName(COMMON_MAIN))
                    }
                    maybeCreate(JVM_COMMON_TEST).apply {
                        dependsOn(getByName(COMMON_TEST))
                    }
                }

                val jsTargets = enabledTargets.filterIsInstance<KmpTarget.NON_JVM.JS>()
                val nativeTargets = enabledTargets.filterIsInstance<KmpTarget.NON_JVM.NATIVE>()

                if (jsTargets.isNotEmpty() || nativeTargets.isNotEmpty()) {
                    maybeCreate(NON_JVM_MAIN).apply {
                        dependsOn(getByName(COMMON_MAIN))
                    }
                    maybeCreate(NON_JVM_TEST).apply {
                        dependsOn(getByName(COMMON_TEST))
                    }
                }

                if (nativeTargets.isNotEmpty()) {
                    maybeCreate(NATIVE_COMMON_MAIN).apply {
                        dependsOn(getByName(NON_JVM_MAIN))
                    }
                    maybeCreate(NATIVE_COMMON_TEST).apply {
                        dependsOn(getByName(NON_JVM_TEST))
                    }

                    val unixTargets = nativeTargets.filterIsInstance<KmpTarget.NON_JVM.NATIVE.UNIX>()
                    if (unixTargets.isNotEmpty()) {
                        maybeCreate(UNIX_COMMON_MAIN).apply {
                            dependsOn(getByName(NATIVE_COMMON_MAIN))
                        }
                        maybeCreate(UNIX_COMMON_TEST).apply {
                            dependsOn(getByName(NATIVE_COMMON_TEST))
                        }

                        val darwinTargets = unixTargets.filterIsInstance<KmpTarget.NON_JVM.NATIVE.UNIX.DARWIN>()
                        if (darwinTargets.isNotEmpty()) {
                            maybeCreate(DARWIN_COMMON_MAIN).apply {
                                dependsOn(getByName(NATIVE_COMMON_MAIN))
                                dependsOn(getByName(UNIX_COMMON_MAIN))
                            }
                            maybeCreate(DARWIN_COMMON_TEST).apply {
                                dependsOn(getByName(NATIVE_COMMON_TEST))
                                dependsOn(getByName(UNIX_COMMON_TEST))
                            }
                        }

                        val linuxTargets = unixTargets.filterIsInstance<KmpTarget.NON_JVM.NATIVE.UNIX.LINUX>()
                        if (linuxTargets.isNotEmpty()) {
                            maybeCreate(LINUX_COMMON_MAIN).apply {
                                dependsOn(getByName(NATIVE_COMMON_MAIN))
                                dependsOn(getByName(UNIX_COMMON_MAIN))
                            }
                            maybeCreate(LINUX_COMMON_TEST).apply {
                                dependsOn(getByName(NATIVE_COMMON_TEST))
                                dependsOn(getByName(UNIX_COMMON_TEST))
                            }
                        }
                    }

                    val mingwTargets = nativeTargets.filterIsInstance<KmpTarget.NON_JVM.NATIVE.MINGW>()
                    if (mingwTargets.isNotEmpty()) {
                        maybeCreate(MINGW_COMMON_MAIN).apply {
                            dependsOn(getByName(NATIVE_COMMON_MAIN))
                        }
                        maybeCreate(MINGW_COMMON_TEST).apply {
                            dependsOn(getByName(NATIVE_COMMON_TEST))
                        }
                    }
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
