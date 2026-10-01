package com.sliverzfx.gothicquest

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class QuestGuidesLibraryTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun questGuidesOpensReusableGameLibrary() {
        waitForHome()
        composeRule.onNodeWithText("QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("game_library_screen").assertExists()
        composeRule.onNodeWithTag("game_gothic").assertExists()
        composeRule.onNodeWithTag("game_gothic_2").assertExists()
        composeRule.onNodeWithTag("game_new_balance").assertExists()
        composeRule.onNodeWithTag("library_back").assertExists()
        composeRule.onNodeWithText("FAVORITES").assertExists()
    }

    @Test
    fun gameHubBackReturnsToQuestGuidesLibrary() {
        waitForHome()
        composeRule.onNodeWithText("QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("game_gothic").performClick()
        composeRule.onNodeWithText("‹  BACK TO QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("game_library_screen").assertExists()
    }
}
