package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test

class QuestGuidesLibraryTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 10000) {
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
        composeRule.onNodeWithTag("library_game_list").performScrollToNode(hasTestTag("game_gothic_3"))
        composeRule.onNodeWithTag("game_gothic_3").assertExists()
        composeRule.onNodeWithTag("library_back").assertExists()
        composeRule.onNodeWithText("FAVORITES").assertExists()
    }

    @Test
    fun gothic3UsesPartsInsteadOfChapters() {
        waitForHome()
        composeRule.onNodeWithText("QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("library_game_list").performScrollToNode(hasTestTag("game_gothic_3"))
        composeRule.onNodeWithTag("game_gothic_3").performClick()
        composeRule.onNodeWithText("PART 1").assertExists()
        composeRule.onNodeWithText("PART 7").assertExists()
    }

    @Test
    fun gameHubBackReturnsToQuestGuidesLibrary() {
        waitForHome()
        composeRule.onNodeWithText("QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("game_gothic").performClick()
        composeRule.onNodeWithText("‹  BACK TO QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("game_library_screen").assertExists()
    }
    @Test
    fun gameSearchFiltersGamesAndCanBeCleared() {
        waitForHome()
        composeRule.onNodeWithText("QUEST GUIDES").performClick()
        composeRule.onNodeWithTag("library_header_divider").assertExists()
        composeRule.onNodeWithTag("library_search_toggle").performClick()
        composeRule.onNodeWithTag("library_game_search").performTextInput("Risen")
        composeRule.onNodeWithTag("game_risen_1").assertExists()
        composeRule.onNodeWithTag("game_gothic").assertDoesNotExist()
        composeRule.onNodeWithTag("library_game_search").performTextReplacement("no matching game")
        composeRule.onNodeWithText("No games found.").assertExists()
        composeRule.onNodeWithTag("library_clear_search").performClick()
        composeRule.onNodeWithTag("game_gothic").assertExists()
        composeRule.onNodeWithTag("library_search_toggle").performClick()
        composeRule.onNodeWithTag("library_game_search").assertDoesNotExist()
        composeRule.onNodeWithTag("library_top_right_action").assertExists()
    }

}
