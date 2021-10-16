import io.matthewnelson.kotlin.components.dependencies.Plugins

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
}

gradlePlugin {
    plugins.register("kmp-configuration") {
        id = "kmp-configuration"
        implementationClass = "io.matthewnelson.kotlin.components.kmp.KmpConfigurationPlugin"
    }
}
