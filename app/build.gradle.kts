plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "pe.com.proyectocatalogo"
    compileSdk = 34

    defaultConfig {
        applicationId = "pe.com.proyectocatalogo"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.base)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.android.gms:play-services-auth:20.4.1")
    implementation ("com.google.android.gms:play-services-auth:20.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.android.gms:play-services-auth:20.6.0")
    implementation ("com.google.android.gms:play-services-identity:17.0.0")
    // Dependencia para Google Sign-In
    implementation ("com.google.android.gms:play-services-auth:20.5.0") // Asegúrate de usar la versión más reciente

    // Dependencia para RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.2.1") // Asegúrate de usar la versión más reciente

}