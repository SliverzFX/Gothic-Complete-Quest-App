package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RisenQuestDataTest {
    @Test
    fun allSuppliedChapterEntriesHaveUniqueIdsAndOrderedWalkthroughs() {
        assertEquals(listOf(203, 42, 21, 17), (1..4).map { GameId.RISEN.chapterQuests(it).size })
        val quests = GameId.RISEN.quests()
        assertEquals(quests.size, quests.map { it.id }.distinct().size)
        (1..4).forEach { chapter ->
            val entries = GameId.RISEN.chapterQuests(chapter)
            assertEquals((1..entries.size).toList(), entries.map { it.playOrder })
            assertTrue(entries.all { it.summary.isNotBlank() && it.walkthroughSteps.isNotEmpty() })
        }
    }

    @Test
    fun guideAdviceAndFinalBattleAreNotCountedAsQuests() {
        val notes = RisenQuestData.notes(4)
        assertTrue(notes.any { it.title.contains("FINAL TITAN BATTLE") && it.paragraphs.any { step -> step.contains("Titan Shield") } })
        assertFalse(GameId.RISEN.quests().any { it.title.contains("FINAL TITAN BATTLE") })
        assertTrue(RisenQuestData.notes(1).any { it.paragraphs.any { step -> step.contains("3–3") } })
    }

    @Test
    fun campAndTownProtectionMoneyHaveSeparateProgressIds() {
        val camp = RisenQuestData.quests.single { it.id == "R1-C1-021" }
        val town = RisenQuestData.quests.single { it.id == "R1-C1-038" }
        assertTrue(camp.title.contains("Protection money", ignoreCase = true))
        assertTrue(town.title.contains("Protection money", ignoreCase = true))
        assertTrue(camp.location != town.location)
        assertTrue(camp.id != town.id)
    }

    @Test
    fun risenQuestResumeUsesTheExistingQuestRoute() {
        val quest = GameId.RISEN.chapterQuests(4).last()
        assertEquals(AppRoute.QuestDetail(GameId.RISEN, quest.id), routeFromResume("Risen", 4, quest.id))
    }
    @Test
    fun banditCampDeliveryAndGoldFeverFollowCanonicalNpcChains() {
        val goldFever = RisenQuestData.quests.single { it.id == "R1-C1-017" }
        assertEquals("Rachel", goldFever.giver)
        assertTrue(goldFever.walkthroughSteps.any { it.contains("hunters") && it.contains("Power struggle") })

        val beer = RisenQuestData.quests.single { it.id == "R1-C1-018" }
        assertEquals("Rhobart", beer.giver)
        assertTrue(beer.walkthroughSteps.any { it.contains("ten bottles") && it.contains("Rachel") })
        assertTrue(beer.reward.contains("200 XP") && beer.reward.contains("100 XP"))
    }

    @Test
    fun chapterFourSeverinInvasionIsActionable() {
        val quest = RisenQuestData.quests.single { it.id == "R1-C4-007" }
        assertEquals("Severin", quest.giver)
        assertTrue(quest.walkthroughSteps.any { it.contains("five") && it.contains("lizardmen") })
        assertTrue(quest.reward.contains("500 XP"))
    }

    @Test
    fun fullRisenMetadataAuditHasNoBlankRequiredFields() {
        val quests = RisenQuestData.quests
        assertEquals(283, quests.size)
        assertTrue(quests.all { it.title.isNotBlank() && it.giver.isNotBlank() })
        assertTrue(quests.all { it.prerequisites.isNotBlank() && it.reward.isNotBlank() })
        assertTrue(quests.all { it.summary.isNotBlank() && it.walkthroughSteps.isNotEmpty() })
        assertEquals(quests.size, quests.map { it.id }.distinct().size)
    }

    @Test
    fun guideCrossReferencesNeverCountAsIndependentCompletedQuests() {
        val expected = setOf("R1-C1-030", "R1-C2-002", "R1-C2-036", "R1-C2-037", "R1-C2-040", "R1-C2-041", "R1-C1-037", "R1-C1-038")
        val guideIds = RisenQuestData.quests.filter(RisenQuestData::isGuideEntry).map { it.id }.toSet()
        assertEquals(expected, guideIds)
        assertEquals(275, RisenQuestData.quests.count { !RisenQuestData.isGuideEntry(it) })
        assertTrue(RisenQuestData.quests.filter(RisenQuestData::isGuideEntry).all { isReferenceQuest(GameId.RISEN, it) })
        assertFalse(isReferenceQuest(GameId.RISEN, RisenQuestData.quests.first()))
    }

    @Test
    fun correctedHarbourTownAndBanditCampQuestsUseDocumentedGivers() {
        assertEquals("Master Belschwur", RisenQuestData.quests.single { it.id == "R1-C1-031" }.giver)
        assertEquals("Flavio", RisenQuestData.quests.single { it.id == "R1-C1-032" }.giver)
        assertEquals("Scordo", RisenQuestData.quests.single { it.id == "R1-C1-028" }.giver)
        assertEquals("Oscar (journal trigger); Walter (resolution)", RisenQuestData.quests.single { it.id == "R1-C2-017" }.giver)
        assertEquals("Romanov", RisenQuestData.quests.single { it.id == "R1-C1-040" }.giver)
    }

    @Test
    fun newSideQuestAppendixIsUniqueAndHasActionableSteps() {
        val extra = RisenGuidePart9Data.quests
        assertEquals(27, extra.size)
        assertEquals((90..116).toList(), extra.map { it.playOrder })
        assertTrue(extra.all { it.id.startsWith("R1-C1-") && it.chapter == 1 })
        assertTrue(extra.all { it.giver.isNotBlank() && it.reward.isNotBlank() && it.prerequisites.isNotBlank() })
        assertTrue(extra.all { it.walkthroughSteps.size >= 3 })
        assertTrue(extra.map { it.id }.toSet().intersect(RisenGuidePart1Data.quests.map { it.id }.toSet()).isEmpty())
        assertTrue(extra.map { it.id }.toSet().intersect(RisenGuidePart2Data.quests.map { it.id }.toSet()).isEmpty())
        assertTrue(extra.map { it.id }.toSet().intersect(RisenGuidePart3Data.quests.map { it.id }.toSet()).isEmpty())
        assertTrue(extra.map { it.id }.toSet().intersect(RisenGuidePart4Data.quests.map { it.id }.toSet()).isEmpty())
    }

    @Test
    fun harbourTownStandaloneExpansionHasStableAndDisjointIds() {
        val neutral = RisenGuidePart10Data.quests
        val faction = RisenGuidePart11Data.quests
        assertEquals(18, neutral.size)
        assertEquals(36, faction.size)
        assertEquals((117..134).toList(), neutral.map { it.playOrder })
        assertEquals((135..170).toList(), faction.map { it.playOrder })
        assertTrue((neutral + faction).all { it.chapter == 1 && it.walkthroughSteps.size == 3 })
        assertEquals(54, (neutral + faction).map { it.id }.distinct().size)
    }

    @Test
    fun banditCampMissingQuestsAreStandaloneAndStable() {
        val quests = RisenGuidePart12Data.quests
        assertEquals(16, quests.size)
        assertEquals((171..186).toList(), quests.map { it.playOrder })
        assertTrue(quests.all { it.chapter == 1 && it.prerequisites.isNotBlank() && it.reward.isNotBlank() })
        assertEquals(16, quests.map { it.id }.distinct().size)
        assertTrue(quests.any { it.title == "Artefact Delivery" && it.giver == "Beppo" })
        assertTrue(quests.any { it.title == "To the Temple Ruins with Lorenzo" })
    }

    @Test
    fun pattyAndMonasteryMissingQuestsKeepUniqueProgress() {
        val appendix = RisenGuidePart13Data.quests
        assertEquals(7, appendix.size)
        assertEquals((187..193).toList(), appendix.map { it.playOrder })
        assertEquals(7, appendix.map { it.id }.distinct().size)
        assertTrue(appendix.all { it.walkthroughSteps.size == 3 && it.reward.isNotBlank() })
        assertTrue(appendix.any { it.title == "Mental Arithmetic!" && it.reward == "100 XP" })
    }

    @Test
    fun missingPrologueJournalQuestsKeepIndependentProgressKeys() {
        val quests = RisenGuidePart14Data.quests
        assertEquals(4, quests.size)
        assertEquals((194..197).toList(), quests.map { it.playOrder })
        assertEquals((194..197).map { "R1-C1-" + it }, quests.map { it.id })
        assertEquals(listOf("Take Sara to Safety", "Investigate the Abandoned House",
            "Find the Key in the Abandoned House", "Loot the Chest in the Abandoned House"), quests.map { it.title })
        assertTrue(quests.all { it.chapter == 1 && it.reward.contains("25 XP") })
        assertTrue(quests.all { it.walkthroughSteps.size == 3 && it.warnings.contains("Chronology:") })
        assertTrue(quests.all { !RisenQuestData.isGuideEntry(it) })
        assertEquals("Find survivors of the shipwreck", RisenQuestData.quests.single { it.id == "R1-C1-001" }.title)
        assertEquals("Take some fried meat to Sara", RisenQuestData.quests.single { it.id == "R1-C1-002" }.title)
    }
    @Test
    fun finalMissingQuestsMatchSourcedGiversAndCounts() {
        val late = RisenGuidePart15Data.quests
        assertEquals(6, late.size)
        assertEquals((198..203).toList(), late.map { it.playOrder })
        assertTrue(late.all { it.giver.isNotBlank() && it.prerequisites.isNotBlank() && it.reward.isNotBlank() })
        assertEquals("Josh", RisenQuestData.quests.single { it.id == "R1-C1-125" }.giver)
        assertEquals("Dirk", RisenQuestData.quests.single { it.id == "R1-C1-187" }.giver)
        assertEquals("500 XP", RisenQuestData.quests.single { it.id == "R1-C1-198" }.reward)
        assertTrue(RisenQuestData.quests.single { it.id == "R1-C1-094" }.summary.contains("five vassal rings"))
    }

}
