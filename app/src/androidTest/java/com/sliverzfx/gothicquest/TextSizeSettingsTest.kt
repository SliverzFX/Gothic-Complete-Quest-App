package com.sliverzfx.gothicquest

import android.content.Context
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TextSizeSettingsTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private var previousSize: String? = null

    @Before
    fun rememberPreference() {
        previousSize = composeRule.activity
            .getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
            .getString("text_size", null)
    }

    @After
    fun restorePreference() {
        val editor = composeRule.activity
            .getSharedPreferences("quest_prefs", Context.MODE_PRIVATE).edit()
        previousSize?.let { editor.putString("text_size", it) }
            ?: editor.remove("text_size")
        editor.commit()
    }

    private fun openSettings() {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("PICK A GAME").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithTag("home_settings").performClick()
        composeRule.waitForIdle()
    }

    @Test
    fun sizesChangeTextImmediatelyAndPersistAcrossActivityRecreation() {
        openSettings()
        composeRule.onNodeWithTag("text_size_normal").performClick()
        composeRule.onNodeWithTag("text_size_normal").assertIsSelected()
        val normalHeight = composeRule.onNodeWithTag("text_size_preview")
            .getUnclippedBoundsInRoot().height

        composeRule.onNodeWithTag("text_size_small").performClick()
        composeRule.onNodeWithTag("text_size_small").assertIsSelected()
        val smallHeight = composeRule.onNodeWithTag("text_size_preview")
            .getUnclippedBoundsInRoot().height

        composeRule.onNodeWithTag("text_size_large").performClick()
        composeRule.onNodeWithTag("text_size_large").assertIsSelected()
        val largeHeight = composeRule.onNodeWithTag("text_size_preview")
            .getUnclippedBoundsInRoot().height
        assertTrue(smallHeight < normalHeight)
        assertTrue(normalHeight < largeHeight)

        composeRule.activityRule.scenario.recreate()
        openSettings()
        composeRule.onNodeWithTag("text_size_large").assertIsSelected()
        composeRule.onNodeWithTag("text_size_normal").performClick()
        composeRule.onNodeWithTag("text_size_normal").assertIsSelected()
    }
}
