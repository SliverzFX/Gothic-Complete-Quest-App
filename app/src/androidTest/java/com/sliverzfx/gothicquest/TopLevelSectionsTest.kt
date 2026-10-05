package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
    fun supportAndDonationsHaveRealRoutes() {
        waitForHome()
        listOf(
            "SUPPORT" to "SUPPORT / BUGS",
            "DONATIONS" to "DONATIONS"
        ).forEach { (menuLabel, sectionTitle) ->
            composeRule.onNodeWithText(menuLabel).performClick()
            composeRule.onNodeWithTag("section_placeholder").assertExists()
            composeRule.onNodeWithText(sectionTitle).assertExists()
            composeRule.onNodeWithTag("section_back").performClick()
        }
    }

    @Test
    fun aboutShowsCreatorAndCommunityLinksAndReturnsHome() {
        waitForHome()
        composeRule.onNodeWithText("ABOUT").performClick()
        composeRule.onNodeWithTag("about_screen").assertExists()
        composeRule.onNodeWithText("INFO / ABOUT").assertExists()
        composeRule.onNodeWithText("Created by SliverZFX").performScrollTo().assertExists()
        composeRule.onNodeWithTag("about_youtube").performScrollTo().assertExists()
        composeRule.onNodeWithTag("about_discord").performScrollTo().assertExists()
        composeRule.onNodeWithTag("about_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

}
