package dev.uistudio.compose

/** Public, app-independent style identifiers. Persist [id], not ordinal. */
enum class StudioStyle(val id: String, val title: String) {
    ORIGINAL("original", "Original"),
    EDITORIAL("editorial", "Editorial Minimal"),
    PRECISION("precision", "Precision Dark"),
    PLAYFUL("playful", "Playful Expressive");

    companion object {
        fun fromId(id: String?): StudioStyle = entries.firstOrNull { it.id == id } ?: ORIGINAL
    }
}

/** Minimum touch target for each clickable UI Studio component. */
const val MIN_TOUCH_TARGET_DP: Int = 48

/** Non-UI configuration to make spacing/layout decisions testable on the JVM. */
data class StudioLayout(val columns: Int, val compact: Boolean, val heroHeightDp: Int, val spacingDp: Int)

fun layoutFor(style: StudioStyle): StudioLayout = when (style) {
    StudioStyle.ORIGINAL -> StudioLayout(2, false, 52, 8)
    StudioStyle.EDITORIAL -> StudioLayout(3, false, 52, 7)
    StudioStyle.PRECISION -> StudioLayout(3, true, 52, 4)
    StudioStyle.PLAYFUL -> StudioLayout(2, false, 62, 7)
}
