#!/usr/bin/env python3
"""Phase 9 release contract checks, no Gradle/SDK needed."""
from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET

base = Path(__file__).resolve().parent.parent
read = lambda p: (base / p).read_text(encoding="utf-8")
library = read("build.gradle.kts")
settings = read("settings.gradle.kts")
sample = read("examples/notes-consumer/app/build.gradle.kts")
sample_settings = read("examples/notes-consumer/settings.gradle.kts")
source = read("src/main/java/dev/uistudio/compose/StudioComponents.kt")
public = read("src/main/java/dev/uistudio/compose/StudioStyle.kt")
manifest = ET.parse(base / "examples/notes-consumer/app/src/main/AndroidManifest.xml")

assertions = {
    "standalone Android library plugin": 'id("com.android.library")' in library,
    "no host app modules": 'include(' not in settings and 'rootProject.name = "UIStudio-Compose"' in settings,
    "maven-publish applied": '`maven-publish`' in library,
    "release variant configured": 'singleVariant("release")' in library,
    "sources AAR publication": 'withSourcesJar()' in library,
    "release Maven publication": 'create<MavenPublication>("release")' in library,
    "JitPack version injected": 'System.getenv("VERSION")' in library,
    "public Compose UI API": 'api("androidx.compose.ui:ui")' in library,
    "independent consumer uses Maven coordinate": 'implementation("com.github.anbhfund:UIStudio-Compose:$uiVersion")' in sample,
    "no project module dependency": 'implementation(project(' not in sample,
    "MavenLocal consumer verification": 'mavenLocal()' in sample_settings,
    "consumer manifest parses": manifest.getroot().tag == 'manifest',
    "app-owned callbacks remain": 'val onClick: () -> Unit' in source,
    "minimum 48dp target": 'MIN_TOUCH_TARGET_DP' in source and 'MIN_TOUCH_TARGET_DP: Int = 48' in public,
    "four style IDs preserved": all(f'"{s}"' in public for s in ('original','editorial','precision','playful')),
    "JitPack Java17 declared": 'openjdk17' in read('jitpack.yml'),
    "CI checks consumer app": 'examples/notes-consumer :app:assembleDebug' in read('.github/workflows/library-ci.yml'),
    "on-device tests compiled in CI": 'assembleDebugAndroidTest' in read('.github/workflows/library-ci.yml'),
    "no invalid member-function test imports": 'import androidx.compose.ui.test.assertExists' not in read('src/androidTest/java/dev/uistudio/compose/StudioAccessibilityTest.kt'),
    "no private project names in public files": not any(
        w in f.read_text(encoding="utf-8", errors="ignore").lower()
        for f in base.rglob("*") if f.is_file() and f.suffix in (".md", ".kt", ".kts", ".yml", ".xml", ".properties")
        for w in ("clearscan", "tasks demo", "ui_test")),
    "JSON parser remains present": (base / 'src/main/java/dev/uistudio/compose/StudioImport.kt').is_file(),
}
for title, passed in assertions.items():
    print(("PASS" if passed else "FAIL") + "  " + title)
print(f"{sum(assertions.values())}/{len(assertions)} checks passed")
if not all(assertions.values()): sys.exit(1)
