package io.matthewnelson.kotlin.components.kmp.publish

import com.vanniktech.maven.publish.MavenPublishPluginExtension
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension

@Suppress("unused")
inline val Project.kmpPublishRootProjectConfiguration: KmpPublishRootProjectConfiguration?
    get() = KmpPublishRootProjectConfiguration.configuration

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
