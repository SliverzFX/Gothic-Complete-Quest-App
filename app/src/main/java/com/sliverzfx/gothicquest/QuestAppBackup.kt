package com.sliverzfx.gothicquest

import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

internal data class QuestAppBackup(
    val completed: Set<String>,
    val favorites: Set<String>,
    val textSize: String,
    val boxOpacity: Int,
    val backgroundBrightness: Int,
    val completedDisplay: String,
    val musicEnabled: Boolean,
    val keepScreenAwake: Boolean,
    val resumeGame: String?,
    val resumeChapter: Int?,
    val resumeQuest: String?,
    val reduceAnimations: Boolean = false,
    val backgroundAnimationEnabled: Boolean = true,
    val recentVisits: String? = null
)

internal object QuestBackupCodec {
    private const val FORMAT = "gothic-complete-quest-guide"
    fun capture(prefs: SharedPreferences) = QuestAppBackup(
        prefs.getStringSet("completed", emptySet())!!.toSet(),
        prefs.getStringSet("favorites", emptySet())!!.toSet(),
        prefs.getString("text_size", "NORMAL")!!,
        prefs.getInt("box_opacity_percent", 65),
        prefs.getInt("background_brightness_percent", 30),
        prefs.getString("completed_quest_display", "SHOW")!!,
        prefs.getBoolean("music_enabled", true),
        prefs.getBoolean("keep_screen_awake", false),
        prefs.getString("resume_game", null),
        if (prefs.contains("resume_chapter")) prefs.getInt("resume_chapter", 1) else null,
        prefs.getString("resume_quest", null),
        prefs.getBoolean("reduce_animations", false),
        prefs.getBoolean("background_animation_enabled", true),
        prefs.getString(RecentVisitsKey, null)
    )

    fun encode(backup: QuestAppBackup): String = JSONObject().apply {
        put("format", FORMAT)
        put("version", 1)
        put("completed", JSONArray(backup.completed.sorted()))
        put("favorites", JSONArray(backup.favorites.sorted()))
        put("settings", JSONObject().apply {
            put("text_size", backup.textSize)
            put("box_opacity_percent", backup.boxOpacity)
            put("background_brightness_percent", backup.backgroundBrightness)
            put("completed_quest_display", backup.completedDisplay)
            put("music_enabled", backup.musicEnabled)
            put("keep_screen_awake", backup.keepScreenAwake)
            put("reduce_animations", backup.reduceAnimations)
            put("background_animation_enabled", backup.backgroundAnimationEnabled)
        })
        backup.recentVisits?.let { put("recent_visits", JSONArray(it)) }
        put("resume", if (backup.resumeGame != null && backup.resumeChapter != null) JSONObject().apply {
            put("game", backup.resumeGame)
            put("chapter", backup.resumeChapter)
            put("quest", backup.resumeQuest ?: JSONObject.NULL)
        } else JSONObject.NULL)
    }.toString(2)

    fun decode(text: String): QuestAppBackup {
        require(text.length <= 2_000_000) { "Backup file is too large." }
        val root = JSONObject(text)
        require(root.get("format") == FORMAT && root.get("version") == 1) { "Unsupported backup file." }
        fun string(obj: JSONObject, key: String): String {
            val value = obj.get(key)
            require(value is String) { "Invalid backup field: $key" }
            return value
        }
        fun number(obj: JSONObject, key: String, range: IntRange): Int {
            val value = obj.get(key)
            require(value is Int && value in range) { "Invalid backup field: $key" }
            return value
        }
        fun boolean(obj: JSONObject, key: String): Boolean {
            val value = obj.get(key)
            require(value is Boolean) { "Invalid backup field: $key" }
            return value
        }
        fun keys(key: String): Set<String> {
            val array = root.getJSONArray(key)
            require(array.length() <= 20_000) { "Too many saved quests." }
            return (0 until array.length()).map { index ->
                val value = array.get(index)
                require(value is String && value.length <= 200 &&
                    value.matches(Regex("(G1|G2|NB|G3|R1|R2|R3|AR)\\|[^|\\s]+"))) { "Invalid saved quest." }
                value
            }.toSet()
        }
        val settings = root.getJSONObject("settings")
        val size = string(settings, "text_size")
        val display = string(settings, "completed_quest_display")
        require(AppTextSize.entries.any { it.name == size })
        require(CompletedQuestDisplay.entries.any { it.name == display })
        val resume = if (root.isNull("resume")) null else root.getJSONObject("resume")
        val gameName = resume?.let { string(it, "game") }
        val game = gameName?.let { GameId.fromPersistedName(it) ?: error("Unknown resume game.") }
        val chapter = resume?.let { number(it, "chapter", 1..game!!.sectionCount) }
        val quest = resume?.let {
            if (it.isNull("quest")) null else string(it, "quest").also { value ->
                require(value.length in 1..200) { "Invalid resume quest." }
            }
        }
        return QuestAppBackup(keys("completed"), keys("favorites"), size,
            number(settings, "box_opacity_percent", 20..100),
            number(settings, "background_brightness_percent", 0..80), display,
            boolean(settings, "music_enabled"), boolean(settings, "keep_screen_awake"),
            gameName, chapter, quest,
            if (settings.has("reduce_animations")) boolean(settings, "reduce_animations") else false,
            if (settings.has("background_animation_enabled")) boolean(settings, "background_animation_enabled") else true,
            if (root.has("recent_visits")) root.getJSONArray("recent_visits").toString().also {
                RecentVisitsCodec.decode(it)
            } else null)
    }

    fun restore(prefs: SharedPreferences, backup: QuestAppBackup): Boolean =
        prefs.edit()
            .putStringSet("completed", backup.completed)
            .putStringSet("favorites", backup.favorites)
            .putString("text_size", backup.textSize)
            .putInt("box_opacity_percent", backup.boxOpacity)
            .putInt("background_brightness_percent", backup.backgroundBrightness)
            .putString("completed_quest_display", backup.completedDisplay)
            .putBoolean("music_enabled", backup.musicEnabled)
            .putBoolean("keep_screen_awake", backup.keepScreenAwake)
            .putBoolean("reduce_animations", backup.reduceAnimations)
            .putBoolean("background_animation_enabled", backup.backgroundAnimationEnabled)
            .remove(RecentVisitsKey)
            .remove("resume_game").remove("resume_chapter").remove("resume_quest")
            .apply {
                backup.recentVisits?.let { putString(RecentVisitsKey, it) }
                if (backup.resumeGame != null && backup.resumeChapter != null) {
                    putString("resume_game", backup.resumeGame)
                    putInt("resume_chapter", backup.resumeChapter)
                    backup.resumeQuest?.let { putString("resume_quest", it) }
                }
            }.commit()
}

internal fun progressWithoutGame(completed: Set<String>, prefix: String): Set<String> =
    completed.filterNot { it.startsWith("$prefix|") }.toSet()
