@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.scarpim.funkme"
        targetSdk = 35
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.scarpim.funkme"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":funkplayer"))
    implementation(project(":recorder"))

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.icons)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.google.accompanist.systemuicontroller)
    implementation(libs.google.accompanist.permissions)

    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

    androidTestImplementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.compose.junit)
    debugImplementation(libs.test.compose.ui.tooling)
    debugImplementation(libs.test.compose.ui.test.manifest)
}