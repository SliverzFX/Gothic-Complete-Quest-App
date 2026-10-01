package com.sliverzfx.gothicquest

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun GothicQuestApp() {
    var showSplash by remember { mutableStateOf(true) }
    var destination by remember { mutableStateOf<String?>(null) }
    var gothicChapter by remember { mutableStateOf<Int?>(null) }
    var selectedQuest by remember { mutableStateOf<Quest?>(null) }

    LaunchedEffect(Unit) {
        delay(2200)
        showSplash = false
    }

    val screenKey = when {
        showSplash -> "splash"
        selectedQuest != null -> "quest:" + selectedQuest!!.id
        gothicChapter != null -> "chapter:" + gothicChapter
        destination == "Gothic" -> "gothicHub"
        destination == null -> "home"
        else -> "destination:" + destination
    }

    Crossfade(
        targetState = screenKey,
        animationSpec = tween(durationMillis = 350),
        label = "screenCrossfade"
    ) { screen ->
        when {
            screen == "splash" -> SplashScreen()
            screen.startsWith("quest:") -> {
                val questId = screen.removePrefix("quest:")
                val quest = GothicQuestData.quests.firstOrNull { it.id == questId }
                if (quest != null) {
                    QuestDetailScreen(
                        quest = quest,
                        onBack = { selectedQuest = null }
                    )
                }
            }
            screen.startsWith("chapter:") -> {
                val chapter = screen.removePrefix("chapter:").toIntOrNull()
                if (chapter != null) {
                    ChapterQuestListScreen(
                        chapter = chapter,
                        onBack = { gothicChapter = null },
                        onQuestSelected = { selectedQuest = it }
                    )
                }
            }
            screen == "gothicHub" -> GothicHubScreen(
                onBack = { destination = null },
                onChapterSelected = { gothicChapter = it }
            )
            screen == "home" -> HomeScreen { destination = it }
            screen.startsWith("destination:") -> DestinationPlaceholder(
                title = screen.removePrefix("destination:"),
                onBack = { destination = null }
            )
        }
    }
}
@Composable
private fun GothicHubScreen(onBack: () -> Unit, onChapterSelected: (Int) -> Unit) {
    BackHandler(onBack = onBack)

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1A0C08), Color(0xFF080706), Color.Black)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.gothic_classic_logo),
                contentDescription = "Gothic Classic",
                modifier = Modifier.width(230.dp),
                contentScale = ContentScale.Fit
            )
            Text("COMPLETE QUEST GUIDE", color = Color(0xFFC79A55), fontSize = 13.sp)
            Spacer(Modifier.height(22.dp))

            (1..6).forEach { chapter ->
                ChapterButton(chapter) { onChapterSelected(chapter) }
                Spacer(Modifier.height(11.dp))
            }

            Spacer(Modifier.height(8.dp))
            UtilityButton("ALL QUESTS")
            Spacer(Modifier.height(10.dp))
            UtilityButton("SEARCH")
            Spacer(Modifier.height(24.dp))
            Text(
                "‹  BACK TO MAIN MENU",
                color = Color(0xFFB6935B),
                fontSize = 13.sp,
                modifier = Modifier.clickable(onClick = onBack).padding(12.dp)
            )
        }
    }
}

@Composable
private fun ChapterButton(chapter: Int, onClick: () -> Unit) {
    val shape = RoundedCornerShape(6.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .border(1.dp, Color(0xFF76552E), shape)
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF211712), Color(0xFF35160F), Color(0xFF17110E))
                ),
                shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("CHAPTER $chapter", color = Color(0xFFD7B06A), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Quest walkthroughs", color = Color(0xFF9E8B70), fontSize = 12.sp)
        }
        Text("›", color = Color(0xFFD7B06A), fontSize = 32.sp)
    }
}

@Composable
private fun UtilityButton(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.dp, Color(0xFF4D4030), RoundedCornerShape(5.dp))
            .background(Color(0xFF11100E), RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color(0xFFBDA47A), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ChapterPlaceholder(chapter: Int, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    Box(
        Modifier.fillMaxSize().background(Color(0xFF090706)).padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("GOTHIC — CHAPTER $chapter", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text("Quest list comes next.", color = Color(0xFFC7B89B), fontSize = 16.sp)
            Spacer(Modifier.height(24.dp))
            Text("‹  BACK", color = Color(0xFFB6935B), modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
        }
    }
}

@Composable
private fun DestinationPlaceholder(title: String, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF090706)).padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Quest Guide — coming next", color = Color(0xFFC7B89B), fontSize = 16.sp)
            Text("Use Android Back to return to the menu.", color = Color(0xFF8F806A), fontSize = 12.sp)
        }
    }
}@Composable
private fun ChapterQuestListScreen(chapter: Int, onBack: () -> Unit, onQuestSelected: (Quest) -> Unit) {
    BackHandler(onBack = onBack)
    val quests = GothicQuestData.chapter(chapter)

    Column(
        Modifier.fillMaxSize().background(Color(0xFF090706)).statusBarsPadding().verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("‹  BACK TO CHAPTERS", color = Color(0xFFB6935B), fontSize = 12.sp, modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
        Spacer(Modifier.height(4.dp))
        Text("GOTHIC — CHAPTER $chapter", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(if (quests.isNotEmpty()) "${quests.size} QUESTS • CHRONOLOGICAL ORDER" else "QUEST DATA COMING NEXT", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        quests.forEach { quest ->
            Column(
                Modifier.fillMaxWidth().border(1.dp, Color(0xFF5F4529), RoundedCornerShape(6.dp))
                    .background(Color(0xFF17110E), RoundedCornerShape(6.dp))
                    .clickable { onQuestSelected(quest) }.padding(16.dp)
            ) {
                Text(quest.id, color = Color(0xFF8F806A), fontSize = 11.sp)
                Text(quest.title, color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(quest.category, color = Color(0xFF9E8B70), fontSize = 12.sp)
                Spacer(Modifier.height(5.dp))
                Text(quest.summary, color = Color(0xFFC7B89B), fontSize = 13.sp)
            }
            Spacer(Modifier.height(10.dp))
        }
        Text("‹  BACK TO CHAPTERS", color = Color(0xFFB6935B), modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
    }
}

@Composable
private fun QuestDetailScreen(quest: Quest, onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    Column(
        Modifier.fillMaxSize().background(Color(0xFF090706)).statusBarsPadding().verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("‹  BACK TO QUESTS", color = Color(0xFFB6935B), fontSize = 12.sp, modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
        Spacer(Modifier.height(4.dp))
        Text(quest.id, color = Color(0xFF8F806A), fontSize = 12.sp)
        Text(quest.title, color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        if (quest.aliases.isNotEmpty()) Text("Also: " + quest.aliases.joinToString(), color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))
        DetailLine("TYPE", quest.category)
        DetailLine("QUEST GIVER", quest.giver)
        DetailLine("LOCATION", quest.location)
        DetailLine("PREREQUISITE", quest.prerequisites)
        Spacer(Modifier.height(16.dp))
        Text(quest.summary, color = Color(0xFFE0D5C2), fontSize = 16.sp)
        Spacer(Modifier.height(20.dp))
        Text("WALKTHROUGH", color = Color(0xFFD7B06A), fontWeight = FontWeight.Bold)
        quest.walkthroughSteps.forEachIndexed { index, step ->
            Text("${index + 1}. $step", color = Color(0xFFC7B89B), fontSize = 14.sp, modifier = Modifier.padding(top = 9.dp))
        }
        Spacer(Modifier.height(20.dp))
        Text("REWARD", color = Color(0xFFD7B06A), fontWeight = FontWeight.Bold)
        Text(quest.reward, color = Color(0xFFC7B89B), fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))
        Text("CHOICES / MISSABLE NOTES", color = Color(0xFFD7B06A), fontWeight = FontWeight.Bold)
        Text(quest.warnings, color = Color(0xFFC7B89B), fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))
        Text("‹  BACK TO QUESTS", color = Color(0xFFB6935B), modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Text(label, color = Color(0xFF8F806A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    Text(value, color = Color(0xFFC7B89B), fontSize = 14.sp)
    Spacer(Modifier.height(8.dp))
}


