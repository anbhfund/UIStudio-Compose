package dev.uistudio.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/** Checks both accessibility semantics and real callback routing. */
class StudioAccessibilityTest {
    @get:Rule val rule = createComposeRule()

    @Test fun precisionActionsAreTappableAndAtLeast48dp() {
        var addClicks = 0
        var editClicks = 0
        rule.setContent {
            MaterialTheme {
                StudioActionPanel(
                    style = StudioStyle.PRECISION,
                    heading = "Actions",
                    heroActionId = "new",
                    actions = listOf(
                        StudioAction("new", "New", Icons.Default.Add) { addClicks++ },
                        StudioAction("edit", "Edit", Icons.Default.Check) { editClicks++ }
                    )
                )
            }
        }
        rule.onNodeWithTag("ui-studio.hero").assertExists().assertHasClickAction()
            .assertHeightIsAtLeast(48.dp).performClick()
        rule.onNodeWithTag("ui-studio.action.edit").assertExists().assertHasClickAction()
            .assertHeightIsAtLeast(48.dp).performClick()
        rule.runOnIdle {
            assertEquals(1, addClicks)
            assertEquals(1, editClicks)
        }
    }

    @Test fun playfulActionsKeep48dpTouchTargets() {
        var clicks = 0
        rule.setContent {
            MaterialTheme {
                StudioActionPanel(
                    style = StudioStyle.PLAYFUL,
                    actions = listOf(StudioAction("save", "Save note", Icons.Default.Check) { clicks++ })
                )
            }
        }
        rule.onNodeWithTag("ui-studio.action.save").assertExists().assertHasClickAction()
            .assertHeightIsAtLeast(48.dp).performClick()
        rule.runOnIdle { assertEquals(1, clicks) }
    }
}
