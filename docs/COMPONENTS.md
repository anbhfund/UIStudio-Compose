# Public API and integration notes

| Symbol | Function |
|---|---|
| `StudioStyle` | Stable style IDs: original, editorial, precision, playful |
| `StudioAction` | App-owned ID, label, icon, onClick callback |
| `StudioActionPanel` | Themed action panel; app owns MaterialTheme |
| `StudioLayout` / `layoutFor` | Testable per-style column/gap/touch structure |
| `StudioConfig` | Portable UI presentation settings |
| `StudioConfig.toJson()` | Versioned JSON exporter |
| `parseStudioConfig(String)` | Returns Result; rejects invalid JSON/schema |

`StudioActionPanel` does not expose database, billing, OCR, or scanner behavior.
It is not a standalone screen generator. The four styles change the panel layout;
they do not automatically recolor every screen of an existing Android app.

**Compatibility:** Android 8.0+ (minSdk 26), compileSdk 35, Java 17,
Jetpack Compose using Kotlin 2.0.21, AGP 8.7.3 / Gradle 8.10.2 tested by CI.

**Schema 1 contract:**

```json
{
  "schemaVersion": 1,
  "library": "ui-studio",
  "style": "playful",
  "heading": "Task actions",
  "heroLabel": "Create task",
  "showHero": false,
  "showActions": true,
  "showTasks": true
}
```

`showTasks` is a demo-app preference. The library does not render a full
task manager; host apps decide what their tasks mean.
