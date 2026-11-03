# Notes consumer (independent Gradle project)

This app intentionally does NOT include or reference UI Studio's source module.
Its dependency is `com.github.anbhfund:UIStudio-Compose:$VERSION`.

From the UI Studio repository root, after installing Android SDK and Gradle 8.10.2:

```bash
gradle testDebugUnitTest assembleRelease publishReleasePublicationToMavenLocal
gradle -p examples/notes-consumer :app:assembleDebug
```

CI runs both commands sequentially using Maven Local. Once `v0.9.0` is tagged
and JitPack resolves it, delete `mavenLocal()` from the sample's settings and
rerun the consumer using the real JitPack dependency before announcing release.
