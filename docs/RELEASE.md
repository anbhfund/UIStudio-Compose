# Phase 9 release checklist

## 1. Repository and legal decisions

- [ ] Pick and add a real license for the free library core (e.g. MIT or Apache-2.0,
  or an explicit proprietary evaluation license). Do **not** publish as open source
  without a license decision; reserve any paid Pro assets separately.
- [ ] Create a **separate** public GitHub repository named `UIStudio-Compose`
  under `anbhfund` (or edit build files / docs if choosing another name).
- [ ] Extract this ZIP and commit the **contents** to the new repository's root.
  A ZIP-only upload workflow cannot publish JitPack artifacts; JitPack needs real source files in the repo.
- [ ] Confirm no user credentials, private app data, API tokens, or proprietary paid assets.

## 2. Before tagging

- [ ] Run `.github/workflows/library-ci.yml` successfully on `main`.
- [ ] Confirm standalone library JVM unit tests and AAR release assembly succeed.
- [ ] Confirm `publishReleasePublicationToMavenLocal` succeeds.
- [ ] Confirm independent Notes consumer compiles using the published Maven AAR.
- [ ] If changing component layout, run the screenshot/regression suite in the
  private demo project and approve intentional changes there. The
  screenshots are not included in this standalone repository.
- [ ] Check the API surface, accessibility, contrast, touch-target behavior and
  migration notes on a real phone or emulator before broader adoption.

## 3. JitPack release

- [ ] Tag the tested commit **v0.9.0** and create a GitHub Release for that tag.
- [ ] Visit `https://jitpack.io/#anbhfund/UIStudio-Compose` and request v0.9.0.
- [ ] Verify the build is green and AAR metadata resolves.
- [ ] Verify `com.github.anbhfund:UIStudio-Compose:v0.9.0` builds in a clean
  independent Android app; CI's MavenLocal test alone is not enough.
- [ ] Replace any example tag/repo coordinates if release settings differ.

## 4. Upgrade policy

- Do not mutate released Git tags. Use v0.9.1 to fix a published bug.
- Preserve `StudioStyle.id` values and schemaVersion 1 for existing imports.
- Breaking API changes require migration notes and an intentional version bump.
- Do not claim fully automated app redesign; the agent must review the host UI
  and map real callback/navigation behavior safely.

## Distribution caveat

JitPack builds a **GitHub source checkout**, not an uploaded source ZIP artifact.
A public GitHub repo usually requires no separate publishing account; private
repositories may require authorization. JitPack itself controls build availability.
