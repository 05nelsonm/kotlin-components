/*
*  Copyright 2021 Matthew Nelson
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
* */
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
