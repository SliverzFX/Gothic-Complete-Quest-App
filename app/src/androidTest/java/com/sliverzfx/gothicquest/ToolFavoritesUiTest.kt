package com.sliverzfx.gothicquest

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ToolFavoritesUiTest {
    @get:Rule val rule = createComposeRule()

    @Test fun starSavesWeaponInItsCategoryAndDetailCanCopyAndRemoveIt() {
        var keys by mutableStateOf(emptySet<String>())
        var showFavorites by mutableStateOf(false)
        rule.setContent {
            CompositionLocalProvider(LocalToolFavoriteKeys provides keys,
                LocalToggleToolFavorite provides { game, id ->
                    val key = toolFavoriteKey(game, id)
                    keys = if (key in keys) keys - key else keys + key
                }) {
                if (showFavorites) Column { ToolFavoritesSections(buildToolFavorites(keys)) }
                else GothicReferenceScreen(ToolSection.MARVIN_CODES, {}, {}, embedded = true)
            }
        }
        rule.onNodeWithTag("tools_search_toggle").performClick()
        rule.onNodeWithTag("tool_reference_search").performTextInput("rusty sword")
        rule.onNodeWithTag("tool_favorite_rusty_sword_insert").performClick()
        rule.runOnIdle {
            assertTrue("G1|tool:rusty_sword_insert" in keys)
            showFavorites = true
        }
        rule.onNodeWithTag("favorites_category_weapons").assertExists()
        rule.onNodeWithTag("saved_tool_gothic_rusty_sword_insert").performClick()
        rule.onNodeWithTag("tool_detail_body").assertExists()
        rule.onNodeWithTag("tool_detail_copy").performClick()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        rule.runOnIdle {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            assertEquals("insert itmw_1h_sword_old_01", clipboard.primaryClip!!.getItemAt(0).text.toString())
        }
        rule.onNodeWithTag("tool_detail_favorite").performClick()
        rule.onNodeWithTag("tool_detail_close").performClick()
        rule.onNodeWithTag("saved_tool_gothic_rusty_sword_insert").assertDoesNotExist()
        rule.runOnIdle { assertTrue(keys.isEmpty()) }
    }
}
