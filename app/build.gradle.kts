plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.hola"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hola"

        minSdk = 21
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding=true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //ssp dsp


    implementation ("com.intuit.ssp:ssp-android:1.0.6")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.google.android.material:material:1.9.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.android.material:material:1.6.0")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.picasso:picasso:2.8")

    implementation ("com.google.android.material:material:1.9.0")





        // OkHttp dependencies for multipart and media types
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

        // Kotlin extension for Retrofit and OkHttp (for toRequestBody)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0") // Example: Replace with the latest version

    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.10.0")
    implementation ("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    implementation ("com.github.bumptech.glide:glide:4.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")




}

