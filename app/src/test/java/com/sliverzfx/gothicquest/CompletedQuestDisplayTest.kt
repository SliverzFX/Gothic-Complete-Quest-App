package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CompletedQuestDisplayTest {
    @Test fun defaultsToShowForMissingOrUnknownPreference() {
        assertEquals(CompletedQuestDisplay.SHOW, CompletedQuestDisplay.fromStoredValue(null))
        assertEquals(CompletedQuestDisplay.SHOW, CompletedQuestDisplay.fromStoredValue("invalid"))
        CompletedQuestDisplay.entries.forEach {
            assertEquals(it, CompletedQuestDisplay.fromStoredValue(it.name))
        }
    }

    @Test fun onlyHideRemovesCompletedQuests() {
        CompletedQuestDisplay.entries.forEach {
            assertTrue(it.isVisible(false))
        }
        assertTrue(CompletedQuestDisplay.SHOW.isVisible(true))
        assertTrue(CompletedQuestDisplay.DIM.isVisible(true))
        assertFalse(CompletedQuestDisplay.HIDE.isVisible(true))
    }

    @Test fun dimOnlyFadesCompletedQuests() {
        assertEquals(0.5f, CompletedQuestDisplay.DIM.opacity(true), 0f)
        assertEquals(1f, CompletedQuestDisplay.DIM.opacity(false), 0f)
        assertEquals(1f, CompletedQuestDisplay.SHOW.opacity(true), 0f)
    }
}
