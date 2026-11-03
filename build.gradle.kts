import org.gradle.api.publish.maven.MavenPublication

plugins {
    id("com.android.library") version "8.7.3"
    id("org.jetbrains.kotlin.android") version "2.0.21"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    `maven-publish`
}

// JitPack injects GROUP and VERSION from the GitHub repository and tag.
// For local developer builds, use the coordinate shown in the sample consumer.
group = System.getenv("GROUP")?.takeIf { it.isNotBlank() } ?: "com.github.anbhfund"
version = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.9.0-local"

android {
    namespace = "dev.uistudio.compose"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
    publishing {
        singleVariant("release") { withSourcesJar() }
    }
}

dependencies {
    api(platform("androidx.compose:compose-bom:2024.12.01"))
    api("androidx.compose.ui:ui") // ImageVector is part of public StudioAction API.
    api("androidx.compose.runtime:runtime") // Public composable API.
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")
    implementation("com.google.code.gson:gson:2.11.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = project.group.toString()
                artifactId = "UIStudio-Compose"
                version = project.version.toString()
                pom {
                    name.set("UI Studio Compose")
                    description.set("Host-independent Jetpack Compose actions and style configurations")
                    url.set("https://github.com/anbhfund/UIStudio-Compose")
                    scm { url.set("https://github.com/anbhfund/UIStudio-Compose") }
                }
            }
        }
    }
}
