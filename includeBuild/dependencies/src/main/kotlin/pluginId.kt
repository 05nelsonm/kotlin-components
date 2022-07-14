@file:Suppress("ClassName", "unused", "SpellCheckingInspection")

/*
* Copyright (c) 2022 Matthew Nelson
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*         https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
**/
object pluginId {

    object android {
        const val application               = "com.android.application"
        const val library                   = "com.android.library"
    }

    object androidx {
        const val safeArgs                  = "androidx.navigation.safeargs"
    }

    const val dependencies                  = "dependencies"

    object google {
        const val hilt                      = "dagger.hilt.android.plugin"
    }

    const val gradleVersions                = "com.github.ben-manes.versions"

    object kotlin {
        const val android                   = "org.jetbrains.kotlin.android"
        const val atomicfu                  = "kotlinx-atomicfu"
        const val binaryCompat              = "org.jetbrains.kotlinx.binary-compatibility-validator"
        const val dokka                     = "org.jetbrains.dokka"
        const val kapt                      = "org.jetbrains.kotlin.kapt"
        const val multiplatform             = "org.jetbrains.kotlin.multiplatform"
        const val serialization             = "org.jetbrains.kotlin.plugin.serialization"
    }

    object kmp {
        const val configuration             = "kmp-configuration"
        const val publish                   = "kmp-publish"
    }

    object square {
        const val exhaustive                = "app.cash.exhaustive"
        const val sqlDelight                = "com.squareup.sqldelight"
    }
}
