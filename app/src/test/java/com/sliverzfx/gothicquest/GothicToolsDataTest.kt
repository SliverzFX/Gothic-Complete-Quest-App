package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GothicToolsDataTest {
    @Test fun normalPlayEntriesDoNotContainSpawnCommands() {
        (GothicToolsData.items + GothicToolsData.tips).forEach {
            assertEquals(null, it.command)
            assertEquals(null, it.codeCategory)
        }
    }

    @Test fun codesCoverAllCategoriesAndKeepExistingEntryKeys() {
        assertEquals(CodeCategory.entries.toSet(), GothicToolsData.codes.mapNotNull { it.codeCategory }.toSet())
        assertEquals("insert itarscrolllight", GothicToolsData.codes.single { it.id == "light_insert" }.command)
        assertEquals("insert pc_thief", GothicToolsData.codes.single { it.id == "diego" }.command)
        assertEquals(CodeCategory.ARMOR, GothicToolsData.codes.single { it.command == "insert crw_armor_h" }.codeCategory)
        assertTrue(GothicToolsData.codes.single { it.id == "diego" }.body.contains("duplicate"))
        assertEquals("insert org_801_lares", GothicToolsData.codes.single { it.title == "Spawn Lares" }.command)
    }

    @Test fun originalGothicStatsDoNotUseGothicTwoRules() {
        assertTrue(GothicToolsData.items.single { it.title == "Rusty Sword" }.body.contains("10 damage • 5 strength"))
        assertTrue(GothicToolsData.items.single { it.title == "War Crossbow" }.body.contains("100 damage • 55 dexterity"))
        assertTrue(GothicToolsData.items.single { it.title == "Dragonroot" }.body.contains("30 mana"))
        assertTrue(GothicToolsData.items.single { it.title == "Minecrawler Plate Armor" }.body.contains("15 minecrawler warrior plates"))
    }

    @Test fun descriptionsAndListKeysAreValidForEachSection() {
        ToolSection.entries.forEach { section ->
            val entries = GothicToolsData.entries(section)
            assertTrue(entries.isNotEmpty())
            assertEquals(entries.size, entries.map { it.id }.distinct().size)
            entries.forEach { entry ->
                assertTrue(entry.title.isNotBlank() && entry.body.isNotBlank())
                assertTrue(entry.source.startsWith("https://"))
            }
        }
    }
}
