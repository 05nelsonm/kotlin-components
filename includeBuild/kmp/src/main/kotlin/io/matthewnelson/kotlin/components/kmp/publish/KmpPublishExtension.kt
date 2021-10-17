package io.matthewnelson.kotlin.components.kmp.publish

import com.vanniktech.maven.publish.MavenPublishPluginExtension
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension
import java.util.*
import javax.inject.Inject
import kotlin.Throws

open class KmpPublishExtension @Inject constructor(private val project: Project) {

    @Suppress("unused")
    @Throws(IllegalStateException::class)
    fun setupRootProject(
        versionName: String,
        versionCode: Int,
        pomInceptionYear: Int,

        // uses default configuration if no callback is set
        mavenPublish: (MavenPublishPluginExtension.(Project) -> Unit)? = null,

        // uses useGpgCmd if no callback is set
        signing: (SigningExtension.(Project) -> Unit)? = null,

        group: String = "io.matthewnelson.kotlin-components",
        pomLicenseName: String = "The Apache Software License, Version 2.0",
        pomLicenseUrl: String = "https://www.apache.org/licenses/LICENSE-2.0.txt",
        pomLicenseDist: String = "https://www.apache.org/licenses/LICENSE-2.0.txt",
        pomDeveloperId: String = "05nelsonm",
        pomDeveloperName: String = "Matthew Nelson",
        pomDeveloperUrl: String = "https://github.com/05nelsonm",

        // Convenience to set pom info below. Not used anywhere else.
        repoName: String = project.name,

        pomUrl: String = "https://github.com/05nelsonm/$repoName",
        pomScmUrl: String = "https://github.com/05nelsonm/$repoName",
        pomScmConnection: String = "scm:git:git://github.com/05nelsonm/$repoName",
        pomScmDevConnection: String = "scm:git:ssh://git@github.com/05nelsonm/$repoName.git"
    ) {
        check(project.rootProject.equals(project)) {
            "setupRootProject is only available from the rootProject's build.gradle(.kts) file"
        }

        require(versionName.isNotEmpty()) { "versionName cannot be empty" }
        require(versionCode > 0) { "versionCode must be greater than 0" }
        require(pomInceptionYear >= 1971) { "pomInceptionYear must be greater than or equal to 1971" }
        require(group.isNotEmpty()) { "group cannot be empty" }
        require(pomLicenseName.isNotEmpty()) { "pomLicenseName cannot be empty" }
        require(pomLicenseUrl.isNotEmpty()) { "pomLicenseUrl cannot be empty" }
        require(pomLicenseDist.isNotEmpty()) { "pomLicenseDist cannot be empty" }
        require(pomDeveloperId.isNotEmpty()) { "pomDeveloperId cannot be empty" }
        require(pomDeveloperName.isNotEmpty()) { "pomDeveloperName cannot be empty" }
        require(pomDeveloperUrl.isNotEmpty()) { "pomDeveloperUrl cannot be empty" }
        require(pomUrl.isNotEmpty()) { "pomUrl cannot be empty" }
        require(pomScmUrl.isNotEmpty()) { "pomScmUrl cannot be empty" }
        require(pomScmConnection.isNotEmpty()) { "pomScmConnection cannot be empty" }
        require(pomScmDevConnection.isNotEmpty()) { "pomScmDevConnection cannot be empty" }

        KmpPublishRootProjectConfiguration(
            project,
            versionName,
            versionCode,
            mavenPublish,
            signing,
            group,
            pomInceptionYear,
            pomLicenseName,
            pomLicenseUrl,
            pomLicenseDist,
            pomDeveloperId,
            pomDeveloperName,
            pomDeveloperUrl,
            pomUrl,
            pomScmUrl,
            pomScmConnection,
            pomScmDevConnection
        )
    }

    @Suppress("unused")
    @Throws(IllegalStateException::class)
    fun setupModule(
        pomDescription: String,
        pomArtifactId: String = project.name,
        pomName: String = pomArtifactId
            .split("-")
            .joinToString("") {
                it.capitalize(Locale.ROOT)
        }
    ) {
        check(!project.rootProject.equals(project)) {
            "setupModule is only available from the subproject's build.gradle(.kts) file"
        }

        project.kmpPublishRootProjectConfiguration?.let { config ->
            require(pomDescription.isNotEmpty()) { "pomDescription cannot be empty" }
            require(pomArtifactId.isNotEmpty()) { "pomArtifactId cannot be empty" }
            require(pomName.isNotEmpty()) { "pomName cannot be empty" }

            project.propertyExt {
                set("POM_ARTIFACT_ID", pomArtifactId)
                set("POM_NAME", pomName)
                set("POM_DESCRIPTION", pomDescription)
            }

            config.mavenPublish?.let { publish ->
                project.extensions.configure<MavenPublishPluginExtension>("mavenPublish") {
                    publish.invoke(this, project)
                }
            }

            project.extensions.configure<SigningExtension>("signing") {
                if (config.signing?.invoke(this, this@KmpPublishExtension.project) == null) {
                    useGpgCmd()
                }
            }

        } ?: throw IllegalStateException("""
            kmpPublish.setupModule requires the rootProject's build.gradle(.kts)
            file to be configured first using the kmpPublish.setupRootProject method.
        """.trimIndent())
    }
}
