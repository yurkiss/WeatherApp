import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

//val catalogs = extensions.getByType<VersionCatalogsExtension>()
//val libs = catalogs.named("libs")
