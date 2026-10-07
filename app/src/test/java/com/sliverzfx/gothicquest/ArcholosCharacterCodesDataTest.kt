package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class ArcholosCharacterCodesDataTest {
    @Test fun actorsAreUniqueAndCategorizedWithoutUnsafeInternalHelpers() {
        val entries = ArcholosCharacterCodesData.entries
        assertEquals(2952, entries.size)
        assertEquals(entries.size, entries.map { it.id }.toSet().size)
        assertEquals(entries.size, entries.map { it.command }.toSet().size)
        assertTrue(entries.all { it.codeCategory == CodeCategory.CHARACTERS })
        assertTrue(entries.all { Regex("insert [a-z0-9_]+").matches(it.command!!) })
        assertFalse(entries.any { Regex("helper|selfbak|test|onlyhead|fakehero|insert pc_").containsMatchIn(it.command!!) })
        assertTrue(entries.all { it.source.startsWith("https://docs.google.com/spreadsheets/d/1LZa9Key") })
        assertTrue(entries.all { it.body.contains("version is not specified") })
    }
    @Test fun englishNamesDoNotRewriteActualInstanceIds() {
        assertEquals("Jorn", actor("none_1_jorn").title)
        assertEquals("Elco", actor("bau_2279_nirko").title)
        assertEquals("Beckett", actor("pir_6330_captain_archolos").title)
        assertTrue(actor("none_1_jorn").body.contains("English dialogue filenames"))
        assertTrue(actor("none_1_jorn").body.contains("English localization"))
    }
    @Test fun namedAndQuestVariantsRemainSeparate() {
        val normal = actor("none_202_kessel")
        val variant = actor("none_202_kessel_beforeq405_01")
        assertNotEquals(normal.id, variant.id)
        assertNotEquals(normal.command, variant.command)
        assertTrue(variant.body.contains("keep the full ID"))
        assertTrue(normal.body.contains("quest progress are not recreated"))
    }
    @Test fun creatureAndBossEntriesUseThePublishedArcholosIds() {
        assertEquals("Wolf", actor("wolf").title)
        assertEquals("CREATURES", actor("wolf").group)
        assertEquals("BOUNTIES & BOSSES", actor("minecrawlerqueen").group)
        assertTrue(actor("bdt_8011_puma_monster").title.contains("monster variant"))
        assertTrue(actor("bdt_8011_puma_monster").body.contains("combat values and behavior have not been verified"))
    }
    @Test fun actorSearchWorksForNamesVariantsAndFullCommands() {
        val entry = actor("none_202_kessel_beforeq405_01")
        val searchable = listOf(entry.title, entry.group, entry.body, entry.command.orEmpty())
        assertTrue(searchable.any { it.contains("Kessel", ignoreCase = true) })
        assertTrue(searchable.any { it.contains("beforeq405_01", ignoreCase = true) })
        assertTrue(ArcholosCharacterCodesData.attribution.contains("© CrazyRaus, 2022"))
    }
    private fun actor(id: String) = ArcholosCharacterCodesData.entries.single { it.command == "insert $id" }
}
