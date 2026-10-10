package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ContinueHistoryScreen(visits: List<RecentVisit>, onHome: () -> Unit,
    onPickGame: () -> Unit, onResume: (RecentVisit) -> Unit, onRemove: (Set<GameId>) -> Unit) {
    BackHandler(onBack = onHome)
    val gold = Color(0xFFD7B06A)
    val body = Color(0xFFC7B89B)
    var selected by remember { mutableStateOf(emptySet<GameId>()) }
    var pendingRemoval by remember { mutableStateOf<Set<GameId>?>(null) }
    LaunchedEffect(visits) { selected = selected.intersect(visits.map { it.game }.toSet()) }
    val tablet = isLandscapeTablet()
    val compactTablet = isCompactLandscapeTablet()
    val continueContent: @Composable ColumnScope.() -> Unit = {
    Spacer(Modifier.height(if (compactTablet) 4.dp else 18.dp))
    Text("CONTINUE", color = gold, fontSize = if (compactTablet) 23.sp else 28.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    Text("Your last page in each game. Most recently visited first.", color = body, fontSize = 14.sp)
    if (visits.isNotEmpty()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = {
                selected = if (selected.size == visits.size) emptySet() else visits.map { it.game }.toSet()
            }, modifier = Modifier.heightIn(min = if (compactTablet) 48.dp else 56.dp).testTag("continue_select_all")) {
                Text(if (selected.size == visits.size) "CLEAR SELECTION" else "SELECT ALL", color = gold, fontSize = 13.sp)
            }
            TextButton(enabled = selected.isNotEmpty(), onClick = { pendingRemoval = selected },
                modifier = Modifier.heightIn(min = if (compactTablet) 48.dp else 56.dp).testTag("continue_delete_selected")) {
                Text("DELETE SELECTED", color = if (selected.isNotEmpty()) gold else body.copy(alpha = 0.4f), fontSize = 13.sp)
            }
        }
        TextButton(onClick = { pendingRemoval = visits.map { it.game }.toSet() },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("continue_delete_all")) {
            Text("DELETE ALL ENTRIES", color = gold, fontSize = 13.sp)
        }
    }
    LazyColumn(Modifier.weight(1f).fillMaxWidth()) {
        if (visits.isEmpty()) item {
            Spacer(Modifier.height(24.dp))
            Text("No recent games yet", color = gold, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("Open a game to start its Continue entry. Removing entries here never clears completed quests or favorites.",
                color = body, fontSize = 16.sp, lineHeight = 23.sp)
        }
        items(visits, key = { it.game.name }) { visit ->
            val shape = RoundedCornerShape(12.dp)
            Row(Modifier.fillMaxWidth().background(Color(0xEF15100D), shape)
                .border(1.dp, if (visit.game in selected) gold else gold.copy(alpha = 0.35f), shape)
                .padding(8.dp).testTag("continue_entry_${visit.game.name.lowercase()}")) {
                Checkbox(checked = visit.game in selected, onCheckedChange = { checked ->
                    selected = if (checked) selected + visit.game else selected - visit.game
                }, colors = CheckboxDefaults.colors(checkedColor = gold, uncheckedColor = body),
                    modifier = Modifier.testTag("continue_select_${visit.game.name.lowercase()}"))
                Column(Modifier.weight(1f)) {
                    TextButton(onClick = { onResume(visit) }, modifier = Modifier.fillMaxWidth()
                        .heightIn(min = if (compactTablet) 52.dp else 64.dp).testTag("continue_resume_${visit.game.name.lowercase()}")) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(visit.game.persistedName, color = gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(6.dp))
                            Text(visit.description(), color = body, fontSize = 14.sp)
                        }
                    }
                    TextButton(onClick = { onRemove(setOf(visit.game)) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("continue_delete_${visit.game.name.lowercase()}")) {
                        Text("REMOVE ENTRY", color = body, fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(if (compactTablet) 6.dp else 12.dp))
        }
        item {
            TextButton(onClick = onPickGame, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
                .testTag("continue_pick_game")) {
                Text("PICK A GAME", color = gold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(20.dp))
        }
    }

    }
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
        .padding(horizontal = if (tablet) { if (compactTablet) 14.dp else 32.dp } else 20.dp)
        .testTag("continue_history_screen")) {
        ToolsHeader(onHome, onHome)
        if (tablet) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                Column(Modifier.widthIn(max = if (compactTablet) 780.dp else 900.dp).fillMaxSize()) {
                    continueContent()
                }
            }
        } else {
            continueContent()
        }
    }
    pendingRemoval?.let { games ->
        val all = games.size == visits.size
        AlertDialog(onDismissRequest = { pendingRemoval = null },
            containerColor = Color(0xFF211712),
            title = { Text(if (all) "Remove all Continue entries?" else "Remove selected entries?", color = gold) },
            text = { Text("This removes ${games.size} Continue entries. Completed quests and favorites will stay saved.", color = body) },
            confirmButton = {
                TextButton(onClick = { onRemove(games); selected = selected - games; pendingRemoval = null },
                    modifier = Modifier.testTag("continue_confirm_delete")) { Text("DELETE", color = gold) }
            }, dismissButton = {
                TextButton(onClick = { pendingRemoval = null }, modifier = Modifier.testTag("continue_cancel_delete")) {
                    Text("CANCEL", color = body)
                }
            })
    }
}
