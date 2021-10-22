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

import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Companion.JVM_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Companion.JVM_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Companion.NON_JVM_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Companion.NON_JVM_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Companion.NATIVE_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Companion.NATIVE_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Mingw.Companion.MINGW_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Mingw.Companion.MINGW_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Companion.UNIX_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Companion.UNIX_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Darwin.Companion.DARWIN_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Darwin.Companion.DARWIN_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Linux.Companion.LINUX_COMMON_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Linux.Companion.LINUX_COMMON_TEST
import io.matthewnelson.kotlin.components.kmp.util.EnvProperty
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import javax.inject.Inject

/**
 * See [KmpConfigurationPlugin]
 * */
open class KmpConfigurationExtension @Inject constructor(private val project: Project) {

    companion object {

        internal val ALL_ENV_PROPERTY_TARGETS: Set<String> = setOf(
            // jvm
            KmpTarget.Jvm.Jvm.ENV_PROPERTY_VALUE,
            KmpTarget.Jvm.Android.ENV_PROPERTY_VALUE,

            // js
            KmpTarget.NonJvm.JS.ENV_PROPERTY_VALUE,

            // darwin
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.All.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.SimulatorArm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.Arm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.All.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.SimulatorArm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.All.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.Arm32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.X86.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.SimulatorArm64.ENV_PROPERTY_VALUE,

            // linux
            KmpTarget.NonJvm.Native.Unix.Linux.Arm32Hfp.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Linux.Mips32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Linux.Mipsel32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Linux.X64.ENV_PROPERTY_VALUE,

            // mingw
            KmpTarget.NonJvm.Native.Mingw.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Mingw.X86.ENV_PROPERTY_VALUE
        )
    }

    @Suppress("unused")
    fun setupMultiplatform(
        targets: Set<KmpTarget>,
        commonPluginIds: Set<String>? = null,
        commonMainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        commonTestSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        kotlin: (KotlinMultiplatformExtension.() -> Unit)? = null
    ): Boolean {
        if (targets.isEmpty()) {
            return false
        }

        val enabledEnvironmentTargets: Set<String> = getEnabledEnvironmentTargets(project)
        val enabledTargets: List<KmpTarget> = targets.filter { enabledEnvironmentTargets.contains(it.envPropertyValue) }

        if (enabledTargets.isEmpty()) {
            return false
        }

        project.plugins.apply("org.jetbrains.kotlin.multiplatform")

        var android: KmpTarget.Jvm.Android? = null
        for (target in enabledTargets) {
            if (target is KmpTarget.Jvm.Android) {
                android = target
                break
            }
        }

        if (android != null) {
            project.plugins.apply("com.android.library")
        }

        commonPluginIds?.let { ids ->
            for (id in ids) {
                project.plugins.apply(id)
            }
        }

        setupMultiplatformCommon(project, enabledTargets, commonMainSourceSet, commonTestSourceSet)

        android?.setupMultiplatform(project)

        for (target in enabledTargets) {
            if (target !is KmpTarget.Jvm.Android) {
                target.setupMultiplatform(project)
            }
        }

        if (kotlin != null) {
            project.extensions.configure<KotlinMultiplatformExtension>() {
                kotlin.invoke(this)
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

                val jvmTargets = enabledTargets.filterIsInstance<KmpTarget.Jvm>()
                if (jvmTargets.isNotEmpty()) {
                    maybeCreate(JVM_COMMON_MAIN).apply {
                        dependsOn(getByName(COMMON_MAIN))
                    }
                    maybeCreate(JVM_COMMON_TEST).apply {
                        dependsOn(getByName(COMMON_TEST))
                    }
                }

                val jsTargets = enabledTargets.filterIsInstance<KmpTarget.NonJvm.JS>()
                val nativeTargets = enabledTargets.filterIsInstance<KmpTarget.NonJvm.Native>()

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

                    val unixTargets = nativeTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix>()
                    if (unixTargets.isNotEmpty()) {
                        maybeCreate(UNIX_COMMON_MAIN).apply {
                            dependsOn(getByName(NATIVE_COMMON_MAIN))
                        }
                        maybeCreate(UNIX_COMMON_TEST).apply {
                            dependsOn(getByName(NATIVE_COMMON_TEST))
                        }

                        val darwinTargets = unixTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin>()
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

                        val linuxTargets = unixTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Linux>()
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

                    val mingwTargets = nativeTargets.filterIsInstance<KmpTarget.NonJvm.Native.Mingw>()
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

        if (EnvProperty.isEnableAllTargetsSet) {
            println("""
                
                Property 'KMP_TARGETS_ALL' is set. Overriding 'KMP_TARGETS' and enabling all.
            """.trimIndent())
            return ALL_ENV_PROPERTY_TARGETS
        }

        return if (propertyTargets != null && propertyTargets is String && propertyTargets.isNotEmpty()) {

            val enabledTargets: Set<String> = propertyTargets.split(",").mapNotNull { propertyTarget ->
                if (ALL_ENV_PROPERTY_TARGETS.contains(propertyTarget)) {
                    propertyTarget
                } else {
                    println("""
                        
                        WARNING: KMP_TARGET property '$propertyTarget' not recognized...
                    """.trimIndent())
                    null
                }
            }.toSet()

            if (enabledTargets.isEmpty()) {
                throw IllegalArgumentException(
                    "KMP_TARGETS property is set, but did not contain any recognized values"
                )
            }

            enabledTargets
        } else {
            println(
                """
                    
                    KMP_TARGETS property not set... Enabling all targets for project ${project.name}
                """.trimIndent()
            )
            ALL_ENV_PROPERTY_TARGETS
        }
    }
}
