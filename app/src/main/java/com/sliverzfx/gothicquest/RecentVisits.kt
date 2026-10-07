package com.sliverzfx.gothicquest

import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

internal const val RecentVisitsKey = "recent_game_visits"

internal data class RecentVisit(val game: GameId, val kind: String,
    val chapter: Int? = null, val questId: String? = null, val tab: String = "quests") {
    fun route(): AppRoute = when (kind) {
        "chapter" -> AppRoute.Chapter(game, chapter!!)
        "quest" -> if (game.quests().any { it.id == questId }) AppRoute.QuestDetail(game, questId!!)
            else AppRoute.Chapter(game, chapter!!)
        "all" -> AppRoute.AllQuests(game)
        "search" -> AppRoute.Search(game)
        else -> AppRoute.GameHub(game, tab)
    }
    fun description(): String = when (kind) {
        "chapter" -> "${game.sectionLabel.lowercase().replaceFirstChar { it.uppercase() }} $chapter"
        "quest" -> game.quests().firstOrNull { it.id == questId }?.title ?: "Quest"
        "all" -> "All quests"
        "search" -> "Quest search"
        else -> when (tab) {
            "codes" -> if (game in listOf(GameId.GOTHIC_3, GameId.RISEN, GameId.RISEN_2, GameId.RISEN_3)) "Console codes" else "Marvin codes"
            "tips" -> "Useful tips"
            "items" -> "Items"
            else -> "${game.sectionLabel.lowercase().replaceFirstChar { it.uppercase() }}s"
        }
    }
}

internal fun recentVisitFromRoute(route: AppRoute): RecentVisit? = when (route) {
    is AppRoute.GameHub -> RecentVisit(route.game, "hub", tab = route.tab)
    is AppRoute.Chapter -> RecentVisit(route.game, "chapter", chapter = route.chapter)
    is AppRoute.QuestDetail -> route.game.quests().firstOrNull { it.id == route.questId }?.let {
        RecentVisit(route.game, "quest", chapter = it.chapter, questId = it.id)
    }
    is AppRoute.AllQuests -> RecentVisit(route.game, "all")
    is AppRoute.Search -> RecentVisit(route.game, "search")
    else -> null
}

internal fun updatedRecentVisits(visits: List<RecentVisit>, visit: RecentVisit): List<RecentVisit> =
    listOf(visit) + visits.filterNot { it.game == visit.game }

internal object RecentVisitsCodec {
    fun encode(visits: List<RecentVisit>): String = JSONArray().apply {
        visits.forEach { visit -> put(JSONObject().apply {
            put("game", visit.game.persistedName); put("kind", visit.kind)
            put("chapter", visit.chapter ?: JSONObject.NULL)
            put("quest", visit.questId ?: JSONObject.NULL); put("tab", visit.tab)
        }) }
    }.toString()

    fun decode(text: String): List<RecentVisit> {
        require(text.length <= 20_000) { "Continue history is too large." }
        val array = JSONArray(text)
        require(array.length() <= GameId.entries.size) { "Too many Continue entries." }
        val visits = (0 until array.length()).map { index ->
            val obj = array.getJSONObject(index)
            require(obj.get("game") is String && obj.get("kind") is String && obj.get("tab") is String)
            require(obj.isNull("chapter") || obj.get("chapter") is Int)
            require(obj.isNull("quest") || obj.get("quest") is String)
            val game = GameId.fromPersistedName(obj.getString("game")) ?: error("Unknown game in Continue history.")
            val kind = obj.getString("kind")
            require(kind in setOf("hub", "chapter", "quest", "all", "search"))
            val tab = obj.getString("tab"); require(tab in setOf("quests", "codes", "tips", "items"))
            val chapter = if (obj.isNull("chapter")) null else obj.getInt("chapter")
            val quest = if (obj.isNull("quest")) null else obj.getString("quest")
            if (kind == "chapter" || kind == "quest") require(chapter != null && chapter in 1..game.sectionCount)
            if (kind == "quest") require(quest != null && quest.length in 1..200)
            RecentVisit(game, kind, chapter, quest, tab)
        }
        require(visits.map { it.game }.distinct().size == visits.size) { "Duplicate games in Continue history." }
        return visits
    }

    fun load(prefs: SharedPreferences): List<RecentVisit> {
        if (prefs.contains(RecentVisitsKey)) return runCatching {
            decode(prefs.getString(RecentVisitsKey, "[]")!!)
        }.getOrDefault(emptyList())
        // Import the old single Continue target once. An explicitly saved empty list stays empty.
        val game = prefs.getString("resume_game", null) ?: return emptyList()
        val target = routeFromResume(game, prefs.getInt("resume_chapter", -1), prefs.getString("resume_quest", null))
        return target?.let(::recentVisitFromRoute)?.let { listOf(it) }.orEmpty()
    }
}
