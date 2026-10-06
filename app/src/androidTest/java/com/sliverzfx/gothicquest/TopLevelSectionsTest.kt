package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

class TopLevelSectionsTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 10000) {
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
    fun faqsExpandCollapseAndReturnHome() {
        waitForHome()
        composeRule.onNodeWithText("FAQs").performClick()
        composeRule.onNodeWithTag("faq_screen").assertExists()
        composeRule.onNodeWithTag("faq_answer_what_is_questbound").assertDoesNotExist()
        composeRule.onNodeWithTag("faq_question_what_is_questbound").performClick()
        composeRule.onNodeWithTag("faq_answer_what_is_questbound").assertExists()
        composeRule.onNodeWithTag("faq_question_offline").performClick()
        composeRule.onNodeWithTag("faq_answer_offline").assertExists()
        composeRule.onNodeWithTag("faq_answer_what_is_questbound").assertDoesNotExist()
        composeRule.onNodeWithTag("faq_question_offline").performClick()
        composeRule.onNodeWithTag("faq_answer_offline").assertDoesNotExist()
        composeRule.onNodeWithTag("faq_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun faqsShowCurrentGameCoverageAndLinkToSupport() {
        waitForHome()
        composeRule.onNodeWithText("FAQs").performClick()
        composeRule.onNodeWithTag("faq_list").performScrollToNode(hasTestTag("faq_question_supported_games"))
        composeRule.onNodeWithTag("faq_question_supported_games").performClick()
        composeRule.onNodeWithTag("faq_answer_supported_games").assertExists()
        composeRule.onNodeWithTag("faq_list").performScrollToNode(hasTestTag("faq_support"))
        composeRule.onNodeWithTag("faq_support").performClick()
        composeRule.onNodeWithTag("support_screen").assertExists()
        composeRule.onNodeWithTag("support_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun donationsPlaceholderReturnsHome() {
        waitForHome()
        listOf(
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

    @Test
    fun supportCopiesReportAndReturnsHome() {
        waitForHome()
        composeRule.onNodeWithText("SUPPORT").performClick()
        composeRule.onNodeWithTag("support_screen").assertExists()
        composeRule.onNodeWithText("SUPPORT / BUGS").assertExists()
        composeRule.onNodeWithTag("support_copy_report").performScrollTo().performClick()
        composeRule.onNodeWithText("Report copied. Paste it into Discord and fill in the details.").performScrollTo().assertExists()
        composeRule.onNodeWithTag("support_discord").performScrollTo().assertExists()
        composeRule.onNodeWithTag("support_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

}
