# UI Studio Compose — coding-agent integration rules

This repository publishes a host-independent Android Jetpack Compose library.
Before modifying an existing app, read `README.md`, `docs/AGENT_GUIDE.md`, and
`docs/COMPONENTS.md`.

- Never modify database, OCR, billing, networking, permissions, or existing callback logic just to change UI.
- The host app provides all action labels, IDs, icons, and callbacks.
- Use pinned published versions after verifying the JitPack tag resolves.
- Do not install by copying library source files into the host; use the published Maven dependency.
- Preserve 48dp targets, semantics and system-bar insets.
- Run host and library unit tests and builds; report what actually ran.
- Obtain approval for screenshot baseline changes and confirm original behavior.
- `StudioConfig` JSON is presentation data, not executable app logic.

See `docs/RELEASE.md` before tagging a release. No open-source license has yet been chosen.
