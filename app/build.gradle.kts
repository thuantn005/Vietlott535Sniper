plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.thuan.vietlott535sniper"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.thuan.vietlott535sniper"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-sniper"
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.8" }
    buildTypes { release { isMinifyEnabled = false } }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
}