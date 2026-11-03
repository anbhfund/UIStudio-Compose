plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "dev.uistudio.consumer"
    compileSdk = 35
    defaultConfig { applicationId = "dev.uistudio.consumer"; minSdk = 26; targetSdk = 35 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
}
dependencies {
    val uiVersion = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.9.0-local"
    // IMPORTANT: Maven coordinate, not project(":ui-studio") or source copying.
    implementation("com.github.anbhfund:UIStudio-Compose:$uiVersion")
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
}
