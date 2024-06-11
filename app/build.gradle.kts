plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.gms.google-services")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.dss.xeapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dss.xeapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 8
        versionName = "1.8"

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
        dataBinding =true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.8.0")
    implementation("androidx.databinding:viewbinding:8.3.0")
    implementation ("com.google.android.material:material:1.8.0'")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'")

    //ads
    implementation ("com.google.android.gms:play-services-ads:23.0.0")
    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")
    //in-app-update
    implementation ("com.google.android.play:app-update-ktx:2.1.0")
    implementation ("com.google.android.play:app-update:2.1.0")
    //DI
    implementation ("com.google.dagger:hilt-android:2.44")
    implementation("com.google.android.ump:user-messaging-platform:2.2.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
    kapt("com.google.dagger:hilt-compiler:2.44")
    //room
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    //lib
    implementation ("org.greenrobot:eventbus:3.3.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    implementation ("com.mikhaellopez:circularprogressbar:3.1.0")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("com.airbnb.android:lottie:5.2.0")
    implementation ("com.guolindev.permissionx:permissionx:1.7.1")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.github.ome450901:SimpleRatingBar:1.5.1")
}