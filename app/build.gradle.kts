import io.matthewnelson.components.dependencies.Deps
import io.matthewnelson.components.dependencies.DebugDeps
import io.matthewnelson.components.dependencies.KaptDeps
import io.matthewnelson.components.dependencies.TestDeps
import io.matthewnelson.components.dependencies.Versions

plugins {
    id("app.cash.exhaustive")
    id("com.android.application")
//    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dependencies")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    buildFeatures.viewBinding = true
    defaultConfig {
        applicationId("io.matthewnelson.components")
        minSdkVersion(21)
        targetSdkVersion(Versions.compileSdk)
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
    implementation(Deps.androidx.annotation)
    implementation(Deps.androidx.appCompat)
    implementation(Deps.androidx.camera.core)
    implementation(Deps.androidx.camera.camera2)
    implementation(Deps.androidx.camera.lifecycle)
    implementation(Deps.androidx.camera.view)
    implementation(Deps.androidx.camera.extensions)
    implementation(Deps.androidx.constraintLayout)
    implementation(Deps.androidx.core)
    implementation(Deps.androidx.exifInterface)
    implementation(Deps.androidx.lifecycle.commonJava8)
    implementation(Deps.androidx.lifecycle.hilt)
    implementation(Deps.androidx.lifecycle.processLifecycleOwner)
    implementation(Deps.androidx.lifecycle.runtime)
    implementation(Deps.androidx.lifecycle.service)
    implementation(Deps.androidx.lifecycle.viewModel)
    implementation(Deps.androidx.lifecycle.viewModelSavedState)
    implementation(Deps.androidx.media)
    implementation(Deps.androidx.navigation.fragment)
    implementation(Deps.androidx.navigation.ui)
    implementation(Deps.androidx.paging3)
    implementation(Deps.androidx.recyclerView)
    implementation(Deps.androidx.securityCrypto)
    implementation(Deps.androidx.viewBinding)

    implementation(Deps.chrisBanes.insetter)
    implementation(Deps.chrisBanes.insetterWidgets)

    implementation(Deps.google.hilt)
    kapt(KaptDeps.google.hilt)
    implementation(Deps.google.material)
    implementation(Deps.google.mlKitBarcodeScanning)
    implementation(Deps.google.zxing)

    implementation(Deps.instacart.coil)
    implementation(Deps.instacart.coilBase)
    implementation(Deps.instacart.coilGif)
    implementation(Deps.instacart.coilSvg)
    implementation(Deps.instacart.coilVideo)

    implementation(Deps.javax.inject)

    implementation(Deps.jncryptor)

    implementation(Deps.kotlin.coroutinesAndroid)
    implementation(Deps.kotlin.coroutinesCore)
    implementation(Deps.kotlin.reflect)

    implementation(Deps.sqlCipher)
    implementation(Deps.sqlRequery)

    implementation(Deps.square.okio)
    implementation(Deps.square.okhttp)
    implementation(Deps.square.okhttpLogging)
    implementation(Deps.square.moshi)
    kapt(KaptDeps.square.moshiCodegen)
    implementation(Deps.square.sqlDelightAndroid)
    implementation(Deps.square.sqlDelightAndroidPaging3)
    implementation(Deps.square.sqlDelightCoroutines)
    implementation(Deps.square.sqlDelightJvm)
    implementation(Deps.square.sqlDelightRuntime)

    implementation(Deps.viewBindingDelegateNoReflect)

    debugImplementation(DebugDeps.square.leakCanary)

    testImplementation(TestDeps.androidx.archCore)
    testImplementation(TestDeps.androidx.core)
    testImplementation(TestDeps.androidx.espresso)
    testImplementation(TestDeps.androidx.junit)
    testImplementation(TestDeps.google.hilt)
    testImplementation(TestDeps.google.guava)
    testImplementation(TestDeps.junit)
    testImplementation(TestDeps.kotlin.coroutines)
    testImplementation(TestDeps.robolectric)
    testImplementation(TestDeps.turbine)
}
