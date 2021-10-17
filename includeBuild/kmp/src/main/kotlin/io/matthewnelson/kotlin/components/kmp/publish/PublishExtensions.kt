package io.matthewnelson.kotlin.components.kmp.publish

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.getByType

inline fun Project.propertyExt(crossinline block: ExtraPropertiesExtension.() -> Unit) {
    block.invoke(extensions.getByType())
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun ExtraPropertiesExtension.setPropertyIfDifferent(propertyName: String, value: Any) {
    if (!has(propertyName)) {
        set(propertyName, value)
    } else if (get(propertyName)?.toString() != value.toString()) {
        set(propertyName, value)
    }
}
