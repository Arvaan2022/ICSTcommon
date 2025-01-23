pluginManagement {
    repositories {
        google ()
        maven (url = "https://jitpack.io")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven (url = "https://jitpack.io")
    }
}

rootProject.name = "IcstCommon"
include(":app")
include(":commonModule")
