package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class ArcholosAcquisitionDataTest {
    @Test fun normalEquipmentGetsAcquisitionWithoutChangingExportStats() {
        val leather = ArcholosToolsData.items.single { it.id == "arch_item_itar_km_leather_l" }
        assertTrue(leather.body.contains("Veit for 300 gold"))
        assertTrue(leather.body.contains("chapter 1"))
        assertTrue(leather.body.contains("Edge protection: 15"))
        assertFalse(leather.body.contains("player availability is not verified"))
        val southern = ArcholosToolsData.items.single { it.id == "arch_item_itar_sth_h" }
        assertTrue(southern.body.contains("Arrow protection: 85"))
        assertTrue(southern.body.contains("3000 gold"))
    }
    @Test fun upgradesReferenceBaseAcquisitionRatherThanDirectTierSales() {
        val body = ArcholosAcquisitionData.details("itar_mil_b_tier2")!!
        assertTrue(body.contains("routes below obtain the base armor"))
        assertTrue(body.contains("Missing in Action"))
    }
    @Test fun recipeSellersRemainMarkedAsDiagramSources() {
        val recipe = ArcholosToolsData.items.single { it.id == "arch_item_itre_bow_09" }
        assertTrue(recipe.body.contains("Diagram:"))
        assertTrue(recipe.body.contains("Frida"))
        assertTrue(recipe.body.contains("Recipe requirement: rookie bowmaking"))
    }
    @Test fun mergedBookRowsAreNotMisattributedToMagicCircles() {
        val books = ArcholosAcquisitionData.extraItems.filter { it.group == "MAGIC CIRCLE BOOK LOCATIONS" }
        assertEquals(14, books.size)
        assertEquals(books.size, books.map { it.title }.toSet().size)
        assertEquals(2, books.count { it.title.startsWith("Magic Circle I —") })
        assertEquals(3, books.count { it.title.startsWith("Magic Circle II —") })
        assertEquals(4, books.count { it.title.startsWith("Magic Circle III —") })
        assertEquals(5, books.count { it.title.startsWith("Magic Circle IV —") })
    }
    @Test fun teleportCardsSeparateRuneSourceAndDestination() {
        val cards = ArcholosAcquisitionData.extraItems.filter { it.group == "TELEPORT LOCATIONS" }
        assertEquals(15, cards.size)
        assertTrue(cards.all { it.body.contains("Destination circle:") && it.body.contains("Rune source:") })
        assertFalse(cards.any { it.title.endsWith("None") })
    }
    @Test fun setRingSourcesStayWithTheCorrectSet() {
        val water = ArcholosAcquisitionData.details("itri_watermageset_01")!!
        assertTrue(water.contains("sewers near bandits"))
        assertTrue(water.contains("Ingolf"))
        assertFalse(water.contains("Otmar"))
        val south = ArcholosAcquisitionData.details("itri_southernerset_01")!!
        assertTrue(south.contains("Otmar"))
    }
    @Test fun officialPatchCorrectionsAreSeparateFromSnapshotStatistics() {
        assertEquals("1.2.2", ArcholosToolsData.sourceVersion)
        assertEquals("1.2.11", ArcholosToolsData.reviewedPatchVersion)
        val tips = ArcholosGameplayTipsData.entries
        assertTrue(tips.single { it.id == "arch_gameplay_alchemy_trainer" }.body.contains("master"))
        assertTrue(tips.single { it.id == "arch_gameplay_fang_sale" }.body.contains("1.2.5"))
        assertTrue(tips.single { it.id == "arch_gameplay_patch_review" }.body.contains("does not certify every value"))
    }
    @Test fun locationSearchAndCodeCopyFieldsRemainSeparate() {
        val item = ArcholosToolsData.items.single { it.id == "arch_item_itar_km_leather_l" }
        assertTrue(listOf(item.title, item.body).any { it.contains("Veit", ignoreCase = true) })
        assertNull(item.command)
        val code = ArcholosToolsData.codes.single { it.command == "insert itar_km_leather_l" }
        assertTrue(code.body.contains("Veit"))
        assertTrue(ArcholosToolsData.items.all { it.command == null })
    }
}
