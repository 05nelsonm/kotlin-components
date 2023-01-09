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
import io.matthewnelson.kotlin.components.kmp.util.sourceSetJvmJsMain
import io.matthewnelson.kotlin.components.kmp.util.sourceSetJvmJsTest
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
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

    object SetNames {
        const val COMMON_MAIN = KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME
        const val COMMON_TEST = KotlinSourceSet.COMMON_TEST_SOURCE_SET_NAME

        private const val MAIN = "Main"
        private const val TEST = "Test"

        const val JVM_ANDROID_MAIN = "jvmAndroid$MAIN"
        const val JVM_ANDROID_TEST = "jvmAndroid$TEST"

        const val JVM_MAIN = "jvm$MAIN"
        const val JVM_TEST = "jvm$TEST"

        const val ANDROID_MAIN = "android$MAIN"
        const val ANDROID_TEST_UNIT = "androidUnit$TEST"
        const val ANDROID_TEST_INSTRUMENTED = "androidInstrumented$TEST"

        const val NON_JVM_MAIN = "nonJvm$MAIN"
        const val NON_JVM_TEST = "nonJvm$TEST"

        const val JS_MAIN = "js$MAIN"
        const val JS_TEST = "js$TEST"

        const val JVM_JS_MAIN = "jvmJs$MAIN"
        const val JVM_JS_TEST = "jvmJs$TEST"

        const val NATIVE_MAIN = "native$MAIN"
        const val NATIVE_TEST = "native$TEST"

        const val UNIX_MAIN = "unix$MAIN"
        const val UNIX_TEST = "unix$TEST"

        const val DARWIN_MAIN = "darwin$MAIN"
        const val DARWIN_TEST = "darwin$TEST"

        const val IOS_MAIN = "ios$MAIN"
        const val IOS_TEST = "ios$TEST"
        const val IOS_ARM32_MAIN = "iosArm32$MAIN"
        const val IOS_ARM32_TEST = "iosArm32$TEST"
        const val IOS_ARM64_MAIN = "iosArm64$MAIN"
        const val IOS_ARM64_TEST = "iosArm64$TEST"
        const val IOS_X64_MAIN = "iosX64$MAIN"
        const val IOS_X64_TEST = "iosX64$TEST"
        const val IOS_SIMULATOR_ARM64_MAIN = "iosSimulatorArm64$MAIN"
        const val IOS_SIMULATOR_ARM64_TEST = "iosSimulatorArm64$TEST"

        const val MACOS_MAIN = "macos$MAIN"
        const val MACOS_TEST = "macos$TEST"
        const val MACOS_ARM64_MAIN = "macosArm64$MAIN"
        const val MACOS_ARM64_TEST = "macosArm64$TEST"
        const val MACOS_X64_MAIN = "macosX64$MAIN"
        const val MACOS_X64_TEST = "macosX64$TEST"

        const val TVOS_MAIN = "tvos$MAIN"
        const val TVOS_TEST = "tvos$TEST"
        const val TVOS_ARM64_MAIN = "tvosArm64$MAIN"
        const val TVOS_ARM64_TEST = "tvosArm64$TEST"
        const val TVOS_X64_MAIN = "tvosX64$MAIN"
        const val TVOS_X64_TEST = "tvosX64$TEST"
        const val TVOS_SIMULATOR_ARM64_MAIN = "tvosSimulatorArm64$MAIN"
        const val TVOS_SIMULATOR_ARM64_TEST = "tvosSimulatorArm64$TEST"

        const val WATCHOS_MAIN = "watchos$MAIN"
        const val WATCHOS_TEST = "watchos$TEST"
        const val WATCHOS_ARM32_MAIN = "watchosArm32$MAIN"
        const val WATCHOS_ARM32_TEST = "watchosArm32$TEST"
        const val WATCHOS_ARM64_MAIN = "watchosArm64$MAIN"
        const val WATCHOS_ARM64_TEST = "watchosArm64$TEST"
        const val WATCHOS_DEVICE_ARM64_MAIN = "watchosDeviceArm64$MAIN"
        const val WATCHOS_DEVICE_ARM64_TEST = "watchosDeviceArm64$TEST"
        const val WATCHOS_X64_MAIN = "watchosX64$MAIN"
        const val WATCHOS_X64_TEST = "watchosX64$TEST"
        const val WATCHOS_X86_MAIN = "watchosX86$MAIN"
        const val WATCHOS_X86_TEST = "watchosX86$TEST"
        const val WATCHOS_SIMULATOR_ARM64_MAIN = "watchosSimulatorArm64$MAIN"
        const val WATCHOS_SIMULATOR_ARM64_TEST = "watchosSimulatorArm64$TEST"

        const val LINUX_MAIN = "linux$MAIN"
        const val LINUX_TEST = "linux$TEST"
        const val LINUX_ARM32HFP_MAIN = "linuxArm32Hfp$MAIN"
        const val LINUX_ARM32HFP_TEST = "linuxArm32Hfp$TEST"
        const val LINUX_ARM64_MAIN = "linuxArm64$MAIN"
        const val LINUX_ARM64_TEST = "linuxArm64$TEST"
        const val LINUX_MIPS32_MAIN = "linuxMips32$MAIN"
        const val LINUX_MIPS32_TEST = "linuxMips32$TEST"
        const val LINUX_MIPSEL32_MAIN = "linuxMipsel32$MAIN"
        const val LINUX_MIPSEL32_TEST = "linuxMipsel32$TEST"
        const val LINUX_X64_MAIN = "linuxX64$MAIN"
        const val LINUX_X64_TEST = "linuxX64$TEST"

        const val MINGW_MAIN = "mingw$MAIN"
        const val MINGW_TEST = "mingw$TEST"
        const val MINGW_X64_MAIN = "mingwX64$MAIN"
        const val MINGW_X64_TEST = "mingwX64$TEST"
        const val MINGW_X86_MAIN = "mingwX86$MAIN"
        const val MINGW_X86_TEST = "mingwX86$TEST"

        const val ANDROID_NATIVE_MAIN = "androidNative$MAIN"
        const val ANDROID_NATIVE_TEST = "androidNative$TEST"
        const val ANDROID_ARM32_MAIN = "androidNativeArm32$MAIN"
        const val ANDROID_ARM32_TEST = "androidNativeArm32$TEST"
        const val ANDROID_ARM64_MAIN = "androidNativeArm64$MAIN"
        const val ANDROID_ARM64_TEST = "androidNativeArm64$TEST"
        const val ANDROID_X64_MAIN = "androidNativeX64$MAIN"
        const val ANDROID_X64_TEST = "androidNativeX64$TEST"
        const val ANDROID_X86_MAIN = "androidNativeX86$MAIN"
        const val ANDROID_X86_TEST = "androidNativeX86$TEST"

        const val WASM_MAIN = "wasm$MAIN"
        const val WASM_TEST = "wasm$TEST"
        const val WASM_32_MAIN = "wasm32$MAIN"
        const val WASM_32_TEST = "wasm32$TEST"
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
        return other is KmpTarget<*> && other.toString() == this.toString()
    }

    override fun hashCode(): Int {
        return 17 * 31 + toString().hashCode()
    }

    override fun toString(): String {
        return "${this.javaClass.toString().split("$").takeLast(2).joinToString(".")}()"
    }

    sealed class Jvm<T: KotlinTarget>: KmpTarget<T>() {

        protected fun KotlinMultiplatformExtension.setupJvmSourceSets() {
            sourceSets {
                getByName(sourceSetMainName).apply ss@ {
                    dependsOn(getByName(SetNames.JVM_ANDROID_MAIN))

                    if (this@Jvm is Jvm) {
                        sourceSetJvmJsMain?.let { dependsOn(it) }
                    }

                    mainSourceSet?.invoke(this@ss)
                }

                val jvmAndroidTest = getByName(SetNames.JVM_ANDROID_TEST)

                getByName(sourceSetTestName).apply ss@ {
                    dependsOn(jvmAndroidTest)

                    if (this@Jvm is Jvm) {
                        sourceSetJvmJsTest?.let { dependsOn(it) }
                    }

                    testSourceSet?.invoke(this@ss)
                }

                if (this@Jvm is Android) {
                    getByName(sourceSetTestInstrumentedName).apply ss@ {
                        dependsOn(jvmAndroidTest)

                        testSourceSetInstrumented?.invoke(this@ss)
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
                val DEFAULT = Jvm()

                const val ENV_PROPERTY_VALUE: String = "JVM"
            }

            override val sourceSetMainName: String get() = SetNames.JVM_MAIN
            override val sourceSetTestName: String get() = SetNames.JVM_TEST
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    jvm t@ {
                        target?.invoke(this@t)

                        compilations.all {
                            kotlinOptions.jvmTarget = kotlinJvmTarget.toString()
                        }
                    }

                    setupJvmSourceSets()
                }
            }
        }

        class Android(
            val compileSdk: Int,
            val minSdk: Int,
            val namespace: String? = null,
            override val pluginIds: Set<String>? = null,
            val buildTools: String? = null,
            val targetSdk: Int? = null,
            val compileSourceOption: JavaVersion = JavaVersion.VERSION_11,
            val compileTargetOption: JavaVersion = compileSourceOption,
            val kotlinJvmTarget: JavaVersion = JavaVersion.VERSION_11,
            val androidConfig: (BaseExtension.() -> Unit)? = null,
            override val target: (KotlinAndroidTarget.() -> Unit)? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            val testSourceSetInstrumented: (KotlinSourceSet.() -> Unit)? = null
        ) : KmpTarget.Jvm<KotlinAndroidTarget>() {

            companion object {
                const val ENV_PROPERTY_VALUE: String = "ANDROID"
            }

            init {
                require(buildTools?.isNotEmpty() ?: true) { "ANDROID.buildTools cannot be null or empty" }
                require(compileSdk >= 1) { "ANDROID.compileSdk must be greater than 0" }
                require(minSdk >= 1) { "ANDROID.minSdk must be greater than 0" }
                require(if (targetSdk != null) targetSdk >= 1 else true) { "ANDROID.targetSdk must be greater than 0" }
                require(if (targetSdk != null) targetSdk >= minSdk else true) { "ANDROID.targetSdk must be greater than ANDROID.minSdk" }
                require(compileSdk >= minSdk) { "ANDROID.compileSdk must be greater than ANDROID.minSdk" }
            }

            override val sourceSetMainName: String get() = SetNames.ANDROID_MAIN
            override val sourceSetTestName: String get() = SetNames.ANDROID_TEST_UNIT
            val sourceSetTestInstrumentedName: String get() = SetNames.ANDROID_TEST_INSTRUMENTED
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    android t@ {

                        target?.invoke(this@t)

                        compilations.all {
                            kotlinOptions.jvmTarget = kotlinJvmTarget.toString()
                        }
                    }

                    setupJvmSourceSets()
                }

                project.extensions.configure(BaseExtension::class) config@ {
                    compileSdkVersion(this@Android.compileSdk)
                    this@Android.buildTools?.let { buildToolsVersion = it }
                    this@config.namespace = this@Android.namespace

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

                const val ENV_PROPERTY_VALUE: String = "JS"
            }

            override val sourceSetMainName: String get() = SetNames.JS_MAIN
            override val sourceSetTestName: String get() = SetNames.JS_TEST
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                applyPlugins(project)
                project.kotlin {
                    js(compilerType) t@ {

                        browser?.let { nnBrowser ->
                            browser b@{

                                if (nnBrowser.jsBrowserDsl?.invoke(this@b) == null) {
                                    testTask {
                                        useMocha {
                                            timeout = "30s"
                                        }
                                    }
                                }

                            }
                        }

                        node?.let { nnNode ->
                            nodejs n@ {

                                if (nnNode.jsNodeDsl?.invoke(this@n) == null) {
                                    testTask {
                                        useMocha {
                                            timeout = "30s"
                                        }
                                    }
                                }

                            }
                        }

                        target?.invoke(this@t)

                        sourceSets {
                            getByName(sourceSetMainName).apply ss@ {
                                dependsOn(getByName(SetNames.NON_JVM_MAIN))

                                sourceSetJvmJsMain?.let { ss ->
                                    dependsOn(ss)
                                }

                                mainSourceSet?.invoke(this@ss)
                            }
                            getByName(sourceSetTestName).apply ss@ {
                                dependsOn(getByName(SetNames.NON_JVM_TEST))
                                sourceSetJvmJsTest?.let { ss ->
                                    dependsOn(ss)
                                }

                                testSourceSet?.invoke(this@ss)
                            }
                        }
                    }
                }
            }
        }

        sealed class Native<T: KotlinTarget>: NonJvm<T>() {

            sealed class Android<T: KotlinNativeTarget>: Native<T>() {

                companion object {
                    val ALL_DEFAULT get() = setOf(
                        Android.Arm32.DEFAULT,
                        Android.Arm64.DEFAULT,
                        Android.X64.DEFAULT,
                        Android.X86.DEFAULT
                    )
                }

                protected fun KotlinMultiplatformExtension.setupAndroidNativeSourceSets() {
                    sourceSets {
                        getByName(sourceSetMainName).apply ss@ {
                            dependsOn(getByName(SetNames.ANDROID_NATIVE_MAIN))

                            mainSourceSet?.invoke(this@ss)
                        }

                        getByName(sourceSetTestName).apply ss@ {
                            dependsOn(getByName(SetNames.ANDROID_NATIVE_TEST))

                            testSourceSet?.invoke(this@ss)
                        }
                    }
                }

                class Arm32(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Android<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Android.Arm32()

                        const val ENV_PROPERTY_VALUE: String = "ANDROID_ARM32"
                    }

                    override val sourceSetMainName: String get() = SetNames.ANDROID_ARM32_MAIN
                    override val sourceSetTestName: String get() = SetNames.ANDROID_ARM32_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            androidNativeArm32 t@ {
                                target?.invoke(this@t)
                            }

                            setupAndroidNativeSourceSets()
                        }
                    }
                }

                class Arm64(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Android<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Android.Arm64()

                        const val ENV_PROPERTY_VALUE: String = "ANDROID_ARM64"
                    }

                    override val sourceSetMainName: String get() = SetNames.ANDROID_ARM64_MAIN
                    override val sourceSetTestName: String get() = SetNames.ANDROID_ARM64_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            androidNativeArm64 t@ {
                                target?.invoke(this@t)
                            }

                            setupAndroidNativeSourceSets()
                        }
                    }
                }

                class X64(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Android<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Android.X64()

                        const val ENV_PROPERTY_VALUE: String = "ANDROID_X64"
                    }

                    override val sourceSetMainName: String get() = SetNames.ANDROID_X64_MAIN
                    override val sourceSetTestName: String get() = SetNames.ANDROID_X64_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            androidNativeX64 t@ {
                                target?.invoke(this@t)
                            }

                            setupAndroidNativeSourceSets()
                        }
                    }
                }

                class X86(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Android<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Android.X86()

                        const val ENV_PROPERTY_VALUE: String = "ANDROID_X86"
                    }

                    override val sourceSetMainName: String get() = SetNames.ANDROID_X86_MAIN
                    override val sourceSetTestName: String get() = SetNames.ANDROID_X86_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            androidNativeX86 t@ {
                                target?.invoke(this@t)
                            }

                            setupAndroidNativeSourceSets()
                        }
                    }
                }
            }

            sealed class Unix<T: KotlinTarget>: Native<T>() {

                sealed class Darwin<T: KotlinTarget>: Unix<T>() {

                    protected fun KotlinMultiplatformExtension.setupDarwinSourceSets() {
                        sourceSets {
                            getByName(sourceSetMainName).apply ss@ {
                                when (this@Darwin) {
                                    is Ios -> {
                                        dependsOn(getByName(SetNames.IOS_MAIN))
                                    }
                                    is Macos -> {
                                        dependsOn(getByName(SetNames.MACOS_MAIN))
                                    }
                                    is Tvos -> {
                                        dependsOn(getByName(SetNames.TVOS_MAIN))
                                    }
                                    is Watchos -> {
                                        dependsOn(getByName(SetNames.WATCHOS_MAIN))
                                    }
                                }

                                mainSourceSet?.invoke(this@ss)
                            }
                            getByName(sourceSetTestName).apply ss@ {
                                when (this@Darwin) {
                                    is Ios -> {
                                        dependsOn(getByName(SetNames.IOS_TEST))
                                    }
                                    is Macos -> {
                                        dependsOn(getByName(SetNames.MACOS_TEST))
                                    }
                                    is Tvos -> {
                                        dependsOn(getByName(SetNames.TVOS_TEST))
                                    }
                                    is Watchos -> {
                                        dependsOn(getByName(SetNames.WATCHOS_TEST))
                                    }
                                }

                                testSourceSet?.invoke(this@ss)
                            }
                        }
                    }

                    sealed class Ios<T: KotlinNativeTarget> : Darwin<T>() {

                        companion object {
                            val ALL_DEFAULT: Set<Ios<*>> get() = setOf(
                                Ios.Arm32.DEFAULT,
                                Ios.Arm64.DEFAULT,
                                Ios.X64.DEFAULT,
                                Ios.SimulatorArm64.DEFAULT
                            )
                        }

                        class Arm32(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Ios<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Ios.Arm32()

                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SetNames.IOS_ARM32_MAIN
                            override val sourceSetTestName: String get() = SetNames.IOS_ARM32_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosArm32 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Ios.Arm64()

                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.IOS_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.IOS_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Ios.X64()

                                const val ENV_PROPERTY_VALUE: String = "IOS_X64"
                            }

                            override val sourceSetMainName: String get() = SetNames.IOS_X64_MAIN
                            override val sourceSetTestName: String get() = SetNames.IOS_X64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosX64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Ios.SimulatorArm64()

                                const val ENV_PROPERTY_VALUE: String = "IOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.IOS_SIMULATOR_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.IOS_SIMULATOR_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    iosSimulatorArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
                                }
                            }
                        }

                    }

                    sealed class Macos : Darwin<KotlinNativeTargetWithHostTests>() {

                        companion object {
                            val ALL_DEFAULT: Set<Macos> get() = setOf(
                                Macos.Arm64.DEFAULT,
                                Macos.X64.DEFAULT
                            )
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Macos() {

                             companion object {
                                 val DEFAULT = Macos.Arm64()

                                 const val ENV_PROPERTY_VALUE: String = "MACOS_ARM64"
                             }

                            override val sourceSetMainName: String get() = SetNames.MACOS_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.MACOS_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    macosArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                 val DEFAULT = Macos.X64()

                                 const val ENV_PROPERTY_VALUE: String = "MACOS_X64"
                             }

                            override val sourceSetMainName: String get() = SetNames.MACOS_X64_MAIN
                            override val sourceSetTestName: String get() = SetNames.MACOS_X64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    macosX64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
                                }
                            }
                        }

                    }

                    sealed class Tvos<T: KotlinNativeTarget> : Darwin<T>() {

                        companion object {
                            val ALL_DEFAULT: Set<Tvos<*>> get() = setOf(
                                Tvos.Arm64.DEFAULT,
                                Tvos.X64.DEFAULT,
                                Tvos.SimulatorArm64.DEFAULT
                            )
                        }

                        class Arm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Tvos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Tvos.Arm64()

                                const val ENV_PROPERTY_VALUE: String = "TVOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.TVOS_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.TVOS_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Tvos.X64()

                                const val ENV_PROPERTY_VALUE: String = "TVOS_X64"
                            }

                            override val sourceSetMainName: String get() = SetNames.TVOS_X64_MAIN
                            override val sourceSetTestName: String get() = SetNames.TVOS_X64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosX64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Tvos.SimulatorArm64()

                                const val ENV_PROPERTY_VALUE: String = "TVOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.TVOS_SIMULATOR_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.TVOS_SIMULATOR_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    tvosSimulatorArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
                                }
                            }
                        }

                    }

                    sealed class Watchos<T: KotlinNativeTarget> : Darwin<T>() {

                        companion object {
                            val ALL_DEFAULT: Set<Watchos<*>> get() = setOf(
                                Watchos.Arm32.DEFAULT,
                                Watchos.Arm64.DEFAULT,
                                // Watchos.DeviceArm64.DEFAULT,
                                Watchos.X64.DEFAULT,
                                Watchos.X86.DEFAULT,
                                Watchos.SimulatorArm64.DEFAULT
                            )
                        }

                        class Arm32(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Watchos.Arm32()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_ARM32_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_ARM32_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosArm32 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Watchos.Arm64()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
                                }
                            }
                        }

                        class DeviceArm64(
                            override val pluginIds: Set<String>? = null,
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                        ) : Watchos<KotlinNativeTarget>() {

                            companion object {
                                val DEFAULT = Watchos.DeviceArm64()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_DEVICE_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_DEVICE_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_DEVICE_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosDeviceArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Watchos.X64()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_X64"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_X64_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_X64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosX64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Watchos.X86()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_X86"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_X86_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_X86_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosX86 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
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
                                val DEFAULT = Watchos.SimulatorArm64()

                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_SIMULATOR_ARM64"
                            }

                            override val sourceSetMainName: String get() = SetNames.WATCHOS_SIMULATOR_ARM64_MAIN
                            override val sourceSetTestName: String get() = SetNames.WATCHOS_SIMULATOR_ARM64_TEST
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                applyPlugins(project)
                                project.kotlin {
                                    watchosSimulatorArm64 t@ {
                                        target?.invoke(this@t)
                                    }

                                    setupDarwinSourceSets()
                                }
                            }
                        }

                    }
                }

                sealed class Linux : Unix<KotlinNativeTarget>() {

                    companion object {
                        val ALL_DEFAULT: Set<Linux> get() = setOf(
                            Linux.Arm32Hfp.DEFAULT,
                            Linux.Arm64.DEFAULT,
                            Linux.Mips32.DEFAULT,
                            Linux.Mipsel32.DEFAULT,
                            Linux.X64.DEFAULT
                        )
                    }

                    protected fun KotlinMultiplatformExtension.setupLinuxSourceSets() {
                        sourceSets {
                            getByName(sourceSetMainName).apply ss@ {
                                dependsOn(getByName(SetNames.LINUX_MAIN))

                                mainSourceSet?.invoke(this@ss)
                            }
                            getByName(sourceSetTestName).apply ss@ {
                                dependsOn(getByName(SetNames.LINUX_TEST))

                                testSourceSet?.invoke(this@ss)
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
                            val DEFAULT = Linux.Arm32Hfp()

                            const val ENV_PROPERTY_VALUE: String = "LINUX_ARM32HFP"
                        }

                        override val sourceSetMainName: String get() = SetNames.LINUX_ARM32HFP_MAIN
                        override val sourceSetTestName: String get() = SetNames.LINUX_ARM32HFP_TEST
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxArm32Hfp t@ {
                                    target?.invoke(this@t)
                                }

                                setupLinuxSourceSets()
                            }
                        }
                    }

                    class Arm64(
                        override val pluginIds: Set<String>? = null,
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                    ): Linux() {

                        companion object {
                            val DEFAULT = Linux.Arm64()

                            const val ENV_PROPERTY_VALUE: String = "LINUX_ARM64"
                        }

                        override val sourceSetMainName: String get() = SetNames.LINUX_ARM64_MAIN
                        override val sourceSetTestName: String get() = SetNames.LINUX_ARM64_TEST
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxArm64 t@ {
                                    target?.invoke(this@t)
                                }

                                setupLinuxSourceSets()
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
                            val DEFAULT = Linux.Mips32()

                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPS32"
                        }

                        override val sourceSetMainName: String get() = SetNames.LINUX_MIPS32_MAIN
                        override val sourceSetTestName: String get() = SetNames.LINUX_MIPS32_TEST
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxMips32 t@ {
                                    target?.invoke(this@t)
                                }

                                setupLinuxSourceSets()
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
                            val DEFAULT = Linux.Mipsel32()

                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPSEL32"
                        }

                        override val sourceSetMainName: String get() = SetNames.LINUX_MIPSEL32_MAIN
                        override val sourceSetTestName: String get() = SetNames.LINUX_MIPSEL32_TEST
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxMipsel32 t@ {
                                    target?.invoke(this@t)
                                }

                                setupLinuxSourceSets()
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
                            val DEFAULT = Linux.X64()

                            const val ENV_PROPERTY_VALUE: String = "LINUX_X64"
                        }

                        override val sourceSetMainName: String get() = SetNames.LINUX_X64_MAIN
                        override val sourceSetTestName: String get() = SetNames.LINUX_X64_TEST
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            applyPlugins(project)
                            project.kotlin {
                                linuxX64 t@ {
                                    target?.invoke(this@t)
                                }

                                setupLinuxSourceSets()
                            }
                        }
                    }

                }

            }

            sealed class Mingw<T: KotlinNativeTarget> : Native<T>() {

                companion object {
                    val ALL_DEFAULT: Set<Mingw<*>> get() = setOf(
                        Mingw.X64.DEFAULT,
                        Mingw.X86.DEFAULT
                    )
                }

                protected fun KotlinMultiplatformExtension.setupMingwSourceSets() {
                    sourceSets {
                        getByName(sourceSetMainName).apply ss@ {
                            dependsOn(getByName(SetNames.MINGW_MAIN))

                            mainSourceSet?.invoke(this@ss)
                        }
                        getByName(sourceSetTestName).apply ss@ {
                            dependsOn(getByName(SetNames.MINGW_TEST))

                            testSourceSet?.invoke(this@ss)
                        }
                    }
                }

                class X64(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Mingw<KotlinNativeTargetWithHostTests>() {

                    companion object {
                        val DEFAULT = Mingw.X64()

                        const val ENV_PROPERTY_VALUE: String = "MINGW_X64"
                    }

                    override val sourceSetMainName: String get() = SetNames.MINGW_X64_MAIN
                    override val sourceSetTestName: String get() = SetNames.MINGW_X64_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            mingwX64 t@ {
                                target?.invoke(this@t)
                            }

                            setupMingwSourceSets()
                        }
                    }
                }

                class X86(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null
                ) : Mingw<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Mingw.X86()

                        const val ENV_PROPERTY_VALUE: String = "MINGW_X86"
                    }

                    override val sourceSetMainName: String get() = SetNames.MINGW_X86_MAIN
                    override val sourceSetTestName: String get() = SetNames.MINGW_X86_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            mingwX86 t@ {
                                target?.invoke(this@t)
                            }

                            setupMingwSourceSets()
                        }
                    }
                }

            }

            sealed class Wasm<T: KotlinNativeTarget> : Native<T>() {

                companion object {
                    val ALL_DEFAULT: Set<Wasm<*>> get() = setOf(
                        Wasm._32.DEFAULT
                    )
                }

                protected fun KotlinMultiplatformExtension.setupWasmSourceSets() {
                    sourceSets {
                        getByName(sourceSetMainName).apply ss@ {
                            dependsOn(getByName(SetNames.WASM_MAIN))

                            mainSourceSet?.invoke(this@ss)
                        }
                        getByName(sourceSetTestName).apply ss@ {
                            dependsOn(getByName(SetNames.WASM_TEST))

                            testSourceSet?.invoke(this@ss)
                        }
                    }
                }

                class _32(
                    override val pluginIds: Set<String>? = null,
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                ) : Wasm<KotlinNativeTarget>() {

                    companion object {
                        val DEFAULT = Wasm._32()

                        const val ENV_PROPERTY_VALUE: String = "WASM_32"
                    }

                    override val sourceSetMainName: String get() = SetNames.WASM_32_MAIN
                    override val sourceSetTestName: String get() = SetNames.WASM_32_TEST
                    override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                    override fun setupMultiplatform(project: Project) {
                        applyPlugins(project)
                        project.kotlin {
                            wasm32 t@ {
                                target?.invoke(this@t)
                            }

                            setupWasmSourceSets()
                        }
                    }
                }
            }
        }

    }
}
