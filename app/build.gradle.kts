plugins {
    alias(libs.plugins.modular.android.application)
    alias(libs.plugins.modular.compose)
}

android {
    namespace = "dev.maples.template"

    defaultConfig {
        applicationId = "dev.maples.template"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.compose.activity)
    implementation(project(":core:ui"))
    implementation(project(":core:lifecycle"))
}
