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
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_weapons"))
        composeRule.onNodeWithTag("code_category_weapons").performClick()
        composeRule.onNodeWithTag("gothic_reference_g1_crw_armor_h").assertDoesNotExist()
        composeRule.onNodeWithTag("code_category_list").performScrollToNode(hasTestTag("code_category_all"))
        composeRule.onNodeWithTag("code_category_all").performClick()
        composeRule.onNodeWithTag("gothic_reference_g1_crw_armor_h").assertExists()
    }

    @Test fun normalItemSearchDoesNotShowSpawnCommands() {
        composeRule.setContent {
            MaterialTheme {
                GothicReferenceScreen(ToolSection.ITEMS, {}, {}, embedded = true,
                    game = GameId.GOTHIC)
            }
        }
        composeRule.onNodeWithTag("tools_search_toggle").performClick()
        composeRule.onNodeWithTag("tool_reference_search").performTextInput("War Crossbow")
        composeRule.onNodeWithTag("gothic_reference_g1_item_itrw_crossbow_04").assertExists()
        composeRule.onNodeWithText("COPY").assertDoesNotExist()
    }
}
