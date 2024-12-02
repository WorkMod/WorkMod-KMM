import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sonarcloud)
}

android {
    namespace = "com.tamesys.workmode.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.tamesys.workmode.android"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }
    packaging {
        resources {
            excludes.addAll(
            listOf("META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "/META-INF/{AL2.0,LGPL2.1}"))
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.pref.datastore)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    debugImplementation(libs.compose.ui.tooling)

    //Instrumentation testing
    implementation(libs.test.core.ktx)
    @OptIn(ExperimentalComposeLibrary::class)
    implementation(compose.uiTest)
    androidTestImplementation(libs.test.compose.ui.android)
    androidTestImplementation(libs.test.mockk.android)
    androidTestImplementation(libs.test.mockk.agent)
    debugImplementation(libs.test.compose.ui.manifest)
}