import org.jetbrains.dokka.gradle.DokkaTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinSymbolProcessor) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.dokka)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "org.jetbrains.dokka")
}

tasks.register("generateDocs") {}

tasks.getByName("generateDocs") {
    dependsOn(tasks.dokkaHtmlMultiModule)
    dependsOn(tasks.dokkaGfmMultiModule)
}

tasks.withType<DokkaTask>().configureEach {
    notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/2231")
}

tasks.dokkaHtmlMultiModule {
    outputDirectory.set(rootProject.file("docs/html"))
}

tasks.dokkaGfmMultiModule {
    outputDirectory.set(rootProject.file("docs/markdown"))
}
