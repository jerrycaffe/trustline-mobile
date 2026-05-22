import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
    namespace = "com.example.trustline"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.trustline"
        minSdk = 24
        targetSdk = 37
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
            val baseUrl = project.findProperty("PROD_BASE_URL") ?: ""
            val institutionId = project.findProperty("PROD_INSTITUTION_ID") ?: ""
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "INSTITUTION_ID", "\"$institutionId\"")
        }
        debug {
            val baseUrl = project.findProperty("DEV_BASE_URL") ?: ""
            val institutionId = project.findProperty("DEV_INSTITUTION_ID") ?: ""
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "INSTITUTION_ID", "\"$institutionId\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    runtimeOnly(libs.koin.androidx.compose)

    // Retrofit & Serialization
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.gson)

    // Security & DataStore
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.datastore.preferences)

    // Image loading
    implementation(libs.coil.compose)

    // OkHttp logging (for debugging)
    implementation(libs.logging.interceptor)

    // Compose & UI
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
