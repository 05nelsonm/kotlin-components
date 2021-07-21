plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "io.matthewnelson.components.dependencies"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "io.matthewnelson.components.dependencies.DependenciesPlugin"
    }
}
