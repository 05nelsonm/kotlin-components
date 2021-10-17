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

dependencies {
    compileOnly(Plugins.android.gradle)
    compileOnly(Plugins.kotlin.gradle)
    compileOnly(Plugins.kotlin.dokka)
    compileOnly(Plugins.mavenPublish)
}

gradlePlugin {
    plugins.register("kmp-configuration") {
        id = "kmp-configuration"
        implementationClass = "io.matthewnelson.kotlin.components.kmp.KmpConfigurationPlugin"
    }
    plugins.register("kmp-publish") {
        id = "kmp-publish"
        implementationClass = "io.matthewnelson.kotlin.components.kmp.publish.KmpPublishPlugin"
    }
}
