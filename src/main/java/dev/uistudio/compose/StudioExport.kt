package dev.uistudio.compose

/** A portable configuration describing visual choices, not host app behavior. */
data class StudioConfig(
    val style: StudioStyle,
    val heading: String,
    val heroLabel: String,
    val showHero: Boolean = true,
    val showActions: Boolean = true,
    val showTasks: Boolean = true,
)

/** Deterministic, dependency-free JSON for copy, share and agent hand-off. */
fun StudioConfig.toJson(): String = """{
  "schemaVersion": 1,
  "library": "ui-studio",
  "style": "${style.id}",
  "heading": "${heading.escapeJson()}",
  "heroLabel": "${heroLabel.escapeJson()}",
  "showHero": $showHero,
  "showActions": $showActions,
  "showTasks": $showTasks
}"""

private fun String.escapeJson(): String = buildString {
    for (char in this@escapeJson) {
        when (char) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> if (char.code < 0x20) append("\\u%04x".format(char.code)) else append(char)
        }
    }
}
