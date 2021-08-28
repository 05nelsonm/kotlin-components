@file:Suppress("SpellCheckingInspection", "unused", "ClassName")

package io.matthewnelson.components.dependencies

object Versions {
    const val buildTools                    = "30.0.3"
    const val compileSdk                    = 30
    const val minSdk16                      = 16
    const val minSdk21                      = 21
    const val minSdk23                      = 23
    const val minSdk26                      = 26

    const val androidGradle                 = "4.2.0"
    const val arch                          = "2.1.0"
    const val camera                        = "1.1.0-alpha08"
    const val cameraView                    = "1.0.0-alpha28"
    const val coil                          = "1.3.2"
    const val coroutines                    = "1.5.1"
    const val lifecycle                     = "2.3.1"
    const val hilt                          = "2.38"
    const val hiltJetpack                   = "1.0.0-alpha03"
    const val kotlin                        = "1.5.30"
    const val moshi                         = "1.12.0"
    const val navigation                    = "2.3.5"
    const val okhttp                        = "4.9.1"
    const val sqlDelight                    = "1.5.1"
}

object Deps {

    object androidx {
        const val annotation                = "androidx.annotation:annotation:1.2.0"
        const val appCompat                 = "androidx.appcompat:appcompat:1.3.1"

        object camera {
            const val core                  = "androidx.camera:camera-core:${Versions.camera}"
            const val camera2               = "androidx.camera:camera-camera2:${Versions.camera}"
            const val extensions            = "androidx.camera:camera-extensions:${Versions.cameraView}"
            const val lifecycle             = "androidx.camera:camera-lifecycle:${Versions.camera}"
            const val view                  = "androidx.camera:camera-view:${Versions.cameraView}"
        }

        const val constraintLayout          = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val core                      = "androidx.core:core-ktx:1.6.0"
        const val exifInterface             = "androidx.exifinterface:exifinterface:1.3.3"

        object lifecycle {
            const val commonJava8           = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
            const val hiltViewmodel         = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpack}"
            const val processLifecycleOwner = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
            const val runtime               = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val service               = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
            const val viewModel             = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
            const val viewModelSavedState   = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
        }

        const val media                     = "androidx.media:media:1.4.1"

        object navigation {
            const val fragment              = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val ui                    = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        const val paging3                   = "androidx.paging:paging-runtime:3.0.1"
        const val recyclerView              = "androidx.recyclerview:recyclerview:1.2.1"
        const val securityCrypto            = "androidx.security:security-crypto:1.1.0-alpha03"
        const val viewBinding               = "androidx.databinding:viewbinding:${Versions.androidGradle}"
    }

    const val insetter                      = "dev.chrisbanes.insetter:insetter:0.6.0"

    object google {
        const val hilt                      = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val material                  = "com.google.android.material:material:1.4.0-rc01"
        const val mlKitBarcodeScanning      = "com.google.mlkit:barcode-scanning:17.0.0"
        const val zxing                     = "com.google.zxing:core:3.4.1"
    }

    object instacart {
        object coil {
            const val base                  = "io.coil-kt:coil-base:${Versions.coil}"
            const val coil                  = "io.coil-kt:coil:${Versions.coil}"
            const val gif                   = "io.coil-kt:coil-gif:${Versions.coil}"
            const val svg                   = "io.coil-kt:coil-svg:${Versions.coil}"
            const val video                 = "io.coil-kt:coil-video:${Versions.coil}"
        }
    }

    object javax {
        const val inject                    = "javax.inject:javax.inject:1"
    }

    const val jncryptor                     = "org.cryptonode.jncryptor:jncryptor:1.2.0"

    object kotlin {
        object coroutines {
            const val android               = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
            const val core                  = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        }

        const val reflect                   = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

        object stdLib {
            const val common                = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
            const val jdk8                  = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
            const val js                    = "org.jetbrains.kotlin:kotlin-stdlib-js:${Versions.kotlin}"
        }
    }

    const val sqlCipher                     = "net.zetetic:android-database-sqlcipher:4.4.3"
    const val sqlRequery                    = "com.github.requery:sqlite-android:3.36.0"

    object square {
        const val okio                      = "com.squareup.okio:okio:2.10.0"

        object okhttp {
            const val logging               = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
            const val okhttp                = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        }

        const val moshi                     = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

        object sqlDelight {
            const val android               = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
            const val jvm                   = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
            const val js                    = "com.squareup.sqldelight:sqljs-driver:${Versions.sqlDelight}"
            const val native                = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
            const val runtime               = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"

            object extensions {
                const val paging3           = "com.squareup.sqldelight:android-paging3-extensions:${Versions.sqlDelight}"
                const val coroutines        = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
            }
        }
    }

    const val viewBindingDelegateNoReflect  = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.7"

}

object DebugDeps {

    object square {
        const val leakCanary                = "com.squareup.leakcanary:leakcanary-android:2.7"
    }

}

object Plugins {

    object android {
        const val gradle                    = "com.android.tools.build:gradle:${Versions.androidGradle}"
    }

    object androidx {
        object navigation {
            const val safeArgs              = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        }
    }

    object google {
        const val hilt                      = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    const val gradleVersions                = "com.github.ben-manes:gradle-versions-plugin:0.39.0"
    const val intellijGradle                = "gradle.plugin.org.jetbrains.intellij.plugins:gradle-intellij-plugin:0.4.26"

    object kotlin {
        const val dokka                     = "org.jetbrains.dokka:dokka-gradle-plugin:1.5.0"
        const val gradle                    = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val serialization             = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    }

    const val mavenPublish                  = "com.vanniktech:gradle-maven-publish-plugin:0.17.0"

    object square {
        const val exhaustive                = "app.cash.exhaustive:exhaustive-gradle:0.2.0"
        const val sqlDelight                = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
    }

}

object TestDeps {

    object androidx {
        const val archCore                  = "androidx.arch.core:core-testing:${Versions.arch}"
        const val core                      = "androidx.test:core:1.4.0"
        const val espresso                  = "androidx.test.espresso:espresso-core:3.4.0"
        const val junit                     = "androidx.test.ext:junit:1.1.3"
    }

    object google {
        const val hilt                      = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        const val guava                     = "com.google.guava:guava:30.1.1-jre"
    }

    const val junit                         = "junit:junit:4.12"

    object kotlin {
        const val coroutines                = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    const val robolectric                   = "org.robolectric:robolectric:4.6.1"
    const val turbine                       = "app.cash.turbine:turbine:0.6.0"

}

/**
 * Setting up `kapt` in KotlinMultiPlatform projects is not straight
 * forward as from the Android's source set configuration lambda you
 * don not have the ability to express `kapt("some-kapt-dep:kapt-dep:1.0")`
 * like you do from a regular `project.kotlin.dependencies` block.
 *
 * Android example configuring the hilt-compiler for it's main source set:
 *
 * dependencies {
 *     // include the dependency implementation
 *     implementation(KaptDeps.google.hilt.hilt)
 *
 *     // configure kapt to utilize it
 *     configurations.getByName(KaptDeps.kapt).dependencies.add(
 *         DefaultExternalModuleDependency(
 *             KaptDeps.google.hilt.group,
 *             KaptDeps.google.hilt.name,
 *             KaptDeps.google.hilt.version,
 *         )
 *     )
 * }
 * */
object KaptDeps {

    const val kapt                          = "kapt"

    object google {
        object hilt {
            // Needed for KMP projects to specify kapt configs
            const val group                 = "com.google.dagger"
            const val name                  = "hilt-compiler"
            const val version               = Versions.hilt

            const val hilt                  = "$group:$name:$version"
        }
    }

    object square {
        object moshi {
            object codegen {
                // Needed for KMP projects to specify kapt configs
                const val group             = "com.squareup.moshi"
                const val name              = "moshi-kotlin-codegen"
                const val version           = Versions.moshi

                const val codegen           = "$group:$name:$version"
            }
        }
    }

}
