package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class QuestJournalUiTest {
    @get:Rule val rule = createComposeRule()

    @Test fun notesAutosaveAndQuestSwitchKeepsSeparateDrafts() {
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        val a = "G1|journal-test-a"
        val b = "G2|journal-test-b"
        var key by mutableStateOf(a)
        try {
            rule.setContent { Column { QuestNotesSection(key) } }
            rule.onNodeWithTag("quest_personal_notes").performTextInput("Return to Diego")
            rule.runOnIdle {
                assertEquals("Return to Diego", prefs.getString("quest_note:$a", null))
                key = b
            }
            rule.onNodeWithTag("quest_personal_notes").performTextInput("Learn lockpicking")
            rule.runOnIdle {
                assertEquals("Learn lockpicking", prefs.getString("quest_note:$b", null))
                key = a
            }
            rule.onNodeWithTag("quest_personal_notes").assertTextEquals("Return to Diego")
        } finally { prefs.edit().remove("quest_note:$a").remove("quest_note:$b").commit() }
    }

    @Test fun readingPositionRestoresAfterScreenIsDisposedAndReopened() {
        val prefs = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        val key = "G1|reading-test"
        var shown by mutableStateOf(true)
        lateinit var scroll: ScrollState
        try {
            prefs.edit().remove("quest_scroll:$key").commit()
            rule.setContent {
                if (shown) {
                    scroll = rememberQuestReadingScroll(key)
                    Column(Modifier.verticalScroll(scroll)) { repeat(150) { Text("Guide line $it") } }
                }
            }
            rule.runOnIdle { runBlocking { scroll.scrollTo(420) }; shown = false }
            rule.waitForIdle()
            rule.runOnIdle {
                assertEquals(420, prefs.getInt("quest_scroll:$key", -1))
                shown = true
            }
            rule.waitForIdle()
            rule.runOnIdle { assertEquals(420, scroll.value); shown = false }
            rule.waitForIdle()
        } finally { prefs.edit().remove("quest_scroll:$key").commit() }
    }
}
