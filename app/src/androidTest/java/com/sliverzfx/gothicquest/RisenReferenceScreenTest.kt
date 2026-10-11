package com.sliverzfx.gothicquest

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class RisenReferenceScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun risenRouteShowsOfflineSpellDetailsAndCopiesTheCorrectCommand() {
        composeRule.setContent {
            MaterialTheme {
                ToolReferenceScreen(ToolSection.MARVIN_CODES, GameId.RISEN, {}, {}, embedded = true)
            }
        }
        composeRule.onNodeWithText("Reference being prepared").assertDoesNotExist()
        composeRule.onNodeWithText("Risen 1 • original PC / minsky • source patch unspecified").assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("It_Ru_Protection")
        composeRule.onNodeWithTag("gothic_reference_r1_item_It_Ru_Protection").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("armour values by 20", substring = true)
        composeRule.onNodeWithText("OPEN REFERENCE 1").assertDoesNotExist()
        composeRule.onNodeWithTag("tool_detail_copy").performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
        composeRule.onNodeWithTag("tool_detail_close").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
    }

    @Test fun normalTipsUseTheRisenRouteAndCanBeSearched() {
        composeRule.setContent {
            MaterialTheme {
                ToolReferenceScreen(ToolSection.USEFUL_TIPS, GameId.RISEN, {}, {}, embedded = true)
            }
        }
        composeRule.onNodeWithText("Risen 1 • normal gameplay").assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("pickaxe")
        composeRule.onNodeWithTag("gothic_reference_r1_tip_mining").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("pickaxe", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").assertDoesNotExist()
    }
}
