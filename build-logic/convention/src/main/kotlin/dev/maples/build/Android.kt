package dev.maples.build

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun configureAndroid(commonExtension: CommonExtension<*, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 32
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildFeatures.buildConfig = true
    }
}

internal fun Project.configureAndroidBase(commonExtension: BaseExtension) {
    commonExtension.apply {
        val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            implementation(libs, "androidx.core.ktx")
            implementation(libs, "kotlinx.coroutines.core")
            implementation(libs, "kotlinx.coroutines.android")

            implementation(libs, "androidx.lifecycle.runtime.ktx")
            implementation(libs, "androidx.lifecycle.viewmodel.compose")
            implementation(libs, "androidx.lifecycle.service")
            annotationProcessor(libs, "androidx.lifecycle.compiler")

            implementation(libs, "koin.bom")
            implementation(libs, "koin.android")
            implementation(libs, "util.timber")
        }
    }
}
