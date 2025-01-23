

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url =uri("https://jitpack.io") }

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.2.0") //7.2.2
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url =uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory) // Recommended
}