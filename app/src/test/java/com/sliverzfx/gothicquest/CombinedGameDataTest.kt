package com.sliverzfx.gothicquest
import org.junit.Assert.*
import org.junit.Test
class CombinedGameDataTest {
    private val games = listOf(GothicToolsData::entries, Gothic2ToolsData::entries, Gothic3ToolsData::entries, ArcholosToolsData::entries)
    @Test fun everyOriginalCommandSurvivesUnchanged() {
        games.forEach { entries ->
            val codes = entries(ToolSection.MARVIN_CODES)
            val combined = combineToolReferences(codes, entries(ToolSection.ITEMS))
            val byId = combined.associateBy { it.entry.id }
            codes.forEach { assertEquals(it.command, byId.getValue(it.id).entry.command) }
            assertEquals(combined.size, byId.size)
        }
    }
    @Test fun everyOriginalDescriptionAndSourceIsPreserved() {
        games.forEach { entries ->
            val originals = entries(ToolSection.MARVIN_CODES) + entries(ToolSection.ITEMS)
            val combined = combineToolReferences(entries(ToolSection.MARVIN_CODES), entries(ToolSection.ITEMS))
            originals.forEach { original ->
                assertTrue("Description missing: ${original.id}", combined.any { it.entry.body.contains(original.body.trim()) })
            }
            assertEquals(originals.map { it.source }.toSet(), combined.flatMap { it.sources }.toSet())
        }
    }
    @Test fun gothicOneWarCrossbowShowsStatsAndCopyableCodeTogether() {
        val combined = combineToolReferences(GothicToolsData.entries(ToolSection.MARVIN_CODES), GothicToolsData.entries(ToolSection.ITEMS))
        val bow = combined.single { it.entry.command == "insert itrw_crossbow_04" }.entry
        assertEquals("War Crossbow", bow.title)
        assertTrue(bow.body.contains("100 damage"))
        assertTrue(bow.body.contains("55 dexterity"))
        assertTrue(bow.body.contains("Crossbows need bolts"))
        assertEquals(1, combined.count { it.entry.title == "War Crossbow" })
    }
    @Test fun archolosLocationOnlyReferencesAreRetained() {
        val extras = ArcholosAcquisitionData.extraItems
        val combined = combineToolReferences(ArcholosToolsData.entries(ToolSection.MARVIN_CODES), ArcholosToolsData.entries(ToolSection.ITEMS))
        extras.forEach { extra -> assertTrue(combined.any { it.entry.body.contains(extra.body.trim()) }) }
    }
}
