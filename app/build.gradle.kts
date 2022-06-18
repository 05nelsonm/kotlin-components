import io.matthewnelson.kotlin.components.dependencies.deps
import io.matthewnelson.kotlin.components.dependencies.depsDebug
import io.matthewnelson.kotlin.components.dependencies.depsKapt
import io.matthewnelson.kotlin.components.dependencies.depsTest
import io.matthewnelson.kotlin.components.dependencies.versions

plugins {
    id(pluginId.square.exhaustive)
    id(pluginId.android.application)
    id(pluginId.kotlin.android)
    id(pluginId.kotlin.kapt)
    id(pluginId.google.hilt)
    id(pluginId.dependencies)
    id(pluginId.kotlin.serialization)
}

android {
    compileSdk = versions.android.sdkCompile
    buildToolsVersion = versions.android.buildTools

    buildFeatures.viewBinding = true
    defaultConfig {
        applicationId  = "io.matthewnelson.components"
        minSdk = versions.android.sdkMin23
        targetSdk = versions.android.sdkTarget
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["disableAnalytics"] = "true"
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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(deps.androidx.annotation)
    implementation(deps.androidx.appCompat)
    implementation(deps.androidx.camera.core)
    implementation(deps.androidx.camera.camera2)
    implementation(deps.androidx.camera.extensions)
    implementation(deps.androidx.camera.lifecycle)
    implementation(deps.androidx.camera.view)
    implementation(deps.androidx.constraintLayout)
    implementation(deps.androidx.core)
    implementation(deps.androidx.exifInterface)
    implementation(deps.androidx.lifecycle.commonJava8)
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

    implementation(deps.components.buildConfig)
    implementation(deps.components.coroutines)
    implementation(deps.components.encoding.base16)
    implementation(deps.components.encoding.base32)
    implementation(deps.components.encoding.base64)
    implementation(deps.components.kmptor.manager.manager)
    implementation(deps.components.kmptor.binary.android)
    implementation(deps.components.kmptor.binary.geoip)
    implementation(deps.components.kmptor.binary.extract)
    implementation(deps.components.request.concept)
    implementation(deps.components.request.feature)
    implementation(deps.components.request.extensions.navigationAndroid)

    implementation(deps.toxicity.rsaApiKeyValidator)

    implementation(deps.insetter)

    implementation(deps.google.hilt)
    kapt(depsKapt.google.hilt)
    implementation(deps.google.guava.jre)
    implementation(deps.google.material)
    implementation(deps.google.mlKitBarcodeScanning)
    implementation(deps.google.zxing)

    implementation(deps.instacart.coil.base)
    implementation(deps.instacart.coil.coil)
    implementation(deps.instacart.coil.gif)
    implementation(deps.instacart.coil.svg)
    implementation(deps.instacart.coil.video)

    implementation(deps.javax.inject)

    implementation(deps.kodein.di)

    implementation(deps.kotlin.atomicfu.atomicfu)
    implementation(deps.kotlin.coroutines.android)
    implementation(deps.kotlin.coroutines.core.core)
    implementation(deps.kotlin.ktor.client.core)
    implementation(deps.kotlin.reflect)
    implementation(deps.kotlin.serialization.json)
    implementation(deps.kotlin.time)

    implementation(deps.sql.cipher)
    implementation(deps.sql.requery)

    implementation(deps.square.okhttp.okhttp)
    implementation(deps.square.okhttp.logging)
    implementation(deps.square.okio.okio)
    implementation(deps.square.moshi)
    kapt(depsKapt.square.moshi)
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
    testImplementation(depsTest.junit)
    testImplementation(depsTest.kotlin.coroutines)
    testImplementation(depsTest.robolectric)
    testImplementation(depsTest.square.okio.fakeFileSys)
    testImplementation(depsTest.square.turbine)
}
