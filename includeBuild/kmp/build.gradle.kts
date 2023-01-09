plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("dependencies")
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    compileOnly(pluginDeps.android.gradle)
    compileOnly(pluginDeps.kotlin.gradle)
    compileOnly(pluginDeps.kotlin.dokka)
    compileOnly(pluginDeps.mavenPublish)
    compileOnly(pluginDeps.npmPublish)
}

gradlePlugin {
    plugins.register(pluginId.kmp.configuration) {
        id = pluginId.kmp.configuration
        implementationClass = "io.matthewnelson.kotlin.components.kmp.KmpConfigurationPlugin"
    }
    plugins.register(pluginId.kmp.publish) {
        id = pluginId.kmp.publish
        implementationClass = "io.matthewnelson.kotlin.components.kmp.publish.KmpPublishPlugin"
    }
}
