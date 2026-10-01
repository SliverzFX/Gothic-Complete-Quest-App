package com.sliverzfx.gothicquest

enum class GameId(val persistedName: String, val displayTitle: String) {
    GOTHIC("Gothic", "GOTHIC"),
    GOTHIC_2_GOLD("Gothic II Gold Edition", "GOTHIC II"),
    NEW_BALANCE("Gothic II New Balance", "NEW BALANCE");

    companion object {
        fun fromPersistedName(value: String): GameId? = entries.firstOrNull { it.persistedName == value }
    }
}

sealed interface AppRoute {
    data object Home : AppRoute
    data object QuestGuides : AppRoute
    data object Cheats : AppRoute
    data object Faqs : AppRoute
    data object About : AppRoute
    data object Support : AppRoute
    data object Donations : AppRoute
    data object Settings : AppRoute
    data object Favorites : AppRoute
    data class GameHub(val game: GameId) : AppRoute
    data class Chapter(val game: GameId, val chapter: Int) : AppRoute
    data class QuestDetail(val game: GameId, val questId: String) : AppRoute
    data class AllQuests(val game: GameId) : AppRoute
    data class Search(val game: GameId) : AppRoute
}

internal data class ResumeState(val game: String, val chapter: Int, val questId: String?)

internal fun GameId.quests(): List<Quest> = when (this) {
    GameId.GOTHIC -> GothicQuestData.quests
    GameId.GOTHIC_2_GOLD -> Gothic2QuestData.quests
    GameId.NEW_BALANCE -> NewBalanceQuestData.quests
}

internal fun GameId.chapterQuests(chapter: Int): List<Quest> = when (this) {
    GameId.GOTHIC -> GothicQuestData.chapter(chapter)
    GameId.GOTHIC_2_GOLD -> Gothic2QuestData.chapter(chapter)
    GameId.NEW_BALANCE -> NewBalanceQuestData.chapter(chapter)
}

internal fun ResumeState.toRouteOrNull(): AppRoute? {
    val gameId = GameId.fromPersistedName(game) ?: return null
    if (chapter !in 1..6) return null
    val savedQuestId = questId
    return if (savedQuestId != null && gameId.quests().any { it.id == savedQuestId }) {
        AppRoute.QuestDetail(gameId, savedQuestId)
    } else {
        AppRoute.Chapter(gameId, chapter)
    }
}
