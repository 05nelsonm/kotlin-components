import io.matthewnelson.kotlin.components.dependencies.plugins as Plugins

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("dependencies")
}

repositories {
    google()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(Plugins.android.gradle)
    compileOnly(Plugins.kotlin.gradle)
    compileOnly(Plugins.kotlin.dokka)
    compileOnly(Plugins.mavenPublish)
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
