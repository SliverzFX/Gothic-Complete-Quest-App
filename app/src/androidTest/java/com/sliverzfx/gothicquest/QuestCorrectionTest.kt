package com.sliverzfx.gothicquest

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class QuestCorrectionTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun reportUsesSelectedQuestAndBackReturnsThroughOriginalSearch() {
        rule.waitUntil(10000) {
            rule.onAllNodesWithText("PICK A GAME").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithText("PICK A GAME").performClick()
        rule.onNodeWithTag("game_gothic").performClick()
        rule.onNodeWithText("SEARCH").performClick()
        rule.onNodeWithText("Quest, NPC, location, ID...").performTextInput("G1-C1-02")
        rule.onNodeWithText("Test of Faith").performClick()
        rule.onNodeWithTag("quest_report_correction").performScrollTo().performClick()
        rule.onNodeWithTag("support_correction_context").assertExists()
        rule.onNodeWithTag("support_copy_report").performScrollTo().performClick()
        val clipboard = rule.activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        rule.runOnIdle {
            val report = clipboard.primaryClip!!.getItemAt(0).text.toString()
            assertTrue(report.contains("Game / mod: Gothic"))
            assertTrue(report.contains("CHAPTER: 1"))
            assertTrue(report.contains("Quest: Test of Faith"))
            assertTrue(report.contains("Quest ID: G1-C1-02"))
        }
        rule.onNodeWithTag("support_back").performClick()
        rule.onNodeWithTag("quest_report_correction").assertExists()
        rule.onAllNodesWithText("‹  BACK TO QUESTS")[0].performScrollTo().performClick()
        rule.onNodeWithText("GOTHIC — SEARCH").assertExists()
        rule.onNodeWithText("HOME").performClick()
        rule.onNodeWithText("SUPPORT").performClick()
        rule.onNodeWithTag("support_correction_context").assertDoesNotExist()
        rule.onNodeWithTag("support_copy_report").performScrollTo().performClick()
        rule.runOnIdle {
            assertFalse(clipboard.primaryClip!!.getItemAt(0).text.toString().contains("G1-C1-02"))
        }
    }
}
