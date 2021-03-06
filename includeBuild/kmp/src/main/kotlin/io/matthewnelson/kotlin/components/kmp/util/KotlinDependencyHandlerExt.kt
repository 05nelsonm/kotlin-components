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
package io.matthewnelson.kotlin.components.kmp.util

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

@Suppress("unused")
fun KotlinDependencyHandler.kapt(project: Project, dependencyNotation: String) {
    implementation(dependencyNotation)
    val splits = dependencyNotation.split(":")
    project.configurations["kapt"].dependencies.add(
        DefaultExternalModuleDependency(
            splits[0], // group
            splits[1], // name
            splits[2]  // version
        )
    )
}
