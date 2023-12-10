pluginManagement {
    repositories {
        google()
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

rootProject.name = "DutyReminder-2"
include(":app")

include(":notifications")
include(":pickers")
include(":epoxy")
include(":navigation")
include(":preferences")

include(":common:ui")
include(":common:core")

include(":feature:reminder:core")
include(":feature:reminder:reminder-list-screen")
include(":feature:reminder:reminder-screen")

include(":feature:login-screen")
