package com.sliverzfx.gothicquest

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.After
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedMenuBackgroundTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private val previous = mutableMapOf<String, Boolean?>()

    @Before fun enableVideo() {
        val prefs = composeRule.activity.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE)
        listOf("background_animation_enabled", "reduce_animations").forEach { key ->
            previous[key] = if (prefs.contains(key)) prefs.getBoolean(key, false) else null
        }
        prefs.edit().putBoolean("background_animation_enabled", true)
            .putBoolean("reduce_animations", false).commit()
        composeRule.activityRule.scenario.recreate()
        waitForHome()
    }

    @After fun restorePreferences() {
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

    private fun findVideo(view: View): HomeVideoView? {
        if (view is HomeVideoView) return view
        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                findVideo(view.getChildAt(index))?.let { return it }
            }
        }
        return null
    }

    private fun currentVideo(): HomeVideoView = composeRule.runOnIdle {
        checkNotNull(findVideo(composeRule.activity.findViewById(android.R.id.content)))
    }

    private val menus = listOf(
        Triple("home_settings", "‹  BACK TO MAIN MENU", null),
        Triple("home_donations", null, "donations_home"),
        Triple("home_support", null, "support_home"),
        Triple("home_about", null, "about_home"),
        Triple("home_faqs", null, "faq_home")
    )

    private fun returnHome(backText: String?, backTag: String?) {
        if (backTag != null) composeRule.onNodeWithTag(backTag).performClick()
        else composeRule.onNodeWithText(checkNotNull(backText)).performScrollTo().performClick()
        waitForHome()
    }

    @Test fun navigatingAppMenusKeepsTheSameVideoSurface() {
        val initialVideo = currentVideo()
        menus.forEach { (menuTag, backText, backTag) ->
            composeRule.onNodeWithTag(menuTag).performClick()
            composeRule.onNodeWithTag("menu_background_dimmer").assertExists()
            composeRule.onNodeWithTag("home_background_video").assertExists()
            assertSame(initialVideo, currentVideo())
            returnHome(backText, backTag)
            assertSame(initialVideo, currentVideo())
        }
    }

    @Test fun stillFallbackWorksInEveryMenuAndVideoCanStartInsideSettings() {
        composeRule.onNodeWithTag("home_settings").performClick()
        composeRule.onNodeWithTag("background_animation_toggle").performScrollTo().performClick()
        composeRule.onNodeWithTag("background_animation_toggle").assertIsOff()
        composeRule.onNodeWithTag("home_background_video").assertDoesNotExist()
        returnHome("‹  BACK TO MAIN MENU", null)
        menus.forEach { (menuTag, backText, backTag) ->
            composeRule.onNodeWithTag(menuTag).performClick()
            composeRule.onNodeWithTag("home_background_still").assertExists()
            composeRule.onNodeWithTag("home_background_video").assertDoesNotExist()
            returnHome(backText, backTag)
        }
        composeRule.onNodeWithTag("home_settings").performClick()
        composeRule.onNodeWithTag("background_animation_toggle").performScrollTo().performClick()
        composeRule.onNodeWithTag("home_background_video").assertExists()
        currentVideo()
    }
}
