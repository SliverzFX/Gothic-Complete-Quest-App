package com.sliverzfx.gothicquest

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class Risen2ReferenceScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun risen2RouteShowsOfflineEquipmentDetailsAndCopiesTheCorrectCommand() {
        composeRule.setContent {
            MaterialTheme {
                ToolReferenceScreen(ToolSection.MARVIN_CODES, GameId.RISEN_2, {}, {}, embedded = true)
            }
        }
        composeRule.onNodeWithText("Reference being prepared").assertDoesNotExist()
        composeRule.onNodeWithText("Risen 2 • PC / pommes • item source 1.0.1210.0").assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("It_Pants_White")
        composeRule.onNodeWithTag("gothic_reference_r2_item_it_pants_white").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("Bladeproof: 3", substring = true)
        composeRule.onNodeWithText("OPEN REFERENCE 1").assertDoesNotExist()
        composeRule.onNodeWithTag("tool_detail_copy").performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
        composeRule.onNodeWithTag("tool_detail_close").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
    }

    @Test fun normalTipsUseTheRisenRouteAndCanBeSearched() {
        composeRule.setContent {
            MaterialTheme {
                ToolReferenceScreen(ToolSection.USEFUL_TIPS, GameId.RISEN_2, {}, {}, embedded = true)
            }
        }
        composeRule.onNodeWithText("Risen 2 • normal gameplay").assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("distill")
        composeRule.onNodeWithTag("gothic_reference_r2_tip_distill").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("distill", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").assertDoesNotExist()
    }
}
