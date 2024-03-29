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
package io.matthewnelson.kotlin.components.kmp

import org.gradle.api.Project
import javax.inject.Inject

open class KmpRootProjectConfigurationExtension @Inject constructor(private val project: Project) {

    init {
        check(project.rootProject == project) {
            """
                KmpRootProjectConfigurationExtension can only be configured
                from the root project's build.gradle(.kts) file.
            """.trimIndent()
        }
    }

    fun optInArgs(args: Set<String>) {
        optInArgs.addAll(args)
    }

    companion object {
        internal val optInArgs = mutableSetOf<String>()
    }
}
