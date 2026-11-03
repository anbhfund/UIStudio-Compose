package dev.uistudio.compose

import org.junit.Assert.*
import org.junit.Test

class StudioCatalogTest {
    @Test fun styleIdsAreStableAndUnique() {
        assertEquals(4, StudioStyle.entries.size)
        assertEquals(4, StudioStyle.entries.map { it.id }.toSet().size)
        assertEquals(StudioStyle.ORIGINAL, StudioStyle.fromId(null))
        assertEquals(StudioStyle.ORIGINAL, StudioStyle.fromId("invalid"))
        StudioStyle.entries.forEach { assertEquals(it, StudioStyle.fromId(it.id)) }
    }

    @Test fun layoutsAreDistinctAndValid() {
        val variants = StudioStyle.entries.map(::layoutFor)
        assertEquals(4, variants.toSet().size)
        variants.forEach {
            assertTrue(it.columns in 1..4)
            assertTrue(it.heroHeightDp >= 48)
            assertTrue(it.spacingDp > 0)
        }
    }
}
