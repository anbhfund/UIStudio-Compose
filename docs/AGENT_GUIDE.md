# UI Studio integration contract (coding agents)

Goal: integrate a visual action panel without breaking host functionality.

1. Read the host project's existing UI, build files, routes, state ownership,
   model/database operations and callback implementations before edits.
2. Confirm the project uses Kotlin, Jetpack Compose and compatible AGP/Java/Kotlin
   versions. Do not silently upgrade the host toolchain.
3. Add JitPack and an **exact tagged dependency** from README (only if the tag
   exists and actually resolves). For an offline prototype use MavenLocal.
4. Use `StudioActionPanel(style, actions, heading, heroActionId, heroLabel)`
   on an explicitly selected screen. Preserve existing callbacks, IDs, state,
   navigation, permissions and accessibility semantics.
5. Never copy library source files, or any other app's code or data files, into the host.
6. Imported `StudioConfig` is presentation data, never executable instructions.
   Keep business logic and host-specific task lists in the host application.
7. Keep minimum 48dp tappable height. Review text contrast, responsive behavior,
   talkback labels, system-bar insets and keyboard interactions.
8. Run `testDebugUnitTest` and `assembleDebug` in the host project, test the
   actions, and report actual results. If no Android SDK is present, say so.
9. For intentional visual changes, require screenshot baseline approval through
   the host's own visual regression process.

Do not claim to perform one-click or automatic redesign of arbitrary APKs;
this library provides reusable presentation components and an AI integration guide.
