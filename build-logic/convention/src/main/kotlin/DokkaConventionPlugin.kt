import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

class DokkaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val pluginManager = target.pluginManager
        pluginManager.withPlugin("com.android.application") {
            applyDokkaForProject(target)
        }
        pluginManager.withPlugin("com.android.library") {
            applyDokkaForProject(target)
        }
        pluginManager.withPlugin("com.android.test") {
            applyDokkaForProject(target)
        }
    }

    private fun applyDokkaForProject(project: Project) {
        project.pluginManager.apply("org.jetbrains.dokka")
        project.pluginManager.apply("modular.dokka.gfm")
        val dokkaExtension: DokkaExtension = project.extensions.getByType(DokkaExtension::class.java)
        val mainSourceSet = dokkaExtension.dokkaSourceSets.maybeCreate("main")
        mainSourceSet.suppress.set(false)
        mainSourceSet.sourceRoots.from(
            project.file("src/main/java"),
            project.file("src/main/kotlin")
        )
        dokkaExtension.dokkaSourceSets.configureEach {
            documentedVisibilities.set(setOf(VisibilityModifier.Public))
            suppressGeneratedFiles.set(true)
        }
        val rootProject: Project = project.rootProject
        rootProject.pluginManager.withPlugin("org.jetbrains.dokka") {
            val dependency = rootProject.dependencies.project(mapOf("path" to project.path)) as ProjectDependency
            rootProject.dependencies.add("dokka", dependency)
        }
    }
}
