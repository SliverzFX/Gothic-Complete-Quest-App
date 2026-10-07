package com.sliverzfx.gothicquest

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class KeepScreenAwakeTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private var previousValue: Boolean? = null

    @Before
    fun rememberPreference() {
        val prefs = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        previousValue = if (prefs.contains("keep_screen_awake")) prefs.getBoolean("keep_screen_awake", false) else null
    }

    @After
    fun restorePreference() {
        val editor = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        previousValue?.let { editor.putBoolean("keep_screen_awake", it) }
            ?: editor.remove("keep_screen_awake")
        editor.commit()
    }

    private fun openSettings() {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("PICK A GAME").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("home_settings").performClick()
        composeRule.onNodeWithTag("keep_screen_awake_toggle").performScrollTo()
    }

    private fun keepsScreenOn(view: View): Boolean =
        view.keepScreenOn || (view is ViewGroup &&
            (0 until view.childCount).any { keepsScreenOn(view.getChildAt(it)) })

    private fun assertScreenAwake(expected: Boolean) {
        composeRule.runOnIdle {
            assertEquals(expected, keepsScreenOn(composeRule.activity.findViewById(android.R.id.content)))
        }
    }

    @Test
    fun toggleControlsScreenAndSurvivesRecreation() {
        openSettings()
        if (previousValue == true) composeRule.onNodeWithTag("keep_screen_awake_toggle").performClick()
        composeRule.onNodeWithTag("keep_screen_awake_toggle").assertIsOff()
        assertScreenAwake(false)

        composeRule.onNodeWithTag("keep_screen_awake_toggle").performClick()
        composeRule.onNodeWithTag("keep_screen_awake_toggle").assertIsOn()
        assertScreenAwake(true)

        composeRule.activityRule.scenario.recreate()
        openSettings()
        composeRule.onNodeWithTag("keep_screen_awake_toggle").assertIsOn()
        assertScreenAwake(true)

        composeRule.onNodeWithTag("keep_screen_awake_toggle").performClick()
        composeRule.onNodeWithTag("keep_screen_awake_toggle").assertIsOff()
        assertScreenAwake(false)
    }
}
