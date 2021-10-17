package io.matthewnelson.kotlin.components.kmp.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

@Suppress("unused")
class KmpPublishPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        if (!target.rootProject.equals(target)) {
            target.pluginManager.apply("com.vanniktech.maven.publish")
        }
        target.extensions.create<KmpPublishExtension>("kmpPublish", target)
    }
}
