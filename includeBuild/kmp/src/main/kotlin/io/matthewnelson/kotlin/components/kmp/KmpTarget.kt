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
@file:Suppress("unused", "ClassName", "SpellCheckingInspection")

package io.matthewnelson.kotlin.components.kmp

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import com.android.build.gradle.BaseExtension
import io.matthewnelson.kotlin.components.kmp.util.kotlin
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * A sealed class with the same heirarchical structure as how the source sets
 * will be setup (see diagram in [KmpConfigurationPlugin]).
 * */
sealed class KmpTarget<T: KotlinTarget> {

    companion object {
        private const val MAIN = "Main"
        private const val TEST = "Test"
    }

    object SetNames {

        val COMMON_MAIN get() = KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME
        val COMMON_TEST get() = KotlinSourceSet.COMMON_TEST_SOURCE_SET_NAME

        val JVM_COMMON_MAIN get() = Jvm.JVM_COMMON_MAIN
        val JVM_COMMON_TEST get() = Jvm.JVM_COMMON_TEST

        val JVM_MAIN get() = Jvm.Jvm.SOURCE_SET_MAIN_NAME
        val JVM_TEST get() = Jvm.Jvm.SOURCE_SET_TEST_NAME

        val ANDROID_MAIN get() = Jvm.Android.SOURCE_SET_MAIN_NAME
        val ANDROID_TEST get() = Jvm.Android.SOURCE_SET_TEST_NAME

        val NON_JVM_MAIN get() = NonJvm.NON_JVM_MAIN
        val NON_JVM_TEST get() = NonJvm.NON_JVM_TEST

        val JS_MAIN get() = NonJvm.JS.SOURCE_SET_MAIN_NAME
        val JS_TEST get() = NonJvm.JS.SOURCE_SET_TEST_NAME

        val NATIVE_COMMON_MAIN get() = NonJvm.Native.NATIVE_COMMON_MAIN
        val NATIVE_COMMON_TEST get() = NonJvm.Native.NATIVE_COMMON_TEST

        val UNIX_COMMON_MAIN get() = NonJvm.Native.Unix.UNIX_COMMON_MAIN
        val UNIX_COMMON_TEST get() = NonJvm.Native.Unix.UNIX_COMMON_TEST

        val DARWIN_COMMON_MAIN get() = NonJvm.Native.Unix.Darwin.DARWIN_COMMON_MAIN
        val DARWIN_COMMON_TEST get() = NonJvm.Native.Unix.Darwin.DARWIN_COMMON_TEST

        val IOS_MAIN get() = NonJvm.Native.Unix.Darwin.Ios.All.SOURCE_SET_MAIN_NAME
        val IOS_TEST get() = NonJvm.Native.Unix.Darwin.Ios.All.SOURCE_SET_TEST_NAME
        val IOS_ARM32_MAIN get() = NonJvm.Native.Unix.Darwin.Ios.Arm32.SOURCE_SET_MAIN_NAME
        val IOS_ARM32_TEST get() = NonJvm.Native.Unix.Darwin.Ios.Arm32.SOURCE_SET_TEST_NAME
        val IOS_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Ios.Arm64.SOURCE_SET_MAIN_NAME
        val IOS_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Ios.Arm64.SOURCE_SET_TEST_NAME
        val IOS_X64_MAIN get() = NonJvm.Native.Unix.Darwin.Ios.X64.SOURCE_SET_MAIN_NAME
        val IOS_X64_TEST get() = NonJvm.Native.Unix.Darwin.Ios.X64.SOURCE_SET_TEST_NAME
        val IOS_SIMULATOR_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Ios.SimulatorArm64.SOURCE_SET_MAIN_NAME
        val IOS_SIMULATOR_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Ios.SimulatorArm64.SOURCE_SET_TEST_NAME

        val MACOS_COMMON_MAIN get() = NonJvm.Native.Unix.Darwin.Macos.MACOS_COMMON_MAIN
        val MACOS_COMMON_TEST get() = NonJvm.Native.Unix.Darwin.Macos.MACOS_COMMON_TEST
        val MACOS_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Macos.Arm64.SOURCE_SET_MAIN_NAME
        val MACOS_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Macos.Arm64.SOURCE_SET_TEST_NAME
        val MACOS_X64_MAIN get() = NonJvm.Native.Unix.Darwin.Macos.X64.SOURCE_SET_MAIN_NAME
        val MACOS_X64_TEST get() = NonJvm.Native.Unix.Darwin.Macos.X64.SOURCE_SET_TEST_NAME

        val TVOS_MAIN get() = NonJvm.Native.Unix.Darwin.Tvos.All.SOURCE_SET_MAIN_NAME
        val TVOS_TEST get() = NonJvm.Native.Unix.Darwin.Tvos.All.SOURCE_SET_TEST_NAME
        val TVOS_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Tvos.Arm64.SOURCE_SET_MAIN_NAME
        val TVOS_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Tvos.Arm64.SOURCE_SET_TEST_NAME
        val TVOS_X64_MAIN get() = NonJvm.Native.Unix.Darwin.Tvos.X64.SOURCE_SET_MAIN_NAME
        val TVOS_X64_TEST get() = NonJvm.Native.Unix.Darwin.Tvos.X64.SOURCE_SET_TEST_NAME
        val TVOS_SIMULATOR_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Tvos.SimulatorArm64.SOURCE_SET_MAIN_NAME
        val TVOS_SIMULATOR_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Tvos.SimulatorArm64.SOURCE_SET_TEST_NAME

        val WATCHOS_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.All.SOURCE_SET_MAIN_NAME
        val WATCHOS_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.All.SOURCE_SET_TEST_NAME
        val WATCHOS_ARM32_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.Arm32.SOURCE_SET_MAIN_NAME
        val WATCHOS_ARM32_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.Arm32.SOURCE_SET_TEST_NAME
        val WATCHOS_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.Arm64.SOURCE_SET_MAIN_NAME
        val WATCHOS_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.Arm64.SOURCE_SET_TEST_NAME
        val WATCHOS_X64_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.X64.SOURCE_SET_MAIN_NAME
        val WATCHOS_X64_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.X64.SOURCE_SET_TEST_NAME
        val WATCHOS_X86_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.X86.SOURCE_SET_MAIN_NAME
        val WATCHOS_X86_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.X86.SOURCE_SET_TEST_NAME
        val WATCHOS_SIMULATOR_ARM64_MAIN get() = NonJvm.Native.Unix.Darwin.Watchos.SimulatorArm64.SOURCE_SET_MAIN_NAME
        val WATCHOS_SIMULATOR_ARM64_TEST get() = NonJvm.Native.Unix.Darwin.Watchos.SimulatorArm64.SOURCE_SET_TEST_NAME

        val LINUX_COMMON_MAIN get() = NonJvm.Native.Unix.Linux.LINUX_COMMON_MAIN
        val LINUX_COMMON_TEST get() = NonJvm.Native.Unix.Linux.LINUX_COMMON_TEST
        val LINUX_ARM32HFP_MAIN get() = NonJvm.Native.Unix.Linux.Arm32Hfp.SOURCE_SET_MAIN_NAME
        val LINUX_ARM32HFP_TEST get() = NonJvm.Native.Unix.Linux.Arm32Hfp.SOURCE_SET_TEST_NAME
        val LINUX_MIPS32_MAIN get() = NonJvm.Native.Unix.Linux.Mips32.SOURCE_SET_MAIN_NAME
        val LINUX_MIPS32_TEST get() = NonJvm.Native.Unix.Linux.Mips32.SOURCE_SET_TEST_NAME
        val LINUX_MIPSEL32_MAIN get() = NonJvm.Native.Unix.Linux.Mipsel32.SOURCE_SET_MAIN_NAME
        val LINUX_MIPSEL32_TEST get() = NonJvm.Native.Unix.Linux.Mipsel32.SOURCE_SET_TEST_NAME
        val LINUX_X64_MAIN get() = NonJvm.Native.Unix.Linux.X64.SOURCE_SET_MAIN_NAME
        val LINUX_X64_TEST get() = NonJvm.Native.Unix.Linux.X64.SOURCE_SET_TEST_NAME

        val MINGW_COMMON_MAIN get() = NonJvm.Native.Mingw.MINGW_COMMON_MAIN
        val MINGW_COMMON_TEST get() = NonJvm.Native.Mingw.MINGW_COMMON_TEST
        val MINGW_X64_MAIN get() = NonJvm.Native.Mingw.X64.SOURCE_SET_MAIN_NAME
        val MINGW_X64_TEST get() = NonJvm.Native.Mingw.X64.SOURCE_SET_TEST_NAME
        val MINGW_X86_MAIN get() = NonJvm.Native.Mingw.X86.SOURCE_SET_MAIN_NAME
        val MINGW_X86_TEST get() = NonJvm.Native.Mingw.X86.SOURCE_SET_TEST_NAME

    }

    abstract val target: (T.() -> Unit)?
    abstract val mainSourceSet: (KotlinSourceSet.() -> Unit)?
    abstract val testSourceSet: (KotlinSourceSet.() -> Unit)?

    abstract val sourceSetMainName: String
    abstract val sourceSetTestName: String
    abstract val pluginIds: Set<String>?
    internal abstract val envPropertyValue: String

    @JvmSynthetic
    internal abstract fun setupMultiplatform(project: Project)

    protected fun applyPlugins(project: Project) {
        pluginIds?.let { ids ->
            for (id in ids) {
                project.plugins.apply(id)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        return other is KmpTarget<*> && other.toString() == this.toString()
    }

    override fun hashCode(): Int {
        return 17 * 31 + toString().hashCode()
    }

    override fun toString(): String {
        return "${this.javaClass.toString().split("$").takeLast(2).joinToString(".")}()"
    }

    sealed class Jvm<T: KotlinTarget>: KmpTarget<T>() {

        companion object {
            const val JVM_COMMON_MAIN = "jvmCommon$MAIN"
            const val JVM_COMMON_TEST = "jvmCommon$TEST"
        }

        protected fun setupJvmSourceSets(project: Project) {
            project.kotlin {
                sourceSets {
                    maybeCreate(sourceSetMainName).apply mainSourceSet@ {
                        dependsOn(getByName(JVM_COMMON_MAIN))

                        mainSourceSet?.invoke(this@mainSourceSet)
                    }
                    maybeCreate(sourceSetTestName).apply testSourceSet@ {
                        dependsOn(getByName(JVM_COMMON_TEST))

                        if (this@Jvm is Android) {
                            dependsOn(getByName("androidAndroidTestRelease"))
                        }

                        testSourceSet?.invoke(this@testSourceSet)
                    }
                }
            }
        }

        class Jvm(
            override val pluginIds: Set<String>? = null,
            val kotlinJvmTarget: JavaVersion = JavaVersion.VERSION_11,
            override val target: (KotlinJvmTarget.() -> Unit)? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
        ) : KmpTarget.Jvm<KotlinJvmTarget>() {

            companion object {
                val DEFAULT: Jvm = Jvm()

                const val TARGET_NAME: String = "jvm"
                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                const val ENV_PROPERTY_VALUE: String = "JVM"
            }

            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    jvm(TARGET_NAME) target@ {
                        target?.invoke(this@target)

                        compilations.all {
                            kotlinOptions.jvmTarget = kotlinJvmTarget.toString()
                        }
                    }

                    setupJvmSourceSets(project)
                }
            }
        }

        class Android(
            val compileSdk: Int,
            val minSdk: Int,
            manifestPath: String,
            override val pluginIds: Set<String>? = null,
            val buildTools: String? = null,
            val targetSdk: Int? = null,
            val compileSourceOption: JavaVersion = JavaVersion.VERSION_11,
            val compileTargetOption: JavaVersion = compileSourceOption,
            val kotlinJvmTarget: JavaVersion = JavaVersion.VERSION_11,
            val androidConfig: (BaseExtension.() -> Unit)? = null,
            override val target: (KotlinAndroidTarget.() -> Unit)? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
        ) : KmpTarget.Jvm<KotlinAndroidTarget>() {

            companion object {
                const val TARGET_NAME: String = "android"
                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                const val ENV_PROPERTY_VALUE: String = "ANDROID"
            }

            val manifestPath: String? = manifestPath.ifEmpty { null }

            init {
                require(buildTools?.isNotEmpty() ?: true) { "ANDROID.buildTools cannot be null or empty" }
                require(compileSdk >= 1) { "ANDROID.compileSdk must be greater than 0" }
                require(minSdk >= 1) { "ANDROID.minSdk must be greater than 0" }
                require(if (targetSdk != null) targetSdk >= 1 else true) { "ANDROID.targetSdk must be greater than 0" }
                require(if (targetSdk != null) targetSdk >= minSdk else true) { "ANDROID.targetSdk must be greater than ANDROID.minSdk" }
                require(compileSdk >= minSdk) { "ANDROID.compileSdk must be greater than ANDROID.minSdk" }
            }

            constructor(
                compileSdk: Int,
                minSdk: Int,
                pluginIds: Set<String>? = null,
                buildTools: String? = null,
                targetSdk: Int? = null,
                compileSourceOption: JavaVersion = JavaVersion.VERSION_11,
                compileTargetOption: JavaVersion = compileSourceOption,
                kotlinJvmTarget: JavaVersion = JavaVersion.VERSION_11,
                androidConfig: (BaseExtension.() -> Unit)? = null,
                target: (KotlinAndroidTarget.() -> Unit)? = null,
                mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                testSourceSet: (KotlinSourceSet.() -> Unit)? = null
            ): this(
                compileSdk,
                minSdk,
                "",
                pluginIds,
                buildTools,
                targetSdk,
                compileSourceOption,
                compileTargetOption,
                kotlinJvmTarget,
                androidConfig,
                target,
                mainSourceSet,
                testSourceSet
            )

            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    android(TARGET_NAME) target@ {

                        target?.invoke(this@target)

                        compilations.all {
                            kotlinOptions.jvmTarget = kotlinJvmTarget.toString()
                        }
                    }

                    setupJvmSourceSets(project)
                }

                project.extensions.configure(BaseExtension::class) config@ {
                    compileSdkVersion(this@Android.compileSdk)
                    this@Android.buildTools?.let { buildToolsVersion = it }

                    this@Android.manifestPath?.let { path -> sourceSets.getByName("main").manifest.srcFile(path) }

                    defaultConfig {
                        minSdk = this@Android.minSdk
                        this@Android.targetSdk?.let { targetSdk = it }

                        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
                    }

                    compileOptions {
                        sourceCompatibility = compileSourceOption
                        targetCompatibility = compileTargetOption
                    }

                    androidConfig?.invoke(this@config)
                }
            }
        }

    }

    sealed class NonJvm<T: KotlinTarget>: KmpTarget<T>() {

        companion object {
            const val NON_JVM_MAIN = "nonJvmMain"
            const val NON_JVM_TEST = "nonJvmTest"
        }

        class JS(
            val compilerType: KotlinJsCompilerType,
            val browser: Browser?,
            val node: Node?,
            override val target: (KotlinJsTargetDsl.() -> Unit)? = null,
            override val pluginIds: Set<String>? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
        ) : NonJvm<KotlinJsTargetDsl>() {

            class Browser(val jsBrowserDsl: (KotlinJsBrowserDsl.() -> Unit)? = null) {
                override fun toString(): String {
                    return  "Browser(" +
                            "jsBrowserDsl=" +
                            if (jsBrowserDsl == null) {
                                "null"
                            } else {
                                "{ /* omitted */ }"
                            } +
                            ")"
                }
            }

            class Node(val jsNodeDsl: (KotlinJsNodeDsl.() -> Unit)? = null) {
                override fun toString(): String {
                    return  "Node(" +
                            "jsNodeDsl=" +
                            if (jsNodeDsl == null) {
                                "null"
                            } else {
                                "{ /* omitted */ }"
                            } +
                            ")"
                }
            }

            init {
                if (browser == null && node == null) {
                    throw IllegalArgumentException("Arguments 'node' or 'browser' cannot both be null")
                }
            }

            companion object {
                val DEFAULT = JS(KotlinJsCompilerType.BOTH, Browser(), Node())

                const val TARGET_NAME: String = "js"
                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                const val ENV_PROPERTY_VALUE: String = "JS"
            }

            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    js(TARGET_NAME, compilerType) jsTarget@ {

                        browser?.let { nnBrowser ->
                            browser browser@{

                                if (nnBrowser.jsBrowserDsl?.invoke(this@browser) == null) {
                                    testTask {
                                        useMocha {
                                            timeout = "30s"
                                        }
                                    }
                                }

                            }
                        }

                        node?.let { nnNode ->
                            nodejs nodejs@ {

                                if (nnNode.jsNodeDsl?.invoke(this@nodejs) == null) {
                                    testTask {
                                        useMocha {
                                            timeout = "30s"
                                        }
                                    }
                                }

                            }
                        }

                        target?.invoke(this@jsTarget)

                        sourceSets {
                            maybeCreate(sourceSetMainName).apply mainSourceSet@ {
                                dependsOn(getByName(NON_JVM_MAIN))

                                mainSourceSet?.invoke(this@mainSourceSet)
                            }
                            maybeCreate(sourceSetTestName).apply testSourceSet@ {
                                dependsOn(getByName(NON_JVM_TEST))

                                testSourceSet?.invoke(this@testSourceSet)
                            }
                        }
                    }
                }
            }
        }

        sealed class Native<T: KotlinTarget>: NonJvm<T>() {

            companion object {
                const val NATIVE_COMMON_MAIN = "nativeCommon$MAIN"
                const val NATIVE_COMMON_TEST = "nativeCommon$TEST"
            }

            sealed class Unix<T: KotlinTarget>: Native<T>() {

                companion object {
                    const val UNIX_COMMON_MAIN = "unixCommon$MAIN"
                    const val UNIX_COMMON_TEST = "unixCommon$TEST"
                }

                sealed class Darwin<T: KotlinTarget>: Unix<T>() {

                    companion object {
                        const val DARWIN_COMMON_MAIN = "darwinCommon$MAIN"
                        const val DARWIN_COMMON_TEST = "darwinCommon$TEST"
                    }

                    protected fun setupDarwinSourceSets(project: Project) {
                        project.kotlin {
                            sourceSets {
                                maybeCreate(sourceSetMainName).apply sourceSetMain@ {
                                    if (this@Darwin is Macos) {
                                        dependsOn(getByName(Macos.MACOS_COMMON_MAIN))
                                    } else {
                                        dependsOn(getByName(DARWIN_COMMON_MAIN))
                                    }

                                    mainSourceSet?.invoke(this@sourceSetMain)
                                }
                                maybeCreate(sourceSetTestName).apply sourceSetTest@ {
                                    if (this@Darwin is Macos) {
                                        dependsOn(getByName(Macos.MACOS_COMMON_TEST))
                                    } else {
                                        dependsOn(getByName(DARWIN_COMMON_TEST))
                                    }

                                    testSourceSet?.invoke(this@sourceSetTest)
                                }
                            }
                        }
                    }

                    sealed class Ios<T: KotlinNativeTarget> : Darwin<T>() {

                        /**
                         * To enable the [SimulatorArm64] target, set [enableSimulator] value.
                         * It will create a new instance of [SimulatorArm64] and utilize your
                         * already declared [mainSourceSet] and [testSourceSet] values.
                         *
                         * If you need something different for the Main and Test sourceSets as
                         * they pertain to the SimulatorArm64 functionality, simply leave
                         * [enableSimulator] null, and pass the additional [KmpTarget] in the
                         * [Set] during configuration.
                         * */
                        class All(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            val enableSimulator: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ): Ios<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = All()

                                const val TARGET_NAME: String = "ios"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_ALL"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    ios(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }

                                if (enableSimulator != null) {
                                    SimulatorArm64(
                                        pluginIds = null,
                                        target = enableSimulator,
                                        mainSourceSet = mainSourceSet,
                                        testSourceSet = testSourceSet
                                    ).setupMultiplatform(project)
                                }
                            }
                        }

                        class Arm32(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Ios<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Arm32()

                                const val TARGET_NAME: String = "iosArm32"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosArm32(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                            override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null
                        ) : Ios<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Arm64()

                                const val TARGET_NAME: String = "iosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Ios<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = X64()

                                const val TARGET_NAME: String = "iosX64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_X64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class SimulatorArm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Ios<KotlinNativeTargetWithSimulatorTests>() {

                            companion object {
                                val DEFAULT = SimulatorArm64()

                                const val TARGET_NAME: String = "iosSimulatorArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosSimulatorArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class Macos : Darwin<KotlinNativeTargetWithHostTests>() {

                        companion object {
                            const val MACOS_COMMON_MAIN = "macosCommon$MAIN"
                            const val MACOS_COMMON_TEST = "macosCommon$TEST"
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Macos() {

                             companion object {
                                 val DEFAULT = Arm64()

                                 const val TARGET_NAME: String = "macosArm64"
                                 const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                 const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                 const val ENV_PROPERTY_VALUE: String = "MACOS_ARM64"
                             }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    macosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Macos() {

                             companion object {
                                 val DEFAULT = X64()

                                 const val TARGET_NAME: String = "macosX64"
                                 const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                 const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                 const val ENV_PROPERTY_VALUE: String = "MACOS_X64"
                             }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    macosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class Tvos<T: KotlinNativeTarget> : Darwin<T>() {

                        /**
                         * To enable the [SimulatorArm64] target, set [enableSimulator] value.
                         * It will create a new instance of [SimulatorArm64] and utilize your
                         * already declared [mainSourceSet] and [testSourceSet] values.
                         *
                         * If you need something different for the Main and Test sourceSets as
                         * they pertain to the SimulatorArm64 functionality, simply leave
                         * [enableSimulator] null, and pass the additional [KmpTarget] in the
                         * [Set] during configuration.
                         * */
                        class All(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            val enableSimulator: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Tvos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = All()

                                const val TARGET_NAME: String = "tvos"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "TVOS_ALL"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvos(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }

                                if (enableSimulator != null) {
                                    SimulatorArm64(
                                        pluginIds = null,
                                        target = enableSimulator,
                                        mainSourceSet = mainSourceSet,
                                        testSourceSet = testSourceSet
                                    ).setupMultiplatform(project)
                                }
                            }
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Tvos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Arm64()

                                const val TARGET_NAME: String = "tvosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "TVOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Tvos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = X64()

                                const val TARGET_NAME: String = "tvosX64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "TVOS_X64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class SimulatorArm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Tvos<KotlinNativeTargetWithSimulatorTests>() {

                            companion object {
                                val DEFAULT = SimulatorArm64()

                                const val TARGET_NAME: String = "tvosSimulatorArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "TVOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosSimulatorArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class Watchos<T: KotlinNativeTarget> : Darwin<T>() {

                        /**
                         * To enable the [SimulatorArm64] target, set [enableSimulator] value.
                         * It will create a new instance of [SimulatorArm64] and utilize your
                         * already declared [mainSourceSet] and [testSourceSet] values.
                         *
                         * If you need something different for the Main and Test sourceSets as
                         * they pertain to the SimulatorArm64 functionality, simply leave
                         * [enableSimulator] null, and pass the additional [KmpTarget] in the
                         * [Set] during configuration.
                         * */
                        class All(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            val enableSimulator: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = All()

                                const val TARGET_NAME: String = "watchos"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ALL"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchos(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }

                                if (enableSimulator != null) {
                                    SimulatorArm64(
                                        pluginIds = null,
                                        target = enableSimulator,
                                        mainSourceSet = mainSourceSet,
                                        testSourceSet = testSourceSet
                                    ).setupMultiplatform(project)
                                }
                            }
                        }

                        class Arm32(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Arm32()

                                const val TARGET_NAME: String = "watchosArm32"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosArm32(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Arm64()

                                const val TARGET_NAME: String = "watchosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = X64()

                                const val TARGET_NAME: String = "watchosX64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_X64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X86(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = X86()

                                const val TARGET_NAME: String = "watchosX86"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_X86"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosX86(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class SimulatorArm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithSimulatorTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTargetWithSimulatorTests>() {

                            companion object {
                                val DEFAULT = SimulatorArm64()

                                const val TARGET_NAME: String = "watchosSimulatorArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosSimulatorArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }
                }

                sealed class Linux : Unix<KotlinNativeTarget>() {

                    companion object {
                        const val LINUX_COMMON_MAIN = "linuxCommon$MAIN"
                        const val LINUX_COMMON_TEST = "linuxCommon$TEST"
                    }

                    protected fun setupLinuxSourceSets(project: Project) {
                        project.kotlin {
                            sourceSets {
                                maybeCreate(sourceSetMainName).apply sourceSetMain@ {
                                    dependsOn(getByName(LINUX_COMMON_MAIN))

                                    mainSourceSet?.invoke(this@sourceSetMain)
                                }
                                maybeCreate(sourceSetTestName).apply sourceSetTest@ {
                                    dependsOn(getByName(LINUX_COMMON_TEST))

                                    testSourceSet?.invoke(this@sourceSetTest)
                                }
                            }
                        }
                    }

                    class Arm32Hfp(
                        override val pluginIds: Set<String>? = null,
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                    ) : Linux() {

                        companion object {
                            val DEFAULT = Arm32Hfp()

                            const val TARGET_NAME: String = "linuxArm32Hfp"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_ARM32HFP"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxArm32Hfp(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class Mips32(
                        override val pluginIds: Set<String>? = null,
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                    ) : Linux() {

                        companion object {
                            val DEFAULT = Mips32()

                            const val TARGET_NAME: String = "linuxMips32"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPS32"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxMips32(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class Mipsel32(
                        override val pluginIds: Set<String>? = null,
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                    ) : Linux() {

                        companion object {
                            val DEFAULT = Mipsel32()

                            const val TARGET_NAME: String = "linuxMipsel32"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPSEL32"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxMipsel32(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class X64(
                        override val pluginIds: Set<String>? = null,
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                    ) : Linux() {

                        companion object {
                            val DEFAULT = X64()

                            const val TARGET_NAME: String = "linuxX64"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_X64"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxX64(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                }

            }

            sealed class Mingw<T: KotlinNativeTarget> : Native<T>() {

                companion object {
                    const val MINGW_COMMON_MAIN = "mingwCommon$MAIN"
                    const val MINGW_COMMON_TEST = "mingwCommon$TEST"
                }

                protected fun setupMingwSourceSets(project: Project) {
                    project.kotlin {
                        sourceSets {
                            maybeCreate(sourceSetMainName).apply sourceSetMain@ {
                                dependsOn(getByName(MINGW_COMMON_MAIN))

                                mainSourceSet?.invoke(this@sourceSetMain)
                            }
                            maybeCreate(sourceSetTestName).apply sourceSetTest@ {
                                dependsOn(getByName(MINGW_COMMON_TEST))

                                testSourceSet?.invoke(this@sourceSetTest)
                            }
                        }
                    }
                }

                class X64(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                    override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                    override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null
                ) : Mingw<KotlinNativeTargetWithHostTests>() {

                    companion object {
                        val DEFAULT = X64()

                        const val TARGET_NAME: String = "mingwX64"
                        const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                        const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                        const val ENV_PROPERTY_VALUE: String = "MINGW_X64"
                    }

                    override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                    override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            mingwX64(TARGET_NAME) target@ {
                                target?.invoke(this@target)
                            }

                            setupMingwSourceSets(project)
                        }
                    }
                }

                class X86(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                    override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null
                ) : Mingw<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = X86()

                        const val TARGET_NAME: String = "mingwX86"
                        const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                        const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                        const val ENV_PROPERTY_VALUE: String = "MINGW_X86"
                    }

                    override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                    override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            mingwX86(TARGET_NAME) target@ {
                                target?.invoke(this@target)
                            }

                            setupMingwSourceSets(project)
                        }
                    }
                }

            }
        }

    }
}