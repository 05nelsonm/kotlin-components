pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

rootProject.name = "Components"

includeBuild("includeBuild/dependencies")
includeBuild("includeBuild/kmp")

include(":app")
