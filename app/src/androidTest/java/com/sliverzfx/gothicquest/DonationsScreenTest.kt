package com.sliverzfx.gothicquest

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DonationsScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun supportButtonOpensProvidedWiseLink() {
        var openedUrl: String? = null
        val handler = object : UriHandler {
            override fun openUri(uri: String) { openedUrl = uri }
        }
        composeRule.setContent {
            CompositionLocalProvider(LocalUriHandler provides handler) { DonationsScreen(onBack = {}) }
        }
        composeRule.onNodeWithTag("donations_wise").performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals("https://wise.com/pay/me/mihag25", openedUrl) }
    }

    @Test fun unavailableBrowserShowsUsefulFallback() {
        val handler = object : UriHandler {
            override fun openUri(uri: String) { throw IllegalArgumentException("No browser") }
        }
        composeRule.setContent {
            CompositionLocalProvider(LocalUriHandler provides handler) { DonationsScreen(onBack = {}) }
        }
        composeRule.onNodeWithTag("donations_wise").performScrollTo().performClick()
        composeRule.onNodeWithText("Could not open Wise. Open wise.com/pay/me/mihag25 in your browser.")
            .performScrollTo().assertExists()
    }
}
