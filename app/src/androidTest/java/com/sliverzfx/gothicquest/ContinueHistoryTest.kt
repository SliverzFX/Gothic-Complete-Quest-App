package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ContinueHistoryTest {
    @get:Rule val rule = createComposeRule()

    @Test fun eachGameKeepsOnlyItsLatestPageAndResumesTheRightTab() {
        val old = listOf(RecentVisit(GameId.GOTHIC, "chapter", chapter = 1), RecentVisit(GameId.RISEN, "hub"))
        val updated = updatedRecentVisits(old, RecentVisit(GameId.GOTHIC, "hub", tab = "codes"))
        assertEquals(2, updated.size)
        assertEquals(AppRoute.GameHub(GameId.GOTHIC, "codes"), updated.first().route())
        assertEquals(GameId.RISEN, updated.last().game)
        assertEquals(updated, RecentVisitsCodec.decode(RecentVisitsCodec.encode(updated)))
    }

    @Test fun deleteAllRequiresConfirmationAndCancelKeepsEntries() {
        var visits by mutableStateOf(listOf(RecentVisit(GameId.GOTHIC, "hub"), RecentVisit(GameId.RISEN, "hub")))
        rule.setContent {
            ContinueHistoryScreen(visits, {}, {}, {}, { games -> visits = visits.filterNot { it.game in games } })
        }
        rule.onNodeWithTag("continue_delete_all").performClick()
        rule.runOnIdle { assertEquals(2, visits.size) }
        rule.onNodeWithTag("continue_cancel_delete").performClick()
        rule.runOnIdle { assertEquals(2, visits.size) }
        rule.onNodeWithTag("continue_select_all").performClick()
        rule.onNodeWithTag("continue_delete_selected").performClick()
        rule.onNodeWithTag("continue_confirm_delete").performClick()
        rule.runOnIdle { assertTrue(visits.isEmpty()) }
    }

    @Test fun removingOneEntryKeepsOtherGames() {
        var removed = emptySet<GameId>()
        rule.setContent {
            ContinueHistoryScreen(listOf(RecentVisit(GameId.GOTHIC, "hub"), RecentVisit(GameId.RISEN, "hub")),
                {}, {}, {}, { removed = it })
        }
        rule.onNodeWithTag("continue_delete_gothic").performClick()
        rule.runOnIdle { assertEquals(setOf(GameId.GOTHIC), removed) }
    }

    @Test fun backupPreservesHistoryAndEmptyHistoryDoesNotReviveLegacyContinue() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val prefs = context.getSharedPreferences("continue_history_test", Context.MODE_PRIVATE)
        prefs.edit().clear().putString("resume_game", GameId.GOTHIC.persistedName)
            .putInt("resume_chapter", 1).putStringSet("completed", setOf("G1|G1-C1-001")).commit()
        try {
            assertEquals(1, RecentVisitsCodec.load(prefs).size)
            prefs.edit().putString(RecentVisitsKey, "[]").commit()
            assertTrue(RecentVisitsCodec.load(prefs).isEmpty())
            val history = RecentVisitsCodec.encode(listOf(RecentVisit(GameId.RISEN, "hub", tab = "items")))
            prefs.edit().putString(RecentVisitsKey, history).commit()
            val backup = QuestBackupCodec.decode(QuestBackupCodec.encode(QuestBackupCodec.capture(prefs)))
            assertEquals(history, backup.recentVisits)
            assertTrue(QuestBackupCodec.restore(prefs, backup))
            assertEquals("items", RecentVisitsCodec.load(prefs).single().tab)
            assertEquals(setOf("G1|G1-C1-001"), prefs.getStringSet("completed", emptySet()))
        } finally { prefs.edit().clear().commit() }
    }
}
