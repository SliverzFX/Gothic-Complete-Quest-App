package com.sliverzfx.gothicquest

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Surface and reading colors shared by a game's quests, journal and reference panels. */
internal data class GameGuidePalette(
    val accent: Color = Color(0xFFD7B06A),
    val body: Color = Color(0xFFC7B89B),
    val reading: Color = Color(0xFFE0D5C2),
    val muted: Color = Color(0xFF9E8B70),
    val faint: Color = Color(0xFF746957),
    val surface: Color = Color(0xFF15100D),
    val selected: Color = Color(0xFF594123),
    val highlight: Color = Color(0xFFFFE0A0),
    val border: Color = Color(0xFF5F4529),
    val track: Color = Color(0xFF0B0907)
)

private val defaultGuidePalette = GameGuidePalette()
private val gothicGuidePalette = GameGuidePalette(
    accent = Color(0xFFE1E6EB), body = Color(0xFFC8CED5), reading = Color(0xFFEDF0F3),
    muted = Color(0xFFA5ADB6), faint = Color(0xFF808A95), surface = Color(0xFF1C2127),
    selected = Color(0xFF434C57), highlight = Color(0xFFF4F6F8), border = Color(0xFF78838F),
    track = Color(0xFF101419)
)
private val gothicGoldGuidePalette = GameGuidePalette(
    accent = Color(0xFFF1DFB0), body = Color(0xFFDBD0B9), reading = Color(0xFFF5EFDF),
    muted = Color(0xFFB8AA8C), faint = Color(0xFF8D8066), surface = Color(0xFF1E1A13),
    selected = Color(0xFF5B4A2C), highlight = Color(0xFFFFF4D6), border = Color(0xFFB39A65),
    track = Color(0xFF100E0A)
)

internal fun gameGuidePalette(game: GameId?): GameGuidePalette = when (game) {
    GameId.GOTHIC -> gothicGuidePalette
    GameId.GOTHIC_2_GOLD -> gothicGoldGuidePalette
    else -> defaultGuidePalette
}

internal val LocalGameGuidePalette = staticCompositionLocalOf { defaultGuidePalette }

internal fun AppRoute.guideGame(): GameId? = when (this) {
    is AppRoute.GameHub -> game
    is AppRoute.Chapter -> game
    is AppRoute.QuestDetail -> game
    is AppRoute.AllQuests -> game
    is AppRoute.Search -> game
    is AppRoute.ToolReference -> game
    else -> null
}
