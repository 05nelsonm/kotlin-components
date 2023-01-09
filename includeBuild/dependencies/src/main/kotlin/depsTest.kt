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
 * Test dependencies
 * */
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

        object ktor {
            object client {
                const val cio               = "io.ktor:ktor-client-cio:${versions.kotlin.ktor}"
            }

            object server {
                const val core              = "io.ktor:ktor-server-core:${versions.kotlin.ktor}"
                const val testHost          = "io.ktor:ktor-server-test-host:${versions.kotlin.ktor}"
                const val testSuites        = "io.ktor:ktor-server-test-suites:${versions.kotlin.ktor}"
            }
        }
    }

    const val robolectric                   = "org.robolectric:robolectric:${versions.test.robolectric}"

    object square {
        object okio {
            const val fakeFileSys           = "com.squareup.okio:okio-fakefilesystem:${versions.square.okio}"
        }

        const val turbine                   = "app.cash.turbine:turbine:${versions.test.turbine}"
    }

}
