// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinSymbolProcessor) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.modular.dokka.gfm)
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(rootProject.layout.projectDirectory.dir("docs/html"))
    }
    dokkaPublications.named("markdown") {
        outputDirectory.set(rootProject.layout.projectDirectory.dir("docs/markdown"))
    }
}

tasks.register("generateDocs") {
    group = "documentation"
    description = "Generate API docs (HTML + Markdown)."
    dependsOn(tasks.named("dokkaGenerate"))
}
