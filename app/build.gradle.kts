plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android") // Adding the Kotlin Android plugin
}

android {
    namespace = "com.example.kanji_prototype_1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kanji_prototype_1"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8  // Java target version
        targetCompatibility = JavaVersion.VERSION_1_8  // Java target version
    }
    kotlinOptions {
        jvmTarget = "1.8"  // Ensure Kotlin also targets JVM 1.8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // AndroidX and other dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)

    // Unit and Android Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Add the OpenCV library
    implementation(project(":openCVLibrary"))
}