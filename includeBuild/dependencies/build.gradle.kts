plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "io.matthewnelson.kotlin.components.dependencies"
version = "SNAPSHOT"

repositories {
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins.register("dependencies") {
        id = "dependencies"
        implementationClass = "io.matthewnelson.kotlin.components.dependencies.DependenciesPlugin"
    }
}
