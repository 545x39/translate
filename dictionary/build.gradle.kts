import dependencies.Versions.DAGGER_VERSION
import dependencies.Versions.RX_BINDING_VERSION

plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
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
}

dependencies {
    implementation(project(":app"))
    /** DAGGER */
    kapt("com.google.dagger:dagger-compiler:$DAGGER_VERSION")
    kapt("com.google.dagger:dagger-android-processor:$DAGGER_VERSION")
    implementation("androidx.core:core-ktx:1.7.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.jakewharton.rxbinding4:rxbinding:$RX_BINDING_VERSION")
    implementation("com.jakewharton.rxbinding4:rxbinding-core:$RX_BINDING_VERSION")
    implementation("com.jakewharton.rxbinding4:rxbinding-appcompat:$RX_BINDING_VERSION")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.annotation:annotation:1.3.0")
}