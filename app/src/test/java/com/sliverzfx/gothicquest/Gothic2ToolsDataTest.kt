package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Gothic2ToolsDataTest {
    @Test fun normalGameplaySectionsNeverExposeCheatCommands() {
        (Gothic2ToolsData.items + Gothic2ToolsData.tips).forEach {
            assertEquals(null, it.command)
            assertEquals(null, it.codeCategory)
        }
    }

    @Test fun insertCommandsAreCategorizedAndCanBeFoundByTheirIdentifier() {
        val entries = Gothic2ToolsData.entries(ToolSection.MARVIN_CODES)
        assertEquals(CodeCategory.entries.toSet(), entries.mapNotNull { it.codeCategory }.toSet())
        assertEquals(CodeCategory.ARMOR, entries.single { it.command.equals("insert itar_ranger_addon", true) }.codeCategory)
        assertEquals(CodeCategory.WEAPONS, entries.single { it.command.equals("insert ItMw_Meisterdegen", true) }.codeCategory)
        assertEquals(CodeCategory.CHARACTERS, entries.single { it.command.equals("insert PC_Thief_NW", true) }.codeCategory)
        assertEquals(CodeCategory.ITEMS, entries.single { it.command == "insert itmi_gold" }.codeCategory)
        assertTrue(entries.any { it.command == "cheat god" })
    }

    @Test fun entriesHaveStableUniqueKeysAndDescriptions() {
        ToolSection.entries.forEach { section ->
            val entries = Gothic2ToolsData.entries(section)
            assertFalse(entries.isEmpty())
            assertEquals(entries.size, entries.map { it.id }.distinct().size)
            entries.forEach {
                assertTrue(it.id.startsWith("g2_"))
                assertTrue(it.title.isNotBlank() && it.body.isNotBlank())
                assertTrue(it.source.startsWith("https://"))
            }
        }
    }

    @Test fun npcCopiesAndExpansionTabletsExplainTheirLimits() {
        assertTrue(Gothic2ToolsData.codes.single { it.command == "insert PC_Thief_NW" }.body.contains("duplicate"))
        assertTrue(Gothic2ToolsData.codes.single { it.command == "insert itwr_strstoneplate3_addon" }.body.contains("language"))
        assertTrue(Gothic2ToolsData.items.single { it.title == "Strength Tablets" }.body.contains("+2 / +4 / +6"))
    }
}
