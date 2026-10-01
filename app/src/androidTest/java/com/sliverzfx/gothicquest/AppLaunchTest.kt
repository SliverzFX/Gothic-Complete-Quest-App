package com.sliverzfx.gothicquest

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class AppLaunchTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun splashRootExistsAfterLaunch() {
        composeRule.onNodeWithTag("splash_screen").assertExists()
    }
}
