package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AboutGold = Color(0xFFD7B06A)
private val AboutBody = Color(0xFFC7B89B)

@Composable
internal fun AboutScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val version = remember(context) {
        @Suppress("DEPRECATION")
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()
    }
    var linkError by remember { mutableStateOf<String?>(null) }
    val openLink: (String) -> Unit = { url ->
        linkError = null
        try {
            uriHandler.openUri(url)
        } catch (_: IllegalArgumentException) {
            linkError = "Could not open the link. Please check that a browser is available and try again."
        } catch (_: SecurityException) {
            linkError = "Could not open the link. Please try again."
        }
    }

    Column(
        Modifier.fillMaxSize()

            .statusBarsPadding().navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .testTag("about_screen")
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("about_back")) {
                Text("‹  BACK", color = AboutGold, fontSize = 16.sp)
            }
            TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp).testTag("about_home")) {
                Text("HOME", color = AboutGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(bottom = 24.dp)) {
            Text("INFO / ABOUT", color = AboutGold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            AboutBlock("QUESTBOUND") {
                Text("RPG Quest Guides", color = AboutGold, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                Text("Quest walkthroughs for Gothic, Risen and selected mods, together in one place.",
                    color = AboutBody, fontSize = 16.sp)
                Spacer(Modifier.height(10.dp))
                Text("Browse chapters, search for a quest, save favorites and mark quests as completed. Use Continue on the main menu to return to your last chapter or quest.",
                    color = AboutBody, fontSize = 14.sp)
                Spacer(Modifier.height(10.dp))
                Text("The included guides work offline. Community links need an internet connection.",
                    color = AboutBody, fontSize = 14.sp)
                if (version.isNotBlank()) {
                    Spacer(Modifier.height(12.dp))
                    Text("Version $version", color = Color(0xFF9E8B70), fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(16.dp))
            AboutBlock("INCLUDED GUIDES") {
                GameId.entries.forEach { game ->
                    Text("• ${game.persistedName}", color = AboutBody, fontSize = 14.sp)
                    Spacer(Modifier.height(6.dp))
                }
                Text("Gothic II Gold includes Night of the Raven. More games and mods will be added as their guides are prepared.",
                    color = AboutBody, fontSize = 14.sp)
            }
            Spacer(Modifier.height(16.dp))
            AboutBlock("CREATOR & COMMUNITY") {
                Text("Created by SliverZFX", color = AboutGold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("Watch playthroughs and guides on YouTube, or join the community on Discord.",
                    color = AboutBody, fontSize = 14.sp)
                Spacer(Modifier.height(12.dp))
                AboutLinkButton("YOUTUBE", "about_youtube") { openLink("https://www.youtube.com/@sliverz_fx") }
                Spacer(Modifier.height(8.dp))
                AboutLinkButton("DISCORD", "about_discord") { openLink("https://discord.gg/evwry6hzwH") }
                linkError?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = AboutBody, fontSize = 13.sp)
                }
            }
            Spacer(Modifier.height(16.dp))
            AboutBlock("UNOFFICIAL FAN GUIDE") {
                Text("This is an unofficial fan-made guide and is not affiliated with or endorsed by the games' developers or publishers. Game names and trademarks belong to their respective owners.",
                    color = AboutBody, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                Text("Quest availability and solutions can vary by game version, mod version and choices made during your playthrough.",
                    color = AboutBody, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun AboutBlock(title: String, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    Column(Modifier.fillMaxWidth().background(Color(0xFF15100D), shape)
        .border(1.dp, Color(0xFF5F4529), shape).padding(16.dp)) {
        Text(title, color = AboutGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun AboutLinkButton(label: String, tag: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag(tag),
        shape = RoundedCornerShape(5.dp), border = BorderStroke(1.dp, Color(0xFF76552E))) {
        Text(label, color = AboutGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
