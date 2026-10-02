package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class TopLevelSectionsTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun cheatsOpensEmptyLibraryWithoutInventedEntries() {
        waitForHome()
        composeRule.onNodeWithText("MARVIN").performClick()
        composeRule.onNodeWithTag("game_library_screen").assertExists()
        composeRule.onNodeWithText("MARVIN CODES / CHEATS").assertExists()
        composeRule.onNodeWithTag("game_gothic").assertDoesNotExist()
        composeRule.onNodeWithTag("library_back").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun faqPlaceholderReturnsHome() {
        waitForHome()
        composeRule.onNodeWithText("FAQs").performClick()
        composeRule.onNodeWithTag("section_placeholder").assertExists()
        composeRule.onNodeWithText("FAQs").assertExists()
        composeRule.onNodeWithTag("section_back").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun aboutSupportAndDonationsHaveRealRoutes() {
        waitForHome()
        listOf(
            "ABOUT" to "INFO / ABOUT",
            "SUPPORT" to "SUPPORT / BUGS",
            "DONATIONS" to "DONATIONS"
        ).forEach { (menuLabel, sectionTitle) ->
            composeRule.onNodeWithText(menuLabel).performClick()
            composeRule.onNodeWithTag("section_placeholder").assertExists()
            composeRule.onNodeWithText(sectionTitle).assertExists()
            composeRule.onNodeWithTag("section_back").performClick()
        }
    }
}
