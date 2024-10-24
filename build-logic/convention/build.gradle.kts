/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "dev.maples.build"

val javaVersion = JavaVersion.VERSION_17

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    jvmToolchain(javaVersion.ordinal + 1)
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    /**
     * Register convention plugins so they are available in the build scripts of the application
     */
    plugins {
        register("modularAndroidApplication") {
            id = "modular.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("modularAndroidLibrary") {
            id = "modular.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("modularAndroidTest") {
            id = "modular.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("modularCompose") {
            id = "modular.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
}
