package com.sliverzfx.gothicquest

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class GothicReferenceScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun commandSearchCategoriesAndCopyWorkForOriginalGothic() {
        composeRule.setContent {
            MaterialTheme {
                GothicReferenceScreen(ToolSection.MARVIN_CODES, {}, {}, embedded = true,
                    game = GameId.GOTHIC)
            }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("CRW_ARMOR_H")
        composeRule.onNodeWithTag("gothic_reference_g1_crw_armor_h").assertExists()
        composeRule.onNodeWithTag("tool_copy_g1_crw_armor_h").performScrollTo().performClick()
        composeRule.onNodeWithText("COPIED").assertExists()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_weapons"))
        composeRule.onNodeWithTag("code_category_weapons").performClick()
        composeRule.onNodeWithTag("gothic_reference_g1_crw_armor_h").assertDoesNotExist()
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_all"))
        composeRule.onNodeWithTag("code_category_all").performClick()
        composeRule.onNodeWithTag("gothic_reference_g1_crw_armor_h").assertExists()
    }

    @Test fun legacyItemsRouteOpensCombinedCompactCardsAndFullDetails() {
        composeRule.setContent {
            MaterialTheme {
                GothicReferenceScreen(ToolSection.ITEMS, {}, {}, embedded = true,
                    game = GameId.GOTHIC)
            }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("War Crossbow")
        composeRule.onNodeWithTag("gothic_reference_g1_itrw_crossbow_04").assertExists()
        composeRule.onNodeWithTag("tool_detail_body").assertDoesNotExist()
        composeRule.onNodeWithTag("tool_copy_g1_itrw_crossbow_04").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
        composeRule.onNodeWithTag("gothic_reference_g1_itrw_crossbow_04").performClick()
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("100 damage", substring = true)
        composeRule.onNodeWithTag("tool_detail_body").assertTextContains("55 dexterity", substring = true)
        composeRule.onNodeWithTag("tool_detail_copy").assertExists()
        composeRule.onNodeWithTag("tool_detail_close").performClick()
        composeRule.onNodeWithTag("tool_reference_detail").assertDoesNotExist()
        composeRule.onNodeWithTag("tool_reference_search").assertTextContains("War Crossbow")
    }
}
