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
package io.matthewnelson.kmp;

/**
 *
 * */
public enum KmpTarget {
    ANDROID,

    IOS_ARM32,
    IOS_ARM64,
    IOS_X64,

    JS_IR,
    JS_LEGACY,

    JVM,

    LINUX_ARM32_HFP,
    LINUX_MIPS32,
    LINUX_MIPSEL32,
    LINUX_X64,

    MACOS_X64,

    MINGW_X64,
    MINGW_X86,

    TVOS_ARM64,
    TVOS_X64,

    WATCHOS_ARM32,
    WATCHOS_ARM64,
    WATCHOS_X64,
    WATCHOS_X86;

    private String  buildTools  = "30.0.3";
    private int     compileSdk  = 30;
    private int     minSdk      = 23;

    public String getBuildTools() throws IllegalArgumentException {
        checkAndroidOnly("getBuildTools");
        return buildTools;
    }

    public int getCompileSdk() throws IllegalArgumentException {
        checkAndroidOnly("getCompileSdk");

        return compileSdk;
    }

    public int getMinSdk() throws IllegalArgumentException {
        checkAndroidOnly("getMinSdk");

        return minSdk;
    }

    public KmpTarget setBuildTools(String buildTools) throws IllegalArgumentException {
        checkAndroidOnly("setBuildTools");

        if (buildTools != null && !buildTools.isEmpty()) {
            this.buildTools = buildTools;
        }

        return this;
    }

    public KmpTarget setCompileSdk(int compileSdk) throws IllegalArgumentException {
        checkAndroidOnly("setCompileSdk");

        this.compileSdk = compileSdk;
        return this;
    }

    public KmpTarget setMinSdk(int minSdk) throws IllegalArgumentException {
        checkAndroidOnly("setMinSdk");

        this.minSdk = minSdk;
        return this;
    }

    private void checkAndroidOnly(String methodName) throws IllegalArgumentException {
        if (this != ANDROID) {
            throw new IllegalArgumentException("KmpTarget." + methodName + " is only applicable for ANDROID");
        }
    }
}
