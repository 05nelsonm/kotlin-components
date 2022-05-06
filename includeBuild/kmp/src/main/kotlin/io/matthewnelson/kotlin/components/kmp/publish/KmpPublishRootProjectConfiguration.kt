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
package io.matthewnelson.kotlin.components.kmp.publish

import com.vanniktech.maven.publish.MavenPublishPluginExtension
import io.matthewnelson.kotlin.components.kmp.util.propertyExt
import io.matthewnelson.kotlin.components.kmp.util.setPropertyIfDifferent
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension

@Suppress("unused")
inline val Project.kmpPublishRootProjectConfiguration: KmpPublishRootProjectConfiguration?
    get() = KmpPublishRootProjectConfiguration.configuration

@Suppress("unused")
inline val KmpPublishRootProjectConfiguration.isSnapshotVersion: Boolean
    get() = versionName.endsWith("-SNAPSHOT")

@Suppress("MemberVisibilityCanBePrivate", "unused", "CanBeParameter")
class KmpPublishRootProjectConfiguration internal constructor(
    rootProject: Project,
    val versionName: String,
    val versionCode: Int,
    val mavenPublish: (MavenPublishPluginExtension.(Project) -> Unit)?,
    val signing: (SigningExtension.(Project) -> Unit)?,

    val group: String,

    val pomInceptionYear: Int,
    val pomLicenseName: String,
    val pomLicenseUrl: String,
    val pomLicenseDist: String,
    val pomDeveloperId: String,
    val pomDeveloperName: String,
    val pomDeveloperUrl: String,

    val pomUrl: String,
    val pomScmUrl: String,
    val pomScmConnection: String,
    val pomScmDevConnection: String
) {
    companion object {
        var configuration: KmpPublishRootProjectConfiguration? = null
            private set
    }

    init {
        configuration = this

        // Apply properties to root project for GradleMavenPublish
        rootProject.propertyExt {
            setPropertyIfDifferent("GROUP", group)
            setPropertyIfDifferent("POM_LICENSE_NAME", pomLicenseName)
            setPropertyIfDifferent("POM_LICENSE_URL", pomLicenseUrl)
            setPropertyIfDifferent("POM_LICENSE_DIST", pomLicenseDist)
            setPropertyIfDifferent("POM_DEVELOPER_ID", pomDeveloperId)
            setPropertyIfDifferent("POM_DEVELOPER_NAME", pomDeveloperName)
            setPropertyIfDifferent("POM_DEVELOPER_URL", pomDeveloperUrl)

            setPropertyIfDifferent("VERSION_NAME", versionName)
            setPropertyIfDifferent("VERSION_CODE", versionCode)
            setPropertyIfDifferent("POM_INCEPTION_YEAR", pomInceptionYear)
            setPropertyIfDifferent("POM_URL", pomUrl)
            setPropertyIfDifferent("POM_SCM_URL", pomScmUrl)
            setPropertyIfDifferent("POM_SCM_CONNECTION", pomScmConnection)
            setPropertyIfDifferent("POM_SCM_DEV_CONNECTION", pomScmDevConnection)
        }
    }
}
