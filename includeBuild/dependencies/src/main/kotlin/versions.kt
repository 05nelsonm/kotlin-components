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
 * Versions for all dependencies
 * */
object versions {

    object android {
        // https://developer.android.com/studio/releases/build-tools
        const val buildTools                = "33.0.1"
        const val sdkCompile                = 33
        const val sdkMin16                  = 16
        const val sdkMin19                  = 19
        const val sdkMin21                  = 21
        const val sdkMin23                  = 23
        const val sdkMin26                  = 26
        const val sdkTarget                 = 33
    }

    object androidx {
        const val annotation                = "1.5.0"
        const val appCompat                 = "1.5.1"
        const val camera                    = "1.2.0"
        const val cameraExt                 = "1.2.0"
        const val constraintLayout          = "2.1.4"
        const val core                      = "1.9.0"
        const val exifInterface             = "1.3.5"
        const val lifecycle                 = "2.5.1"
        const val navigation                = "2.5.3"
        const val media                     = "1.6.0"
        const val paging3                   = "3.1.1"
        const val recyclerView              = "1.2.1"
        const val securityCrypto            = "1.0.0"
        const val sqlite                    = "2.0.1"
    }

    object components {
        const val buildConfig               = "3.0.5"
        const val coroutines                = "1.1.5"
        const val encoding                  = "1.2.1"

        object kmptor {
            const val binary                = "4.7.12-2"
            const val kmptor                = "1.3.3"
        }

        const val parcelize                 = "0.1.2"
        const val request                   = "3.0.6"
        const val secureRandom              = "0.1.2"
        const val valueClazz                = "0.1.0"
    }

    object google {
        const val hilt                      = "2.44.2"
        const val guava                     = "31.1"
        const val material                  = "1.7.0"
        const val mlKitBarcodeScanning      = "17.0.3"
        const val zxing                     = "3.5.1"
    }

    const val insetter                      = "0.6.0"

    object instacart {
        const val coil                      = "2.2.2"
    }

    object javax {
        const val inject                    = "1"
    }

    const val jnrUnixSocket                 = "0.38.19"

    object kodein {
        const val di                        = "7.17.0"
    }

    object kotlin {
        const val atomicfu                  = "0.19.0"
        const val coroutines                = "1.6.4"
        const val kotlin                    = "1.8.0"
        const val ktor                      = "2.2.2"

        object parcelize {
            const val runtime               = kotlin
        }

        object serialization {
            const val json                  = "1.4.1"
        }

        const val time                      = "0.4.0"
    }

    const val kotlinResult                  = "1.1.17"

    object square {
        const val okhttp                    = "4.10.0"
        const val okio                      = "3.3.0"
        const val leakCanary                = "2.10"
        const val moshi                     = "1.14.0"
        const val sqlDelight                = "1.5.5"
        const val turbine                   = "0.12.1"
    }

    object sql {
        const val cipher                    = "4.5.3"
        const val requery                   = "3.36.0"
    }

    object toxicity {
        const val rsaApiKeyValidator        = "2.0.4"
    }

    const val viewBindingDelegate           = "1.5.6"

    object gradle {
        const val android                   = "7.3.1"

        const val atomicfu                  = versions.kotlin.atomicfu
        const val binaryCompat              = "0.12.1"
        const val dokka                     = "1.7.20"
        const val gradleVersions            = "0.44.0"
        const val kotlin                    = versions.kotlin.kotlin
        const val hilt                      = versions.google.hilt
        const val intellij                  = "0.4.26"
        const val mavenPublish              = "0.18.0"

        // 3.0.0+ update pluginDeps.npmPublish
        const val npmPublish                = "2.1.2"

        const val navigation                = versions.androidx.navigation
        const val serialization             = versions.kotlin.kotlin
        const val sqlDelight                = versions.square.sqlDelight
    }

    object test {
        object androidx {
            const val archCore              = "2.1.0"
            const val core                  = "1.5.0"
            const val espresso              = "3.5.1"
            const val junit                 = "1.1.5"
        }

        object google {
            const val hilt                  = versions.google.hilt
        }

        const val junit                     = "4.12"

        object kotlin {
            const val coroutines            = versions.kotlin.coroutines
        }

        const val robolectric               = "4.9.2"
        const val turbine                   = versions.square.turbine
    }

}
