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
package io.matthewnelson.kotlin.components.kmp.util

import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

@JvmOverloads
@Suppress("NOTHING_TO_INLINE", "unused")
@Throws(ExtraPropertiesExtension.UnknownPropertyException::class)
inline fun Project.includeStagingRepoIfTrue(
    include: Boolean,
    sonatypeHost: SonatypeHost = SonatypeHost.DEFAULT,
): Boolean {
    if (!include) return include

    repositories {
        maven("${sonatypeHost.rootUrl}/content/groups/staging") {
            credentials {
                rootProject.propertyExt {
                    username = get("mavenCentralUsername").toString()
                    password = get("mavenCentralPassword").toString()
                }
            }
        }
    }

    return include
}

@JvmOverloads
@Suppress("NOTHING_TO_INLINE", "unused")
inline fun Project.includeSnapshotsRepoIfTrue(
    include: Boolean,
    sonatypeHost: SonatypeHost = SonatypeHost.DEFAULT,
): Boolean {
    if (!include) return include

    repositories {
        maven("${sonatypeHost.rootUrl}/content/repositories/snapshots/")
    }

    return include
}

/* Why the value is marked internal is beyond me... */
inline val SonatypeHost.rootUrl: String get() {
    return when (this) {
        SonatypeHost.DEFAULT -> "https://oss.sonatype.org"
        SonatypeHost.S01 -> "https://s01.oss.sonatype.org"
    }
}
