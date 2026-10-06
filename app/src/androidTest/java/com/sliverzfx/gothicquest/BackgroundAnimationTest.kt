package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BackgroundAnimationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val previous = mutableMapOf<String, Boolean?>()

    @Before
    fun startWithVideoEnabled() {
        val prefs = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        listOf("background_animation_enabled", "reduce_animations").forEach { key ->
            previous[key] = if (prefs.contains(key)) prefs.getBoolean(key, false) else null
        }
        prefs.edit().putBoolean("background_animation_enabled", true)
            .putBoolean("reduce_animations", false).commit()
        composeRule.activityRule.scenario.recreate()
    }

    @After
    fun restorePreferences() {
        val editor = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        previous.forEach { (key, value) ->
            if (value == null) editor.remove(key) else editor.putBoolean(key, value)
        }
        editor.commit()
    }

    private fun waitForHome() {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("QUEST GUIDES").fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun openSettings() {
        composeRule.onNodeWithTag("home_settings").performClick()
        composeRule.onNodeWithTag("background_animation_toggle").performScrollTo()
    }

    private fun returnHome() {
        composeRule.onNodeWithText("‹  BACK TO MAIN MENU").performScrollTo().performClick()
        waitForHome()
    }

    @Test
    fun disabledBackgroundUsesStillAcrossRestartAndCanBeEnabledAgain() {
        waitForHome()
        composeRule.onNodeWithTag("home_background_video").assertExists()
        openSettings()
        composeRule.onNodeWithTag("background_animation_toggle").assertIsOn().performClick()
        composeRule.onNodeWithTag("background_animation_toggle").assertIsOff()
        returnHome()
        composeRule.onNodeWithTag("home_background_still").assertExists()
        composeRule.onNodeWithTag("home_background_video").assertDoesNotExist()

        composeRule.activityRule.scenario.recreate()
        waitForHome()
        composeRule.onNodeWithTag("home_background_still").assertExists()
        composeRule.onNodeWithTag("home_background_video").assertDoesNotExist()
        assertFalse(composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getBoolean("background_animation_enabled", true))

        openSettings()
        composeRule.onNodeWithTag("background_animation_toggle").assertIsOff().performClick()
        returnHome()
        composeRule.onNodeWithTag("home_background_video").assertExists()
    }
}
