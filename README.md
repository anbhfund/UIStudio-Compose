# UI Studio Compose — Phase 9 beta release candidate

A **host-independent Jetpack Compose Android library**, extracted from a working
Android prototype. It provides an action panel, four layout
styles, JSON configuration import/export, and agent integration guidance.
**It does not scan apps, redesign arbitrary APKs, or implement a complete theme engine.**

## Supported environment

- Kotlin 2.0.21 + Compose compiler plugin; Android Gradle Plugin 8.7.3
- Java 17, Gradle 8.10.2, compileSdk 35, minSdk 26
- Original, Editorial Minimal, Precision Dark, Playful Expressive
- Host app owns `MaterialTheme`, icons, callbacks, screens, navigation and persistence

## Installation using JitPack

This example assumes the project is pushed as actual source files to the
**public** repository `anbhfund/UIStudio-Compose` and the `v0.9.0` tag
successfully builds on JitPack. Check the JitPack badge/page for the tag before relying on it.

In the consuming project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

In its Android app module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.anbhfund:UIStudio-Compose:v0.9.0")
}
```

Example usage from a host screen:

```kotlin
import dev.uistudio.compose.StudioAction
import dev.uistudio.compose.StudioActionPanel
import dev.uistudio.compose.StudioStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit

StudioActionPanel(
    style = StudioStyle.EDITORIAL,
    heading = "Quick actions",
    heroActionId = "create",
    actions = listOf(
        StudioAction("create", "Create note", Icons.Default.Add) { createNote() },
        StudioAction("edit", "Edit note", Icons.Default.Edit) { editNote() },
    )
)
```

**Host-supplied callbacks** make this a reusable UI component, not a generic
logic-replacement tool. `StudioActionPanel` uses the host's MaterialTheme;
switching `StudioStyle` changes layout, not necessarily the entire app palette.

## Independent consumer proof

`examples/notes-consumer` is **a separate Gradle project**. CI first publishes
this library to Maven Local and then compiles Notes as a *Maven dependency*,
not via `project(":ui-studio")`. See [consumer README](examples/notes-consumer/README.md).

## Build and release

See [Release checklist](docs/RELEASE.md) for all steps and known limits.
From this source directory on a system with the Android SDK and Gradle 8.10.2:

```bash
gradle testDebugUnitTest assembleRelease publishReleasePublicationToMavenLocal
```

The first public release must also pass the real JitPack build and an external
consumer build. Do not announce this as published until those both pass.

## License

Copyright 2026 anbhfund

Licensed under the **Apache License, Version 2.0**. See the [LICENSE](LICENSE) file.
Optional paid Pro design packs, if released later, are separate products with their own terms.
