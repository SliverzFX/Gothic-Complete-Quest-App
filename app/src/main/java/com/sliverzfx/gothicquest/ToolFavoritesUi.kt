package com.sliverzfx.gothicquest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val LocalToolFavoriteKeys = compositionLocalOf<Set<String>> { emptySet() }
internal val LocalToggleToolFavorite = compositionLocalOf<(GameId, String) -> Unit> { { _, _ -> } }

@Composable
internal fun ToolFavoriteButton(game: GameId, entry: ToolReferenceEntry,
    tag: String = "tool_favorite_${entry.id}") {
    val saved = toolFavoriteKey(game, entry.id) in LocalToolFavoriteKeys.current
    val toggle = LocalToggleToolFavorite.current
    TextButton(onClick = { toggle(game, entry.id) },
        modifier = Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp).testTag(tag)
            .semantics { contentDescription = if (saved) "Remove ${entry.title} from favorites"
                else "Save ${entry.title} to favorites" }) {
        Text(if (saved) "★" else "☆", color = gameGuidePalette(game).accent, fontSize = 26.sp)
    }
}

@Composable
internal fun ToolFavoritesSections(entries: List<SavedToolFavorite>) {
    var selected by remember { mutableStateOf<SavedToolFavorite?>(null) }
    CodeCategory.entries.forEach { category ->
        val favorites = entries.filter { it.category == category }
        if (favorites.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(category.title.uppercase(), color = Color(0xFFD7B06A), fontSize = 20.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.testTag("favorites_category_${category.name.lowercase()}"))
            Spacer(Modifier.height(12.dp))
            favorites.forEach { favorite ->
                val entry = favorite.card.entry
                val shape = RoundedCornerShape(7.dp)
                Column(Modifier.fillMaxWidth().background(gameGuidePalette(favorite.game).surface, shape)
                    .border(1.dp, gameGuidePalette(favorite.game).border, shape)
                    .clickable { selected = favorite }
                    .testTag("saved_tool_${favorite.game.name.lowercase()}_${entry.id}")
                    .padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text("${favorite.game.persistedName} • ${entry.group}", color = gameGuidePalette(favorite.game).muted, fontSize = 11.sp)
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(entry.title, color = gameGuidePalette(favorite.game).accent, fontSize = 18.sp,
                            fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        ToolFavoriteButton(favorite.game, entry, "saved_tool_remove_${favorite.game.name.lowercase()}_${entry.id}")
                    }
                    entry.command?.let { Text(it, color = gameGuidePalette(favorite.game).body, fontSize = 13.sp) }
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
    selected?.let { favorite ->
        ToolReferenceDetailDialog(favorite.game, favorite.card) { selected = null }
    }
}
