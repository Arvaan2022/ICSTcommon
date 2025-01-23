plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.icst.commonmodule"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.icst.commonmodule"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":commonModule"))

    implementation ("androidx.core:core-ktx:1.13.1") //1.9.0
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0") //1.9.0
    implementation ("com.android.support:support-compat:28.0.0")//1.9.0


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1") //1.1.5
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1") // 3.5.1

}