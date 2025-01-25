pluginManagement {
    includeBuild("convention/myplugins")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PlanRadarWeatherApp"
include(":app")
include(":common:domain")
include(":common:data")
include(":common:presentation")

include(":features:favorite-cities:presentation")
include(":features:favorite-cities:domain")
include(":features:favorite-cities:data")

include(":features:details:presentation")
include(":features:details:domain")

include(":features:historical-data:presentation")
include(":features:historical-data:domain")
