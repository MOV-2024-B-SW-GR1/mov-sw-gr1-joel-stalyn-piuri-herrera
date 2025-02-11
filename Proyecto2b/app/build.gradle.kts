    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.kotlin.android)
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    }

    android {
        namespace = "com.example.a04_deber01"
        compileSdk = 35

        buildFeatures {
            viewBinding = true
        }

        defaultConfig {
            applicationId = "com.example.a04_deber01"
            minSdk = 24
            targetSdk = 35
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
        // Dependencias esenciales
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)

        // ViewModel y LiveData
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

        // Kotlinx Serialization
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }