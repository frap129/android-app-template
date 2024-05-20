import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.FileCollectionFactory
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiddenApisConventionPlugin : Plugin<Project> {
    private fun getHiddenApiJar(target: Project): FileCollection =
        target.files(target.rootDir.path + "/libs/" + "android-32-hidden-apis.jar")

    override fun apply(target: Project) {
        with(target) {
            extensions.getByType<BaseExtension>().apply {
                dependencies {
                    add("compileOnly", getHiddenApiJar(target))
                }
            }

            tasks.withType(JavaCompile::class.java) {
                options.bootstrapClasspath = getHiddenApiJar(target)
                    .plus(options.bootstrapClasspath ?: FileCollectionFactory.empty())
            }
        }
    }
}
