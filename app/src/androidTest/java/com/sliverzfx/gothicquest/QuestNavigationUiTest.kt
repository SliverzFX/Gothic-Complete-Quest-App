package com.sliverzfx.gothicquest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

class QuestNavigationUiTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun nextAndPreviousOpenQuestsAndBackKeepsOriginalChapter() {
        rule.waitUntil(10000) {
            rule.onAllNodesWithText("PICK A GAME").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithText("PICK A GAME").performClick()
        rule.onNodeWithTag("game_gothic").performClick()
        rule.onNodeWithTag("chapter_button_1").performClick()
        rule.onNodeWithText("Admission to the Old Camp").performClick()
        rule.onNodeWithTag("quest_previous").performScrollTo().assertIsNotEnabled()
        rule.onNodeWithTag("quest_next").performScrollTo().performClick()
        rule.onNodeWithText("G1-C1-02").assertExists()
        rule.onNodeWithTag("quest_previous").performScrollTo().performClick()
        rule.onNodeWithText("G1-C1-01").assertExists()
        rule.onAllNodesWithText("‹  BACK TO QUESTS")[0].performScrollTo().performClick()
        rule.onNodeWithText("GOTHIC — CHAPTER 1").assertExists()
    }
}
