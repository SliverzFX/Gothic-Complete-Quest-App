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
    var showAllQuests by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2200)
        showSplash = false
    }

    val screenKey = when {
        showSplash -> "splash"
        selectedQuest != null -> "quest:" + selectedQuest!!.id
        showAllQuests -> "allQuests"
        gothicChapter != null -> "chapter:" + gothicChapter
        destination == "Gothic" -> "gothicHub"
        destination == "Gothic II Gold Edition" -> "gothic2Hub"
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
                val quest = if (destination == "Gothic II Gold Edition") Gothic2QuestData.quests.firstOrNull { it.id == questId } else GothicQuestData.quests.firstOrNull { it.id == questId }
                if (quest != null) {
                    QuestDetailScreen(
                        quest = quest,
                        onBack = { selectedQuest = null }
                    )
                }
            }
            screen == "allQuests" -> {
                val quests = if (destination == "Gothic II Gold Edition") Gothic2QuestData.quests else GothicQuestData.quests
                AllQuestsScreen(
                    gameTitle = if (destination == "Gothic II Gold Edition") "GOTHIC II" else "GOTHIC",
                    quests = quests,
                    onBack = { showAllQuests = false },
                    onQuestSelected = { selectedQuest = it }
                )
            }
            screen.startsWith("chapter:") -> {
                val chapter = screen.removePrefix("chapter:").toIntOrNull()
                if (chapter != null) {
                    ChapterQuestListScreen(
                        gameTitle = if (destination == "Gothic II Gold Edition") "GOTHIC II" else "GOTHIC",
                        chapter = chapter,
                        quests = if (destination == "Gothic II Gold Edition") Gothic2QuestData.chapter(chapter) else GothicQuestData.chapter(chapter),
                        onBack = { gothicChapter = null },
                        onQuestSelected = { selectedQuest = it }
                    )
                }
            }
            screen == "gothicHub" -> GothicHubScreen(
                onBack = { destination = null },
                onChapterSelected = { gothicChapter = it },
                onAllQuests = { showAllQuests = true }
            )
            screen == "gothic2Hub" -> Gothic2HubScreen(
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
private fun GothicHubScreen(onBack: () -> Unit, onChapterSelected: (Int) -> Unit, onAllQuests: () -> Unit) {
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
                ChapterButton(chapter, questCount = GothicQuestData.chapter(chapter).size) { onChapterSelected(chapter) }
                Spacer(Modifier.height(11.dp))
            }

            Spacer(Modifier.height(8.dp))
            UtilityButton("ALL QUESTS", onClick = onAllQuests)
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
private fun Gothic2HubScreen(onBack: () -> Unit, onChapterSelected: (Int) -> Unit, onAllQuests: () -> Unit) {
    BackHandler(onBack = onBack)
    Box(
        Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF1A0C08), Color(0xFF080706), Color.Black))
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.gothic_2_gold_logo),
                contentDescription = "Gothic II Gold Edition",
                modifier = Modifier.width(300.dp),
                contentScale = ContentScale.Fit
            )
            Text("COMPLETE QUEST GUIDE", color = Color(0xFFC79A55), fontSize = 13.sp)
            Spacer(Modifier.height(22.dp))
            (1..6).forEach { chapter ->
                ChapterButton(chapter, questCount = Gothic2QuestData.chapter(chapter).size) { onChapterSelected(chapter) }
                Spacer(Modifier.height(11.dp))
            }
            Spacer(Modifier.height(8.dp))
            UtilityButton("ALL QUESTS", onClick = onAllQuests)
            Spacer(Modifier.height(10.dp))
            UtilityButton("SEARCH")
            Spacer(Modifier.height(24.dp))
            Text("‹  BACK TO MAIN MENU", color = Color(0xFFB6935B), fontSize = 13.sp,
                modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
        }
    }
}

@Composable
private fun ChapterButton(chapter: Int, questCount: Int, onClick: () -> Unit) {
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
            Text("$questCount quests • walkthroughs", color = Color(0xFF9E8B70), fontSize = 12.sp)
        }
        Text("›", color = Color(0xFFD7B06A), fontSize = 32.sp)
    }
}

@Composable
private fun UtilityButton(label: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.dp, Color(0xFF4D4030), RoundedCornerShape(5.dp))
            .background(Color(0xFF11100E), RoundedCornerShape(5.dp))
            .clickable(onClick = onClick),
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
private fun ChapterQuestListScreen(gameTitle: String, chapter: Int, quests: List<Quest>, onBack: () -> Unit, onQuestSelected: (Quest) -> Unit) {
    BackHandler(onBack = onBack)

    Column(
        Modifier.fillMaxSize().background(Color(0xFF090706)).statusBarsPadding().verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("‹  BACK TO CHAPTERS", color = Color(0xFFB6935B), fontSize = 12.sp, modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
        Spacer(Modifier.height(4.dp))
        Text("$gameTitle — CHAPTER $chapter", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(if (quests.isNotEmpty()) "${quests.size} QUESTS • CHRONOLOGICAL ORDER" else "QUEST DATA COMING NEXT", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        quests.forEachIndexed { index, quest ->
            val shape = RoundedCornerShape(7.dp)
            Column(
                Modifier.fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF1B1410), Color(0xFF26150F), Color(0xFF15100D))
                        ), shape
                    )
                    .border(1.dp, Color(0xFF5F4529), shape)
                    .clickable { onQuestSelected(quest) }
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${index + 1}.  ${quest.title}",
                        color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text("›", color = Color(0xFFB6935B), fontSize = 25.sp)
                }
                Spacer(Modifier.height(3.dp))
                Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(7.dp))
                Text(quest.summary, color = Color(0xFFC7B89B), fontSize = 13.sp)
                Spacer(Modifier.height(7.dp))
                Text(quest.id, color = Color(0xFF746957), fontSize = 10.sp)
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
        Modifier.fillMaxSize().background(Color(0xFF090706)).statusBarsPadding()
            .verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("‹  BACK TO QUESTS", color = Color(0xFFB6935B), fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
        Spacer(Modifier.height(6.dp))
        Text(quest.id, color = Color(0xFF746957), fontSize = 11.sp)
        Text(quest.title, color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        if (quest.aliases.isNotEmpty()) {
            Text("Also: " + quest.aliases.joinToString(), color = Color(0xFF9E8B70), fontSize = 12.sp)
        }
        Spacer(Modifier.height(16.dp))

        val infoShape = RoundedCornerShape(7.dp)
        Column(
            Modifier.fillMaxWidth()
                .background(Color(0xFF15100D), infoShape)
                .border(1.dp, Color(0xFF493720), infoShape)
                .padding(14.dp)
        ) {
            DetailLine("TYPE", quest.category)
            DetailLine("QUEST GIVER", quest.giver)
            DetailLine("LOCATION", quest.location)
            DetailLine("PREREQUISITE", quest.prerequisites, addSpace = false)
        }

        Spacer(Modifier.height(18.dp))
        Text("OBJECTIVE", color = Color(0xFFD7B06A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(quest.summary, color = Color(0xFFE0D5C2), fontSize = 16.sp)
        Spacer(Modifier.height(22.dp))

        Text("WALKTHROUGH", color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        quest.walkthroughSteps.forEachIndexed { index, step ->
            val stepShape = RoundedCornerShape(6.dp)
            Row(
                Modifier.fillMaxWidth()
                    .padding(bottom = 9.dp)
                    .background(Color(0xFF15100D), stepShape)
                    .border(1.dp, Color(0xFF3E3020), stepShape)
                    .padding(13.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    "${index + 1}",
                    color = Color(0xFFD7B06A),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(28.dp)
                )
                Text(step, color = Color(0xFFD4C7B1), fontSize = 14.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(10.dp))
        if (quest.reward.isNotBlank() && !quest.reward.startsWith("Not specified")) {
            DetailCallout("REWARD", quest.reward, Color(0xFF3F4A2B))
            Spacer(Modifier.height(12.dp))
        }
        if (quest.warnings.isNotBlank()) {
            DetailCallout("CHOICES / MISSABLE NOTES", quest.warnings, Color(0xFF4A2D24))
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(12.dp))
        Text("‹  BACK TO QUESTS", color = Color(0xFFB6935B),
            modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
    }
}

@Composable
private fun DetailCallout(label: String, value: String, tint: Color) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        Modifier.fillMaxWidth()
            .background(tint.copy(alpha = 0.32f), shape)
            .border(1.dp, tint.copy(alpha = 0.85f), shape)
            .padding(14.dp)
    ) {
        Text(label, color = Color(0xFFD7B06A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(value, color = Color(0xFFD4C7B1), fontSize = 14.sp)
    }
}

@Composable
private fun DetailLine(label: String, value: String, addSpace: Boolean = true) {
    Text(label, color = Color(0xFF8F806A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    Text(value, color = Color(0xFFC7B89B), fontSize = 14.sp)
    if (addSpace) Spacer(Modifier.height(8.dp))
}


@Composable
private fun AllQuestsScreen(gameTitle: String, quests: List<Quest>, onBack: () -> Unit, onQuestSelected: (Quest) -> Unit) {
    BackHandler(onBack = onBack)
    Column(
        Modifier.fillMaxSize().background(Color(0xFF090706)).statusBarsPadding()
            .verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("‹  BACK TO CHAPTERS", color = Color(0xFFB6935B), fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
        Spacer(Modifier.height(4.dp))
        Text("$gameTitle — ALL QUESTS", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("${quests.size} QUESTS • CHRONOLOGICAL BY CHAPTER", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        quests.sortedWith(compareBy<Quest> { it.chapter }.thenBy { it.playOrder }).forEach { quest ->
            val shape = RoundedCornerShape(7.dp)
            Column(
                Modifier.fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(Color(0xFF1B1410), Color(0xFF26150F), Color(0xFF15100D))), shape)
                    .border(1.dp, Color(0xFF5F4529), shape)
                    .clickable { onQuestSelected(quest) }
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text("CHAPTER ${quest.chapter}  •  ${quest.id}", color = Color(0xFF8F806A), fontSize = 10.sp)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(quest.title, color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("›", color = Color(0xFFB6935B), fontSize = 25.sp)
                }
                Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
        }
        Text("‹  BACK TO CHAPTERS", color = Color(0xFFB6935B),
            modifier = Modifier.clickable(onClick = onBack).padding(12.dp))
    }
}

