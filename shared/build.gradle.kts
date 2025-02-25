plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.skie)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.androidx.pref.datastore)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)

            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.test.mockk.common)
            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.client.cio)
        }

        androidMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            //implementation(libs.ktor.client.android)

            implementation(libs.ktor.client.okhttp)
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.test.mockk.jvm)
            }
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }

    task("testClasses")
}

android {
    namespace = "com.tamesys.workmode"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
}
