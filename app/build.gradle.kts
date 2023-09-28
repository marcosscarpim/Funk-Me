@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.scarpim.funkme"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.scarpim.funkme"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":funkplayer"))

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)

    implementation(libs.google.accompanist.systemuicontroller)

    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.compose.junit)
    debugImplementation(libs.test.compose.ui.tooling)
    debugImplementation(libs.test.compose.ui.test.manifest)
}