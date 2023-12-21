plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val compose_version = "1.4.3"

android {
    namespace = "com.dicoding.chickfarm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.chickfarm"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        mlModelBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

//    Jetpack Compose
    implementation("androidx.activity:activity-compose:$compose_version")
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$compose_version")
    implementation("androidx.compose.ui:ui")
    implementation ("androidx.compose.runtime:runtime:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation ("androidx.compose.material3:material3")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    Navigation
    implementation ("androidx.navigation:navigation-compose:2.6.0")
//    Maps
    implementation ("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.android.gms:play-services-location:18.0.0")
//    Camera
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation ("androidx.camera:camera-camera2:1.3.0")
    implementation ("androidx.camera:camera-view:1.3.0")
//    Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.18.0")
//    Icons
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
//    Coil untuk AsynImage
    implementation ("io.coil-kt:coil-compose:2.2.0")
//    TensorFlow Lite
    implementation ("org.tensorflow:tensorflow-lite:2.8.0")
//    Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


}