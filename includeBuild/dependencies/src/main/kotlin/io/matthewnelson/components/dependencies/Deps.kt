@file:Suppress("SpellCheckingInspection", "unused", "ClassName")

package io.matthewnelson.components.dependencies

object Versions {

    const val buildTools                = "30.0.3"
    const val compileSdk                = 30
    const val minSdk                    = 16

    internal const val androidGradle    = "4.2.2"
    internal const val arch             = "2.1.0"
    internal const val camera           = "1.1.0-alpha06"
    internal const val cameraView       = "1.0.0-alpha26"
    internal const val coil             = "1.3.0"
    internal const val coroutines       = "1.5.1"
    internal const val insetter         = "0.5.0"
    internal const val lifecycle        = "2.3.1"
    internal const val hilt             = "2.37"
    internal const val hiltJetpack      = "1.0.0-alpha03"
    internal const val kotlin           = "1.5.21"
    internal const val moshi            = "1.12.0"
    internal const val navigation       = "2.3.5"
    internal const val okhttp           = "4.9.1"
    internal const val sqlDelight       = "1.5.1"
    internal const val toplAndroid      = "2.1.1"

}

object Deps {

    object androidx {

        const val annotation        = "androidx.annotation:annotation:1.2.0"
        const val appCompat         = "androidx.appcompat:appcompat:1.3.0"

        object camera {

            const val core          = "androidx.camera:camera-core:${Versions.camera}"
            const val camera2       = "androidx.camera:camera-camera2:${Versions.camera}"
            const val lifecycle     = "androidx.camera:camera-lifecycle:${Versions.camera}"
            const val view          = "androidx.camera:camera-view:${Versions.cameraView}"
            const val extensions    = "androidx.camera:camera-extensions:${Versions.cameraView}"

        }

        const val constraintLayout  = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val core              = "androidx.core:core-ktx:1.6.0"
        const val exifInterface     = "androidx.exifinterface:exifinterface:1.3.2"

        object lifecycle {

            const val commonJava8           = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
            const val hilt                  = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpack}"
            const val processLifecycleOwner = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
            const val runtime               = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val service               = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
            const val viewModel             = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
            const val viewModelSavedState   = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"

        }

        const val media             = "androidx.media:media:1.3.1"

        object navigation {

            const val fragment  = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val ui        = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

        }

        const val paging3           = "androidx.paging:paging-runtime:3.0.0"
        const val recyclerView      = "androidx.recyclerview:recyclerview:1.2.1"
        const val securityCrypto    = "androidx.security:security-crypto:1.1.0-alpha03"
        const val viewBinding       = "androidx.databinding:viewbinding:${Versions.androidGradle}"

    }

    object chrisBanes {

        const val insetter          = "dev.chrisbanes.insetter:insetter:${Versions.insetter}"
        const val insetterWidgets   = "dev.chrisbanes.insetter:insetter-widgets:${Versions.insetter}"

    }

    object google {

        const val hilt                  = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val material              = "com.google.android.material:material:1.4.0-rc01"
        const val mlKitBarcodeScanning  = "com.google.mlkit:barcode-scanning:16.2.0"
        const val zxing                 = "com.google.zxing:core:3.4.1"

    }

    object instacart {

        const val coil      = "io.coil-kt:coil:${Versions.coil}"
        const val coilBase  = "io.coil-kt:coil-base:${Versions.coil}"
        const val coilGif   = "io.coil-kt:coil-gif:${Versions.coil}"
        const val coilSvg   = "io.coil-kt:coil-svg:${Versions.coil}"
        const val coilVideo = "io.coil-kt:coil-video:${Versions.coil}"

    }

    object javax {

        const val inject    = "javax.inject:javax.inject:1"

    }

    const val jncryptor     = "org.cryptonode.jncryptor:jncryptor:1.2.0"
    const val json          = "org.json:json:20210307"

    object kotlin {

        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val coroutinesCore    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val reflect           = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    }

    const val sqlCipher     = "net.zetetic:android-database-sqlcipher:4.4.3"
    const val sqlRequery    = "com.github.requery:sqlite-android:3.36.0"

    object square {

        const val okio                      = "com.squareup.okio:okio:2.10.0"
        const val okhttp                    = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okhttpLogging             = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        const val moshi                     = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        const val sqlDelightAndroid         = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val sqlDelightAndroidPaging3  = "com.squareup.sqldelight:android-paging3-extensions:${Versions.sqlDelight}"
        const val sqlDelightCoroutines      = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val sqlDelightJvm             = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        const val sqlDelightNative          = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val sqlDelightRuntime         = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"

    }

    const val viewBindingDelegateNoReflect  = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.7"
}

object DebugDeps {

    object square {

        const val leakCanary    = "com.squareup.leakcanary:leakcanary-android:2.7"

    }

}

object Plugins {

    object android {

        const val gradle        = "com.android.tools.build:gradle:${Versions.androidGradle}"
    }

    object androidx {

        object navigation {

            const val safeArgs  = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

        }
    }

    object google {

        const val hilt          = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    }

    const val gradleVersions    = "com.github.ben-manes:gradle-Versions-plugin:0.39.0"
    const val intellijGradle    = "gradle.plugin.org.jetbrains.intellij.plugins:gradle-intellij-plugin:0.4.18"

    object kotlin {

        const val dokka         = "org.jetbrains.dokka:dokka-gradle-plugin:1.5.0"
        const val gradle        = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"

    }

    const val mavenPublish      = "com.vanniktech:gradle-maven-publish-plugin:0.17.0"

    object square {

        const val exhaustive    = "app.cash.exhaustive:exhaustive-gradle:0.2.0"
        const val sqlDelight    = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"

    }

}

object TestDeps {

    object androidx {

        const val archCore      = "androidx.arch.core:core-testing:${Versions.arch}"
        const val core          = "androidx.test:core:1.4.0"
        const val espresso      = "androidx.test.espresso:espresso-core:3.4.0"
        const val junit         = "androidx.test.ext:junit:1.1.3"

    }

    object google {

        const val hilt          = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        const val guava         = "com.google.guava:guava:30.1.1-jre"

    }

    const val junit             = "junit:junit:4.12"

    object kotlin {

        const val coroutines    = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    }

    const val robolectric       = "org.robolectric:robolectric:4.6.1"
    const val turbine           = "app.cash.turbine:turbine:0.5.2"

}

object KaptDeps {

    object google {

        const val hilt          = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    }

    object sqquare {

        const val moshiCodegen  = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    }

}