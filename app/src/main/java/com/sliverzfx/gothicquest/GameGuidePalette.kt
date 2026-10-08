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
    val track: Color = Color(0xFF0B0907),
    val secondaryAccent: Color = accent,
    val backgroundColors: List<Color> = emptyList(),
    val cardColors: List<Color> = emptyList()
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

private val newBalanceGuidePalette = GameGuidePalette(
    accent = Color(0xFFFFA8AD), body = Color(0xFFEBC7CA), reading = Color(0xFFFFEDEF),
    muted = Color(0xFFCAA0A6), faint = Color(0xFF9E737A), surface = Color(0xFF260F15),
    selected = Color(0xFF6B2936), highlight = Color(0xFFFFDADD), border = Color(0xFFB45C69),
    track = Color(0xFF14080C), secondaryAccent = Color(0xFFF48C99),
    backgroundColors = listOf(Color(0xFF42151E), Color(0xFF250D14), Color(0xFF11070B)),
    cardColors = listOf(Color(0xFF33131C), Color(0xFF632633), Color(0xFF2A1119))
)
private val archolosGuidePalette = GameGuidePalette(
    accent = Color(0xFFAECFFF), body = Color(0xFFD2D1E8), reading = Color(0xFFF2EFFB),
    muted = Color(0xFFB5AFD0), faint = Color(0xFF817C9D), surface = Color(0xFF1A172C),
    selected = Color(0xFF49365F), highlight = Color(0xFFE6D8FF), border = Color(0xFF8573AD),
    track = Color(0xFF0D0E1B), secondaryAccent = Color(0xFFF4A0AD),
    backgroundColors = listOf(Color(0xFF142A45), Color(0xFF281B3B), Color(0xFF30131E), Color(0xFF0C0C17)),
    cardColors = listOf(Color(0xFF1B3455), Color(0xFF3C2B50), Color(0xFF4B2333))
)
private val gothic3GuidePalette = GameGuidePalette(
    accent = Color(0xFFBCE4FF), body = Color(0xFFD3E4ED), reading = Color(0xFFF5FAFF),
    muted = Color(0xFFB3CFDF), faint = Color(0xFF7896A9), surface = Color(0xFF142633),
    selected = Color(0xFF34556B), highlight = Color(0xFFFFFFFF), border = Color(0xFF82B1CD),
    track = Color(0xFF0B151F), secondaryAccent = Color(0xFFE8F6FF),
    backgroundColors = listOf(Color(0xFF253E50), Color(0xFF142734), Color(0xFF09131E)),
    cardColors = listOf(Color(0xFF203B4F), Color(0xFF37566A), Color(0xFF1B3041))
)

internal fun gameGuidePalette(game: GameId?): GameGuidePalette = when (game) {
    GameId.GOTHIC -> gothicGuidePalette
    GameId.GOTHIC_2_GOLD -> gothicGoldGuidePalette
    GameId.NEW_BALANCE -> newBalanceGuidePalette
    GameId.ARCHOLOS -> archolosGuidePalette
    GameId.GOTHIC_3 -> gothic3GuidePalette
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
