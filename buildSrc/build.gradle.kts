plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(files("../includeBuild/dependencies/build/libs/dependencies-SNAPSHOT.jar"))
}
