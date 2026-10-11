package com.sliverzfx.gothicquest

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class Risen3ReferenceScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun itemLocationCanBeFoundInFullBodyWithoutACopyCommand() {
        composeRule.setContent {
            MaterialTheme { ToolReferenceScreen(ToolSection.MARVIN_CODES, GameId.RISEN_3, {}, {}, embedded = true) }
        }
        composeRule.onNodeWithText("Reference being prepared").assertDoesNotExist()
        composeRule.onNodeWithText(Risen3ToolsData.sourceNote).assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("Zacharias’s tower")
        composeRule.onNodeWithTag("gothic_reference_r3_legendary_sun_crystal").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("Chapter 3", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").assertDoesNotExist()
        composeRule.onNodeWithText("OPEN REFERENCE 1").assertDoesNotExist()
        composeRule.onNodeWithTag("tool_detail_close").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
    }

    @Test fun copyableShortcutKeepsItsTestmodeRequirement() {
        composeRule.setContent {
            MaterialTheme { ToolReferenceScreen(ToolSection.MARVIN_CODES, GameId.RISEN_3, {}, {}, embedded = true) }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("Toggle HUD")
        composeRule.onNodeWithTag("gothic_reference_r3_debug_hud").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("requires active PC testmode", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
    }

    @Test fun tipsCanBeFoundUsingTheirDescription() {
        composeRule.setContent {
            MaterialTheme { ToolReferenceScreen(ToolSection.USEFUL_TIPS, GameId.RISEN_3, {}, {}, embedded = true) }
        }
        composeRule.onNodeWithText("Risen 3 • normal gameplay").assertExists()
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("same island")
        composeRule.onNodeWithTag("gothic_reference_r3_tip_teleporters").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("teleport stone", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").assertDoesNotExist()
    }
}
