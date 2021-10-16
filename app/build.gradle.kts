import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.depsDebug
import io.matthewnelson.kotlin.components.dependencies.depsKapt
import io.matthewnelson.kotlin.components.dependencies.depsTest
import io.matthewnelson.kotlin.components.dependencies.versions

plugins {
    id("app.cash.exhaustive")
    id("com.android.application")
//    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dependencies")
}

android {
    compileSdkVersion(versions.sdkCompile)
    buildToolsVersion(versions.buildTools)

    buildFeatures.viewBinding = true
    defaultConfig {
        applicationId("io.matthewnelson.components")
        minSdkVersion(versions.sdkMin21)
        targetSdkVersion(versions.sdkTarget)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments.putIfAbsent("disableAnalytics", "true")
    }

    // Gradle 4.0's introduction of Google analytics to Android App Developers.
    // https://developer.android.com/studio/releases/gradle-plugin
    dependenciesInfo {
        // Disables dependency metadata when building APKs.
        includeInApk = false
        // Disables dependency metadata when building Android App Bundles.
        includeInBundle = false
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(deps.androidx.annotation)
    implementation(deps.androidx.appCompat)
    implementation(deps.androidx.camera.core)
    implementation(deps.androidx.camera.camera2)
    implementation(deps.androidx.camera.lifecycle)
    implementation(deps.androidx.camera.view)
    implementation(deps.androidx.camera.extensions)
    implementation(deps.androidx.constraintLayout)
    implementation(deps.androidx.core)
    implementation(deps.androidx.exifInterface)
    implementation(deps.androidx.lifecycle.commonJava8)
    implementation(deps.androidx.lifecycle.hiltViewmodel)
    implementation(deps.androidx.lifecycle.processLifecycleOwner)
    implementation(deps.androidx.lifecycle.runtime)
    implementation(deps.androidx.lifecycle.service)
    implementation(deps.androidx.lifecycle.viewModel)
    implementation(deps.androidx.lifecycle.viewModelSavedState)
    implementation(deps.androidx.media)
    implementation(deps.androidx.navigation.fragment)
    implementation(deps.androidx.navigation.ui)
    implementation(deps.androidx.paging3)
    implementation(deps.androidx.recyclerView)
    implementation(deps.androidx.securityCrypto)
    implementation(deps.androidx.viewBinding)

    implementation(deps.insetter)

    implementation(deps.google.hilt)
//    kapt(depsKapt.google.hilt)
    implementation(deps.google.material)
    implementation(deps.google.mlKitBarcodeScanning)
    implementation(deps.google.zxing)

    implementation(deps.instacart.coil.base)
    implementation(deps.instacart.coil.coil)
    implementation(deps.instacart.coil.gif)
    implementation(deps.instacart.coil.svg)
    implementation(deps.instacart.coil.video)

    implementation(deps.javax.inject)

    implementation(deps.jncryptor)

    implementation(deps.kotlin.coroutines.android)
    implementation(deps.kotlin.coroutines.core)
    implementation(deps.kotlin.reflect)

    implementation(deps.sqlCipher)
    implementation(deps.sqlRequery)

    implementation(deps.square.okio)
    implementation(deps.square.okhttp.okhttp)
    implementation(deps.square.okhttp.logging)
    implementation(deps.square.moshi)
//    kapt(depsKapt.square.moshi.codegen.codegen)
    implementation(deps.square.sqlDelight.android)
    implementation(deps.square.sqlDelight.jvm)
    implementation(deps.square.sqlDelight.runtime)
    implementation(deps.square.sqlDelight.extensions.coroutines)
    implementation(deps.square.sqlDelight.extensions.paging3)

    implementation(deps.viewBindingDelegateNoReflect)

    debugImplementation(depsDebug.square.leakCanary)

    testImplementation(depsTest.androidx.archCore)
    testImplementation(depsTest.androidx.core)
    testImplementation(depsTest.androidx.espresso)
    testImplementation(depsTest.androidx.junit)
    testImplementation(depsTest.google.hilt)
    testImplementation(depsTest.google.guava)
    testImplementation(depsTest.junit)
    testImplementation(depsTest.kotlin.coroutines)
    testImplementation(depsTest.robolectric)
    testImplementation(depsTest.turbine)
}
