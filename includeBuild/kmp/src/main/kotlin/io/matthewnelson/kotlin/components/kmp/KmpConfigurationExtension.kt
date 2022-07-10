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
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Companion.JVM_ANDROID_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.Jvm.Companion.JVM_ANDROID_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Companion.NON_JVM_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Companion.NON_JVM_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Companion.NATIVE_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Companion.NATIVE_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Mingw.Companion.MINGW_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Mingw.Companion.MINGW_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Companion.UNIX_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Companion.UNIX_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Darwin.Companion.DARWIN_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Darwin.Companion.DARWIN_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Linux.Companion.LINUX_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.NonJvm.Native.Unix.Linux.Companion.LINUX_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.IOS_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.IOS_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.JVM_JS_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.JVM_JS_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.MACOS_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.MACOS_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.TVOS_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.TVOS_TEST
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.WATCHOS_MAIN
import io.matthewnelson.kotlin.components.kmp.KmpTarget.SetNames.WATCHOS_TEST
import io.matthewnelson.kotlin.components.kmp.util.*
import io.matthewnelson.kotlin.components.kmp.util.EnvProperty
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getting
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
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Ios.SimulatorArm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Macos.Arm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Tvos.SimulatorArm64.ENV_PROPERTY_VALUE,

            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.Arm32.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.Arm64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.X64.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.X86.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Darwin.Watchos.SimulatorArm64.ENV_PROPERTY_VALUE,

            // linux
            KmpTarget.NonJvm.Native.Unix.Linux.Arm32Hfp.ENV_PROPERTY_VALUE,
            KmpTarget.NonJvm.Native.Unix.Linux.Arm64.ENV_PROPERTY_VALUE,
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
        targets: Set<KmpTarget<*>>,
        commonPluginIds: Set<String>? = null,
        commonPluginIdsPostConfiguration: Set<String>? = null,
        commonMainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        commonTestSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        kotlin: (KotlinMultiplatformExtension.() -> Unit)? = null
    ): Boolean {
        
        check(project.rootProject != project) {
            """
                KmpConfiguration.setupMultiplatform can not be run
                from the root project's build.gradle(.kts) file.
            """.trimIndent()
        }

        if (targets.isEmpty()) {
            return false
        }

        val enabledEnvironmentTargets: Set<String> = getEnabledEnvironmentTargets(project)
        val enabledTargets: List<KmpTarget<*>> = targets.filter { enabledEnvironmentTargets.contains(it.envPropertyValue) }

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

        val (commonMain, commonTest) = setupMultiplatformCommon(project, enabledTargets)

        android?.setupMultiplatform(project)

        for (target in enabledTargets) {
            if (target !is KmpTarget.Jvm.Android) {
                target.setupMultiplatform(project)
            }
        }

        commonMainSourceSet?.invoke(commonMain)
        commonTestSourceSet?.invoke(commonTest)

        commonPluginIdsPostConfiguration?.let { ids ->
            for (id in ids) {
                project.plugins.apply(id)
            }
        }

        project.kotlin {
            sourceSets.all {
                languageSettings {
                    optIn("kotlin.RequiresOptIn")
                    KmpRootProjectConfigurationExtension.optInArgs.forEach { arg ->
                        optIn(arg)
                    }
                }
            }
            kotlin?.invoke(this)
        }

        return true
    }

    private fun setupMultiplatformCommon(
        project: Project,
        enabledTargets: List<KmpTarget<*>>,
    ): Pair<KotlinSourceSet, KotlinSourceSet> {
        var commonMain: KotlinSourceSet? = null
        var commonTest: KotlinSourceSet? = null

        project.kotlin {
            sourceSets {

                val jvmTargets = enabledTargets.filterIsInstance<KmpTarget.Jvm<*>>()

                commonMain = getByName(COMMON_MAIN) {
                    if (jvmTargets.isEmpty()) {
                        dependencies {
                            // https://youtrack.jetbrains.com/issue/KT-40333
                            implementation(kotlin("stdlib-common"))
                        }
                    }
                }

                commonTest = getByName(COMMON_TEST)

                if (jvmTargets.isNotEmpty()) {
                    maybeCreate(JVM_ANDROID_MAIN).apply {
                        dependsOn(commonMain!!)
                    }
                    maybeCreate(JVM_ANDROID_TEST).apply {
                        dependsOn(commonTest!!)
                    }
                }

                val jvmTarget = jvmTargets.filterIsInstance<KmpTarget.Jvm.Jvm>()
                val jsTarget = enabledTargets.filterIsInstance<KmpTarget.NonJvm.JS>()

                if (jvmTarget.isNotEmpty() || jsTarget.isNotEmpty()) {
                    maybeCreate(JVM_JS_MAIN).apply {
                        dependsOn(commonMain!!)
                    }
                    maybeCreate(JVM_JS_TEST).apply {
                        dependsOn(commonTest!!)
                    }
                }

                val nativeTargets = enabledTargets.filterIsInstance<KmpTarget.NonJvm.Native<*>>()

                if (jsTarget.isNotEmpty() || nativeTargets.isNotEmpty()) {
                    maybeCreate(NON_JVM_MAIN).apply {
                        dependsOn(commonMain!!)
                    }
                    maybeCreate(NON_JVM_TEST).apply {
                        dependsOn(commonTest!!)
                    }
                }

                if (nativeTargets.isNotEmpty()) {
                    maybeCreate(NATIVE_MAIN).apply {
                        dependsOn(getByName(NON_JVM_MAIN))
                    }
                    maybeCreate(NATIVE_TEST).apply {
                        dependsOn(getByName(NON_JVM_TEST))
                    }

                    val unixTargets = nativeTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix<*>>()
                    if (unixTargets.isNotEmpty()) {
                        maybeCreate(UNIX_MAIN).apply {
                            dependsOn(getByName(NON_JVM_MAIN))
                            dependsOn(getByName(NATIVE_MAIN))
                        }
                        maybeCreate(UNIX_TEST).apply {
                            dependsOn(getByName(NON_JVM_TEST))
                            dependsOn(getByName(NATIVE_TEST))
                        }

                        val darwinTargets = unixTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin<*>>()
                        if (darwinTargets.isNotEmpty()) {
                            maybeCreate(DARWIN_MAIN).apply {
                                dependsOn(getByName(NON_JVM_MAIN))
                                dependsOn(getByName(NATIVE_MAIN))
                                dependsOn(getByName(UNIX_MAIN))
                            }
                            maybeCreate(DARWIN_TEST).apply {
                                dependsOn(getByName(NON_JVM_TEST))
                                dependsOn(getByName(NATIVE_TEST))
                                dependsOn(getByName(UNIX_TEST))
                            }

                            val macosTargets = darwinTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin.Macos>()
                            if (macosTargets.isNotEmpty()) {
                                maybeCreate(MACOS_MAIN).apply {
                                    dependsOn(getByName(NON_JVM_MAIN))
                                    dependsOn(getByName(NATIVE_MAIN))
                                    dependsOn(getByName(UNIX_MAIN))
                                    dependsOn(getByName(DARWIN_MAIN))
                                }
                                maybeCreate(MACOS_TEST).apply {
                                    dependsOn(getByName(NON_JVM_TEST))
                                    dependsOn(getByName(NATIVE_TEST))
                                    dependsOn(getByName(UNIX_TEST))
                                    dependsOn(getByName(DARWIN_TEST))
                                }
                            }

                            val iosTargets = darwinTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin.Ios<*>>()
                            if (iosTargets.isNotEmpty()) {
                                maybeCreate(IOS_MAIN).apply {
                                    dependsOn(getByName(NON_JVM_MAIN))
                                    dependsOn(getByName(NATIVE_MAIN))
                                    dependsOn(getByName(UNIX_MAIN))
                                    dependsOn(getByName(DARWIN_MAIN))
                                }

                                maybeCreate(IOS_TEST).apply {
                                    dependsOn(getByName(NON_JVM_TEST))
                                    dependsOn(getByName(NATIVE_TEST))
                                    dependsOn(getByName(UNIX_TEST))
                                    dependsOn(getByName(DARWIN_TEST))
                                }
                            }

                            val tvosTargets = darwinTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin.Tvos<*>>()
                            if (tvosTargets.isNotEmpty()) {
                                maybeCreate(TVOS_MAIN).apply {
                                    dependsOn(getByName(NON_JVM_MAIN))
                                    dependsOn(getByName(NATIVE_MAIN))
                                    dependsOn(getByName(UNIX_MAIN))
                                    dependsOn(getByName(DARWIN_MAIN))
                                }
                                maybeCreate(TVOS_TEST).apply {
                                    dependsOn(getByName(NON_JVM_TEST))
                                    dependsOn(getByName(NATIVE_TEST))
                                    dependsOn(getByName(UNIX_TEST))
                                    dependsOn(getByName(DARWIN_TEST))
                                }
                            }

                            val watchosTargets = darwinTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Darwin.Watchos<*>>()
                            if (watchosTargets.isNotEmpty()) {
                                maybeCreate(WATCHOS_MAIN).apply {
                                    dependsOn(getByName(NON_JVM_MAIN))
                                    dependsOn(getByName(NATIVE_MAIN))
                                    dependsOn(getByName(UNIX_MAIN))
                                    dependsOn(getByName(DARWIN_MAIN))
                                }

                                maybeCreate(WATCHOS_TEST).apply {
                                    dependsOn(getByName(NON_JVM_TEST))
                                    dependsOn(getByName(NATIVE_TEST))
                                    dependsOn(getByName(UNIX_TEST))
                                    dependsOn(getByName(DARWIN_TEST))
                                }
                            }
                        }

                        val linuxTargets = unixTargets.filterIsInstance<KmpTarget.NonJvm.Native.Unix.Linux>()
                        if (linuxTargets.isNotEmpty()) {
                            maybeCreate(LINUX_MAIN).apply {
                                dependsOn(getByName(NON_JVM_MAIN))
                                dependsOn(getByName(NATIVE_MAIN))
                                dependsOn(getByName(UNIX_MAIN))
                            }
                            maybeCreate(LINUX_TEST).apply {
                                dependsOn(getByName(NON_JVM_TEST))
                                dependsOn(getByName(NATIVE_TEST))
                                dependsOn(getByName(UNIX_TEST))
                            }
                        }
                    }

                    val mingwTargets = nativeTargets.filterIsInstance<KmpTarget.NonJvm.Native.Mingw<*>>()
                    if (mingwTargets.isNotEmpty()) {
                        maybeCreate(MINGW_MAIN).apply {
                            dependsOn(getByName(NON_JVM_MAIN))
                            dependsOn(getByName(NATIVE_MAIN))
                        }
                        maybeCreate(MINGW_TEST).apply {
                            dependsOn(getByName(NON_JVM_TEST))
                            dependsOn(getByName(NATIVE_TEST))
                        }
                    }
                }
            }
        }

        return Pair(commonMain!!, commonTest!!)
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
