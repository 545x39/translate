@file:Suppress("SpellCheckingInspection")

import dependencies.Application.APPLICATION_ID
import dependencies.Application.COMPILE_SDK
import dependencies.Application.MIN_SDK
import dependencies.Application.TARGET_SDK
import dependencies.Application.VERSION_CODE
import dependencies.Application.VERSION_NAME
import dependencies.Versions.DAGGER_VERSION
import dependencies.Versions.GSON_VERSION
import dependencies.Versions.INTERCEPTOR_VERSION
import dependencies.Versions.LIFECYCLE_VERSION
import dependencies.Versions.NAVIGATION_VERSION
import dependencies.Versions.RETROFIT_VERSION
import dependencies.Versions.ROOM_VERSION
import dependencies.Versions.RX_ANDROID_VERSION
import dependencies.Versions.RX_JAVA_VERSION
import dependencies.Versions.TIMBER_VERSION
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    defaultConfig {
        applicationId = APPLICATION_ID
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        versionCode = VERSION_CODE
        versionName = VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        compileSdk = COMPILE_SDK
        buildConfigField("String","API_KEY", gradleLocalProperties(rootDir).getProperty("API_KEY"))
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    dynamicFeatures += setOf(":dictionary", ":favorite")
}


dependencies {
    api("androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION")
    api("com.google.dagger:dagger:$DAGGER_VERSION")
    kapt("com.google.dagger:dagger-compiler:$DAGGER_VERSION")
    kapt("com.google.dagger:dagger-android-processor:$DAGGER_VERSION")
    api("io.reactivex.rxjava3:rxjava:$RX_JAVA_VERSION")
    api("io.reactivex.rxjava3:rxandroid:$RX_ANDROID_VERSION")
    api("androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION")
    api("androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION")
    api("androidx.navigation:navigation-dynamic-features-fragment:$NAVIGATION_VERSION")
    implementation("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:$INTERCEPTOR_VERSION")
    implementation("com.squareup.retrofit2:converter-scalars:$RETROFIT_VERSION")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$RETROFIT_VERSION")
    implementation("com.google.code.gson:gson:$GSON_VERSION")
    api("com.jakewharton.timber:timber:$TIMBER_VERSION")
    implementation("androidx.room:room-runtime:$ROOM_VERSION")
    kapt("androidx.room:room-compiler:$ROOM_VERSION")
    implementation("androidx.room:room-ktx:$ROOM_VERSION")
    implementation("androidx.room:room-rxjava3:$ROOM_VERSION")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation("org.mockito:mockito-core:1.10.19")
}