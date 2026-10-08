package com.sliverzfx.gothicquest

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class QuestJournalStorageTest {
    @Test fun journalAndScrollRoundTripAndOldBackupClearsReplacedJournal() {
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("journal_storage_test", Context.MODE_PRIVATE)
        try {
            prefs.edit().clear().putString("quest_note:G1|G1-C1-01", "Return to Diego")
                .putInt("quest_scroll:G1|G1-C1-01", 420)
                .putStringSet("in_progress", setOf("G1|G1-C1-01")).commit()
            val saved = QuestBackupCodec.capture(prefs)
            val restored = QuestBackupCodec.decode(QuestBackupCodec.encode(saved))
            assertEquals(saved, restored)
            assertEquals("Return to Diego", restored.notes["G1|G1-C1-01"])
            assertEquals(420, restored.readingPositions["G1|G1-C1-01"])
            assertTrue(QuestBackupCodec.restore(prefs, restored))
            assertEquals(saved, QuestBackupCodec.capture(prefs))
            val old = JSONObject(QuestBackupCodec.encode(saved)).apply {
                remove("notes"); remove("reading_positions"); remove("in_progress")
            }
            val older = QuestBackupCodec.decode(old.toString())
            assertTrue(older.notes.isEmpty())
            assertTrue(older.readingPositions.isEmpty())
            assertTrue(QuestBackupCodec.restore(prefs, older))
            assertFalse(prefs.contains("quest_note:G1|G1-C1-01"))
            assertFalse(prefs.contains("quest_scroll:G1|G1-C1-01"))
        } finally { prefs.edit().clear().commit() }
    }
    @Test fun invalidJournalFieldsAreRejectedBeforeRestore() {
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("journal_validation_test", Context.MODE_PRIVATE)
        val base = QuestBackupCodec.encode(QuestBackupCodec.capture(prefs))
        val invalid = listOf(
            JSONObject(base).put("notes", JSONObject().put("bad-key", "note")),
            JSONObject(base).put("notes", JSONObject().put("G1|q", 7)),
            JSONObject(base).put("notes", JSONObject().put("G1|q", "x".repeat(4001))),
            JSONObject(base).put("reading_positions", JSONObject().put("G1|q", -1)),
            JSONObject(base).put("reading_positions", JSONObject().put("G1|q", "42")))
        invalid.forEach { root ->
            assertTrue(runCatching { QuestBackupCodec.decode(root.toString()) }.isFailure)
        }
    }
}
