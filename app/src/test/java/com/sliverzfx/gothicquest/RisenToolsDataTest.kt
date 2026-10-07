package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class RisenToolsDataTest {
    @Test fun activationUsesMinskyAndRisenCommands() {
        assertTrue(RisenToolsData.codes.any { it.command == "minsky" })
        assertTrue(RisenToolsData.codes.any { it.command == "give It_Gold 1000" })
        assertFalse(RisenToolsData.codes.any { it.command?.startsWith("insert ") == true })
    }
    @Test fun huntingKnifeKeepsItsActualStats() {
        val card = RisenToolsData.codes.single { it.command == "give It_1H_Knife_Hunting 1" }
        assertTrue(card.body.contains("Blade damage: 12"))
        assertTrue(card.body.contains("Required strength: 8"))
        assertTrue(card.body.contains("Gold value: 26"))
    }
    @Test fun characterDisplayNamesDoNotReplaceTemplateIds() {
        assertEquals("spawn Dick", RisenToolsData.codes.single { it.title == "Marek" }.command)
        assertEquals("spawn Inquisitor", RisenToolsData.codes.single { it.title == "Inquisitor Mendoza" }.command)
    }
    @Test fun catalogueIsCompleteUniqueAndCategorised() {
        assertEquals(510, RisenToolsData.codes.count { it.command?.startsWith("give It_") == true && it.id.startsWith("r1_item_") })
        assertEquals(141, RisenToolsData.codes.count { it.id.startsWith("r1_npc_") })
        assertEquals(RisenToolsData.codes.size, RisenToolsData.codes.map { it.id }.toSet().size)
        assertTrue(RisenToolsData.codes.all { it.body.isNotBlank() && it.codeCategory != null && it.source.startsWith("https://") })
    }
    @Test fun descriptionsSurviveCombinedCardsAndTipsContainNoCheats() {
        val cards = combineToolReferences(RisenToolsData.codes, RisenToolsData.items)
        assertEquals(RisenToolsData.codes.size, cards.size)
        assertTrue(cards.all { card -> RisenToolsData.codes.any { it.id == card.entry.id && it.body == card.entry.body } })
        assertTrue(RisenToolsData.tips.size >= 15)
        assertTrue(RisenToolsData.tips.all { it.command == null })
        assertEquals(RisenToolsData.tips, RisenToolsData.entries(ToolSection.USEFUL_TIPS))
    }
    @Test fun conflictingMaceRecordsAreFlaggedWithoutDroppingEither() {
        val card = RisenToolsData.codes.single { it.command == "give It_1H_Mace 1" }
        assertTrue(card.body.contains("database conflict"))
        assertTrue(card.body.contains("Blunt weapon damage: 28"))
        assertTrue(card.body.contains("Blunt weapon damage: 18"))
    }
    @Test fun teleportStonesDoNotRequireRuneSealTraining() {
        val card = RisenToolsData.codes.single { it.command == "give It_Ru_TeleportDon 1" }
        assertTrue(card.body.contains("bandit camp"))
        assertFalse(card.body.contains("appropriate magical training and seal"))
    }
    @Test fun spellEffectsRetainNumbersOutsideStatFields() {
        val protection = RisenToolsData.codes.single { it.command == "give It_Ru_Protection 1" }
        assertTrue(protection.body.contains("armour values by 20"))
        val healing = RisenToolsData.codes.single { it.command == "give It_Scr_MinorHeal 1" }
        assertTrue(healing.body.contains("50 health"))
    }
    @Test fun itemAcquisitionFactsAreBundled() {
        val card = RisenToolsData.codes.single { it.command == "give It_2H_Steel_Sharp 1" }
        assertTrue(card.body.contains("Oscar"))
        assertTrue(card.body.contains("chapter 2"))
        assertTrue(card.body.contains("Crafting"))
    }
}
