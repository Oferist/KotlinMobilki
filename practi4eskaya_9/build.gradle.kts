plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.practi4eskaya_9"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practi4eskaya_9"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Compose dependencies
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose.v160) // Adjust version as needed
    implementation(libs.androidx.runtime.livedata) // Adjust version as needed
    implementation(libs.androidx.ui.v130) // or the latest stable version
    implementation(libs.androidx.material) // or the latest stable version
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}