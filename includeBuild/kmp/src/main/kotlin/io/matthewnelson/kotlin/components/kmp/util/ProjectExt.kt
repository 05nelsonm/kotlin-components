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
import dev.petuska.npm.publish.dsl.NpmAccess
import dev.petuska.npm.publish.dsl.NpmPublication
import dev.petuska.npm.publish.dsl.NpmPublishExtension
import io.matthewnelson.kotlin.components.kmp.publish.kmpPublishRootProjectConfiguration
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.*

@JvmOverloads
@Suppress("unused")
@Throws(ExtraPropertiesExtension.UnknownPropertyException::class)
fun Project.includeStagingRepoIfTrue(
    include: Boolean,
    sonatypeHost: SonatypeHost = SonatypeHost.S01,
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
@Suppress("unused")
fun Project.includeSnapshotsRepoIfTrue(
    include: Boolean,
    sonatypeHost: SonatypeHost = SonatypeHost.S01,
): Boolean {
    if (!include) return include

    repositories {
        maven("${sonatypeHost.rootUrl}/content/repositories/snapshots/")
    }

    return include
}

/* Why the value is marked internal is beyond me... */
val SonatypeHost.rootUrl: String get() {
    return when (this) {
        SonatypeHost.DEFAULT -> "https://oss.sonatype.org"
        SonatypeHost.S01 -> "https://s01.oss.sonatype.org"
    }
}

fun Project.configureYarn(
    block: org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin.(
        rootYarn: org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension,
        rootNodeJs: org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension,
    ) -> Unit
) {
    check(this.rootProject == this) {
        "This method can only be called from the the root project's build.gradle(.kts) file"
    }

    rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin>() {
        block.invoke(
            this,
            rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>(),
            rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>()
        )
    }
}

fun Project.npmPublish(block: NpmPublication.() -> Unit) {
    check(rootProject != this) {
        "This method can not be called from the root project's build.gradle(.kts) file"
    }
    check(plugins.hasPlugin("dev.petuska.npm.publish")) {
        "Plugin 'dev.petuska.npm.publish' must be applied"
    }

    extensions.configure<NpmPublishExtension> {
        publications {
            publication(project.name) {
                main = "index.js"
                access = NpmAccess.PUBLIC
                version = kmpPublishRootProjectConfiguration?.versionName
                readme = file("README.md")
                packageJson {
                    homepage = "https://github.com/05nelsonm/${rootProject.name}"
                    license = "Apache 2.0"
                    repository {
                        type = "git"
                        url = "git+https://github.com/05nelsonm/${rootProject.name}.git"
                    }
                    author {
                        name = "Matthew Nelson"
                    }
                    bugs {
                        url = "https://github.com/05nelsonm/${rootProject.name}/issues"
                    }
                }

                repositories {
                    val port = rootProject.findProperty("NPMJS_VERDACCIO_PORT") as? String
                    val token = rootProject.findProperty("NPMJS_VERDACCIO_AUTH_TOKEN") as? String

                    if (port != null && token != null) {
                        repository("verdaccio") {
                            registry = uri("http://localhost:$port")
                            authToken = token
                        }
                    }

                    repository("npmjs") {
                        registry = uri("https://registry.npmjs.org")
                        authToken = rootProject.findProperty("NPMJS_AUTH_TOKEN") as? String
                    }
                }

                block.invoke(this)
            }
        }
    }
}
