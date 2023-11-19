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
include(":reminder")
include(":notifications")
include(":pickers")
include(":epoxy")
include(":reminder-list-ui")
include(":reminder-ui")
include(":navigation")
include(":common-ui")
include(":common")
include(":login")
