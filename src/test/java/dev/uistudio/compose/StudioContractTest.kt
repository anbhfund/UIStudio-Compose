package dev.uistudio.compose

import org.junit.Assert.*
import org.junit.Test

class StudioContractTest {
    @Test fun stableIdentifiers() {
        assertEquals(StudioStyle.entries.size, StudioStyle.entries.map { it.id }.toSet().size)
        assertEquals("editorial", StudioStyle.EDITORIAL.id)
        assertEquals("precision", StudioStyle.PRECISION.id)
        assertEquals("playful", StudioStyle.PLAYFUL.id)
    }
    @Test fun fallbackIsSafe() {
        assertEquals(StudioStyle.ORIGINAL, StudioStyle.fromId(null))
        assertEquals(StudioStyle.ORIGINAL, StudioStyle.fromId("not-a-style"))
        assertEquals(StudioStyle.PLAYFUL, StudioStyle.fromId("playful"))
    }
    @Test fun minimumTouchTargetAndOriginalPhase2Spacing() {
        assertTrue(MIN_TOUCH_TARGET_DP >= 48)
        assertEquals(7, layoutFor(StudioStyle.EDITORIAL).spacingDp)
        assertEquals(7, layoutFor(StudioStyle.PLAYFUL).spacingDp)
    }
    @Test fun everyStyleHasValidLayout() {
        StudioStyle.entries.forEach { style ->
            val spec = layoutFor(style)
            assertTrue(spec.columns in 1..4)
            assertTrue(spec.heroHeightDp >= 48)
            assertTrue(spec.spacingDp > 0)
        }
        assertNotEquals(layoutFor(StudioStyle.PLAYFUL), layoutFor(StudioStyle.PRECISION))
    }
}
