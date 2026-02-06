plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    //room db
    id("kotlin-kapt")
}

android {
    namespace = "com.example.myfavoritequotes"
    compileSdk {
        version = release(36)
    }
    viewBinding.isEnabled = true

    defaultConfig {
        applicationId = "com.example.myfavoritequotes"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //fragments and fragment navigation
    implementation(libs.androidx.fragment.fragment)
    implementation(libs.androidx.fragment.nav)
    //room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    //live data
    implementation(libs.androidx.lifecycle.livedata)
    //datastore
    implementation(libs.androidx.datastore)
    //retrofit
    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.converter.gson)
}