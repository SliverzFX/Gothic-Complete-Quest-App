package com.sliverzfx.gothicquest

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class QuestBackupTest {
    private fun sample() = QuestAppBackup(setOf("G1|Q1", "R2|R2-C4-001"),
        setOf("NB|CH1-001"), "LARGE", 80, 60, "HIDE", false, true,
        "Risen 2", 4, "R2-C4-001", reduceAnimations = true)

    @Test fun backupRoundTripAndRestoreIncludeAllSavedFields() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val prefs = context.getSharedPreferences("backup_test_isolated", Context.MODE_PRIVATE)
        try {
            prefs.edit().clear().putString("unrelated", "keep").commit()
            val restored = QuestBackupCodec.decode(QuestBackupCodec.encode(sample()))
            assertEquals(sample(), restored)
            assertTrue(QuestBackupCodec.restore(prefs, restored))
            assertEquals(sample(), QuestBackupCodec.capture(prefs))
            assertEquals("keep", prefs.getString("unrelated", null))
            val noResume = restored.copy(resumeGame = null, resumeChapter = null, resumeQuest = null)
            assertTrue(QuestBackupCodec.restore(prefs, noResume))
            assertFalse(prefs.contains("resume_game"))
            assertFalse(prefs.contains("resume_chapter"))
            assertFalse(prefs.contains("resume_quest"))
        } finally { prefs.edit().clear().commit() }
    }

    @Test fun invalidBackupValuesAreRejectedBeforeRestore() {
        val base = QuestBackupCodec.encode(sample())
        val invalid = listOf(
            JSONObject(base).put("version", 2).toString(),
            JSONObject(base).put("format", "other-app").toString(),
            JSONObject(base).apply { getJSONObject("settings").put("background_brightness_percent", 81) }.toString(),
            JSONObject(base).apply { getJSONObject("settings").put("box_opacity_percent", 19) }.toString(),
            JSONObject(base).apply { getJSONObject("settings").put("keep_screen_awake", "true") }.toString(),
            JSONObject(base).apply { getJSONObject("settings").put("text_size", "HUGE") }.toString(),
            JSONObject(base).put("completed", org.json.JSONArray(listOf("unknown|quest"))).toString(),
            "not JSON"
        )
        invalid.forEach { text ->
            var rejected = false
            try { QuestBackupCodec.decode(text) } catch (_: Exception) { rejected = true }
            assertTrue(rejected)
        }
    }

    @Test fun olderBackupsDefaultToNormalAnimations() {
        val old = JSONObject(QuestBackupCodec.encode(sample()))
        old.getJSONObject("settings").remove("reduce_animations")
        assertFalse(QuestBackupCodec.decode(old.toString()).reduceAnimations)
        val invalid = JSONObject(QuestBackupCodec.encode(sample()))
        invalid.getJSONObject("settings").put("reduce_animations", "yes")
        var rejected = false
        try { QuestBackupCodec.decode(invalid.toString()) } catch (_: Exception) { rejected = true }
        assertTrue(rejected)
    }

    @Test fun resetOnlyRemovesSelectedGameCompletionMarks() {
        val marks = setOf("G1|Q1", "G2|Q1", "NB|Q1", "G3|Q1", "R1|Q1", "R2|Q1")
        val result = progressWithoutGame(marks, "R1")
        assertEquals(marks - "R1|Q1", result)
        assertTrue("R2|Q1" in result)
        assertEquals(marks, progressWithoutGame(marks, "missing"))
    }
    @Test fun archolosProgressRoundTripsAndResetsIndependently() {
        val quest = GameId.ARCHOLOS.chapterQuests(6).last()
        val key = "AR|${quest.id}"
        val backup = sample().copy(
            completed = sample().completed + key,
            favorites = sample().favorites + key,
            resumeGame = GameId.ARCHOLOS.persistedName,
            resumeChapter = 6,
            resumeQuest = quest.id
        )
        assertEquals(backup, QuestBackupCodec.decode(QuestBackupCodec.encode(backup)))
        assertEquals(sample().completed, progressWithoutGame(backup.completed, "AR"))
    }
}
