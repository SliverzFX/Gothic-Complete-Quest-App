package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class SplashFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeAppearsWithQuestboundBrandingAfterIntro() {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("home_screen").assertExists()
        composeRule.onNodeWithText("QUESTBOUND").assertExists()
        composeRule.onNodeWithText("RPG Quest Guides").assertExists()
        composeRule.onNodeWithText("by SliverzFX").assertExists()
        composeRule.onNodeWithTag("splash_screen").assertDoesNotExist()
    }
}
