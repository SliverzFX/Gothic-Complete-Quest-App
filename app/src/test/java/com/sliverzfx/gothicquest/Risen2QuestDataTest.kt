package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class Risen2QuestDataTest {
    @Test fun chapterOrderCountsAndUniqueProgressRecords() {
        assertEquals(listOf(46, 79, 75, 4), (1..4).map { GameId.RISEN_2.chapterQuests(it).size })
        val quests = GameId.RISEN_2.quests()
        assertEquals(204, quests.size)
        assertEquals(204, quests.map { it.id }.toSet().size)
        assertEquals(204, quests.map { it.title.lowercase() }.toSet().size)
        (1..4).forEach { chapter ->
            val entries = GameId.RISEN_2.chapterQuests(chapter)
            assertEquals((1..entries.size).toList(), entries.map { it.playOrder })
            assertTrue(entries.all { it.summary.isNotBlank() && it.walkthroughSteps.isNotEmpty() })
        }
        assertEquals("Meet the Commandant!", quests.first().title)
        assertEquals("The Pirate Garb", GameId.RISEN_2.chapterQuests(1).last().title)
    }

    @Test fun continuationGuidanceIsPreservedWithoutDuplicateQuests() {
        val largo = GameId.RISEN_2.quests().single { it.title == "Freeing Largo" }
        assertTrue(largo.walkthroughSteps.any { it.contains("Chapter 1") })
        val password = GameId.RISEN_2.quests().single { it.title == "The Password" }
        assertTrue(password.walkthroughSteps.any { it.contains("inspect the Strange Gate", true) })
        assertTrue(password.walkthroughSteps.any { it.contains("Garcia's Logbook") })
        assertTrue(Risen2QuestData.notes(2).any { it.title.startsWith("The Cunning Captain") })
    }

    @Test fun finaleContainsFourQuestsAndSeparateCombatNotes() {
        assertEquals(listOf("The Water Temple", "Defeat the Kraken", "Kill Mara", "Jaffar's Auri Culci"),
            GameId.RISEN_2.chapterQuests(4).map { it.title })
        assertTrue(Risen2QuestData.notes(4).any { it.title == "KRAKEN COMBAT CHECKLIST" })
        assertTrue(Risen2QuestData.notes(4).any { it.title == "MARA COMBAT CHECKLIST" })
        assertTrue(GameId.RISEN_2.chapterQuests(4).last().warnings.contains("Steelbeard's Hat"))
    }

    @Test fun resumeFindsRisen2Quest() {
        val quest = GameId.RISEN_2.chapterQuests(4).last()
        assertEquals(AppRoute.QuestDetail(GameId.RISEN_2, quest.id),
            routeFromResume("Risen 2", 4, quest.id))
    }
}
