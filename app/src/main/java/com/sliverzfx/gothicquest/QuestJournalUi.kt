package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

internal val LocalInProgressKeys = compositionLocalOf<Set<String>> { emptySet() }

@Composable
internal fun QuestStatusButtons(status: QuestStatus, onChanged: (QuestStatus) -> Unit) {
    val gold = LocalGameGuidePalette.current.accent
    Text("QUEST STATUS", color = gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        QuestStatus.entries.forEach { option ->
            FilterChip(selected = status == option, onClick = { onChanged(option) },
                modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("quest_status_${option.name.lowercase()}"),
                label = { Text(option.label, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(containerColor = LocalGameGuidePalette.current.surface,
                    labelColor = gold, selectedContainerColor = LocalGameGuidePalette.current.selected, selectedLabelColor = LocalGameGuidePalette.current.highlight))
        }
    }
}

@Composable
internal fun QuestNotesSection(questKey: String) {
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE) }
    var note by remember(questKey) { mutableStateOf(prefs.getString("quest_note:$questKey", "").orEmpty()) }
    val gold = LocalGameGuidePalette.current.accent
    Text("MY NOTES", color = gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    OutlinedTextField(value = note, onValueChange = { value ->
        if (value.length <= 4000) {
            note = value
            prefs.edit().apply {
                if (value.isEmpty()) remove("quest_note:$questKey") else putString("quest_note:$questKey", value)
            }.apply()
        }
    }, modifier = Modifier.fillMaxWidth().testTag("quest_personal_notes"), minLines = 3, maxLines = 6,
        placeholder = { Text("Add a reminder for this quest…") },
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = LocalGameGuidePalette.current.reading,
            unfocusedTextColor = LocalGameGuidePalette.current.reading, focusedBorderColor = gold,
            unfocusedBorderColor = LocalGameGuidePalette.current.border, cursorColor = gold))
    Text("Saved automatically • ${note.length}/4000", color = LocalGameGuidePalette.current.muted, fontSize = 12.sp)
    Spacer(Modifier.height(16.dp))
}

@OptIn(kotlinx.coroutines.FlowPreview::class)
@Composable
internal fun rememberQuestReadingScroll(questKey: String): ScrollState {
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE) }
    val scroll = remember(questKey) { ScrollState(prefs.getInt("quest_scroll:$questKey", 0).coerceAtLeast(0)) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val save: () -> Unit = { prefs.edit().putInt("quest_scroll:$questKey", scroll.value).apply() }
    LaunchedEffect(questKey, scroll) {
        snapshotFlow { scroll.value }.debounce(200).collect { save() }
    }
    DisposableEffect(questKey, scroll, lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) save()
        }
        lifecycle.addObserver(observer)
        onDispose { save(); lifecycle.removeObserver(observer) }
    }
    return scroll
}

@Composable
internal fun QuestInProgressBadge(game: GameId, quest: Quest) {
    if ("${game.savedKeyPrefix}|${quest.id}" in LocalInProgressKeys.current) {
        Text("IN PROGRESS", color = gameGuidePalette(game).highlight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
    }
}
