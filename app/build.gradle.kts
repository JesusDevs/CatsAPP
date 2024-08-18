import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.jys.catsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jys.catsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val properties = Properties()
        android.buildFeatures.buildConfig = true

        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY_PEXEL", "\"${properties.getProperty("API_KEY_PEXEL")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {}
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    api(platform(libs.koin.bom))
    api(libs.koin.android)
    api(libs.koin.androidx.workmanager)
    api(libs.koin.androidx.navigation)
    api(libs.koin.core.coroutines)
    api(libs.koin.androidx.compose)
    testApi(libs.koin.test.junit4)
    testApi(libs.koin.android.test)

    api(libs.io.coil.kt)
    api(libs.androidx.navigation.compose)

    api(libs.okhttp.logging)
    api(libs.retrofit.core)
    api(libs.retrofit.kotlin.serialization)
    api(libs.retrofit.converter)


    api(libs.paging3.compose)
    api(libs.paging3.runtime)
    testApi(libs.paging3.common.test)
}
