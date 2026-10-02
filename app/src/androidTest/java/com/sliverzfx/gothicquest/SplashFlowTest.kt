package com.sliverzfx.gothicquest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class SplashFlowTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun splashIsShownAtStartup() {
        composeRule.onNodeWithTag("splash_screen").assertExists()
    }

    @Test
    fun homeAppearsAfterSplash() {
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("home_screen").assertExists()
    }
}
