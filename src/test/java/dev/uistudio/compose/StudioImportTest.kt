package dev.uistudio.compose

import org.junit.Assert.*
import org.junit.Test

class StudioImportTest {
    private val sample = StudioConfig(StudioStyle.PLAYFUL, "Quoted \"hello\"", "New\nitem", false, true, true)
    @Test fun roundTripRetainsFieldsAndEscapes() { assertEquals(sample, parseStudioConfig(sample.toJson()).getOrThrow()) }
    @Test fun rejectsInvalidJson() { assertTrue(parseStudioConfig("{").isFailure) }
    @Test fun rejectsUnknownStyle() { assertTrue(parseStudioConfig(sample.toJson().replace("playful", "unknown")).isFailure) }
    @Test fun rejectsFutureSchema() { assertTrue(parseStudioConfig(sample.toJson().replace("\"schemaVersion\": 1", "\"schemaVersion\": 2")).isFailure) }
    @Test fun rejectsWrongBooleanType() { assertTrue(parseStudioConfig(sample.toJson().replace("\"showHero\": false", "\"showHero\": \"false\"")).isFailure) }
    @Test fun rejectsMissingField() { assertTrue(parseStudioConfig(sample.toJson().replace(Regex("\\s*\"showTasks\": true"), "")).isFailure) }
    @Test fun rejectsOversizedInput() { assertTrue(parseStudioConfig(" ".repeat(33000)).isFailure) }
    @Test fun rejectsNonObject() { assertTrue(parseStudioConfig("[]").isFailure) }
}
