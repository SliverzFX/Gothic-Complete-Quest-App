package com.sliverzfx.gothicquest

import android.content.Context
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

private data class ResumeSnapshot(
    val game: String,
    val chapter: Int,
    val questId: String?
)

private data class NavFavoriteEntry(val game: GameId, val quest: Quest)

private val LocalCompletedDisplay = staticCompositionLocalOf { CompletedQuestDisplay.SHOW }
private val LocalCompletedKeys = staticCompositionLocalOf<Set<String>> { emptySet() }

@Composable
private fun questOpacity(game: GameId, quest: Quest): Float =
    LocalCompletedDisplay.current.opacity(navQuestKey(game, quest) in LocalCompletedKeys.current)

private val LocalBackgroundBrightness = staticCompositionLocalOf { 0.30f }

private val LocalBoxOpacity = staticCompositionLocalOf { 0.65f }

private val NavBoxOpacity: Float
    @Composable get() = LocalBoxOpacity.current

private fun loadBoxOpacity(context: Context): Int =
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        .getInt("box_opacity_percent", 65).coerceIn(20, 100)

private fun saveBoxOpacity(context: Context, percent: Int) {
    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        .edit().putInt("box_opacity_percent", percent.coerceIn(20, 100)).apply()
}

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
fun GothicQuestAppV2(onExit: () -> Unit = {}) {
    val context = LocalContext.current
    var textSize by remember { mutableStateOf(loadAppTextSize(context)) }
    var backgroundBrightness by remember {
        mutableStateOf(context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getInt("background_brightness_percent", 30).coerceIn(0, 80))
    }
    var boxOpacity by remember { mutableStateOf(loadBoxOpacity(context)) }
    CompositionLocalProvider(
        LocalBoxOpacity provides (boxOpacity / 100f),
        LocalBackgroundBrightness provides (backgroundBrightness / 100f)
    ) {
        ProvideAppTextSize(textSize) {
            GothicQuestAppContent(
                onExit = onExit,
                textSize = textSize,
                onTextSizeChanged = { size ->
                    textSize = size
                    saveAppTextSize(context, size)
                },
                backgroundBrightness = backgroundBrightness,
                onBackgroundBrightnessChanged = { backgroundBrightness = it.coerceIn(0, 80) },
                onBackgroundBrightnessChangeFinished = {
                    context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
                        .putInt("background_brightness_percent", backgroundBrightness).apply()
                },
                onPreferencesRestored = {
                    textSize = loadAppTextSize(context)
                    boxOpacity = loadBoxOpacity(context)
                    backgroundBrightness = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                        .getInt("background_brightness_percent", 30).coerceIn(0, 80)
                },
                boxOpacity = boxOpacity,
                onBoxOpacityChanged = { boxOpacity = it.coerceIn(20, 100) },
                onBoxOpacityChangeFinished = { saveBoxOpacity(context, boxOpacity) }
            )
        }
    }
}

@Composable
private fun GothicQuestAppContent(
    onExit: () -> Unit,
    onPreferencesRestored: () -> Unit,
    backgroundBrightness: Int,
    onBackgroundBrightnessChanged: (Int) -> Unit,
    onBackgroundBrightnessChangeFinished: () -> Unit,
    textSize: AppTextSize,
    onTextSizeChanged: (AppTextSize) -> Unit,
    boxOpacity: Int,
    onBoxOpacityChanged: (Int) -> Unit,
    onBoxOpacityChangeFinished: () -> Unit
) {
    val context = LocalContext.current
    var introPending by remember { mutableStateOf(true) }
    val menuBackground = remember { MenuBackgroundState() }
    var route by remember { mutableStateOf<AppRoute>(AppRoute.Home) }
    var questReturnRoute by remember { mutableStateOf<AppRoute?>(null) }
    val onGuideHome: () -> Unit = {
        questReturnRoute = null
        route = AppRoute.Home
    }
    var resumeSnapshot by remember { mutableStateOf(loadResumeSnapshot(context)) }
    var favoriteKeys by remember { mutableStateOf(loadNavStringSet(context, "favorites")) }
    var completedKeys by remember { mutableStateOf(loadNavStringSet(context, "completed")) }
    var completedDisplay by remember {
        mutableStateOf(CompletedQuestDisplay.fromStoredValue(
            context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                .getString("completed_quest_display", null)
        ))
    }
    var reduceAnimations by remember {
        mutableStateOf(context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getBoolean("reduce_animations", false))
    }
    var backgroundAnimationEnabled by remember {
        mutableStateOf(context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getBoolean("background_animation_enabled", true))
    }
    var musicEnabled by remember { mutableStateOf(loadNavMusicEnabled(context)) }
    var keepScreenAwake by remember {
        mutableStateOf(
            context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                .getBoolean("keep_screen_awake", false)
        )
    }
    val appView = LocalView.current
    DisposableEffect(appView, keepScreenAwake) {
        val previousKeepScreenOn = appView.keepScreenOn
        appView.keepScreenOn = keepScreenAwake
        onDispose { appView.keepScreenOn = previousKeepScreenOn }
    }
    AppMusic(enabled = musicEnabled, introFinished = !introPending)

    CompositionLocalProvider(
        LocalReduceAnimations provides reduceAnimations,
        LocalCompletedDisplay provides completedDisplay,
        LocalCompletedKeys provides completedKeys
    ) {
    Box(Modifier.fillMaxSize()) {
    MenuBackground(
        state = menuBackground,
        animationEnabled = backgroundAnimationEnabled,
        visible = route.usesMenuBackground,
        dimmed = route != AppRoute.Home
    )
    Crossfade(
        targetState = route,
        animationSpec = tween(durationMillis = if (reduceAnimations) 0 else 350),
        label = "screenCrossfadeV2"
    ) { currentRoute ->
            when (currentRoute) {
                AppRoute.Home -> HomeScreen(
                    playIntro = introPending,
                    backgroundAnimationEnabled = backgroundAnimationEnabled,
                    backgroundState = menuBackground,
                    onIntroFinished = { introPending = false },
                    onExit = onExit,
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
                    enableGameSearch = true,
                    entries = questGuideLibraryEntries(
                        onGameSelected = { game -> route = AppRoute.GameHub(game) }
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

                AppRoute.Faqs -> FaqScreen(
                    onBack = { route = AppRoute.Home },
                    onSupport = { route = AppRoute.Support }
                )
                AppRoute.About -> AboutScreen(onBack = onGuideHome)
                AppRoute.Support -> SupportScreen(onBack = onGuideHome)
                AppRoute.Donations -> DonationsScreen(onBack = onGuideHome)

                AppRoute.Settings -> NavSettingsScreen(
                    backgroundAnimationEnabled = backgroundAnimationEnabled,
                    onBackgroundAnimationChanged = { enabled ->
                        backgroundAnimationEnabled = enabled
                        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
                            .putBoolean("background_animation_enabled", enabled).apply()
                    },
                    reduceAnimations = reduceAnimations,
                    onReduceAnimationsChanged = { reduced ->
                        reduceAnimations = reduced
                        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
                            .putBoolean("reduce_animations", reduced).apply()
                    },
                    onRestored = {
                        resumeSnapshot = loadResumeSnapshot(context)
                        favoriteKeys = loadNavStringSet(context, "favorites")
                        completedKeys = loadNavStringSet(context, "completed")
                        musicEnabled = loadNavMusicEnabled(context)
                        val prefs = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                        keepScreenAwake = prefs.getBoolean("keep_screen_awake", false)
                        reduceAnimations = prefs.getBoolean("reduce_animations", false)
                        backgroundAnimationEnabled = prefs.getBoolean("background_animation_enabled", true)
                        completedDisplay = CompletedQuestDisplay.fromStoredValue(
                            prefs.getString("completed_quest_display", null))
                        questReturnRoute = null
                        onPreferencesRestored()
                    },
                    onResetGame = { game ->
                        val updated = progressWithoutGame(completedKeys, navGamePrefix(game))
                        val saved = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                            .edit().putStringSet("completed", updated).commit()
                        if (saved) completedKeys = updated
                        saved
                    },
                    backgroundBrightness = backgroundBrightness,
                    onBackgroundBrightnessChanged = onBackgroundBrightnessChanged,
                    onBackgroundBrightnessChangeFinished = onBackgroundBrightnessChangeFinished,
                    completedDisplay = completedDisplay,
                    onCompletedDisplayChanged = { display ->
                        completedDisplay = display
                        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                            .edit().putString("completed_quest_display", display.name).apply()
                    },
                    textSize = textSize,
                    onTextSizeChanged = onTextSizeChanged,
                    boxOpacity = boxOpacity,
                    onBoxOpacityChanged = onBoxOpacityChanged,
                    onBoxOpacityChangeFinished = onBoxOpacityChangeFinished,
                    keepScreenAwake = keepScreenAwake,
                    onKeepScreenAwakeChanged = { enabled ->
                        keepScreenAwake = enabled
                        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                            .edit().putBoolean("keep_screen_awake", enabled).apply()
                    },
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
}

private fun questGuideLibraryEntries(
    onGameSelected: (GameId) -> Unit
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
        id = "archolos",
        title = "The Chronicles of Myrtana: Archolos",
        panelRes = R.drawable.archolos_button,
        logoRes = R.drawable.archolos_logo,
        testTag = "game_archolos",
        onClick = { onGameSelected(GameId.ARCHOLOS) }
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
        onClick = { onGameSelected(GameId.RISEN) }
    ),
    GameLibraryEntry(
        id = "risen_2",
        title = "Risen 2",
        panelRes = R.drawable.risen_2_button,
        logoRes = R.drawable.risen_2_logo,
        testTag = "game_risen_2",
        onClick = { onGameSelected(GameId.RISEN_2) }
    ),
    GameLibraryEntry(
        id = "risen_3",
        title = "Risen 3",
        panelRes = R.drawable.risen_3_button,
        logoRes = R.drawable.risen_3_logo,
        testTag = "game_risen_3",
        onClick = { onGameSelected(GameId.RISEN_3) }
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
    GameId.RISEN -> "R1"
    GameId.RISEN_2 -> "R2"
    GameId.RISEN_3 -> "R3"
    GameId.ARCHOLOS -> "AR"
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
        GameId.GOTHIC_3 -> R.drawable.gothic_3_bg
        GameId.RISEN -> R.drawable.risen_1_bg
        GameId.RISEN_2 -> R.drawable.risen_2_background
        else -> null
    }
    Box(Modifier.fillMaxSize().background(navBackgroundBrush(game))) {
        if (backdropRes != null) {
            Image(
                painter = painterResource(backdropRes),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                alpha = LocalBackgroundBrightness.current
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
        GameId.RISEN -> R.drawable.risen_1_logo
        GameId.RISEN_2 -> R.drawable.risen_2_logo
        GameId.RISEN_3 -> R.drawable.risen_3_logo
        GameId.ARCHOLOS -> R.drawable.archolos_logo
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
            Text(
                if (game == GameId.RISEN || game == GameId.RISEN_2 || game == GameId.RISEN_3) "QUEST GUIDE" else "COMPLETE QUEST GUIDE",
                color = Color(0xFFC79A55),
                fontSize = 13.sp
            )
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
            if (game.quests().isNotEmpty()) {
                NavUtilityButton(game, "ALL QUESTS", onAllQuests)
                Spacer(Modifier.height(10.dp))
                NavUtilityButton(game, "SEARCH", onSearch)
            }
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
    val progress by animateFloatAsState(targetProgress, tween(if (LocalReduceAnimations.current) 0 else 500), label = "navChapterProgress")
    val percentage = (targetProgress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
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
            .padding(horizontal = 20.dp, vertical = 10.dp)
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
                    if ((game == GameId.RISEN_2 || game == GameId.RISEN_3) && questCount == 0) "Quest guide coming soon"
                    else "$completedCount / $questCount completed • $percentage%",
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
            .heightIn(min = 56.dp)
            .border(1.dp, navUtilityBorder(game), RoundedCornerShape(5.dp))
            .background(navUtilityBackground(game).copy(alpha = NavBoxOpacity), RoundedCornerShape(5.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color(0xFFBDA47A), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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
    val display = LocalCompletedDisplay.current
    val completed = LocalCompletedKeys.current
    val visibleCount = quests.count { display.isVisible(navQuestKey(game, it) in completed) }
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
            Text(
                if (visibleCount == quests.size) "${quests.size} QUESTS • CHRONOLOGICAL ORDER"
                else "$visibleCount / ${quests.size} QUESTS VISIBLE • CHRONOLOGICAL ORDER",
                color = Color(0xFF9E8B70),
                fontSize = 12.sp
            )
            Spacer(Modifier.height(18.dp))
            if ((game == GameId.RISEN_2 || game == GameId.RISEN_3) && quests.isEmpty()) {
                Text(
                    "The quest guide for this chapter is coming soon.",
                    color = Color(0xFFC7B89B),
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(18.dp))
            }
            if (quests.isNotEmpty() && visibleCount == 0) {
                Text("All quests in this chapter are completed and hidden. Change Completed quests in Settings to show them.",
                    color = Color(0xFFC7B89B), fontSize = 14.sp)
                Spacer(Modifier.height(18.dp))
            }
            val guideNotes = when (game) {
                GameId.RISEN -> RisenQuestData.notes(chapter)
                GameId.RISEN_2 -> Risen2QuestData.notes(chapter)
                GameId.RISEN_3 -> Risen3QuestData.notes(chapter)
                GameId.ARCHOLOS -> ArcholosQuestData.notes(chapter)
                else -> emptyList()
            }
            val notesByOrder = guideNotes.groupBy { it.beforeQuestOrder }
            quests.forEachIndexed { index, quest ->
                notesByOrder[quest.playOrder].orEmpty().forEach { note ->
                    NavRisenGuideNote(game, note)
                }
                if (display.isVisible(navQuestKey(game, quest) in completed)) {
                    NavQuestListCard(game, index + 1, quest) { onQuestSelected(quest) }
                    Spacer(Modifier.height(10.dp))
                }
            }
            notesByOrder[quests.size + 1].orEmpty().forEach { note ->
                NavRisenGuideNote(game, note)
            }
            NavBackText("‹  BACK TO ${game.sectionLabel}S", onBack)
        }
    }
}

@Composable
private fun NavRisenGuideNote(game: GameId, note: RisenGuideNote) {
    if (note.paragraphs.isEmpty()) {
        Text(note.title, color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
    } else {
        NavDetailCallout(note.title, note.paragraphs.joinToString("\n\n"), navInsetColor(game))
    }
    Spacer(Modifier.height(14.dp))
}

@Composable
private fun NavQuestListCard(game: GameId, number: Int, quest: Quest, onClick: () -> Unit) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .alpha(questOpacity(game, quest))
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
    val display = LocalCompletedDisplay.current
    val completed = LocalCompletedKeys.current
    val sorted = quests.filter { display.isVisible(navQuestKey(game, it) in completed) }.sortedWith(compareBy<Quest> { it.chapter }.thenBy { it.playOrder })
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
        Text("${sorted.size} QUESTS • CHRONOLOGICAL BY ${game.sectionLabel}", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        if (sorted.isEmpty() && quests.isNotEmpty()) {
            Text("All quests are completed and hidden. Change Completed quests in Settings to show them.",
                color = Color(0xFFC7B89B), fontSize = 14.sp)
            Spacer(Modifier.height(14.dp))
        }
        sorted.forEachIndexed { index, quest ->
            val shape = RoundedCornerShape(7.dp)
            Column(
                Modifier
                    .fillMaxWidth()
                    .alpha(questOpacity(game, quest))
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
    val display = LocalCompletedDisplay.current
    val completed = LocalCompletedKeys.current
    var query by remember { mutableStateOf("") }
    val normalized = query.trim()
    val visibleQuests = quests.filter { display.isVisible(navQuestKey(game, it) in completed) }
    val results = if (normalized.isBlank()) emptyList() else visibleQuests.filter { quest ->
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
            normalized.isBlank() -> Text("Type something to search ${visibleQuests.size} quests.", color = Color(0xFF9E8B70), fontSize = 14.sp)
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
            .alpha(questOpacity(game, quest))
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
    val display = LocalCompletedDisplay.current
    val completed = LocalCompletedKeys.current
    val visibleEntries = entries.filter { display.isVisible(navQuestKey(it.game, it.quest) in completed) }
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
        Text("${visibleEntries.size} VISIBLE • ${entries.size} SAVED QUESTS", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        if (visibleEntries.isEmpty()) {
            Text(if (entries.isEmpty()) "No favorites yet. Open any quest and tap ☆ to save it here."
                else "Completed favorites are hidden. Change Completed quests in Settings to show them.", color = Color(0xFFC7B89B), fontSize = 14.sp)
        } else {
            visibleEntries.forEach { entry ->
                val quest = entry.quest
                val shape = RoundedCornerShape(7.dp)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .alpha(questOpacity(entry.game, quest))
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
    backgroundAnimationEnabled: Boolean,
    onBackgroundAnimationChanged: (Boolean) -> Unit,
    reduceAnimations: Boolean,
    onReduceAnimationsChanged: (Boolean) -> Unit,
    onRestored: () -> Unit,
    onResetGame: (GameId) -> Boolean,
    backgroundBrightness: Int,
    onBackgroundBrightnessChanged: (Int) -> Unit,
    onBackgroundBrightnessChangeFinished: () -> Unit,
    completedDisplay: CompletedQuestDisplay,
    onCompletedDisplayChanged: (CompletedQuestDisplay) -> Unit,
    textSize: AppTextSize,
    onTextSizeChanged: (AppTextSize) -> Unit,
    boxOpacity: Int,
    onBoxOpacityChanged: (Int) -> Unit,
    onBoxOpacityChangeFinished: () -> Unit,
    keepScreenAwake: Boolean,
    onKeepScreenAwakeChanged: (Boolean) -> Unit,
    musicEnabled: Boolean,
    onMusicChanged: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        NavBackText("‹  BACK TO MAIN MENU", onBack)
        Spacer(Modifier.height(8.dp))
        Text("SETTINGS", color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("APP PREFERENCES", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(24.dp))
        val shape = RoundedCornerShape(7.dp)
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape)
                .padding(16.dp)
        ) {
            Text("TEXT SIZE", color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth().selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppTextSize.entries.forEach { size ->
                    val selected = textSize == size
                    val optionShape = RoundedCornerShape(5.dp)
                    Box(
                        Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp)
                            .background(
                                if (selected) Color(0xFF49351F) else Color(0xFF0E0B08),
                                optionShape
                            )
                            .border(
                                1.dp,
                                if (selected) Color(0xFFD7B06A) else Color(0xFF5F4529),
                                optionShape
                            )
                            .selectable(
                                selected = selected,
                                role = Role.RadioButton,
                                onClick = { onTextSizeChanged(size) }
                            )
                            .testTag("text_size_${size.name.lowercase()}")
                            .padding(horizontal = 4.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(size.label, color = Color(0xFFD7B06A), fontSize = 14.sp)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Quest guide text preview.",
                color = Color(0xFFE0D5C2),
                fontSize = 16.sp,
                modifier = Modifier.testTag("text_size_preview")
            )
            Spacer(Modifier.height(6.dp))
            Text("Applies throughout the app.", color = Color(0xFF9E8B70), fontSize = 12.sp)
        }
        Spacer(Modifier.height(16.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape)
                .padding(16.dp)
        ) {
            Text(
                "BOX OPACITY • $boxOpacity%",
                color = Color(0xFFD7B06A),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Chapter and quest boxes. Text stays fully visible.",
                color = Color(0xFF9E8B70),
                fontSize = 12.sp
            )
            Slider(
                value = boxOpacity.toFloat(),
                onValueChange = { onBoxOpacityChanged(it.roundToInt()) },
                onValueChangeFinished = onBoxOpacityChangeFinished,
                valueRange = 20f..100f,
                modifier = Modifier.fillMaxWidth().testTag("box_opacity_slider"),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFD7B06A),
                    activeTrackColor = Color(0xFFC79A55),
                    inactiveTrackColor = Color(0xFF49351F)
                )
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("20%", color = Color(0xFF9E8B70), fontSize = 12.sp)
                Text("100%", color = Color(0xFF9E8B70), fontSize = 12.sp)
            }
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFF927449), Color(0xFF40564A))),
                        shape
                    )
                    .padding(10.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(navCardBrush(GameId.GOTHIC_2_GOLD), shape, alpha = NavBoxOpacity)
                        .border(1.dp, Color(0xFF76552E), shape)
                        .padding(14.dp)
                ) {
                    Text("QUEST BOX PREVIEW", color = Color(0xFFD7B06A), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("See more of the background.", color = Color(0xFFE0D5C2), fontSize = 13.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Column(
            Modifier.fillMaxWidth()
                .background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape)
                .padding(16.dp)
        ) {
            Text("BACKGROUND BRIGHTNESS • $backgroundBrightness%",
                color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Chapter and quest artwork. 0% hides it; 80% makes it clearer.",
                color = Color(0xFF9E8B70), fontSize = 12.sp)
            Slider(
                value = backgroundBrightness.toFloat(),
                onValueChange = { onBackgroundBrightnessChanged(it.roundToInt()) },
                onValueChangeFinished = onBackgroundBrightnessChangeFinished,
                valueRange = 0f..80f,
                modifier = Modifier.fillMaxWidth().testTag("background_brightness_slider"),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFD7B06A),
                    activeTrackColor = Color(0xFFC79A55),
                    inactiveTrackColor = Color(0xFF49351F)
                )
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("0%", color = Color(0xFF9E8B70), fontSize = 12.sp)
                Text("80%", color = Color(0xFF9E8B70), fontSize = 12.sp)
            }
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier.fillMaxWidth().height(140.dp)
                    .background(Color(0xFF090706), shape)
                    .clip(shape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.gothic_mask_bg),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop,
                    alpha = LocalBackgroundBrightness.current
                )
                Text("BACKGROUND PREVIEW", color = Color(0xFFD7B06A),
                    fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(16.dp))
        Column(
            Modifier.fillMaxWidth()
                .background(Color(0xFF15100D), shape)
                .border(1.dp, Color(0xFF5F4529), shape)
                .padding(16.dp)
        ) {
            Text("COMPLETED QUESTS", color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Applies to quest lists, search and favorites. Your progress stays saved.",
                color = Color(0xFF9E8B70), fontSize = 12.sp)
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth().selectableGroup(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CompletedQuestDisplay.entries.forEach { display ->
                    val selected = display == completedDisplay
                    val optionShape = RoundedCornerShape(5.dp)
                    Box(
                        Modifier.weight(1f).heightIn(min = 48.dp)
                            .background(if (selected) Color(0xFF49351F) else Color(0xFF0E0B08), optionShape)
                            .border(1.dp, if (selected) Color(0xFFD7B06A) else Color(0xFF5F4529), optionShape)
                            .selectable(selected = selected, role = Role.RadioButton,
                                onClick = { onCompletedDisplayChanged(display) })
                            .testTag("completed_quests_${display.name.lowercase()}")
                            .padding(horizontal = 4.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(display.label, color = Color(0xFFD7B06A), fontSize = 14.sp)
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("APP OPTIONS", color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NavCompactToggle("Music", musicEnabled, onMusicChanged, "music_toggle", Modifier.weight(1f))
            NavCompactToggle("Keep screen awake", keepScreenAwake, onKeepScreenAwakeChanged,
                "keep_screen_awake_toggle", Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NavCompactToggle("Reduce animations", reduceAnimations, onReduceAnimationsChanged,
                "reduce_animations_toggle", Modifier.weight(1f))
            NavCompactToggle("Background animation", backgroundAnimationEnabled, onBackgroundAnimationChanged,
                "background_animation_toggle", Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Text("Background animation plays the video across the main menu and app pages. OFF uses the splash background image. Reduce animations also keeps the background still and uses instant transitions.",
            color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))
        BackupSettingsSection(onRestored = onRestored, onResetGame = onResetGame)
    }
}

@Composable
private fun NavCompactToggle(
    label: String,
    enabled: Boolean,
    onChanged: (Boolean) -> Unit,
    tag: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(7.dp)
    Column(
        modifier.heightIn(min = 72.dp)
            .background(Color(0xFF15100D), shape)
            .border(1.dp, Color(0xFF5F4529), shape)
            .toggleable(value = enabled, role = Role.Switch, onValueChange = onChanged)
            .testTag(tag)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Text(label, color = Color(0xFFD7B06A), fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(if (enabled) "ON" else "OFF",
            color = if (enabled) Color(0xFFD7B06A) else Color(0xFF8F806A),
            fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun NavGuideHeader(backLabel: String, onBack: () -> Unit, onHome: () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = onBack, modifier = Modifier.weight(1f).heightIn(min = 56.dp)) {
            Text(backLabel, color = Color(0xFFB6935B), fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth())
        }
        TextButton(onClick = onHome, modifier = Modifier.heightIn(min = 56.dp)) {
            Text("HOME", color = Color(0xFFD7B06A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun NavBackText(label: String, onBack: () -> Unit) {
    TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp)) {
        Text(label, color = Color(0xFFB6935B), fontSize = 16.sp)
    }
}
