package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ToolSection(val title: String, val description: String) {
    MARVIN_CODES("CODES", "Commands, item details, equipment and locations."),
    USEFUL_TIPS("USEFUL TIPS", "Normal gameplay advice, training and exploration."),
    ITEMS("ITEMS", "Equipment, consumables and spell references.");

    // Keep ITEMS as a legacy route value so saved destinations remain valid.
    fun titleFor(game: GameId): String = if (this == ITEMS) MARVIN_CODES.title else title

}

private val ToolsGold = Color(0xFFD7B06A)
private val ToolsBody = Color(0xFFC7B89B)

@Composable
internal fun GameToolsScreen(onBack: () -> Unit, onHome: () -> Unit,
    onSectionSelected: (ToolSection) -> Unit) {
    BackHandler(onBack = onBack)
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
        .padding(horizontal = 20.dp).testTag("game_tools_screen")) {
        ToolsHeader(onBack, onHome)
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(vertical = 24.dp)) {
            Text("GAME TOOLS", color = ToolsGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("A little help for your next adventure.", color = ToolsBody, fontSize = 16.sp)
            Spacer(Modifier.height(24.dp))
            ToolSection.entries.filter { it != ToolSection.ITEMS }.forEach { section ->
                val shape = RoundedCornerShape(14.dp)
                Column(Modifier.fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color(0xF0241B13), Color(0xF0120F0C))), shape)
                    .border(1.dp, ToolsGold.copy(alpha = 0.45f), shape)
                    .clickable(role = Role.Button, onClick = { onSectionSelected(section) })
                    .testTag("tools_category_${section.name.lowercase()}")
                    .padding(20.dp)) {
                    Text(section.title, color = ToolsGold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(section.description, color = ToolsBody, fontSize = 16.sp, lineHeight = 23.sp)
                    Spacer(Modifier.height(14.dp))
                    Text("CHOOSE A GAME  ›", color = ToolsGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
internal fun ToolReferenceScreen(section: ToolSection, game: GameId,
    onBack: () -> Unit, onHome: () -> Unit, embedded: Boolean = false) {
    if (game == GameId.GOTHIC || game == GameId.GOTHIC_2_GOLD || game == GameId.ARCHOLOS || game == GameId.GOTHIC_3 || game == GameId.RISEN || game == GameId.RISEN_2) {
        GothicReferenceScreen(section, onBack, onHome, embedded, game)
        return
    }
    BackHandler(enabled = !embedded, onBack = onBack)
    Column(Modifier.fillMaxSize().then(if (embedded) Modifier else
        Modifier.statusBarsPadding().navigationBarsPadding().padding(horizontal = 20.dp))
        .testTag("tool_reference_screen")) {
        if (!embedded) ToolsHeader(onBack, onHome)
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(vertical = 24.dp)) {
            Text(section.titleFor(game), color = ToolsGold, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            if (!embedded) Text(game.persistedName, color = ToolsBody, fontSize = 18.sp)
            Spacer(Modifier.height(24.dp))
            val shape = RoundedCornerShape(14.dp)
            Column(Modifier.fillMaxWidth().background(Color(0xF015100D), shape)
                .border(1.dp, ToolsGold.copy(alpha = 0.35f), shape).padding(20.dp)) {
                Text("Reference being prepared", color = ToolsGold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text(when (section) {
                    ToolSection.ITEMS -> "Item descriptions and requirements for this game will appear here."
                    ToolSection.USEFUL_TIPS -> "Normal gameplay tips for this game will appear here."
                    ToolSection.MARVIN_CODES -> "Verified commands, activation instructions and item insert codes for this game will appear here."
                },
                    color = ToolsBody, fontSize = 16.sp, lineHeight = 23.sp)
            }
        }
    }
}

@Composable
internal fun ToolsHeader(onBack: () -> Unit, onHome: () -> Unit,
    onSearch: (() -> Unit)? = null, searchVisible: Boolean = false) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("tools_back")) {
            Text("‹  BACK", color = ToolsGold, fontSize = 16.sp)
        }
        if (onSearch != null) {
            TextButton(onClick = onSearch, modifier = Modifier.heightIn(min = 56.dp).testTag("tools_search_toggle")) {
                Text(if (searchVisible) "CLOSE" else "SEARCH", color = ToolsGold, fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
        TextButton(onClick = onHome, modifier = Modifier.heightIn(min = 56.dp).testTag("tools_home")) {
            Text("HOME", color = ToolsGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
    HorizontalDivider(color = ToolsGold.copy(alpha = 0.35f))
}
