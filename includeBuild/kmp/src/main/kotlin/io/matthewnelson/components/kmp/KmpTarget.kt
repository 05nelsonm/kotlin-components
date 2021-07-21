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

sealed class KmpTarget {

    companion object {
        const val COMMON_MAIN = KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME
        const val COMMON_TEST = KotlinSourceSet.COMMON_TEST_SOURCE_SET_NAME
    }

    abstract val sourceSetMainName: String
    abstract val sourceSetTestName: String

    abstract val envPropertyValue: String

    @JvmSynthetic
    internal abstract fun setupMultiplatform(project: Project)

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        return if (other is KmpTarget) {
            other.toString() == this.toString()
        } else false
    }

    override fun hashCode(): Int {
        var result = 17
        result = result * 31 + this.toString().hashCode()
        return result
    }

    override fun toString(): String {
        return this.javaClass.name
    }

    class ANDROID(
        private val buildTools: String,
        private val compileSdk: Int,
        private val minSdk: Int,
        private val targetSdk: Int,
        manifestPath: String,
    ) : KmpTarget() {

        private val manifestPath: String? = manifestPath.ifEmpty { null }

        constructor(
            buildTools: String,
            compileSdk: Int,
            minSdk: Int,
            targetSdk: Int,
        ): this(buildTools, compileSdk, minSdk, targetSdk, "")

        init {
            require(buildTools.isNotEmpty()) { "ANDROID.buildTools cannot be null or empty" }
            require(compileSdk >= 1) { "ANDROID.compileSdk must be greater than 0" }
            require(minSdk >= 1) { "ANDROID.minSdk must be greater than 0" }
            require(targetSdk >= 1) { "ANDROID.targetSdk must be greater than 0" }
            require(targetSdk >= minSdk) { "ANDROID.targetSdk must be greater than ANDROID.minSdk" }
            require(compileSdk >= minSdk) { "ANDROID.compileSdk must be greater than ANDROID.minSdk" }
        }

        companion object {
            const val sourceSetMainName: String = "androidMain"
            const val sourceSetTestName: String = "androidTest"
        }

        override val sourceSetMainName: String = Companion.sourceSetMainName
        override val sourceSetTestName: String = Companion.sourceSetTestName
        override val envPropertyValue: String get() = this.javaClass.simpleName

        override fun setupMultiplatform(project: Project) {
            project.kotlin {
                android()

                sourceSets {
                    maybeCreate(sourceSetMainName)

                    maybeCreate(sourceSetTestName).dependencies {
                        implementation(kotlin("test-junit"))
                    }
                }
            }

            project.extensions.configure(BaseExtension::class.java) {
                compileSdkVersion(this@ANDROID.compileSdk)
                buildToolsVersion(this@ANDROID.buildTools)

                this@ANDROID.manifestPath?.let { path -> sourceSets.getByName("main").manifest.srcFile(path) }

                defaultConfig {
                    minSdkVersion(this@ANDROID.minSdk)
                    targetSdkVersion(this@ANDROID.targetSdk)

                    testInstrumentationRunnerArguments.putIfAbsent("disableAnalytics", "true")
                }

                compileOptions {
                    sourceCompatibility(JavaVersion.VERSION_1_8)
                    targetCompatibility(JavaVersion.VERSION_1_8)
                }

                // TODO: Implement
//                kotlinOptions {
//                    jvmTarget = '1.8'
//                }
            }
        }

        override fun toString(): String {
            return  "ANDROID(" +
                    "buildTools=" + buildTools + "," +
                    "compileSdk=" + compileSdk + "," +
                    "manifestPath=" + manifestPath + "," +
                    "minSdk=" + minSdk + "," +
                    "targetSdk=" + targetSdk + ")"
        }
    }

    sealed class IOS : KmpTarget() {

        object ARM32 : IOS() {
            override val sourceSetMainName: String = "iosArm32Main"
            override val sourceSetTestName: String = "iosArm32Test"
            override val envPropertyValue: String get() = "IOS_ARM32"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    iosArm32()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object ARM64 : IOS() {
            override val sourceSetMainName: String = "iosArm64Main"
            override val sourceSetTestName: String = "iosArm64Test"
            override val envPropertyValue: String get() = "IOS_ARM64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    iosArm64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X64 : IOS() {
            override val sourceSetMainName: String = "iosX64Main"
            override val sourceSetTestName: String = "iosX64Test"
            override val envPropertyValue: String get() = "IOS_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    iosX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }

    sealed class JS : KmpTarget() {

        override fun setupMultiplatform(project: Project) { /* no-op */ }
        override val sourceSetMainName: String = "jsMain"
        override val sourceSetTestName: String = "jsTest"

        object IR : JS() {
            override val envPropertyValue: String get() = "JS_IR"
        }
        object LEGACY : JS() {
            override val envPropertyValue: String get() = "JS_LEGACY"
        }

    }

    object JVM : KmpTarget() {
        override val sourceSetMainName: String = "jvmMain"
        override val sourceSetTestName: String = "jvmTest"
        override val envPropertyValue: String get() = this.javaClass.simpleName

        override fun setupMultiplatform(project: Project) {
            project.kotlin {
                jvm()

                sourceSets {
                    maybeCreate(sourceSetMainName)
                    maybeCreate(sourceSetTestName).dependencies {
                        implementation(kotlin("test-junit"))
                    }
                }
            }
        }
    }

    sealed class LINUX : KmpTarget() {

        object ARM32HFP : LINUX() {
            override val sourceSetMainName: String = "linuxArm32HfpMain"
            override val sourceSetTestName: String = "linuxArm32HfpTest"
            override val envPropertyValue: String get() = "LINUX_ARM32HFP"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    linuxArm32Hfp()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object MIPS32 : LINUX() {
            override val sourceSetMainName: String = "linuxMips32Main"
            override val sourceSetTestName: String = "linuxMips32Test"
            override val envPropertyValue: String get() = "LINUX_MIPS32"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    linuxMips32()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object MIPSEL32 : LINUX() {
            override val sourceSetMainName: String = "linuxMipsel32Main"
            override val sourceSetTestName: String = "linuxMipsel32Test"
            override val envPropertyValue: String get() = "LINUX_MIPSEL32"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    linuxMipsel32()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X64 : LINUX() {
            override val sourceSetMainName: String = "linuxX64Main"
            override val sourceSetTestName: String = "linuxX64Test"
            override val envPropertyValue: String get() = "LINUX_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    linuxX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }

    sealed class MACOS : KmpTarget() {

        object X64 : KmpTarget() {
            override val sourceSetMainName: String = "macosX64Main"
            override val sourceSetTestName: String = "macosX64Test"
            override val envPropertyValue: String get() = "MACOS_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    macosX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }

    sealed class MINGW : KmpTarget() {

        object X64 : MINGW() {
            override val sourceSetMainName: String = "mingwX64Main"
            override val sourceSetTestName: String = "mingwX64Test"
            override val envPropertyValue: String get() = "MINGW_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    mingwX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X86 : MINGW() {
            override val sourceSetMainName: String = "mingwX86Main"
            override val sourceSetTestName: String = "mingwX86Test"
            override val envPropertyValue: String get() = "MINGW_X86"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    mingwX86()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }

    sealed class TVOS : KmpTarget() {

        object ARM64 : TVOS() {
            override val sourceSetMainName: String = "tvosArm64Main"
            override val sourceSetTestName: String = "tvosArm64Test"
            override val envPropertyValue: String get() = "TVOS_ARM64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    tvosArm64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X64 : TVOS() {
            override val sourceSetMainName: String = "tvosX64Main"
            override val sourceSetTestName: String = "tvosX64Test"
            override val envPropertyValue: String get() = "TVOS_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    tvosX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }

    sealed class WATCHOS : KmpTarget() {

        object ARM32 : WATCHOS() {
            override val sourceSetMainName: String = "watchosArm32Main"
            override val sourceSetTestName: String = "watchosArm32Test"
            override val envPropertyValue: String get() = "WATCHOS_ARM32"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    watchosArm32()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object ARM64 : WATCHOS() {
            override val sourceSetMainName: String = "watchosArm64Main"
            override val sourceSetTestName: String = "watchosArm64Test"
            override val envPropertyValue: String get() = "WATCHOS_ARM64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    watchosArm64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X64 : WATCHOS() {
            override val sourceSetMainName: String = "watchosX64Main"
            override val sourceSetTestName: String = "watchosX64Test"
            override val envPropertyValue: String get() = "WATCHOS_X64"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    watchosX64()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }
        object X86 : WATCHOS() {
            override val sourceSetMainName: String = "watchosX86Main"
            override val sourceSetTestName: String = "watchosX86Test"
            override val envPropertyValue: String get() = "WATCHOS_X86"

            override fun setupMultiplatform(project: Project) {
                project.kotlin {
                    watchosX86()

                    sourceSets {
                        maybeCreate(sourceSetMainName)
                        maybeCreate(sourceSetTestName)
                    }
                }
            }
        }

    }
}