plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "io.matthewnelson.kotlin.components.dependencies"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "io.matthewnelson.kotlin.components.dependencies.DependenciesPlugin"
    }
}
