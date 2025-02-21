plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.proyecto.aquamaris"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.proyecto.aquamaris"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "0.9"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

secrets {
    // To add your Maps API key to this project:
    // 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
    // 2. Add this line, where YOUR_API_KEY is your API key:
    //        MAPS_API_KEY=YOUR_API_KEY
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"
}


dependencies {
    implementation("org.jsoup:jsoup:1.10.3")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.google.firebase.database)
    testImplementation(libs.junit)
    implementation(libs.jsoup)
    implementation(libs.glide)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.cardview)
    implementation(libs.recyclerview.selection)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.core)
    implementation(libs.android.maps.utils)
    implementation(libs.asset.delivery)
    implementation(libs.feature.delivery)
    implementation(libs.review)
    implementation (libs.app.update)
}