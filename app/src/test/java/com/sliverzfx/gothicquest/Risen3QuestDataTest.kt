package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Risen3QuestDataTest {
    @Test
    fun importedChaptersHaveUniqueIdsAndContiguousOrder() {
        assertEquals(listOf(10, 140, 11, 11), (1..4).map { GameId.RISEN_3.chapterQuests(it).size })
        val quests = GameId.RISEN_3.quests()
        assertEquals(quests.size, quests.map { it.id }.toSet().size)
        for (chapter in 1..4) {
            val entries = GameId.RISEN_3.chapterQuests(chapter)
            assertEquals((1..entries.size).toList(), entries.map { it.playOrder })
            assertTrue(Risen3QuestData.notes(chapter).all { it.beforeQuestOrder in 1..entries.size + 1 })
        }
    }

    @Test
    fun mutuallyExclusiveFactionInitiationsKeepSeparateCompletionIds() {
        val branches = GameId.RISEN_3.quests().filter { it.title.startsWith("New Allies —") }
        assertEquals(setOf("New Allies — Demon Hunters", "New Allies — Natives", "New Allies — Guardians"),
            branches.map { it.title }.toSet())
        assertEquals(3, branches.map { it.id }.toSet().size)
        assertTrue(branches.all { it.warnings.contains("POINT OF NO RETURN") })
    }

    @Test
    fun repeatedSpiritRitualStagesShareOneEntryAtChapterThreeEnd() {
        assertEquals(1, GameId.RISEN_3.quests().count { it.title == "The Spirit Ritual" })
        assertEquals("The Spirit Ritual", GameId.RISEN_3.chapterQuests(3).last().title)
        assertTrue(Risen3QuestData.notes(3).any { it.title.contains("The Spirit Ritual") })
    }

    @Test
    fun finalQuestResumesInsideRisenThree() {
        val finalQuest = GameId.RISEN_3.chapterQuests(4).last()
        assertEquals("Death Incarnate", finalQuest.title)
        assertEquals(AppRoute.QuestDetail(GameId.RISEN_3, finalQuest.id),
            routeFromResume("Risen 3", 4, finalQuest.id))
    }
}
