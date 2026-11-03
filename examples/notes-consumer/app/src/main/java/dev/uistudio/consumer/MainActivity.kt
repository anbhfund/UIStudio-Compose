package dev.uistudio.consumer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.uistudio.compose.StudioAction
import dev.uistudio.compose.StudioActionPanel
import dev.uistudio.compose.StudioStyle

/** Standalone application; compiles against the published Maven artifact only. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var status by androidx.compose.runtime.remember { mutableStateOf("No action yet") }
            MaterialTheme {
                Surface {
                    Column(Modifier.padding(top = 32.dp)) {
                        Text("Independent Notes app", style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp))
                        StudioActionPanel(
                            style = StudioStyle.EDITORIAL,
                            heading = "Notes actions",
                            heroActionId = "new-note",
                            heroLabel = "New note",
                            actions = listOf(
                                StudioAction("new-note", "New note", Icons.Default.Add) { status = "Created note" },
                                StudioAction("edit-note", "Edit", Icons.Default.Edit) { status = "Edit selected" },
                            ),
                        )
                        Text(status, modifier = Modifier.fillMaxWidth().padding(16.dp))
                    }
                }
            }
        }
    }
}
