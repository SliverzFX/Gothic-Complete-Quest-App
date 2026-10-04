package com.sliverzfx.gothicquest

import android.content.Context
import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private data class ResumeSnapshot(
    val game: String,
    val chapter: Int,
    val questId: String?
)

private data class NavFavoriteEntry(val game: GameId, val quest: Quest)

private const val NavBoxOpacity = 0.65f

private fun navBackgroundBrush(game: GameId): Brush =
    when {
        game.usesStoneTheme -> Brush.verticalGradient(
            listOf(
                Color(0xFF292A29),
                Color(0xFF1B1D1D),
                Color(0xFF111313),
                Color(0xFF080909)
            )
        )
        game.usesBloodTheme -> Brush.verticalGradient(
            listOf(
                Color(0xFF260708),
                Color(0xFF170506),
                Color(0xFF0D0505),
                Color(0xFF050303)
            )
        )
        else -> Brush.verticalGradient(
            listOf(Color(0xFF1A0C08), Color(0xFF080706), Color.Black)
        )
    }

private fun navCardBrush(game: GameId): Brush =
    when {
        game.usesStoneTheme -> Brush.horizontalGradient(
            listOf(
                Color(0xFF252625),
                Color(0xFF343534),
                Color(0xFF202221),
                Color(0xFF171918)
            )
        )
        game.usesBloodTheme -> Brush.horizontalGradient(
            listOf(
                Color(0xFF21090A),
                Color(0xFF3A0B0E),
                Color(0xFF260708),
                Color(0xFF130607)
            )
        )
        else -> Brush.horizontalGradient(
            listOf(Color(0xFF1B1410), Color(0xFF26150F), Color(0xFF15100D))
        )
    }

private fun navCardBorder(game: GameId): Color =
    when {
        game.usesStoneTheme -> Color(0xFF6F6D66)
        game.usesBloodTheme -> Color(0xFF76252A)
        else -> Color(0xFF5F4529)
    }

private fun navInsetColor(game: GameId): Color =
    when {
        game.usesStoneTheme -> Color(0xFF1D1F1E)
        game.usesBloodTheme -> Color(0xFF190708)
        else -> Color(0xFF15100D)
    }

private fun navUtilityBorder(game: GameId): Color =
    when {
        game.usesStoneTheme -> Color(0xFF64645F)
        game.usesBloodTheme -> Color(0xFF652126)
        else -> Color(0xFF4D4030)
    }

private fun navUtilityBackground(game: GameId): Color =
    when {
        game.usesStoneTheme -> Color(0xFF171918)
        game.usesBloodTheme -> Color(0xFF160607)
        else -> Color(0xFF11100E)
    }

private fun navProgressBorder(game: GameId): Color =
    when {
        game.usesStoneTheme -> Color(0xFF5C5D59)
        game.usesBloodTheme -> Color(0xFF642126)
        else -> Color(0xFF493720)
    }

@Composable
fun GothicQuestAppV2() {
    val context = LocalContext.current
    var showSplash by remember { mutableStateOf(true) }
    var route by remember { mutableStateOf<AppRoute>(AppRoute.Home) }
    var questReturnRoute by remember { mutableStateOf<AppRoute?>(null) }
    val onGuideHome: () -> Unit = {
        questReturnRoute = null
        route = AppRoute.Home
    }
    var resumeSnapshot by remember { mutableStateOf(loadResumeSnapshot(context)) }
    var favoriteKeys by remember { mutableStateOf(loadNavStringSet(context, "favorites")) }
    var completedKeys by remember { mutableStateOf(loadNavStringSet(context, "completed")) }
    var musicEnabled by remember { mutableStateOf(loadNavMusicEnabled(context)) }
    val musicPlayer = remember { MediaPlayer.create(context, R.raw.gothic_old_camp) }

    DisposableEffect(musicPlayer) {
        musicPlayer?.isLooping = true
        musicPlayer?.setVolume(0.45f, 0.45f)
        onDispose { musicPlayer?.release() }
    }

    LaunchedEffect(musicEnabled, showSplash) {
        if (musicEnabled && !showSplash) {
            if (musicPlayer?.isPlaying == false) musicPlayer.start()
        } else if (musicPlayer?.isPlaying == true) {
            musicPlayer.pause()
        }
    }

    LaunchedEffect(Unit) {
        delay(2200)
        showSplash = false
    }

    Crossfade(
        targetState = showSplash to route,
        animationSpec = tween(durationMillis = 350),
        label = "screenCrossfadeV2"
    ) { (isSplash, currentRoute) ->
        if (isSplash) {
            SplashScreen()
        } else {
            when (currentRoute) {
                AppRoute.Home -> HomeScreen(
                    hasContinue = resumeSnapshot?.let {
                        routeFromResume(it.game, it.chapter, it.questId)
                    } != null,
                    onContinue = {
                        resumeSnapshot?.let { saved ->
                            routeFromResume(saved.game, saved.chapter, saved.questId)?.let { target ->
                                if (target is AppRoute.QuestDetail) {
                                    questReturnRoute = AppRoute.Chapter(target.game, saved.chapter)
                                }
                                route = target
                            }
                        }
                    },
                    onDestinationSelected = { destination ->
                        route = when (destination) {
                            "Quest Guides" -> AppRoute.QuestGuides
                            "Marvin Codes / Cheats" -> AppRoute.Cheats
                            "FAQs" -> AppRoute.Faqs
                            "Info / About" -> AppRoute.About
                            "Support / Bugs" -> AppRoute.Support
                            "Donations" -> AppRoute.Donations
                            "Settings" -> AppRoute.Settings
                            else -> AppRoute.Home
                        }
                    }
                )

                AppRoute.QuestGuides -> GameLibraryScreen(
                    title = "QUEST GUIDES",
                    entries = questGuideLibraryEntries(
                        onGameSelected = { game -> route = AppRoute.GameHub(game) },
                        onRisenSelected = { route = AppRoute.GamePreview("RISEN") }
                    ),
                    onBack = { route = AppRoute.Home },
                    topRightActionLabel = "FAVORITES",
                    onTopRightAction = { route = AppRoute.Favorites }
                )

                AppRoute.Cheats -> GameLibraryScreen(
                    title = "MARVIN CODES / CHEATS",
                    entries = emptyList(),
                    onBack = { route = AppRoute.Home }
                )

                AppRoute.Faqs -> SectionPlaceholderScreen("FAQs") { route = AppRoute.Home }
                AppRoute.About -> SectionPlaceholderScreen("INFO / ABOUT") { route = AppRoute.Home }
                AppRoute.Support -> SectionPlaceholderScreen("SUPPORT / BUGS") { route = AppRoute.Home }
                AppRoute.Donations -> SectionPlaceholderScreen("DONATIONS") { route = AppRoute.Home }

                AppRoute.Settings -> NavSettingsScreen(
                    musicEnabled = musicEnabled,
                    onMusicChanged = { enabled ->
                        musicEnabled = enabled
                        saveNavMusicEnabled(context, enabled)
                    },
                    onBack = { route = AppRoute.Home }
                )

                is AppRoute.GamePreview -> SectionPlaceholderScreen(currentRoute.title) {
                    route = AppRoute.QuestGuides
                }

                AppRoute.Favorites -> NavFavoritesScreen(
                    onHome = onGuideHome,
                    entries = buildNavFavoriteEntries(favoriteKeys),
                    onBack = { route = AppRoute.QuestGuides },
                    onQuestSelected = { game, quest ->
                        questReturnRoute = AppRoute.Favorites
                        route = AppRoute.QuestDetail(game, quest.id)
                    }
                )

                is AppRoute.GameHub -> NavGameHubScreen(
                    onHome = onGuideHome,
                    game = currentRoute.game,
                    completedKeys = completedKeys,
                    onBack = { route = AppRoute.QuestGuides },
                    onChapterSelected = { chapter ->
                        val snapshot = ResumeSnapshot(currentRoute.game.persistedName, chapter, null)
                        resumeSnapshot = snapshot
                        saveResumeSnapshot(context, snapshot)
                        route = AppRoute.Chapter(currentRoute.game, chapter)
                    },
                    onAllQuests = { route = AppRoute.AllQuests(currentRoute.game) },
                    onSearch = { route = AppRoute.Search(currentRoute.game) }
                )

                is AppRoute.Chapter -> NavChapterQuestListScreen(
                    onHome = onGuideHome,
                    game = currentRoute.game,
                    chapter = currentRoute.chapter,
                    quests = currentRoute.game.chapterQuests(currentRoute.chapter),
                    onBack = { route = AppRoute.GameHub(currentRoute.game) },
                    onQuestSelected = { quest ->
                        val snapshot = ResumeSnapshot(currentRoute.game.persistedName, quest.chapter, quest.id)
                        resumeSnapshot = snapshot
                        saveResumeSnapshot(context, snapshot)
                        questReturnRoute = currentRoute
                        route = AppRoute.QuestDetail(currentRoute.game, quest.id)
                    }
                )

                is AppRoute.QuestDetail -> {
                    val quest = currentRoute.game.quests().firstOrNull { it.id == currentRoute.questId }
                    if (quest == null) {
                        LaunchedEffect(currentRoute) {
                            route = AppRoute.GameHub(currentRoute.game)
                        }
                    } else {
                        val favoriteKey = navQuestKey(currentRoute.game, quest)
                        val completedKey = navQuestKey(currentRoute.game, quest)
                        NavQuestDetailScreen(
                            onHome = onGuideHome,
                            game = currentRoute.game,
                            quest = quest,
                            isFavorite = favoriteKey in favoriteKeys,
                            isCompleted = completedKey in completedKeys,
                            onToggleFavorite = {
                                favoriteKeys = toggleNavSet(context, "favorites", favoriteKeys, favoriteKey)
                            },
                            onToggleCompleted = {
                                completedKeys = toggleNavSet(context, "completed", completedKeys, completedKey)
                            },
                            onBack = {
                                route = questReturnRoute ?: AppRoute.Chapter(currentRoute.game, quest.chapter)
                                questReturnRoute = null
                            }
                        )
                        LaunchedEffect(currentRoute.questId) {
                            val snapshot = ResumeSnapshot(currentRoute.game.persistedName, quest.chapter, quest.id)
                            resumeSnapshot = snapshot
                            saveResumeSnapshot(context, snapshot)
                        }
                    }
                }

                is AppRoute.AllQuests -> NavAllQuestsScreen(
                    onHome = onGuideHome,
                    game = currentRoute.game,
                    quests = currentRoute.game.quests(),
                    onBack = { route = AppRoute.GameHub(currentRoute.game) },
                    onQuestSelected = { quest ->
                        questReturnRoute = currentRoute
                        route = AppRoute.QuestDetail(currentRoute.game, quest.id)
                    }
                )

                is AppRoute.Search -> NavSearchScreen(
                    onHome = onGuideHome,
                    game = currentRoute.game,
                    quests = currentRoute.game.quests(),
                    onBack = { route = AppRoute.GameHub(currentRoute.game) },
                    onQuestSelected = { quest ->
                        questReturnRoute = currentRoute
                        route = AppRoute.QuestDetail(currentRoute.game, quest.id)
                    }
                )
            }
        }
    }
}

private fun questGuideLibraryEntries(
    onGameSelected: (GameId) -> Unit,
    onRisenSelected: () -> Unit
): List<GameLibraryEntry> = listOf(
    GameLibraryEntry(
        id = "gothic",
        title = "Gothic",
        panelRes = R.drawable.gothic_button_1,
        logoRes = R.drawable.gothic_classic_logo,
        testTag = "game_gothic",
        onClick = { onGameSelected(GameId.GOTHIC) }
    ),
    GameLibraryEntry(
        id = "gothic_2_gold",
        title = "Gothic II Gold Edition",
        panelRes = R.drawable.gothic_button_2,
        logoRes = R.drawable.gothic_2_gold_logo,
        testTag = "game_gothic_2",
        onClick = { onGameSelected(GameId.GOTHIC_2_GOLD) }
    ),
    GameLibraryEntry(
        id = "new_balance",
        title = "Gothic II New Balance",
        panelRes = R.drawable.gothic_button_nb,
        logoRes = R.drawable.gothic_2_new_balance_logo,
        testTag = "game_new_balance",
        onClick = { onGameSelected(GameId.NEW_BALANCE) }
    ),
    GameLibraryEntry(
        id = "gothic_3",
        title = "Gothic 3",
        panelRes = R.drawable.gothic_button_3,
        logoRes = R.drawable.gothic_3_logo,
        testTag = "game_gothic_3",
        onClick = { onGameSelected(GameId.GOTHIC_3) }
    ),
    GameLibraryEntry(
        id = "risen_1",
        title = "Risen",
        panelRes = R.drawable.risen_button_1,
        logoRes = R.drawable.risen_1_logo,
        testTag = "game_risen_1",
        onClick = onRisenSelected
    )
)

private fun loadResumeSnapshot(context: Context): ResumeSnapshot? {
    val prefs = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
    val game = prefs.getString("resume_game", null) ?: return null
    val chapter = prefs.getInt("resume_chapter", -1)
    if (chapter !in 1..7) return null
    return ResumeSnapshot(game, chapter, prefs.getString("resume_quest", null))
}

private fun saveResumeSnapshot(context: Context, snapshot: ResumeSnapshot) {
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        .putString("resume_game", snapshot.game)
        .putInt("resume_chapter", snapshot.chapter)
        .apply {
            if (snapshot.questId != null) putString("resume_quest", snapshot.questId) else remove("resume_quest")
        }
        .apply()
}

private fun loadNavStringSet(context: Context, key: String): Set<String> =
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        .getStringSet(key, emptySet())?.toSet() ?: emptySet()

private fun toggleNavSet(
    context: Context,
    preferenceKey: String,
    current: Set<String>,
    value: String
): Set<String> {
    val updated = current.toMutableSet().apply { if (!add(value)) remove(value) }.toSet()
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        .putStringSet(preferenceKey, updated)
        .apply()
    return updated
}

private fun loadNavMusicEnabled(context: Context): Boolean =
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).getBoolean("music_enabled", true)

private fun saveNavMusicEnabled(context: Context, enabled: Boolean) {
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        .putBoolean("music_enabled", enabled)
        .apply()
}

private fun navGamePrefix(game: GameId): String = when (game) {
    GameId.GOTHIC -> "G1"
    GameId.GOTHIC_2_GOLD -> "G2"
    GameId.NEW_BALANCE -> "NB"
    GameId.GOTHIC_3 -> "G3"
}

private fun navQuestKey(game: GameId, quest: Quest): String = "${navGamePrefix(game)}|${quest.id}"

private fun buildNavFavoriteEntries(keys: Set<String>): List<NavFavoriteEntry> = buildList {
    GameId.entries.forEach { game ->
        game.quests().filter { navQuestKey(game, it) in keys }
            .forEach { add(NavFavoriteEntry(game, it)) }
    }
}

@Composable
private fun NavGuideBackground(game: GameId, content: @Composable () -> Unit) {
    val backdropRes = when (game) {
        GameId.GOTHIC -> R.drawable.gothic_mask_bg
        GameId.GOTHIC_2_GOLD -> R.drawable.gothic_2_bg
        GameId.NEW_BALANCE -> R.drawable.gothic_2_nb_bg
        else -> null
    }
    Box(Modifier.fillMaxSize().background(navBackgroundBrush(game))) {
        if (backdropRes != null) {
            Image(
                painter = painterResource(backdropRes),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.30f
            )
        }
        content()
    }
}

@Composable
private fun NavGameHubScreen(
    game: GameId,
    completedKeys: Set<String>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onChapterSelected: (Int) -> Unit,
    onAllQuests: () -> Unit,
    onSearch: () -> Unit
) {
    BackHandler(onBack = onBack)
    val logoRes = when (game) {
        GameId.GOTHIC -> R.drawable.gothic_classic_logo
        GameId.GOTHIC_2_GOLD -> R.drawable.gothic_2_gold_logo
        GameId.NEW_BALANCE -> R.drawable.gothic_2_new_balance_logo
        GameId.GOTHIC_3 -> R.drawable.gothic_3_logo
    }

    NavGuideBackground(game) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavGuideHeader("‹  BACK TO QUEST GUIDES", onBack, onHome)
            Image(
                painter = painterResource(logoRes),
                contentDescription = game.persistedName,
                modifier = Modifier.width(300.dp),
                contentScale = ContentScale.Fit
            )
            Text("COMPLETE QUEST GUIDE", color = Color(0xFFC79A55), fontSize = 13.sp)
            Spacer(Modifier.height(22.dp))
            (1..game.sectionCount).forEach { chapter ->
                NavChapterButton(
                    chapter = chapter,
                    quests = game.chapterQuests(chapter),
                    completedKeys = completedKeys,
                    game = game,
                    onClick = { onChapterSelected(chapter) }
                )
                Spacer(Modifier.height(11.dp))
            }
            Spacer(Modifier.height(8.dp))
            NavUtilityButton(game, "ALL QUESTS", onAllQuests)
            Spacer(Modifier.height(10.dp))
            NavUtilityButton(game, "SEARCH", onSearch)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NavChapterButton(
    chapter: Int,
    quests: List<Quest>,
    completedKeys: Set<String>,
    game: GameId,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(6.dp)
    val questCount = quests.size
    val completedCount = quests.count { navQuestKey(game, it) in completedKeys }
    val targetProgress = if (questCount == 0) 0f else completedCount.toFloat() / questCount.toFloat()
    val progress by animateFloatAsState(targetProgress, tween(500), label = "navChapterProgress")
    val percentage = (targetProgress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .border(
                1.dp,
                when {
                    game.usesStoneTheme -> Color(0xFF77746C)
                    game.usesBloodTheme -> Color(0xFF81272D)
                    else -> Color(0xFF76552E)
                },
                shape
            )
            .background(
                if (game.usesStoneTheme || game.usesBloodTheme) navCardBrush(game)
                else Brush.horizontalGradient(listOf(Color(0xFF211712), Color(0xFF35160F), Color(0xFF17110E))),
                shape,
                alpha = NavBoxOpacity
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text("${game.sectionLabel} $chapter", color = Color(0xFFD7B06A), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(
                    "$completedCount / $questCount completed • $percentage%",
                    color = Color(0xFF9E8B70),
                    fontSize = 11.sp
                )
            }
            Text("›", color = Color(0xFFD7B06A), fontSize = 32.sp)
        }
        Spacer(Modifier.height(3.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color(0xFF0B0907), RoundedCornerShape(3.dp))
                .border(1.dp, navProgressBorder(game), RoundedCornerShape(3.dp))
        ) {
            Box(
                Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(Color(0xFFC79A55), RoundedCornerShape(3.dp))
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun NavUtilityButton(game: GameId, label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.dp, navUtilityBorder(game), RoundedCornerShape(5.dp))
            .background(navUtilityBackground(game).copy(alpha = NavBoxOpacity), RoundedCornerShape(5.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color(0xFFBDA47A), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun NavChapterQuestListScreen(
    game: GameId,
    chapter: Int,
    quests: List<Quest>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onQuestSelected: (Quest) -> Unit
) {
    BackHandler(onBack = onBack)
    NavGuideBackground(game) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            NavGuideHeader("‹  BACK TO ${game.sectionLabel}S", onBack, onHome)
            Spacer(Modifier.height(4.dp))
            Text("${game.displayTitle} — ${game.sectionLabel} $chapter", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("${quests.size} QUESTS • CHRONOLOGICAL ORDER", color = Color(0xFF9E8B70), fontSize = 12.sp)
            Spacer(Modifier.height(18.dp))
            quests.forEachIndexed { index, quest ->
                NavQuestListCard(game, index + 1, quest) { onQuestSelected(quest) }
                Spacer(Modifier.height(10.dp))
            }
            NavBackText("‹  BACK TO ${game.sectionLabel}S", onBack)
        }
    }
}

@Composable
private fun NavQuestListCard(game: GameId, number: Int, quest: Quest, onClick: () -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .background(navCardBrush(game), shape, alpha = NavBoxOpacity)
            .border(1.dp, navCardBorder(game), shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "$number.  ${quest.title}",
                color = Color(0xFFD7B06A),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text("›", color = Color(0xFFB6935B), fontSize = 25.sp)
        }
        Spacer(Modifier.height(3.dp))
        Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(7.dp))
        Text(
            quest.summary,
            color = Color(0xFFC7B89B),
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(7.dp))
        Text(quest.id, color = Color(0xFF746957), fontSize = 10.sp)
    }
}

@Composable
private fun NavQuestDetailScreen(
    game: GameId,
    quest: Quest,
    isFavorite: Boolean,
    isCompleted: Boolean,
    onToggleFavorite: () -> Unit,
    onToggleCompleted: () -> Unit,
    onHome: () -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)
    NavGuideBackground(game) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            NavGuideHeader("‹  BACK TO QUESTS", onBack, onHome)
            Spacer(Modifier.height(6.dp))
            Text(quest.id, color = Color(0xFF746957), fontSize = 11.sp)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    quest.title,
                    color = Color(0xFFD6B06A),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    if (isCompleted) "✓" else "○",
                    color = if (isCompleted) Color(0xFFD7B06A) else Color(0xFF8F806A),
                    fontSize = 30.sp,
                    modifier = Modifier.clickable(onClick = onToggleCompleted).padding(6.dp)
                )
                Text(
                    if (isFavorite) "★" else "☆",
                    color = if (isFavorite) Color(0xFFD7B06A) else Color(0xFF8F806A),
                    fontSize = 32.sp,
                    modifier = Modifier.clickable(onClick = onToggleFavorite).padding(6.dp)
                )
            }
            if (quest.aliases.isNotEmpty()) {
                Text("Also: ${quest.aliases.joinToString()}", color = Color(0xFF9E8B70), fontSize = 12.sp)
            }
            Spacer(Modifier.height(16.dp))

            val infoShape = RoundedCornerShape(7.dp)
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(navInsetColor(game).copy(alpha = NavBoxOpacity), infoShape)
                    .border(1.dp, navCardBorder(game), infoShape)
                    .padding(14.dp)
            ) {
                NavDetailLine("TYPE", quest.category)
                NavDetailLine("QUEST GIVER", quest.giver)
                NavDetailLine("LOCATION", quest.location)
                NavDetailLine("PREREQUISITE", quest.prerequisites, false)
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
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 9.dp)
                        .background(navInsetColor(game).copy(alpha = NavBoxOpacity), stepShape)
                        .border(1.dp, navCardBorder(game).copy(alpha = 0.72f), stepShape)
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
                NavDetailCallout("REWARD", quest.reward, Color(0xFF3F4A2B))
                Spacer(Modifier.height(12.dp))
            }
            if (quest.warnings.isNotBlank()) {
                NavDetailCallout("CHOICES / MISSABLE NOTES", quest.warnings, Color(0xFF4A2D24))
                Spacer(Modifier.height(12.dp))
            }
            Spacer(Modifier.height(12.dp))
            NavBackText("‹  BACK TO QUESTS", onBack)
        }
    }
}

@Composable
private fun NavDetailLine(label: String, value: String, addSpace: Boolean = true) {
    Text(label, color = Color(0xFF8F806A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    Text(value, color = Color(0xFFC7B89B), fontSize = 14.sp)
    if (addSpace) Spacer(Modifier.height(8.dp))
}

@Composable
private fun NavDetailCallout(label: String, value: String, tint: Color) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .background(tint.copy(alpha = NavBoxOpacity), shape)
            .border(1.dp, tint.copy(alpha = 0.85f), shape)
            .padding(14.dp)
    ) {
        Text(label, color = Color(0xFFD7B06A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(value, color = Color(0xFFD4C7B1), fontSize = 14.sp)
    }
}

@Composable
private fun NavAllQuestsScreen(
    game: GameId,
    quests: List<Quest>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onQuestSelected: (Quest) -> Unit
) {
    BackHandler(onBack = onBack)
    val sorted = quests.sortedWith(compareBy<Quest> { it.chapter }.thenBy { it.playOrder })
    Column(
        Modifier
            .fillMaxSize()
            .background(navBackgroundBrush(game))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        NavGuideHeader("‹  BACK TO ${game.sectionLabel}S", onBack, onHome)
        Spacer(Modifier.height(4.dp))
        Text("${game.displayTitle} — ALL QUESTS", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("${quests.size} QUESTS • CHRONOLOGICAL BY ${game.sectionLabel}", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        sorted.forEachIndexed { index, quest ->
            val shape = RoundedCornerShape(7.dp)
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(navCardBrush(game), shape, alpha = NavBoxOpacity)
                    .border(1.dp, navCardBorder(game), shape)
                    .clickable { onQuestSelected(quest) }
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text("${game.sectionLabel} ${quest.chapter}  •  ${quest.id}", color = Color(0xFF8F806A), fontSize = 10.sp)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${index + 1}.  ${quest.title}",
                        color = Color(0xFFD7B06A),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text("›", color = Color(0xFFB6935B), fontSize = 25.sp)
                }
                Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
        }
        NavBackText("‹  BACK TO ${game.sectionLabel}S", onBack)
    }
}

@Composable
private fun NavSearchScreen(
    game: GameId,
    quests: List<Quest>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onQuestSelected: (Quest) -> Unit
) {
    BackHandler(onBack = onBack)
    var query by remember { mutableStateOf("") }
    val normalized = query.trim()
    val results = if (normalized.isBlank()) emptyList() else quests.filter { quest ->
        listOf(
            quest.id,
            quest.title,
            quest.aliases.joinToString(" "),
            quest.category,
            quest.giver,
            quest.location,
            quest.summary,
            quest.searchTags.joinToString(" ")
        ).any { it.contains(normalized, ignoreCase = true) }
    }.sortedWith(compareBy<Quest> { it.chapter }.thenBy { it.playOrder })

    Column(
        Modifier
            .fillMaxSize()
            .background(navBackgroundBrush(game))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        NavGuideHeader("‹  BACK TO ${game.sectionLabel}S", onBack, onHome)
        Spacer(Modifier.height(4.dp))
        Text("${game.displayTitle} — SEARCH", color = Color(0xFFD6B06A), fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("SEARCH ALL QUEST DATA", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Quest, NPC, location, ID...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFFE0D5C2),
                unfocusedTextColor = Color(0xFFE0D5C2),
                focusedBorderColor = Color(0xFFD7B06A),
                unfocusedBorderColor = navCardBorder(game),
                focusedLabelColor = Color(0xFFD7B06A),
                unfocusedLabelColor = Color(0xFF9E8B70),
                cursorColor = Color(0xFFD7B06A)
            )
        )
        Spacer(Modifier.height(14.dp))
        when {
            normalized.isBlank() -> Text("Type something to search ${quests.size} quests.", color = Color(0xFF9E8B70), fontSize = 14.sp)
            results.isEmpty() -> Text("No quests found.", color = Color(0xFF9E8B70), fontSize = 14.sp)
            else -> {
                Text("${results.size} RESULTS", color = Color(0xFFC79A55), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                results.forEach { quest ->
                    NavSearchCard(game, quest) { onQuestSelected(quest) }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        NavBackText("‹  BACK TO ${game.sectionLabel}S", onBack)
    }
}

@Composable
private fun NavSearchCard(game: GameId, quest: Quest, onClick: () -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .background(navCardBrush(game), shape, alpha = NavBoxOpacity)
            .border(1.dp, navCardBorder(game), shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp)
    ) {
        Text("${game.sectionLabel} ${quest.chapter}  •  ${quest.id}", color = Color(0xFF8F806A), fontSize = 10.sp)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(quest.title, color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("›", color = Color(0xFFB6935B), fontSize = 25.sp)
        }
        Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("${quest.giver} • ${quest.location}", color = Color(0xFF9E8B70), fontSize = 11.sp)
    }
}

@Composable
private fun NavFavoritesScreen(
    entries: List<NavFavoriteEntry>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onQuestSelected: (GameId, Quest) -> Unit
) {
    BackHandler(onBack = onBack)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF090706))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        NavGuideHeader("‹  BACK TO QUEST GUIDES", onBack, onHome)
        Spacer(Modifier.height(4.dp))
        Text("FAVORITES", color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("${entries.size} SAVED QUESTS", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        if (entries.isEmpty()) {
            Text("No favorites yet. Open any quest and tap ☆ to save it here.", color = Color(0xFFC7B89B), fontSize = 14.sp)
        } else {
            entries.forEach { entry ->
                val quest = entry.quest
                val shape = RoundedCornerShape(7.dp)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(Color(0xFF1B1410), Color(0xFF26150F), Color(0xFF15100D))), shape, alpha = NavBoxOpacity)
                        .border(1.dp, Color(0xFF5F4529), shape)
                        .clickable { onQuestSelected(entry.game, quest) }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text("${entry.game.displayTitle}  •  ${entry.game.sectionLabel} ${quest.chapter}", color = Color(0xFF8F806A), fontSize = 10.sp)
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(quest.title, color = Color(0xFFD7B06A), fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("★", color = Color(0xFFD7B06A), fontSize = 20.sp)
                    }
                    Text(quest.category.uppercase(), color = Color(0xFFC79A55), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun NavSettingsScreen(
    musicEnabled: Boolean,
    onMusicChanged: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF090706))
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        NavBackText("‹  BACK TO MAIN MENU", onBack)
        Spacer(Modifier.height(8.dp))
        Text("SETTINGS", color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("APP PREFERENCES", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(24.dp))
        val shape = RoundedCornerShape(7.dp)
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape)
                .clickable { onMusicChanged(!musicEnabled) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text("BACKGROUND MUSIC", color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Gothic ambient soundtrack", color = Color(0xFF9E8B70), fontSize = 12.sp)
            }
            Text(
                if (musicEnabled) "ON" else "OFF",
                color = if (musicEnabled) Color(0xFFD7B06A) else Color(0xFF8F806A),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(12.dp))
        Text("Test track: Old Camp • loops continuously", color = Color(0xFF746957), fontSize = 11.sp)
    }
}

@Composable
private fun NavGuideHeader(backLabel: String, onBack: () -> Unit, onHome: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            backLabel,
            color = Color(0xFFB6935B),
            fontSize = 12.sp,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onBack)
                .padding(vertical = 8.dp)
        )
        TextButton(onClick = onHome) {
            Text("HOME", color = Color(0xFFD7B06A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun NavBackText(label: String, onBack: () -> Unit) {
    Text(
        label,
        color = Color(0xFFB6935B),
        fontSize = 12.sp,
        modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp)
    )
}
