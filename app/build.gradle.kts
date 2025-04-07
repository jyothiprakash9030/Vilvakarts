plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.vcartbusbooking"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vilva.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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
    sourceSets {
        getByName("main") {
            res {
                srcDirs("src/main/res", "src/main/res/3")
            }
        }
    }
}

dependencies {

        implementation (libs.recyclerview)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation (libs.com.squareup.okhttp3.okhttp)
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation (libs.com.squareup.okhttp3.okhttp)
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")

    implementation ("com.google.android.material:material:1.9.0")
    implementation(libs.material.v1110)
    implementation ("com.airbnb.android:lottie:6.4.0")

    implementation ("com.squareup.okhttp3:okhttp:4.9.3")


    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")

    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")



        implementation ("com.razorpay:checkout:1.6.26")


}