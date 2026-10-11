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
        assertTrue(Gothic2ToolsData.codes.single { it.command.equals("insert PC_Thief_NW", true) }.body.contains("duplicate"))
        assertTrue(Gothic2ToolsData.codes.single { it.command == "insert itwr_strstoneplate3_addon" }.body.contains("language"))
        assertTrue(Gothic2ToolsData.items.single { it.title == "Strength Tablets" }.body.contains("+2 / +4 / +6"))
    }

    @Test fun goldEquipmentStatsUseExpansionRequirements() {
        fun item(id: String) = Gothic2ToolsData.items.single { it.id == id }.body
        assertTrue(item("g2_item_itmw_meisterdegen").contains("120 damage • 60 dexterity"))
        assertTrue(item("g2_item_itmw_meisterdegen").contains("+10 one-handed"))
        assertTrue(item("g2_item_itrw_crossbow_m_02").contains("120 damage • 90 strength"))
        assertTrue(item("g2_item_itrw_crossbow_l_01").contains("30 damage • 20 strength"))
        assertTrue(item("g2_item_itmw_2h_orcaxe_02").contains("80 strength"))
        assertTrue(item("g2_item_itar_djg_crawler").contains("70 weapons • 70 arrows • 15 fire • 0 magic"))
        assertTrue(item("g2_item_itar_djg_crawler").contains("10 plates"))
        assertTrue(item("g2_item_itar_thorus_addon").contains("Bloodwyn’s chest"))
    }

    @Test fun permanentAndTemporaryBonusesStayDistinct() {
        fun item(id: String) = Gothic2ToolsData.items.single { it.id == id }.body
        assertTrue(item("g2_item_itpo_perm_str").contains("Permanently grants +3 strength"))
        assertTrue(item("g2_item_itpo_perm_health").contains("+20 maximum health"))
        assertTrue(item("g2_item_itpl_strength_herb_01").contains("+1 strength"))
        assertTrue(item("g2_item_itri_orcelitering").contains("lowers strength by 20"))
        assertTrue(item("g2_item_itbe_addon_str_10").contains("+10 strength"))
        assertTrue(item("g2_item_itru_firerain").contains("150 mana per cast"))
        assertTrue(item("g2_item_fire_rain").contains("5 mana per cast"))
    }

    @Test fun npcCostumesDoNotAppearAsNormalObtainableArmor() {
        assertFalse(Gothic2ToolsData.items.any { it.id == "g2_item_itar_raven_addon" })
        assertTrue(Gothic2ToolsData.codes.single { it.command == "insert itar_raven_addon" }.body.contains("NPC"))
        assertTrue(Gothic2ToolsData.items.any { it.id == "g2_item_itar_bau_l" })
    }

    @Test fun expandedCommandsAreUniqueAndRangesAreIndividualCommands() {
        val commands = Gothic2ToolsData.codes.mapNotNull { it.command }
        assertEquals(commands.size, commands.map { it.lowercase() }.distinct().size)
        assertTrue(commands.contains("insert zombie04"))
        assertFalse(commands.any { it.contains("01-04") })
        val claw = Gothic2ToolsData.codes.single { it.command == "insert itmw_beliarweapon_2h_20" }
        assertTrue(claw.body.contains("120 base damage"))
        assertTrue(claw.body.contains("50%"))
    }
}
