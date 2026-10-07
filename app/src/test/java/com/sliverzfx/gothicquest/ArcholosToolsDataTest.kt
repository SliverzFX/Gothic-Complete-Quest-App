package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class ArcholosToolsDataTest {
    @Test fun sectionsHaveDistinctIdsAndExpectedCoverage() {
        assertEquals(2605, ArcholosToolsData.codes.size)
        assertEquals(1252, ArcholosToolsData.items.size)
        assertEquals(15, ArcholosToolsData.tips.size)
        val entries = ToolSection.entries.flatMap { ArcholosToolsData.entries(it) }
        assertEquals(entries.size, entries.map { it.id }.toSet().size)
        assertTrue(entries.all { it.source.startsWith("https://") && it.body.isNotBlank() })
        assertFalse(entries.any { '\uFFFD' in it.title || '\uFFFD' in it.body })
    }
    @Test fun exportedIdsAreSafeCompleteAndCategoryCountsMatch() {
        val codes = ArcholosToolsData.codes.filter { it.command?.startsWith("insert ") == true }
        assertEquals(2599, codes.size)
        assertTrue(codes.all { Regex("insert [a-z0-9_]+").matches(it.command!!) })
        assertEquals(codes.size, codes.map { it.command }.toSet().size)
        assertEquals(488, codes.count { it.codeCategory == CodeCategory.WEAPONS })
        assertEquals(238, codes.count { it.codeCategory == CodeCategory.ARMOR })
        assertEquals(1873, codes.count { it.codeCategory == CodeCategory.ITEMS })
        assertFalse(codes.any { it.codeCategory == CodeCategory.CHARACTERS })
    }
    @Test fun conditionalSummonerBeltStatsAreNotCollapsed() {
        val body = code("itbe_mod_summon_h").body
        assertTrue(body.contains("damage bonus: 15"))
        assertTrue(body.contains("trained mana exceeds 100"))
        assertTrue(body.indexOf("trained mana exceeds 100") < body.indexOf("damage bonus: 10"))
        assertFalse(body.contains("damage bonus: 25"))
    }
    @Test fun setRegenerationKeepsMatchingPieceCondition() {
        val body = code("itri_watermageset_01").body
        assertTrue(body.indexOf("Additional matching set pieces") < body.indexOf("passive mana"))
        assertTrue(body.contains("Maximum mana bonus: 5"))
    }
    @Test fun armorUpgradesRetainArcholosValues() {
        val entry = code("itar_mil_b_tier2")
        assertTrue(entry.title.contains("Enhanced"))
        assertTrue(entry.body.contains("Blunt protection: 62"))
        assertTrue(entry.body.contains("Arrow protection: 57"))
        assertTrue(entry.body.contains("Magic protection: 7"))
        assertTrue(entry.body.contains("player availability is not verified"))
    }
    @Test fun elixirAndPropsStaySeparate() {
        assertTrue(code("itpo_perm_str").body.contains("Strength bonus: 3"))
        assertFalse(code("itpo_perm_str").body.contains("Temporary potion"))
        assertTrue(code("itar_knife").body.contains("animation or legacy"))
        assertFalse(ArcholosToolsData.items.any { it.id == "arch_item_itar_knife" })
        assertTrue(ArcholosToolsData.items.all { it.command == null })
        assertTrue(ArcholosToolsData.tips.all { it.command == null })
    }
    @Test fun exactIdAndNameAreBothSearchable() {
        val entry = code("itpo_perm_str")
        val values = listOf(entry.title, entry.group, entry.body, entry.command.orEmpty())
        assertTrue(values.any { it.contains("elixir of strength", ignoreCase = true) })
        assertTrue(values.any { it.contains("itpo_perm_str", ignoreCase = true) })
        assertEquals("1.2.2", ArcholosToolsData.sourceVersion)
    }
    private fun code(id: String) = ArcholosToolsData.codes.single { it.command == "insert $id" }
}
