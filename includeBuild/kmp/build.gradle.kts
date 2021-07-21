import io.matthewnelson.components.dependencies.Plugins

plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
    id("dependencies")
}

repositories {
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(Plugins.android.gradle)
    implementation(Plugins.kotlin.gradle)
}

gradlePlugin {
    plugins.register("kmp-configuration") {
        id = "kmp-configuration"
        implementationClass = "io.matthewnelson.components.kmp.KmpConfigurationPlugin"
    }
}
