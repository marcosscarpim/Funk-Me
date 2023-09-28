@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
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
    namespace = "com.scarpim.funkme.domain"
}

dependencies {

    implementation(libs.androidx.ktx)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit.ext)
    androidTestImplementation(libs.test.espresso.core)
    testImplementation(libs.test.core.ktx)
    testImplementation(libs.test.mockito.core)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.mockk)
}