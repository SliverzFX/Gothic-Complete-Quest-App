package com.sliverzfx.gothicquest

enum class GameId(val persistedName: String, val displayTitle: String) {
    GOTHIC("Gothic", "GOTHIC"),
    GOTHIC_2_GOLD("Gothic II Gold Edition", "GOTHIC II"),
    NEW_BALANCE("Gothic II New Balance", "NEW BALANCE"),
    GOTHIC_3("Gothic 3", "GOTHIC 3"),
    RISEN("Risen", "RISEN"),
    RISEN_2("Risen 2", "RISEN II");

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
    data class GamePreview(val title: String) : AppRoute
    data class GameHub(val game: GameId) : AppRoute
    data class Chapter(val game: GameId, val chapter: Int) : AppRoute
    data class QuestDetail(val game: GameId, val questId: String) : AppRoute
    data class AllQuests(val game: GameId) : AppRoute
    data class Search(val game: GameId) : AppRoute
}

internal fun GameId.quests(): List<Quest> = when (this) {
    GameId.GOTHIC -> GothicQuestData.quests
    GameId.GOTHIC_2_GOLD -> Gothic2QuestData.quests
    GameId.NEW_BALANCE -> NewBalanceQuestData.quests
    GameId.GOTHIC_3 -> Gothic3QuestData.quests
    GameId.RISEN -> RisenQuestData.quests
    GameId.RISEN_2 -> emptyList()
}

internal fun GameId.chapterQuests(chapter: Int): List<Quest> = when (this) {
    GameId.GOTHIC -> GothicQuestData.chapter(chapter)
    GameId.GOTHIC_2_GOLD -> Gothic2QuestData.chapter(chapter)
    GameId.NEW_BALANCE -> NewBalanceQuestData.chapter(chapter)
    GameId.GOTHIC_3 -> Gothic3QuestData.part(chapter)
    GameId.RISEN -> RisenQuestData.chapter(chapter)
    GameId.RISEN_2 -> emptyList()
}

internal val GameId.sectionCount: Int
    get() = when (this) {
        GameId.GOTHIC_3 -> 7
        GameId.RISEN, GameId.RISEN_2 -> 4
        else -> 6
    }

internal val GameId.sectionLabel: String
    get() = if (this == GameId.GOTHIC_3) "PART" else "CHAPTER"

internal fun routeFromResume(game: String, chapter: Int, questId: String?): AppRoute? {
    val gameId = GameId.fromPersistedName(game) ?: return null
    if (chapter !in 1..gameId.sectionCount) return null
    return if (questId != null && gameId.quests().any { it.id == questId }) {
        AppRoute.QuestDetail(gameId, questId)
    } else {
        AppRoute.Chapter(gameId, chapter)
    }
}


internal val GameId.usesStoneTheme: Boolean
    get() = this == GameId.GOTHIC


internal val GameId.usesBloodTheme: Boolean
    get() = this == GameId.NEW_BALANCE
