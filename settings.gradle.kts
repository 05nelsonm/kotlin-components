pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

rootProject.name = "kotlin-components"

includeBuild("includeBuild/dependencies")
includeBuild("includeBuild/kmp")

include(":app")
