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
import androidx.compose.runtime.saveable.rememberSaveable
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
        gameGuidePalette(game).backgroundColors.isNotEmpty() -> Brush.verticalGradient(gameGuidePalette(game).backgroundColors)
        game == GameId.GOTHIC_2_GOLD -> Brush.verticalGradient(listOf(Color(0xFF292419), Color(0xFF17140F), Color(0xFF0B0A07)))
        game.usesStoneTheme -> Brush.verticalGradient(
            listOf(
                Color(0xFF30363E),
                Color(0xFF1E242B),
                Color(0xFF14191F),
                Color(0xFF0C1015)
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
        gameGuidePalette(game).cardColors.isNotEmpty() -> Brush.horizontalGradient(gameGuidePalette(game).cardColors)
        game == GameId.GOTHIC_2_GOLD -> Brush.horizontalGradient(listOf(Color(0xFF242017), Color(0xFF393124), Color(0xFF211D15)))
        game.usesStoneTheme -> Brush.horizontalGradient(
            listOf(
                Color(0xFF262D35),
                Color(0xFF3B444F),
                Color(0xFF2B333D),
                Color(0xFF1A2028)
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
        gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).border
        game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).border
        game.usesStoneTheme -> Color(0xFF78838F)
        game.usesBloodTheme -> Color(0xFF76252A)
        else -> Color(0xFF5F4529)
    }

private fun navInsetColor(game: GameId): Color =
    when {
        gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).surface
        game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).surface
        game.usesStoneTheme -> Color(0xFF1C2127)
        game.usesBloodTheme -> Color(0xFF190708)
        else -> Color(0xFF15100D)
    }

private fun navUtilityBorder(game: GameId): Color =
    when {
        gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).border.copy(alpha = 0.8f)
        game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).border.copy(alpha = 0.8f)
        game.usesStoneTheme -> Color(0xFF66727F)
        game.usesBloodTheme -> Color(0xFF652126)
        else -> Color(0xFF4D4030)
    }

private fun navUtilityBackground(game: GameId): Color =
    when {
        gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).surface
        game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).surface
        game.usesStoneTheme -> Color(0xFF1A2028)
        game.usesBloodTheme -> Color(0xFF160607)
        else -> Color(0xFF11100E)
    }

private fun navProgressBorder(game: GameId): Color =
    when {
        gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).border.copy(alpha = 0.65f)
        game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).border.copy(alpha = 0.65f)
        game.usesStoneTheme -> Color(0xFF596572)
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
    val visitPrefs = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
    var recentVisits by remember { mutableStateOf(RecentVisitsCodec.load(visitPrefs)) }
    val saveRecentVisits: (List<RecentVisit>) -> Unit = { visits ->
        recentVisits = visits
        visitPrefs.edit().putString(RecentVisitsKey, RecentVisitsCodec.encode(visits)).apply()
    }
    val recordVisit: (RecentVisit) -> Unit = { visit ->
        saveRecentVisits(updatedRecentVisits(recentVisits, visit))
    }
    LaunchedEffect(route) { recentVisitFromRoute(route)?.let(recordVisit) }

    var favoriteKeys by remember { mutableStateOf(loadNavStringSet(context, "favorites")) }
    var completedKeys by remember { mutableStateOf(loadNavStringSet(context, "completed")) }
    var inProgressKeys by remember { mutableStateOf(loadNavStringSet(context, "in_progress")) }
    val setQuestStatus: (String, QuestStatus) -> Unit = { key, status ->
        val progress = withQuestStatus(completedKeys, inProgressKeys, key, status)
        context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
            .putStringSet("completed", progress.completed).putStringSet("in_progress", progress.inProgress).apply()
        completedKeys = progress.completed
        inProgressKeys = progress.inProgress
    }
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
        LocalToolFavoriteKeys provides favoriteKeys,
        LocalToggleToolFavorite provides { game, entryId ->
            favoriteKeys = toggleNavSet(context, "favorites", favoriteKeys, toolFavoriteKey(game, entryId))
        },
        LocalInProgressKeys provides inProgressKeys,
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
        CompositionLocalProvider(LocalGameGuidePalette provides gameGuidePalette(currentRoute.guideGame())) {
            when (currentRoute) {
                AppRoute.Home -> HomeScreen(
                    playIntro = introPending,
                    backgroundAnimationEnabled = backgroundAnimationEnabled,
                    backgroundState = menuBackground,
                    onIntroFinished = { introPending = false },
                    onExit = onExit,
                    hasContinue = true,
                    onContinue = { route = AppRoute.ContinueHistory },
                    onDestinationSelected = { destination ->
                        route = when (destination) {
                            "Quest Guides" -> AppRoute.QuestGuides
                            "Game Tools" -> AppRoute.GameTools
                            "FAQs" -> AppRoute.Faqs
                            "Info / About" -> AppRoute.About
                            "Support / Bugs" -> AppRoute.Support
                            "Donations" -> AppRoute.Donations
                            "Settings" -> AppRoute.Settings
                            else -> AppRoute.Home
                        }
                    }
                )

                AppRoute.ContinueHistory -> ContinueHistoryScreen(
                    visits = recentVisits,
                    onHome = onGuideHome,
                    onPickGame = { route = AppRoute.QuestGuides },
                    onResume = { visit ->
                        if (visit.kind == "quest") questReturnRoute = AppRoute.Chapter(visit.game, visit.chapter!!)
                        route = visit.route()
                    },
                    onRemove = { games -> saveRecentVisits(recentVisits.filterNot { it.game in games }) }
                )

                AppRoute.QuestGuides -> GameLibraryScreen(
                    title = "PICK A GAME",
                    enableGameSearch = true,
                    entries = questGuideLibraryEntries(
                        onGameSelected = { game -> route = AppRoute.GameHub(game) }
                    ),
                    onBack = { route = AppRoute.Home },
                    topRightActionLabel = "FAVORITES",
                    onTopRightAction = { route = AppRoute.Favorites }
                )

                AppRoute.GameTools -> GameToolsScreen(
                    onBack = onGuideHome,
                    onHome = onGuideHome,
                    onSectionSelected = { route = AppRoute.ToolGames(it) }
                )
                is AppRoute.ToolGames -> GameLibraryScreen(
                    title = currentRoute.section.title,
                    entries = questGuideLibraryEntries(
                        onGameSelected = { route = AppRoute.ToolReference(currentRoute.section, it) }
                    ),
                    onBack = { route = AppRoute.GameTools },
                    topRightActionLabel = "HOME",
                    onTopRightAction = onGuideHome,
                    enableGameSearch = true,
                    showHeading = true,
                    useMenuBackground = true
                )
                is AppRoute.ToolReference -> ToolReferenceScreen(
                    section = currentRoute.section,
                    game = currentRoute.game,
                    onBack = { route = AppRoute.ToolGames(currentRoute.section) },
                    onHome = onGuideHome
                )

                AppRoute.Faqs -> FaqScreen(
                    onBack = { route = AppRoute.Home },
                    onSupport = { route = AppRoute.Support }
                )
                AppRoute.About -> AboutScreen(onBack = onGuideHome)
                AppRoute.Support -> SupportScreen(onBack = onGuideHome)
                is AppRoute.QuestCorrection -> {
                    val quest = currentRoute.game.quests().firstOrNull { it.id == currentRoute.questId }
                    SupportScreen(
                        onBack = { route = if (quest != null) AppRoute.QuestDetail(currentRoute.game, quest.id)
                            else AppRoute.GameHub(currentRoute.game) },
                        onHome = onGuideHome,
                        correction = quest?.let {
                            QuestCorrectionContext(currentRoute.game.persistedName,
                                currentRoute.game.sectionLabel, it.chapter, it.title, it.id)
                        }
                    )
                }
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
                        recentVisits = RecentVisitsCodec.load(visitPrefs)
                        favoriteKeys = loadNavStringSet(context, "favorites")
                        completedKeys = loadNavStringSet(context, "completed")
                        inProgressKeys = loadNavStringSet(context, "in_progress")
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
                        val updatedInProgress = progressWithoutGame(inProgressKeys, navGamePrefix(game))
                        val saved = context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
                            .edit().putStringSet("completed", updated).putStringSet("in_progress", updatedInProgress).commit()
                        if (saved) { completedKeys = updated; inProgressKeys = updatedInProgress }
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
                    entries = remember(favoriteKeys) { buildNavFavoriteEntries(favoriteKeys) },
                    toolEntries = remember(favoriteKeys) { buildToolFavorites(favoriteKeys) },
                    onBack = { route = AppRoute.QuestGuides },
                    onQuestSelected = { game, quest ->
                        questReturnRoute = AppRoute.Favorites
                        route = AppRoute.QuestDetail(game, quest.id)
                    }
                )

                is AppRoute.GameHub -> NavGameHubScreen(
                    onHome = onGuideHome,
                    game = currentRoute.game,
                    initialTab = currentRoute.tab,
                    onTabSelected = { tab -> recordVisit(RecentVisit(currentRoute.game, "hub", tab = tab)) },
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
                        val neighbors = remember(currentRoute.game, quest.id, completedDisplay, completedKeys) {
                            adjacentQuests(currentRoute.game.quests(), quest.id) {
                                completedDisplay.isVisible(navQuestKey(currentRoute.game, it) in completedKeys)
                            }
                        }
                        NavQuestDetailScreen(
                            onHome = onGuideHome,
                            game = currentRoute.game,
                            quest = quest,
                            neighbors = neighbors,
                            onAdjacentQuest = { destination ->
                                route = AppRoute.QuestDetail(currentRoute.game, destination.id)
                            },
                            isFavorite = favoriteKey in favoriteKeys,
                            isCompleted = completedKey in completedKeys,
                            status = questStatus(completedKeys, inProgressKeys, completedKey),
                            onStatusChanged = { setQuestStatus(completedKey, it) },
                            onToggleFavorite = {
                                favoriteKeys = toggleNavSet(context, "favorites", favoriteKeys, favoriteKey)
                            },
                            onToggleCompleted = {
                                setQuestStatus(completedKey, if (completedKey in completedKeys)
                                    QuestStatus.NOT_STARTED else QuestStatus.COMPLETED)
                            },
                            onReportCorrection = {
                                route = AppRoute.QuestCorrection(currentRoute.game, quest.id)
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

private fun navGamePrefix(game: GameId): String = game.savedKeyPrefix

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
        GameId.RISEN_3 -> R.drawable.risen_3_bg
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
    initialTab: String,
    onTabSelected: (String) -> Unit,
    completedKeys: Set<String>,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onChapterSelected: (Int) -> Unit,
    onAllQuests: () -> Unit,
    onSearch: () -> Unit
) {
    var selectedTab by rememberSaveable(game, initialTab) { mutableStateOf(if (initialTab == "items") "codes" else initialTab) }
    LaunchedEffect(game, selectedTab) {
        if (selectedTab == "items") selectedTab = "codes"
        else onTabSelected(selectedTab)
    }
    BackHandler { if (selectedTab != "quests") selectedTab = "quests" else onBack() }
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
        Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
            .padding(horizontal = 16.dp).testTag("game_hub_screen"),
            horizontalAlignment = Alignment.CenterHorizontally) {
            NavGuideHeader("‹  BACK TO GAMES", onBack, onHome)
            Image(painterResource(logoRes), contentDescription = game.persistedName,
                modifier = Modifier.width(260.dp).height(70.dp), contentScale = ContentScale.Fit)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth().selectableGroup()) {
                listOf("quests" to "QUESTS", "codes" to "CODES", "tips" to "TIPS")
                    .forEach { (key, label) ->
                        Box(Modifier.weight(1f).heightIn(min = 48.dp)
                            .background(if (selectedTab == key) LocalGameGuidePalette.current.selected.copy(alpha = 0.71f) else LocalGameGuidePalette.current.surface.copy(alpha = 0.53f))
                            .selectable(selected = selectedTab == key, role = Role.Tab,
                                onClick = { selectedTab = key }).testTag("game_tab_$key")
                            .padding(vertical = 14.dp), contentAlignment = Alignment.Center) {
                            Text(label, color = LocalGameGuidePalette.current.accent, fontSize = 14.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }
            }
            Spacer(Modifier.height(10.dp))
            Box(Modifier.weight(1f).fillMaxWidth()) {
                if (selectedTab == "quests") {
                    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                        (1..game.sectionCount).forEach { chapter ->
                            NavChapterButton(chapter, game.chapterQuests(chapter), completedKeys, game) {
                                onChapterSelected(chapter)
                            }
                            if (chapter != game.sectionCount) Spacer(Modifier.height(8.dp))
                        }
                        if (game.quests().isNotEmpty()) {
                            Spacer(Modifier.height(12.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(Modifier.weight(1f).testTag("game_all_quests")) {
                                    NavUtilityButton(game, "ALL QUESTS", onAllQuests)
                                }
                                Box(Modifier.weight(1f).testTag("game_quest_search")) {
                                    NavUtilityButton(game, "SEARCH", onSearch)
                                }
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                } else {
                    ToolReferenceScreen(
                        section = when (selectedTab) {
                            "tips" -> ToolSection.USEFUL_TIPS
                            else -> ToolSection.MARVIN_CODES
                        },
                        game = game,
                        onBack = { selectedTab = "quests" },
                        onHome = onHome,
                        embedded = true
                    )
                }
            }
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
    val heightScale = when {
        game == GameId.GOTHIC_3 -> 0.85f
        game.sectionCount == 6 -> 0.95f
        else -> 1f
    }
    // Take the height reduction from padding so text and progress stay readable.
    val verticalPadding = (10f - 36f * (1f - heightScale)).dp
    val questCount = quests.size
    val completedCount = quests.count { navQuestKey(game, it) in completedKeys }
    val targetProgress = if (questCount == 0) 0f else completedCount.toFloat() / questCount.toFloat()
    val progress by animateFloatAsState(targetProgress, tween(if (LocalReduceAnimations.current) 0 else 500), label = "navChapterProgress")
    val percentage = (targetProgress * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = (72f * heightScale).dp)
            .testTag("chapter_button_$chapter")
            .border(
                1.dp,
                when {
                    gameGuidePalette(game).cardColors.isNotEmpty() -> gameGuidePalette(game).border
                    game == GameId.GOTHIC_2_GOLD -> gameGuidePalette(game).border
                    game.usesStoneTheme -> Color(0xFF8B97A4)
                    game.usesBloodTheme -> Color(0xFF81272D)
                    else -> Color(0xFF76552E)
                },
                shape
            )
            .background(
                if (gameGuidePalette(game).cardColors.isNotEmpty() || game.usesStoneTheme || game.usesBloodTheme || game == GameId.GOTHIC_2_GOLD) navCardBrush(game)
                else Brush.horizontalGradient(listOf(Color(0xFF211712), Color(0xFF35160F), Color(0xFF17110E))),
                shape,
                alpha = NavBoxOpacity
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = verticalPadding)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text("${game.sectionLabel} $chapter", color = LocalGameGuidePalette.current.accent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    if ((game == GameId.RISEN_2 || game == GameId.RISEN_3) && questCount == 0) "Quest guide coming soon"
                    else "$completedCount / $questCount completed • $percentage%",
                    color = LocalGameGuidePalette.current.muted,
                    fontSize = 11.sp
                )
            }
            Text("›", color = LocalGameGuidePalette.current.accent, fontSize = 26.sp)
        }
        Spacer(Modifier.height(5.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(LocalGameGuidePalette.current.track, RoundedCornerShape(3.dp))
                .border(1.dp, navProgressBorder(game), RoundedCornerShape(3.dp))
        ) {
            Box(
                Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(LocalGameGuidePalette.current.secondaryAccent, RoundedCornerShape(3.dp))
            )
        }
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
        Text(label, color = LocalGameGuidePalette.current.accent, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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
            Text("${game.displayTitle} — ${game.sectionLabel} $chapter", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text(
                if (visibleCount == quests.size) "${quests.size} QUESTS • CHRONOLOGICAL ORDER"
                else "$visibleCount / ${quests.size} QUESTS VISIBLE • CHRONOLOGICAL ORDER",
                color = LocalGameGuidePalette.current.muted,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(18.dp))
            if ((game == GameId.RISEN_2 || game == GameId.RISEN_3) && quests.isEmpty()) {
                Text(
                    "The quest guide for this chapter is coming soon.",
                    color = LocalGameGuidePalette.current.body,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(18.dp))
            }
            if (quests.isNotEmpty() && visibleCount == 0) {
                Text("All quests in this chapter are completed and hidden. Change Completed quests in Settings to show them.",
                    color = LocalGameGuidePalette.current.body, fontSize = 14.sp)
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
        Text(note.title, color = LocalGameGuidePalette.current.accent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                color = LocalGameGuidePalette.current.accent,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text("›", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp)
        }
        Spacer(Modifier.height(3.dp))
        QuestInProgressBadge(game, quest)
        Text(quest.category.uppercase(), color = LocalGameGuidePalette.current.secondaryAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(7.dp))
        Text(
            quest.summary,
            color = LocalGameGuidePalette.current.body,
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(7.dp))
        Text(quest.id, color = LocalGameGuidePalette.current.faint, fontSize = 10.sp)
    }
}

@Composable
private fun NavQuestDetailScreen(
    game: GameId,
    quest: Quest,
    neighbors: QuestNeighbors,
    onAdjacentQuest: (Quest) -> Unit,
    isFavorite: Boolean,
    isCompleted: Boolean,
    status: QuestStatus,
    onStatusChanged: (QuestStatus) -> Unit,
    onToggleFavorite: () -> Unit,
    onToggleCompleted: () -> Unit,
    onReportCorrection: () -> Unit,
    onHome: () -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)
    val questKey = navQuestKey(game, quest)
    val scrollState = rememberQuestReadingScroll(questKey)
    NavGuideBackground(game) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            NavGuideHeader("‹  BACK TO QUESTS", onBack, onHome)
            Spacer(Modifier.height(6.dp))
            Text(quest.id, color = LocalGameGuidePalette.current.faint, fontSize = 11.sp)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    quest.title,
                    color = LocalGameGuidePalette.current.accent,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    if (isCompleted) "✓" else "○",
                    color = if (isCompleted) LocalGameGuidePalette.current.accent else LocalGameGuidePalette.current.muted,
                    fontSize = 30.sp,
                    modifier = Modifier.clickable(onClick = onToggleCompleted).padding(6.dp)
                )
                Text(
                    if (isFavorite) "★" else "☆",
                    color = if (isFavorite) LocalGameGuidePalette.current.accent else LocalGameGuidePalette.current.muted,
                    fontSize = 32.sp,
                    modifier = Modifier.clickable(onClick = onToggleFavorite).padding(6.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            QuestStatusButtons(status, onStatusChanged)
            if (quest.aliases.isNotEmpty()) {
                Text("Also: ${quest.aliases.joinToString()}", color = LocalGameGuidePalette.current.muted, fontSize = 12.sp)
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
            Text("OBJECTIVE", color = LocalGameGuidePalette.current.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(5.dp))
            Text(quest.summary, color = LocalGameGuidePalette.current.reading, fontSize = 16.sp)
            Spacer(Modifier.height(22.dp))
            Text("WALKTHROUGH", color = LocalGameGuidePalette.current.accent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                        color = LocalGameGuidePalette.current.accent,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(28.dp)
                    )
                    Text(step, color = LocalGameGuidePalette.current.reading, fontSize = 14.sp, modifier = Modifier.weight(1f))
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
            QuestNotesSection(questKey)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NavAdjacentQuestButton("‹  PREVIOUS QUEST", "quest_previous", neighbors.previous,
                    onAdjacentQuest, Modifier.weight(1f))
                NavAdjacentQuestButton("NEXT QUEST  ›", "quest_next", neighbors.next,
                    onAdjacentQuest, Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onReportCorrection,
                modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).testTag("quest_report_correction")) {
                Text("REPORT A CORRECTION", color = LocalGameGuidePalette.current.accent, fontSize = 14.sp)
            }
            NavBackText("‹  BACK TO QUESTS", onBack)
        }
    }
}

@Composable
private fun NavAdjacentQuestButton(
    label: String,
    tag: String,
    destination: Quest?,
    onSelected: (Quest) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(7.dp)
    TextButton(
        onClick = { destination?.let(onSelected) },
        enabled = destination != null,
        modifier = modifier.heightIn(min = 64.dp)
            .background(LocalGameGuidePalette.current.surface, shape)
            .border(1.dp, LocalGameGuidePalette.current.border, shape).testTag(tag)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(label, color = if (destination != null) LocalGameGuidePalette.current.accent else LocalGameGuidePalette.current.faint,
                fontSize = 12.sp, fontWeight = FontWeight.Bold)
            if (destination != null) {
                Spacer(Modifier.height(4.dp))
                Text(destination.title, color = LocalGameGuidePalette.current.body, fontSize = 12.sp,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
private fun NavDetailLine(label: String, value: String, addSpace: Boolean = true) {
    Text(label, color = LocalGameGuidePalette.current.muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    Text(value, color = LocalGameGuidePalette.current.body, fontSize = 14.sp)
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
        Text(label, color = LocalGameGuidePalette.current.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(value, color = LocalGameGuidePalette.current.reading, fontSize = 14.sp)
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
        Text("${game.displayTitle} — ALL QUESTS", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("${sorted.size} QUESTS • CHRONOLOGICAL BY ${game.sectionLabel}", color = LocalGameGuidePalette.current.muted, fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        if (sorted.isEmpty() && quests.isNotEmpty()) {
            Text("All quests are completed and hidden. Change Completed quests in Settings to show them.",
                color = LocalGameGuidePalette.current.body, fontSize = 14.sp)
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
                QuestInProgressBadge(game, quest)
                Text("${game.sectionLabel} ${quest.chapter}  •  ${quest.id}", color = LocalGameGuidePalette.current.muted, fontSize = 10.sp)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "${index + 1}.  ${quest.title}",
                        color = LocalGameGuidePalette.current.accent,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text("›", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp)
                }
                Text(quest.category.uppercase(), color = LocalGameGuidePalette.current.secondaryAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
        Text("${game.displayTitle} — SEARCH", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("SEARCH ALL QUEST DATA", color = LocalGameGuidePalette.current.muted, fontSize = 12.sp)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text("Quest, NPC, location, ID...") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = LocalGameGuidePalette.current.reading,
                unfocusedTextColor = LocalGameGuidePalette.current.reading,
                focusedBorderColor = LocalGameGuidePalette.current.accent,
                unfocusedBorderColor = navCardBorder(game),
                focusedLabelColor = LocalGameGuidePalette.current.accent,
                unfocusedLabelColor = LocalGameGuidePalette.current.muted,
                cursorColor = LocalGameGuidePalette.current.accent
            )
        )
        Spacer(Modifier.height(14.dp))
        when {
            normalized.isBlank() -> Text("Type something to search ${visibleQuests.size} quests.", color = LocalGameGuidePalette.current.muted, fontSize = 14.sp)
            results.isEmpty() -> Text("No quests found.", color = LocalGameGuidePalette.current.muted, fontSize = 14.sp)
            else -> {
                Text("${results.size} RESULTS", color = LocalGameGuidePalette.current.accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
        Text("${game.sectionLabel} ${quest.chapter}  •  ${quest.id}", color = LocalGameGuidePalette.current.muted, fontSize = 10.sp)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(quest.title, color = LocalGameGuidePalette.current.accent, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("›", color = LocalGameGuidePalette.current.accent, fontSize = 25.sp)
        }
        QuestInProgressBadge(game, quest)
        Text(quest.category.uppercase(), color = LocalGameGuidePalette.current.secondaryAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("${quest.giver} • ${quest.location}", color = LocalGameGuidePalette.current.muted, fontSize = 11.sp)
    }
}

@Composable
private fun NavFavoritesScreen(
    entries: List<NavFavoriteEntry>,
    toolEntries: List<SavedToolFavorite>,
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
        NavGuideHeader("‹  BACK TO GAMES", onBack, onHome)
        Spacer(Modifier.height(4.dp))
        Text("FAVORITES", color = Color(0xFFD6B06A), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("${visibleEntries.size + toolEntries.size} VISIBLE • ${entries.size + toolEntries.size} SAVED FAVORITES", color = Color(0xFF9E8B70), fontSize = 12.sp)
        Spacer(Modifier.height(18.dp))
        if (visibleEntries.isEmpty() && toolEntries.isEmpty()) {
            Text(if (entries.isEmpty()) "No favorites yet. Tap ☆ on a quest, code or item to save it here."
                else "Completed favorites are hidden. Change Completed quests in Settings to show them.", color = Color(0xFFC7B89B), fontSize = 14.sp)
        } else {
            if (visibleEntries.isNotEmpty()) {
                Text("QUESTS", color = Color(0xFFD7B06A), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
            }
            visibleEntries.sortedWith(compareBy<NavFavoriteEntry> { it.quest.category }
                .thenBy { it.game.ordinal }.thenBy { it.quest.chapter }.thenBy { it.quest.playOrder })
                .groupBy { it.quest.category }.forEach { (category, categoryEntries) ->
                Text(category.uppercase(), color = Color(0xFFC79A55), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                categoryEntries.forEach { entry ->
                    val quest = entry.quest
                    val shape = RoundedCornerShape(7.dp)
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .alpha(questOpacity(entry.game, quest))
                            .background(navCardBrush(entry.game), shape, alpha = NavBoxOpacity)
                            .border(1.dp, navCardBorder(entry.game), shape)
                            .clickable { onQuestSelected(entry.game, quest) }
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Text("${entry.game.displayTitle}  •  ${entry.game.sectionLabel} ${quest.chapter}", color = gameGuidePalette(entry.game).muted, fontSize = 10.sp)
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(quest.title, color = gameGuidePalette(entry.game).accent, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("★", color = gameGuidePalette(entry.game).accent, fontSize = 20.sp)
                        }
                        QuestInProgressBadge(entry.game, quest)
                        Text(quest.category.uppercase(), color = gameGuidePalette(entry.game).secondaryAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
            ToolFavoritesSections(toolEntries)
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
            Text(backLabel, color = LocalGameGuidePalette.current.accent, fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth())
        }
        TextButton(onClick = onHome, modifier = Modifier.heightIn(min = 56.dp)) {
            Text("HOME", color = LocalGameGuidePalette.current.accent, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun NavBackText(label: String, onBack: () -> Unit) {
    TextButton(onClick = onBack, modifier = Modifier.heightIn(min = 56.dp)) {
        Text(label, color = LocalGameGuidePalette.current.accent, fontSize = 16.sp)
    }
}
