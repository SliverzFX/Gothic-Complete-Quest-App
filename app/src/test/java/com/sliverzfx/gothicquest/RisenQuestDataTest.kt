package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RisenQuestDataTest {
    @Test
    fun allSuppliedChapterEntriesHaveUniqueIdsAndOrderedWalkthroughs() {
        assertEquals(listOf(89, 42, 21, 17), (1..4).map { GameId.RISEN.chapterQuests(it).size })
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
    fun repeatedTitlesInDifferentAreasKeepSeparateProgress() {
        val entries = GameId.RISEN.chapterQuests(1).filter { it.title == "Protection money" }
        assertEquals(2, entries.size)
        assertEquals(2, entries.map { it.id }.distinct().size)
        assertEquals(2, entries.map { it.location }.distinct().size)
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

}
