package io.matthewnelson.kotlin.components.kmp.util

internal object EnvProperty {
    // ./gradlew publishAllPublicationsToMavenRepository --no-daemon -DKMP_PUBLISH_ALL
    val isPublishingAll: Boolean
        get() = System.getProperty("KMP_PUBLISH_ALL") != null
}
