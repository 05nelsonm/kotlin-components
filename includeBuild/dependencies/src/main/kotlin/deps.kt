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

/**
 * Runtime dependencies
 * */
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
        const val sqlite                    = "androidx.sqlite:sqlite:${versions.androidx.sqlite}"
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

            const val kmptor                = "$group:kmp-tor:${versions.components.kmptor.binary}-${versions.components.kmptor.kmptor}"
            const val common                = "$group:kmp-tor-common:${versions.components.kmptor.kmptor}"

            object controller {
                const val controller        = "$group:kmp-tor-controller:${versions.components.kmptor.kmptor}"
                const val common            = "$group:kmp-tor-controller-common:${versions.components.kmptor.kmptor}"
            }

            object extension {
                const val unixSocket        = "$group:kmp-tor-ext-unix-socket:${versions.components.kmptor.kmptor}"
            }

            object manager {
                const val manager           = "$group:kmp-tor-manager:${versions.components.kmptor.kmptor}"
                const val common            = "$group:kmp-tor-manager-common:${versions.components.kmptor.kmptor}"
            }
        }

        const val parcelize                 = "$group:parcelize:${versions.components.parcelize}"

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

    const val jnrUnixSocket                 = "com.github.jnr:jnr-unixsocket:${versions.jnrUnixSocket}"

    object kodein {
        const val di                        = "org.kodein.di:kodein-di:${versions.kodein.di}"

        object framework {
            object android {
                const val core              = "org.kodein.di:kodein-di-framework-android-core:${versions.kodein.di}"
                const val x                 = "org.kodein.di:kodein-di-framework-android-x:${versions.kodein.di}"
                const val viewModel         = "org.kodein.di:kodein-di-framework-android-x-viewmodel:${versions.kodein.di}"
                const val savedState        = "org.kodein.di:kodein-di-framework-android-x-viewmodel-savedstate:${versions.kodein.di}"
            }

            const val compose               = "org.kodein.di:kodein-di-framework-compose:${versions.kodein.di}"
            const val ktorServer            = "org.kodein.di:kodein-di-framework-ktor-server-jvm:${versions.kodein.di}"
        }
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
                const val logging           = "io.ktor:ktor-client-logging:${versions.kotlin.ktor}"
                const val darwin            = "io.ktor:ktor-client-darwin:${versions.kotlin.ktor}"
                const val okhttp            = "io.ktor:ktor-client-okhttp:${versions.kotlin.ktor}"
            }

            const val serialization         = "io.ktor:ktor-serialization-kotlinx-json:${versions.kotlin.ktor}"

            object server {
                const val cio               = "io.ktor:ktor-server-cio:${versions.kotlin.ktor}"
                const val content           = "io.ktor:ktor-server-content-negotiation:${versions.kotlin.ktor}"
            }
        }

        object parcelize {
            const val runtime               = "org.jetbrains.kotlin:kotlin-parcelize-runtime:${versions.kotlin.parcelize.runtime}"
        }

        const val reflect                   = "org.jetbrains.kotlin:kotlin-reflect:${versions.kotlin.kotlin}"

        object serialization {
            const val json                  = "org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.kotlin.serialization.json}"
        }

        object stdLib {
            const val common                = "org.jetbrains.kotlin:kotlin-stdlib-common:${versions.kotlin.kotlin}"
            const val jdk8                  = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${versions.kotlin.kotlin}"
            const val js                    = "org.jetbrains.kotlin:kotlin-stdlib-js:${versions.kotlin.kotlin}"
        }

        const val time                      = "org.jetbrains.kotlinx:kotlinx-datetime:${versions.kotlin.time}"
    }

    object sql {
        const val cipher                    = "net.zetetic:android-database-sqlcipher:${versions.sql.cipher}"
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
