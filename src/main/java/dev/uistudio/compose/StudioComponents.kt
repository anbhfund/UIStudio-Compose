package dev.uistudio.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** No dependency on the host app. Callbacks are owned by the host. */
data class StudioAction(val id: String, val label: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun StudioActionPanel(
    style: StudioStyle,
    actions: List<StudioAction>,
    modifier: Modifier = Modifier,
    heroActionId: String? = null,
    heading: String = "Actions",
    heroLabel: String? = null,
) {
    val spec = layoutFor(style)
    Column(modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
        Text(heading, style = if (spec.compact) MaterialTheme.typography.labelLarge else MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp))
        val hero = heroActionId?.let { id -> actions.firstOrNull { it.id == id } }
        if (hero != null) {
            Button(onClick = hero.onClick, modifier = Modifier.fillMaxWidth().height(spec.heroHeightDp.dp).testTag("ui-studio.hero")) {
                Icon(hero.icon, contentDescription = null)
                Spacer(Modifier.width(10.dp))
                Text(heroLabel ?: hero.label)
            }
        }
        Spacer(Modifier.height(spec.spacingDp.dp))
        actions.filter { it.id != hero?.id }.chunked(spec.columns).forEach { group ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spec.spacingDp.dp)) {
                group.forEach { action ->
                    if (spec.compact) {
                        FilledTonalButton(
                            onClick = action.onClick,
                            modifier = Modifier.weight(1f).heightIn(min = MIN_TOUCH_TARGET_DP.dp).testTag("ui-studio.action.${action.id}"),
                            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
                        ) { Text(action.label, style = MaterialTheme.typography.labelSmall, maxLines = 2, textAlign = TextAlign.Center) }
                    } else {
                        Surface(
                            modifier = Modifier.weight(1f).padding(vertical = 3.dp)
                                .heightIn(min = MIN_TOUCH_TARGET_DP.dp)
                                .testTag("ui-studio.action.${action.id}")
                                .clickable(onClick = action.onClick),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(Modifier.padding(vertical = if (style == StudioStyle.PLAYFUL) 14.dp else 9.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(action.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                Text(action.label, style = MaterialTheme.typography.labelSmall, maxLines = 2,
                                    textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    }
                }
                repeat(spec.columns - group.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}
