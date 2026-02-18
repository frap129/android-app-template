package dev.maples.build

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import java.io.FileInputStream
import java.util.Properties
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.logging.LogLevel
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

private val javaVersion = JavaVersion.VERSION_21

/**
 * Configures common Android settings shared across application, library, and test modules.
 * Does NOT configure signing (use [configureAndroidSigning] for application modules).
 */
internal fun Project.configureAndroidCommon(commonExtension: CommonExtension) {
    commonExtension.compileSdk = 36

    commonExtension.defaultConfig.apply {
        minSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    commonExtension.compileOptions.apply {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    commonExtension.buildFeatures.apply {
        buildConfig = true
    }

    kotlinExtension.jvmToolchain(javaVersion.ordinal + 1)
}

/**
 * Configures signing for application modules using root keystore.properties if present.
 *
 * Supports:
 * - Generic signing keys: storeFile, storePassword, keyAlias, keyPassword
 * - Per-buildType overrides: <buildType>.storeFile, <buildType>.storePassword, etc.
 *
 * If keystore.properties does not exist, signing is not configured and default signing is used.
 */
internal fun Project.configureAndroidSigning(applicationExtension: ApplicationExtension) {
    try {
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (!keystorePropertiesFile.exists()) {
            return
        }

        val keystoreProperties = Properties().apply {
            load(FileInputStream(keystorePropertiesFile))
        }

        applicationExtension.signingConfigs.apply {
            applicationExtension.buildTypes.names.forEach { variant ->
                val prefix = if (keystoreProperties.keys.any { (it as String).contains(variant) }) "$variant." else ""
                val config = when (variant) {
                    "debug" -> getByName(variant)
                    else -> create(variant)
                }

                config.apply {
                    keyAlias = keystoreProperties["${prefix}keyAlias"] as String
                    keyPassword = keystoreProperties["${prefix}keyPassword"] as String
                    storeFile = rootProject.file(keystoreProperties["${prefix}storeFile"] as String)
                    storePassword = keystoreProperties["${prefix}storePassword"] as String
                }
            }
        }
    } catch (exception: Exception) {
        logger.log(LogLevel.WARN, "Failed to read keystore.properties, using default signing config", exception)
    }
}

/**
 * Adds common dependencies shared across all Android modules.
 * This function does not require an Android extension; it operates purely on Gradle dependencies.
 */
internal fun Project.configureAndroidDependencies() {
    val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        implementation(libs, "androidx.core.ktx")
        implementation(libs, "kotlinx.coroutines.core")
        implementation(libs, "kotlinx.coroutines.android")

        implementation(libs, "androidx.lifecycle.runtime.ktx")
        implementation(libs, "androidx.lifecycle.viewmodel.compose")
        implementation(libs, "androidx.lifecycle.service")
        annotationProcessor(libs, "androidx.lifecycle.compiler")

        add("implementation", platform(libs.findLibrary("koin.bom").get()))
        implementation(libs, "koin.android")
        implementation(libs, "util.timber")
    }
}
