package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
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
    fun gameToolsKeepsCategoryAndGameSelectionWhenGoingBack() {
        waitForHome()
        composeRule.onNodeWithText("GAME TOOLS").performClick()
        composeRule.onNodeWithTag("tools_category_marvin_codes").performClick()
        composeRule.onNodeWithTag("game_gothic").performClick()
        composeRule.onNodeWithText("MARVIN CODES").assertExists()
        composeRule.onNodeWithText("Gothic").assertExists()
        composeRule.onNodeWithTag("tools_back").performClick()
        composeRule.onNodeWithTag("game_library_screen").assertExists()
        composeRule.onNodeWithTag("library_back").performClick()
        composeRule.onNodeWithTag("game_tools_screen").assertExists()
        composeRule.onNodeWithTag("tools_category_items").performClick()
        composeRule.onNodeWithTag("game_gothic_2").performClick()
        composeRule.onNodeWithText("ITEMS").assertExists()
        composeRule.onNodeWithText("Gothic II Gold Edition").assertExists()
        composeRule.onNodeWithTag("tools_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun toolGameSearchAndDirectHomeWork() {
        waitForHome()
        composeRule.onNodeWithText("GAME TOOLS").performClick()
        composeRule.onNodeWithTag("tools_category_marvin_codes").performClick()
        composeRule.onNodeWithTag("library_search_toggle").performClick()
        composeRule.onNodeWithTag("library_game_search").performTextInput("Risen 3")
        composeRule.onNodeWithTag("game_gothic").assertDoesNotExist()
        composeRule.onNodeWithTag("game_risen_3").performClick()
        composeRule.onNodeWithText("CONSOLE CODES").assertExists()
        composeRule.onNodeWithText("Risen 3").assertExists()
        composeRule.onNodeWithTag("tools_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
    }

    @Test
    fun gothicTipsDoNotMixInCheatsAndCodeSearchFindsCommands() {
        waitForHome()
        composeRule.onNodeWithText("GAME TOOLS").performClick()
        composeRule.onNodeWithTag("tools_category_useful_tips").performClick()
        composeRule.onNodeWithTag("game_gothic").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("learning")
        composeRule.onNodeWithTag("gothic_reference_training").assertExists()
        composeRule.onNodeWithTag("tool_copy_enable").assertDoesNotExist()
        composeRule.onNodeWithTag("tools_home").performClick()
        composeRule.onNodeWithText("GAME TOOLS").performClick()
        composeRule.onNodeWithTag("tools_category_marvin_codes").performClick()
        composeRule.onNodeWithTag("game_gothic").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("itarscrolllight")
        composeRule.onNodeWithTag("gothic_reference_light_insert").assertExists()
        composeRule.onNodeWithTag("tool_copy_light_insert").performScrollTo().performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
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
    fun donationsShowsWiseSupportAndReturnsHome() {
        waitForHome()
        composeRule.onNodeWithText("DONATIONS").performClick()
        composeRule.onNodeWithTag("donations_screen").assertExists()
        composeRule.onNodeWithText("Support Questbound").assertExists()
        composeRule.onNodeWithText("@mihag25").assertExists()
        composeRule.onNodeWithTag("donations_wise").performScrollTo().assertExists()
        composeRule.onNodeWithTag("donations_home").performClick()
        composeRule.onNodeWithTag("home_screen").assertExists()
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
