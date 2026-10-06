package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class HomeMenuTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun homeShowsTopLevelCategoriesInsteadOfGameCards() {
        waitForHome()
        listOf(
            "QUEST GUIDES",
            "MARVIN",
            "FAQs",
            "ABOUT",
            "SUPPORT",
            "DONATIONS",
            "SETTINGS"
        ).forEach { composeRule.onNodeWithText(it).assertExists() }

        composeRule.onNodeWithText("MARVIN CODES / CHEATS").assertDoesNotExist()
        composeRule.onNodeWithText("INFO / ABOUT").assertDoesNotExist()
        composeRule.onNodeWithText("SUPPORT / BUGS").assertDoesNotExist()

        composeRule.onNodeWithTag("game_gothic").assertDoesNotExist()
        composeRule.onNodeWithTag("game_gothic_2").assertDoesNotExist()
        composeRule.onNodeWithTag("game_new_balance").assertDoesNotExist()
    }

    @Test
    fun homeUsesConfiguredBackground() {
        waitForHome()
        val prefs = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        composeRule.onNodeWithTag("home_background_still").assertExists()
        if (prefs.getBoolean("background_animation_enabled", true) && !prefs.getBoolean("reduce_animations", false)) {
            composeRule.onNodeWithTag("home_background_video").assertExists()
        } else {
            composeRule.onNodeWithTag("home_background_video").assertDoesNotExist()
        }
    }

    @Test
    fun homeUsesVerticalSmokeBehindMenu() {
        waitForHome()
        composeRule.onNodeWithTag("home_menu_smoke").assertExists()
    }
}
