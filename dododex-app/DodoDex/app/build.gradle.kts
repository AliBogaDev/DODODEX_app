plugins {
    id("com.android.application")

    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.app.dododexapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.dododexapp"
        minSdk = 28
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

    buildFeatures {
        viewBinding = true

        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }

        }
    }

    dependencies {

        implementation("com.google.android.gms:play-services-maps:18.2.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
        implementation("com.github.bumptech.glide:glide:4.13.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
        implementation("androidx.activity:activity-compose:1.8.2")
        implementation(platform("androidx.compose:compose-bom:2023.08.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("com.android.volley:volley:1.2.1")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("androidx.navigation:navigation-fragment:2.7.7")
        implementation("androidx.navigation:navigation-ui:2.7.7")

    }
}