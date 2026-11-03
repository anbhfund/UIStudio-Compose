package dev.uistudio.compose

import org.junit.Assert.*
import org.junit.Test

class StudioExportTest {
    @Test fun exportHasStableVersionAndId() {
        val json = StudioConfig(StudioStyle.PRECISION, "Task actions", "Create").toJson()
        assertTrue(json.contains("\"schemaVersion\": 1"))
        assertTrue(json.contains("\"style\": \"precision\""))
        assertTrue(json.contains("\"showHero\": true"))
    }
    @Test fun exportEscapesUserLabels() {
        val json = StudioConfig(StudioStyle.PLAYFUL, "Hello \"world\"\\test", "New\nline").toJson()
        assertTrue(json.contains("Hello \\\"world\\\"\\\\test"))
        assertTrue(json.contains("New\\nline"))
    }
    @Test fun exportedVisibilityMatchesConfiguration() {
        val json = StudioConfig(StudioStyle.ORIGINAL, "Actions", "Start", false, false, true).toJson()
        assertTrue(json.contains("\"showHero\": false"))
        assertTrue(json.contains("\"showActions\": false"))
        assertTrue(json.contains("\"showTasks\": true"))
    }
}
