package com.sliverzfx.gothicquest

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class Gothic2ReferenceScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun commandSearchCategoriesAndCopyWorkForGoldEdition() {
        composeRule.setContent {
            MaterialTheme {
                GothicReferenceScreen(ToolSection.MARVIN_CODES, {}, {}, embedded = true,
                    game = GameId.GOTHIC_2_GOLD)
            }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("ITAR_RANGER_ADDON")
        composeRule.onNodeWithTag("gothic_reference_g2_itar_ranger_addon").assertExists()
        composeRule.onNodeWithTag("tool_copy_g2_itar_ranger_addon").performScrollTo().performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_weapons"))
        composeRule.onNodeWithTag("code_category_weapons").performClick()
        composeRule.onNodeWithTag("gothic_reference_g2_itar_ranger_addon").assertDoesNotExist()
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_all"))
        composeRule.onNodeWithTag("code_category_all").performClick()
        composeRule.onNodeWithTag("gothic_reference_g2_itar_ranger_addon").assertExists()
    }

    @Test fun legacyItemsRouteOpensCombinedCompactCardsAndFullDetails() {
        composeRule.setContent {
            MaterialTheme {
                GothicReferenceScreen(ToolSection.ITEMS, {}, {}, embedded = true,
                    game = GameId.GOTHIC_2_GOLD)
            }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("Dragonroot")
        composeRule.onNodeWithTag("gothic_reference_g2_itpl_strength_herb_01").assertExists()
        composeRule.onNodeWithTag("tool_detail_body").assertDoesNotExist()
        composeRule.onNodeWithTag("gothic_reference_g2_itpl_strength_herb_01").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("+1 strength", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").performClick()
        composeRule.onNodeWithTag("tool_detail_close").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
    }
}
