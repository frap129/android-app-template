package dev.maples.build

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(libs: VersionCatalog, alias: String): Dependency? =
    add("implementation", libs.findLibrary(alias).get())

fun DependencyHandlerScope.debugImplementation(libs: VersionCatalog, alias: String): Dependency? =
    add("debugImplementation", libs.findLibrary(alias).get())

fun DependencyHandlerScope.testImplementation(libs: VersionCatalog, alias: String): Dependency? =
    add("testImplementation", libs.findLibrary(alias).get())

fun DependencyHandlerScope.annotationProcessor(libs: VersionCatalog, alias: String): Dependency? =
    add("annotationProcessor", libs.findLibrary(alias).get())

fun DependencyHandlerScope.androidTestImplementation(libs: VersionCatalog, alias: String): Dependency? =
    add("androidTestImplementation", libs.findLibrary(alias).get())

fun DependencyHandlerScope.ksp(libs: VersionCatalog, alias: String): Dependency? = add("ksp", libs.findLibrary(alias).get())
