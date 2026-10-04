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
}
