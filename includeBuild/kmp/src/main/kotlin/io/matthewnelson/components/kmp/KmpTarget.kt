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

package io.matthewnelson.components.kmp

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * A sealed class with the same heirarchical structure as how the source sets
 * will be setup (see diagram in [KmpConfigurationPlugin]).
 * */
sealed class KmpTarget {

    companion object {
        const val COMMON_MAIN = KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME
        const val COMMON_TEST = KotlinSourceSet.COMMON_TEST_SOURCE_SET_NAME

        private const val MAIN = "Main"
        private const val TEST = "Test"
    }

    protected abstract val mainSourceSet: (KotlinSourceSet.() -> Unit)?
    protected abstract val testSourceSet: (KotlinSourceSet.() -> Unit)?

    abstract val sourceSetMainName: String
    abstract val sourceSetTestName: String
    abstract val envPropertyValue: String

    @JvmSynthetic
    internal abstract fun setupMultiplatform(project: Project)

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other is KmpTarget) {
            return other.toString() == this.toString()
        }

        return false
    }

    override fun hashCode(): Int {
        var result = 17
        result = result * 31 + toString().hashCode()
        return result
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}()"
    }

    sealed class JVM: KmpTarget() {

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

                        testSourceSet?.invoke(this@testSourceSet)
                    }
                }
            }
        }

        class JVM(
            override val target: (KotlinJvmTarget.() -> Unit)? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        ) : KmpTarget.JVM(), TargetCallback<KotlinJvmTarget> {

            companion object {
                val DEFAULT: JVM = JVM()

                const val TARGET_NAME: String = "jvm"
                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                const val ENV_PROPERTY_VALUE: String = "JVM"
            }

            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    jvm(TARGET_NAME) target@ {
                        target?.invoke(this@target)
                    }

                    setupJvmSourceSets(project)
                }
            }
        }

        class ANDROID(
            private val buildTools: String,
            private val compileSdk: Int,
            private val minSdk: Int,
            private val targetSdk: Int,
            manifestPath: String,
            private val kotlinJvmTarget: String = "1.8",
            private val androidConfig: (BaseExtension.() -> Unit)? = null,
            override val target: (KotlinAndroidTarget.() -> Unit)? = null,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        ) : KmpTarget.JVM(), TargetCallback<KotlinAndroidTarget> {

            companion object {
                const val TARGET_NAME: String = "android"
                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                const val ENV_PROPERTY_VALUE: String = "ANDROID"
            }

            private val manifestPath: String? = manifestPath.ifEmpty { null }

            init {
                require(buildTools.isNotEmpty()) { "ANDROID.buildTools cannot be null or empty" }
                require(compileSdk >= 1) { "ANDROID.compileSdk must be greater than 0" }
                require(minSdk >= 1) { "ANDROID.minSdk must be greater than 0" }
                require(targetSdk >= 1) { "ANDROID.targetSdk must be greater than 0" }
                require(targetSdk >= minSdk) { "ANDROID.targetSdk must be greater than ANDROID.minSdk" }
                require(compileSdk >= minSdk) { "ANDROID.compileSdk must be greater than ANDROID.minSdk" }
            }

            constructor(
                buildTools: String,
                compileSdk: Int,
                minSdk: Int,
                targetSdk: Int,
                kotlinJvmTarget: String = "1.8",
                androidConfig: (BaseExtension.() -> Unit)? = null,
                target: (KotlinAndroidTarget.() -> Unit)? = null,
                mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            ): this(
                buildTools,
                compileSdk,
                minSdk,
                targetSdk,
                "",
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
                project.kotlin {
                    android(TARGET_NAME) target@{

                        target?.invoke(this@target)

                        compilations.all {
                            kotlinOptions.jvmTarget = kotlinJvmTarget
                        }
                    }

                    setupJvmSourceSets(project)
                }

                project.extensions.configure(BaseExtension::class.java) config@ {
                    compileSdkVersion(this@ANDROID.compileSdk)
                    buildToolsVersion(this@ANDROID.buildTools)

                    this@ANDROID.manifestPath?.let { path -> sourceSets.getByName("main").manifest.srcFile(path) }

                    defaultConfig {
                        minSdkVersion(this@ANDROID.minSdk)
                        targetSdkVersion(this@ANDROID.targetSdk)

                        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
                    }

                    compileOptions {
                        sourceCompatibility(JavaVersion.VERSION_1_8)
                        targetCompatibility(JavaVersion.VERSION_1_8)
                    }

                    androidConfig?.invoke(this@config)
                }
            }

            override fun toString(): String {
                return  "ANDROID(" +
                        "buildTools=" + buildTools + "," +
                        "compileSdk=" + compileSdk + "," +
                        "manifestPath=" + manifestPath + "," +
                        "minSdk=" + minSdk + "," +
                        "targetSdk=" + targetSdk + "," +
                        "kotlinJvmTarget=" + kotlinJvmTarget +
                        ")"
            }
        }

    }

    sealed class NON_JVM: KmpTarget() {

        companion object {
            const val NON_JVM_MAIN = "nonJvmMain"
            const val NON_JVM_TEST = "nonJvmTest"
        }

        class JS(
            private val compilerType: KotlinJsCompilerType,
            private val browser: Browser?,
            private val node: Node?,
            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
        ) : NON_JVM() {

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

                        sourceSets {
                            maybeCreate(sourceSetMainName).apply mainSourceSet@ {
                                dependsOn(getByName(NON_JVM_MAIN))

                                mainSourceSet?.invoke(this@mainSourceSet)
                            }
                            maybeCreate(sourceSetTestName).apply testSourceSet@ {
                                dependsOn(getByName(NON_JVM_TEST))

                                if (testSourceSet?.invoke(this@testSourceSet) == null) {
                                    dependencies {
                                        implementation(kotlin("test-js"))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun toString(): String {
                return  "JS(" +
                        "compilerType=" + compilerType + "," +
                        "browser=" + browser.toString() + "," +
                        "node=" + node.toString() +
                        ")"
            }

        }

        sealed class NATIVE: NON_JVM() {

            companion object {
                const val NATIVE_COMMON_MAIN = "nativeCommon$MAIN"
                const val NATIVE_COMMON_TEST = "nativeCommon$TEST"
            }

            sealed class UNIX: NATIVE() {

                companion object {
                    const val UNIX_COMMON_MAIN = "unixCommon$MAIN"
                    const val UNIX_COMMON_TEST = "unixCommon$TEST"
                }

                sealed class DARWIN: UNIX() {

                    companion object {
                        const val DARWIN_COMMON_MAIN = "darwinCommon$MAIN"
                        const val DARWIN_COMMON_TEST = "darwinCommon$TEST"
                    }

                    protected fun setupDarwinSourceSets(project: Project) {
                        project.kotlin {
                            sourceSets {
                                maybeCreate(sourceSetMainName).apply sourceSetMain@ {
                                    dependsOn(getByName(DARWIN_COMMON_MAIN))

                                    mainSourceSet?.invoke(this@sourceSetMain)
                                }
                                maybeCreate(sourceSetTestName).apply sourceSetTest@ {
                                    dependsOn(getByName(DARWIN_COMMON_TEST))

                                    testSourceSet?.invoke(this@sourceSetTest)
                                }
                            }
                        }
                    }

                    sealed class IOS : DARWIN() {

                        class ARM32(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : IOS(), TargetCallback<KotlinNativeTarget> {

                            companion object {
                                val DEFAULT = ARM32()

                                const val TARGET_NAME: String = "iosArm32"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                project.kotlin {
                                    iosArm32(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class ARM64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                            override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                        ) : IOS(), TargetCallback<KotlinNativeTarget> {

                            companion object {
                                val DEFAULT = ARM64()

                                const val TARGET_NAME: String = "iosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "IOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                project.kotlin {
                                    iosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : IOS(), TargetCallback<KotlinNativeTarget> {

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
                                project.kotlin {
                                    iosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class MACOS : DARWIN() {

                        class X64(
                            override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : MACOS(), TargetCallback<KotlinNativeTargetWithHostTests> {

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
                                project.kotlin {
                                    macosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class TVOS : DARWIN() {

                        class ARM64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : TVOS(), TargetCallback<KotlinNativeTarget> {

                            companion object {
                                val DEFAULT = ARM64()

                                const val TARGET_NAME: String = "tvosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "TVOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                project.kotlin {
                                    tvosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : TVOS(), TargetCallback<KotlinNativeTarget> {

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
                                project.kotlin {
                                    tvosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }

                    sealed class WATCHOS : DARWIN() {

                        class ARM32(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : WATCHOS(), TargetCallback<KotlinNativeTarget> {

                            companion object {
                                val DEFAULT = ARM32()

                                const val TARGET_NAME: String = "watchosArm32"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM32"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                project.kotlin {
                                    watchosArm32(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class ARM64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : WATCHOS(), TargetCallback<KotlinNativeTarget> {

                            companion object {
                                val DEFAULT = ARM64()

                                const val TARGET_NAME: String = "watchosArm64"
                                const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                                const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                                const val ENV_PROPERTY_VALUE: String = "WATCHOS_ARM64"
                            }

                            override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                            override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                            override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                            override fun setupMultiplatform(project: Project) {
                                project.kotlin {
                                    watchosArm64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X64(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : WATCHOS(), TargetCallback<KotlinNativeTarget> {

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
                                project.kotlin {
                                    watchosX64(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                        class X86(
                            override val target: (KotlinNativeTarget.() -> Unit)? = null,
                            override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                            override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        ) : WATCHOS(), TargetCallback<KotlinNativeTarget> {

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
                                project.kotlin {
                                    watchosX86(TARGET_NAME) target@ {
                                        target?.invoke(this@target)
                                    }

                                    setupDarwinSourceSets(project)
                                }
                            }
                        }

                    }
                }

                sealed class LINUX : UNIX() {

                    companion object {
                        const val LINUX_COMMON_MAIN = "linuxCommonMain"
                        const val LINUX_COMMON_TEST = "linuxCommonTest"
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

                    class ARM32HFP(
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    ) : LINUX(), TargetCallback<KotlinNativeTarget> {

                        companion object {
                            val DEFAULT = ARM32HFP()

                            const val TARGET_NAME: String = "linuxArm32Hfp"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_ARM32HFP"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            project.kotlin {
                                linuxArm32Hfp(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class MIPS32(
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    ) : LINUX(), TargetCallback<KotlinNativeTarget> {

                        companion object {
                            val DEFAULT = MIPS32()

                            const val TARGET_NAME: String = "linuxMips32"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPS32"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            project.kotlin {
                                linuxMips32(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class MIPSEL32(
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    ) : LINUX(), TargetCallback<KotlinNativeTarget> {

                        companion object {
                            val DEFAULT = MIPSEL32()

                            const val TARGET_NAME: String = "linuxMipsel32"
                            const val SOURCE_SET_MAIN_NAME: String = "$TARGET_NAME$MAIN"
                            const val SOURCE_SET_TEST_NAME: String = "$TARGET_NAME$TEST"
                            const val ENV_PROPERTY_VALUE: String = "LINUX_MIPSEL32"
                        }

                        override val sourceSetMainName: String get() = SOURCE_SET_MAIN_NAME
                        override val sourceSetTestName: String get() = SOURCE_SET_TEST_NAME
                        override val envPropertyValue: String get() = ENV_PROPERTY_VALUE

                        override fun setupMultiplatform(project: Project) {
                            project.kotlin {
                                linuxMipsel32(TARGET_NAME) target@ {
                                    target?.invoke(this@target)
                                }

                                setupLinuxSourceSets(project)
                            }
                        }
                    }

                    class X64(
                        override val target: (KotlinNativeTarget.() -> Unit)? = null,
                        override val mainSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                        override val testSourceSet: (KotlinSourceSet.() -> Unit)? = null,
                    ) : LINUX(), TargetCallback<KotlinNativeTarget> {

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

            sealed class MINGW : NATIVE() {

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
                    override val target: (KotlinNativeTargetWithHostTests.() -> Unit)? = null,
                    override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                    override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                ) : MINGW(), TargetCallback<KotlinNativeTargetWithHostTests> {

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
                        project.kotlin {
                            mingwX64(TARGET_NAME) target@ {
                                target?.invoke(this@target)
                            }

                            setupMingwSourceSets(project)
                        }
                    }
                }

                class X86(
                    override val target: (KotlinNativeTarget.() -> Unit)? = null,
                    override val mainSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                    override val testSourceSet: ((KotlinSourceSet) -> Unit)? = null,
                ) : MINGW(), TargetCallback<KotlinNativeTarget> {

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