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
@file:Suppress("SpellCheckingInspection", "unused", "ClassName")

package io.matthewnelson.kotlin.components.dependencies

object versions {

    object android {
        const val buildTools                = "31.0.0"
        const val sdkCompile                = 31
        const val sdkMin16                  = 16
        const val sdkMin21                  = 21
        const val sdkMin23                  = 23
        const val sdkMin26                  = 26
        const val sdkTarget                 = 31
    }

    object androidx {
        const val annotation                = "1.3.0"
        const val appCompat                 = "1.4.1"
        const val camera                    = "1.1.0-beta03"
        const val cameraExt                 = "1.1.0-beta03"
        const val constraintLayout          = "2.1.3"
        const val core                      = "1.7.0"
        const val exifInterface             = "1.3.3"
        const val lifecycle                 = "2.4.1"
        const val navigation                = "2.4.2"
        const val media                     = "1.6.0"
        const val paging3                   = "3.1.1"
        const val recyclerView              = "1.2.1"
        const val securityCrypto            = "1.0.0"
    }

    object components {
        const val buildConfig               = "3.0.1"
        const val coroutines                = "1.1.1"
        const val encoding                  = "1.1.1"

        object kmptor {
            const val binary                = "0.4.7.7"
            const val kmptor                = "0.1.2"
        }

        const val request                   = "3.0.2"
    }

    object kotlin {
        const val atomicfu                  = "0.17.2"
        const val kotlin                    = "1.6.21"
        const val ktor                      = "2.0.1"
        const val coroutines                = "1.6.1"
    }

    object google {
        const val hilt                      = "2.41"
        const val guava                     = "31.1"
        const val material                  = "1.6.0"
        const val mlKitBarcodeScanning      = "17.0.2"
        const val zxing                     = "3.5.0"
    }

    const val insetter                      = "0.6.0"

    object instacart {
        const val coil                      = "1.4.0"
    }

    object javax {
        const val inject                    = "1"
    }

    object square {
        const val exhaustive                = "0.2.0"
        const val okhttp                    = "4.9.3"
        const val okio                      = "3.1.0"
        const val leakCanary                = "2.9.1"
        const val moshi                     = "1.13.0"
        const val sqlDelight                = "1.5.3"
        const val turbine                   = "0.8.0"
    }

    object sql {
        const val cipher                    = "4.5.1"
        const val requery                   = "3.36.0"
    }

    object toxicity {
        const val rsaApiKeyValidator        = "2.0.0"
    }

    const val viewBindingDelegate           = "1.5.6"

    object gradle {
        const val android                   = "7.1.3"
        const val atomicfu                  = versions.kotlin.atomicfu
        const val exhaustive                = versions.square.exhaustive
        const val dokka                     = versions.kotlin.kotlin
        const val gradleVersions            = "0.42.0"
        const val kotlin                    = versions.kotlin.kotlin
        const val hilt                      = versions.google.hilt
        const val intellij                  = "0.4.26"
        const val mavenPublish              = "0.18.0"
        const val navigation                = versions.androidx.navigation
        const val sqlDelight                = versions.square.sqlDelight
    }

    object test {
        object androidx {
            const val archCore              = "2.1.0"
            const val core                  = "1.4.0"
            const val espresso              = "3.4.0"
            const val junit                 = "1.1.3"
        }

        object google {
            const val hilt                  = versions.google.hilt
        }

        const val junit                     = "4.12"

        object kotlin {
            const val coroutines            = versions.kotlin.coroutines
        }

        const val robolectric               = "4.8.1"
        const val turbine                   = versions.square.turbine
    }

}

object deps {

    object androidx {
        const val annotation                = "androidx.annotation:annotation:${versions.androidx.annotation}"
        const val appCompat                 = "androidx.appcompat:appcompat:${versions.androidx.appCompat}"

        object camera {
            const val core                  = "androidx.camera:camera-core:${versions.androidx.camera}"
            const val camera2               = "androidx.camera:camera-camera2:${versions.androidx.camera}"
            const val extensions            = "androidx.camera:camera-extensions:${versions.androidx.cameraExt}"
            const val lifecycle             = "androidx.camera:camera-lifecycle:${versions.androidx.camera}"
            const val view                  = "androidx.camera:camera-view:${versions.androidx.cameraExt}"
        }

        const val constraintLayout          = "androidx.constraintlayout:constraintlayout:${versions.androidx.constraintLayout}"
        const val core                      = "androidx.core:core-ktx:${versions.androidx.core}"
        const val exifInterface             = "androidx.exifinterface:exifinterface:${versions.androidx.exifInterface}"

        object lifecycle {
            const val commonJava8           = "androidx.lifecycle:lifecycle-common-java8:${versions.androidx.lifecycle}"
            const val processLifecycleOwner = "androidx.lifecycle:lifecycle-process:${versions.androidx.lifecycle}"
            const val runtime               = "androidx.lifecycle:lifecycle-runtime-ktx:${versions.androidx.lifecycle}"
            const val service               = "androidx.lifecycle:lifecycle-service:${versions.androidx.lifecycle}"
            const val viewModel             = "androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.androidx.lifecycle}"
            const val viewModelSavedState   = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${versions.androidx.lifecycle}"
        }

        const val media                     = "androidx.media:media:${versions.androidx.media}"

        object navigation {
            const val fragment              = "androidx.navigation:navigation-fragment-ktx:${versions.androidx.navigation}"
            const val ui                    = "androidx.navigation:navigation-ui-ktx:${versions.androidx.navigation}"
        }

        const val paging3                   = "androidx.paging:paging-runtime:${versions.androidx.paging3}"
        const val recyclerView              = "androidx.recyclerview:recyclerview:${versions.androidx.recyclerView}"
        const val securityCrypto            = "androidx.security:security-crypto:${versions.androidx.securityCrypto}"
        const val viewBinding               = "androidx.databinding:viewbinding:${versions.gradle.android}"
    }

    object components {
        private const val group             = "io.matthewnelson.kotlin-components"

        const val buildConfig               = "$group:build-configuration:${versions.components.buildConfig}"
        const val coroutines                = "$group:coroutines:${versions.components.coroutines}"

        object encoding {
            const val base16                = "$group:encoding-base16:${versions.components.encoding}"
            const val base32                = "$group:encoding-base32:${versions.components.encoding}"
            const val base64                = "$group:encoding-base64:${versions.components.encoding}"
        }

        object kmptor {
            object binary {
                const val geoip             = "$group:kmp-tor-binary-geoip:${versions.components.kmptor.binary}"
                const val extract           = "$group:kmp-tor-binary-extract:${versions.components.kmptor.binary}"

                const val android           = "$group:kmp-tor-binary-android:${versions.components.kmptor.binary}"
                const val linuxx64          = "$group:kmp-tor-binary-linuxx64:${versions.components.kmptor.binary}"
                const val linuxx86          = "$group:kmp-tor-binary-linuxx86:${versions.components.kmptor.binary}"
                const val macosx64          = "$group:kmp-tor-binary-macosx64:${versions.components.kmptor.binary}"
                const val mingwx64          = "$group:kmp-tor-binary-mingwx64:${versions.components.kmptor.binary}"
                const val mingwx86          = "$group:kmp-tor-binary-mingwx86:${versions.components.kmptor.binary}"
            }

            const val kmptor                = "$group:kmp-tor:${versions.components.kmptor.binary}+${versions.components.kmptor.kmptor}"
            const val common                = "$group:kmp-tor-common:${versions.components.kmptor.kmptor}"

            object controller {
                const val controller        = "$group:kmp-tor-controller:${versions.components.kmptor.kmptor}"
                const val common            = "$group:kmp-tor-controller-common:${versions.components.kmptor.kmptor}"
            }

            object manager {
                const val manager           = "$group:kmp-tor-manager:${versions.components.kmptor.kmptor}"
                const val common            = "$group:kmp-tor-manager-common:${versions.components.kmptor.kmptor}"
            }
        }

        object request {
            const val concept               = "$group:request-concept:${versions.components.request}"
            const val feature               = "$group:request-feature:${versions.components.request}"

            object extensions {
                const val navigation        = "$group:request-extension-navigation:${versions.components.request}"
                const val navigationAndroid = "$group:request-extension-navigation-androidx:${versions.components.request}"
            }
        }
    }

    const val insetter                      = "dev.chrisbanes.insetter:insetter:${versions.insetter}"

    object google {
        const val hilt                      = "com.google.dagger:hilt-android:${versions.google.hilt}"

        object guava {
            const val android               = "com.google.guava:guava:${versions.google.guava}-android"
            const val jre                   = "com.google.guava:guava:${versions.google.guava}-jre"
        }

        const val material                  = "com.google.android.material:material:${versions.google.material}"
        const val mlKitBarcodeScanning      = "com.google.mlkit:barcode-scanning:${versions.google.mlKitBarcodeScanning}"
        const val zxing                     = "com.google.zxing:core:${versions.google.zxing}"
    }

    object instacart {
        object coil {
            const val base                  = "io.coil-kt:coil-base:${versions.instacart.coil}"
            const val coil                  = "io.coil-kt:coil:${versions.instacart.coil}"
            const val gif                   = "io.coil-kt:coil-gif:${versions.instacart.coil}"
            const val svg                   = "io.coil-kt:coil-svg:${versions.instacart.coil}"
            const val video                 = "io.coil-kt:coil-video:${versions.instacart.coil}"
        }
    }

    object javax {
        const val inject                    = "javax.inject:javax.inject:${versions.javax.inject}"
    }

    object kotlin {
        object atomicfu {
            const val atomicfu              = "org.jetbrains.kotlinx:atomicfu:${versions.kotlin.atomicfu}"
            const val iosArm32              = "org.jetbrains.kotlinx:atomicfu-iosarm32:${versions.kotlin.atomicfu}"
            const val iosArm64              = "org.jetbrains.kotlinx:atomicfu-iosarm64:${versions.kotlin.atomicfu}"
            const val iosSimArm64           = "org.jetbrains.kotlinx:atomicfu-iossimulatorarm64:${versions.kotlin.atomicfu}"
            const val iosX64                = "org.jetbrains.kotlinx:atomicfu-iosx64:${versions.kotlin.atomicfu}"
            const val js                    = "org.jetbrains.kotlinx:atomicfu-js:${versions.kotlin.atomicfu}"
            const val jvm                   = "org.jetbrains.kotlinx:atomicfu-jvm:${versions.kotlin.atomicfu}"
            const val linuxX64              = "org.jetbrains.kotlinx:atomicfu-linuxx64:${versions.kotlin.atomicfu}"
            const val macosArm64            = "org.jetbrains.kotlinx:atomicfu-macosarm64:${versions.kotlin.atomicfu}"
            const val macosX64              = "org.jetbrains.kotlinx:atomicfu-macosx64:${versions.kotlin.atomicfu}"
            const val mingwX64              = "org.jetbrains.kotlinx:atomicfu-mingwx64:${versions.kotlin.atomicfu}"
            const val tvosArm64             = "org.jetbrains.kotlinx:atomicfu-tvosarm64:${versions.kotlin.atomicfu}"
            const val tvosSimArm64          = "org.jetbrains.kotlinx:atomicfu-tvossimulatorarm64:${versions.kotlin.atomicfu}"
            const val tvosX64               = "org.jetbrains.kotlinx:atomicfu-tvosx64:${versions.kotlin.atomicfu}"
            const val watchosArm32          = "org.jetbrains.kotlinx:atomicfu-watchosarm32:${versions.kotlin.atomicfu}"
            const val watchosArm64          = "org.jetbrains.kotlinx:atomicfu-watchosarm64:${versions.kotlin.atomicfu}"
            const val watchosSimArm64       = "org.jetbrains.kotlinx:atomicfu-watchossimulatorarm64:${versions.kotlin.atomicfu}"
            const val watchosX64            = "org.jetbrains.kotlinx:atomicfu-watchosx64:${versions.kotlin.atomicfu}"
            const val watchosX86            = "org.jetbrains.kotlinx:atomicfu-watchosx86:${versions.kotlin.atomicfu}"
        }

        object coroutines {
            const val android               = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.kotlin.coroutines}"

            object core {
                const val core              = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.kotlin.coroutines}"
                const val iosArm32          = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm32:${versions.kotlin.coroutines}"
                const val iosArm64          = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:${versions.kotlin.coroutines}"
                const val iosSimArm64       = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iossimulatorarm64:${versions.kotlin.coroutines}"
                const val iosX64            = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:${versions.kotlin.coroutines}"
                const val js                = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${versions.kotlin.coroutines}"
                const val jvm               = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${versions.kotlin.coroutines}"
                const val linuxX64          = "org.jetbrains.kotlinx:kotlinx-coroutines-core-linuxx64:${versions.kotlin.coroutines}"
                const val macosArm64        = "org.jetbrains.kotlinx:kotlinx-coroutines-core-macosarm64:${versions.kotlin.coroutines}"
                const val macosX64          = "org.jetbrains.kotlinx:kotlinx-coroutines-core-macosx64:${versions.kotlin.coroutines}"
                const val mingwX64          = "org.jetbrains.kotlinx:kotlinx-coroutines-core-mingwx64:${versions.kotlin.coroutines}"

                const val tvosArm64         = "org.jetbrains.kotlinx:kotlinx-coroutines-core-tvosarm64:${versions.kotlin.coroutines}"
                const val tvosSimArm64      = "org.jetbrains.kotlinx:kotlinx-coroutines-core-tvossimulatorarm64:${versions.kotlin.coroutines}"
                const val tvosX64           = "org.jetbrains.kotlinx:kotlinx-coroutines-core-tvosx64:${versions.kotlin.coroutines}"
                const val watchosArm32      = "org.jetbrains.kotlinx:kotlinx-coroutines-core-watchosarm32:${versions.kotlin.coroutines}"
                const val watchosArm64      = "org.jetbrains.kotlinx:kotlinx-coroutines-core-watchosarm64:${versions.kotlin.coroutines}"
                const val watchosSimArm64   = "org.jetbrains.kotlinx:kotlinx-coroutines-core-watchossimulatorarm64:${versions.kotlin.coroutines}"
                const val watchosX64        = "org.jetbrains.kotlinx:kotlinx-coroutines-core-watchosx64:${versions.kotlin.coroutines}"
                const val watchosX86        = "org.jetbrains.kotlinx:kotlinx-coroutines-core-watchosx86:${versions.kotlin.coroutines}"
            }
        }

        object ktor {
            object client {
                const val core              = "io.ktor:ktor-client-core:${versions.kotlin.ktor}"
                const val cio               = "io.ktor:ktor-client-cio:${versions.kotlin.ktor}"
            }

            object server {
                const val core              = "io.ktor:ktor-server-core:${versions.kotlin.ktor}"
                const val netty             = "io.ktor:ktor-server-netty:${versions.kotlin.ktor}"
            }

            const val websockets            = "io.ktor:ktor-websockets:${versions.kotlin.ktor}"
        }

        const val reflect                   = "org.jetbrains.kotlin:kotlin-reflect:${versions.kotlin.kotlin}"

        object stdLib {
            const val common                = "org.jetbrains.kotlin:kotlin-stdlib-common:${versions.kotlin.kotlin}"
            const val jdk8                  = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions.kotlin.kotlin}"
            const val js                    = "org.jetbrains.kotlin:kotlin-stdlib-js:${versions.kotlin.kotlin}"
        }
    }

    object sql {
        const val cipher                    = "net.zetetic:android-database-sqlcipher:${versions.sql.cipher}"
        const val requery                   = "com.github.requery:sqlite-android:${versions.sql.requery}"
    }

    object square {

        object okhttp {
            const val logging               = "com.squareup.okhttp3:logging-interceptor:${versions.square.okhttp}"
            const val okhttp                = "com.squareup.okhttp3:okhttp:${versions.square.okhttp}"
            const val tls                   = "com.squareup.okhttp3:okhttp-tls:${versions.square.okhttp}"
        }

        object okio {
            const val okio                  = "com.squareup.okio:okio:${versions.square.okio}"
            const val nodeFileSys           = "com.squareup.okio:okio-nodefilesystem:${versions.square.okio}"
        }
        const val moshi                     = "com.squareup.moshi:moshi-kotlin:${versions.square.moshi}"

        object sqlDelight {
            const val android               = "com.squareup.sqldelight:android-driver:${versions.square.sqlDelight}"
            const val jvm                   = "com.squareup.sqldelight:sqlite-driver:${versions.square.sqlDelight}"
            const val js                    = "com.squareup.sqldelight:sqljs-driver:${versions.square.sqlDelight}"
            const val native                = "com.squareup.sqldelight:native-driver:${versions.square.sqlDelight}"
            const val runtime               = "com.squareup.sqldelight:runtime:${versions.square.sqlDelight}"

            object extensions {
                const val paging3           = "com.squareup.sqldelight:android-paging3-extensions:${versions.square.sqlDelight}"
                const val coroutines        = "com.squareup.sqldelight:coroutines-extensions:${versions.square.sqlDelight}"
            }
        }
    }

    object toxicity {
        const val rsaApiKeyValidator        = "io.toxicity:rsa-api-key-validator:${versions.toxicity.rsaApiKeyValidator}"
    }

    const val viewBindingDelegateNoReflect  = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${versions.viewBindingDelegate}"

}

object depsDebug {

    object square {
        const val leakCanary                = "com.squareup.leakcanary:leakcanary-android:${versions.square.leakCanary}"
    }

}

object depsKapt {

    object google {
        const val hilt                      = "com.google.dagger:hilt-compiler:${versions.google.hilt}"
    }

    object square {
        const val moshi                     = "com.squareup.moshi:moshi-kotlin-codegen:${versions.square.moshi}"
    }

}

object depsTest {

    object androidx {
        const val archCore                  = "androidx.arch.core:core-testing:${versions.test.androidx.archCore}"
        const val core                      = "androidx.test:core:${versions.test.androidx.core}"
        const val espresso                  = "androidx.test.espresso:espresso-core:${versions.test.androidx.espresso}"
        const val junit                     = "androidx.test.ext:junit:${versions.test.androidx.junit}"
    }

    object google {
        const val hilt                      = "com.google.dagger:hilt-android-testing:${versions.test.google.hilt}"
    }

    const val junit                         = "junit:junit:${versions.test.junit}"

    object kotlin {
        const val coroutines                = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.test.kotlin.coroutines}"
    }

    const val robolectric                   = "org.robolectric:robolectric:${versions.test.robolectric}"

    object square {
        object okio {
            const val fakeFileSys           = "com.squareup.okio:okio-fakefilesystem:${versions.square.okio}"
        }

        const val turbine                   = "app.cash.turbine:turbine:${versions.test.turbine}"
    }

}

object plugins {

    object android {
        const val gradle                    = "com.android.tools.build:gradle:${versions.gradle.android}"
    }

    object androidx {
        object navigation {
            const val safeArgs              = "androidx.navigation:navigation-safe-args-gradle-plugin:${versions.gradle.navigation}"
        }
    }

    object google {
        const val hilt                      = "com.google.dagger:hilt-android-gradle-plugin:${versions.gradle.hilt}"
    }

    const val gradleVersions                = "com.github.ben-manes:gradle-versions-plugin:${versions.gradle.gradleVersions}"
    const val intellij                      = "gradle.plugin.org.jetbrains.intellij.plugins:gradle-intellij-plugin:${versions.gradle.intellij}"

    object kotlin {
        const val atomicfu                  = "org.jetbrains.kotlinx:atomicfu-gradle-plugin:${versions.gradle.atomicfu}"
        const val dokka                     = "org.jetbrains.dokka:dokka-gradle-plugin:${versions.gradle.dokka}"
        const val gradle                    = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.gradle.kotlin}"
        const val serialization             = "org.jetbrains.kotlin:kotlin-serialization:${versions.gradle.kotlin}"
    }

    const val mavenPublish                  = "com.vanniktech:gradle-maven-publish-plugin:${versions.gradle.mavenPublish}"

    object square {
        const val exhaustive                = "app.cash.exhaustive:exhaustive-gradle:${versions.gradle.exhaustive}"
        const val sqlDelight                = "com.squareup.sqldelight:gradle-plugin:${versions.gradle.sqlDelight}"
    }

}
