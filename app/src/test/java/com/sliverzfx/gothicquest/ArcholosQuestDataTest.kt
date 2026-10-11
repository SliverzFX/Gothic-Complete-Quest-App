package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ArcholosQuestDataTest {
    @Test
    fun allSixChaptersPreserveImportedQuestsAndOrder() {
        assertEquals(listOf(18, 74, 41, 28, 29, 2), (1..6).map { GameId.ARCHOLOS.chapterQuests(it).size })
        val quests = GameId.ARCHOLOS.quests()
        assertEquals(quests.size, quests.map { it.id }.toSet().size)
        assertTrue(quests.all { it.summary.isNotBlank() && it.walkthroughSteps.isNotEmpty() && it.reward.isNotBlank() })
        for (chapter in 1..6) {
            val entries = GameId.ARCHOLOS.chapterQuests(chapter)
            assertEquals((1..entries.size).toList(), entries.map { it.playOrder })
            assertTrue(ArcholosQuestData.notes(chapter).all { it.beforeQuestOrder in 1..entries.size + 1 })
        }
    }

    @Test
    fun chapterSpecificFollowupsUseTheirStatedAvailability() {
        assertEquals(3, ArcholosQuestData.quests.single { it.title == "The Legendary Recipe (Odgar)" }.chapter)
        assertEquals(2, ArcholosQuestData.quests.single { it.title == "Cleaning The Shore" }.chapter)
        assertEquals(2, ArcholosQuestData.quests.single { it.id == "AR-C1-019" }.chapter)
        assertEquals(2, ArcholosQuestData.quests.single { it.id == "AR-C1-020" }.chapter)
        for (number in 67..71) {
            assertEquals(3, ArcholosQuestData.quests.single { it.id == "AR-C2-" + number.toString().padStart(3, '0') }.chapter)
        }
        assertEquals(5, ArcholosQuestData.quests.single { it.title == "Small Gift" }.chapter)
        assertEquals(6, ArcholosQuestData.quests.single { it.title == "A City On Fire" }.chapter)
    }

    @Test
    fun factionRoutesAndStoryStagesHaveIndependentProgress() {
        val quests = ArcholosQuestData.quests
        assertTrue(quests.any { it.title == "Who Killed Stan? (City Guard)" })
        assertTrue(quests.any { it.title == "Blood Money (Merchants’ Guild)" })
        assertEquals(3, quests.count { it.title.startsWith("One Thing Leads To Another (Part") })
        assertEquals(3, quests.filter { it.title.startsWith("One Thing Leads To Another (Part") }.map { it.id }.toSet().size)
    }

    @Test
    fun finalQuestResumesInArcholos() {
        val finalQuest = GameId.ARCHOLOS.chapterQuests(6).last()
        assertEquals("A City On Fire", finalQuest.title)
        assertEquals(AppRoute.QuestDetail(GameId.ARCHOLOS, finalQuest.id),
            routeFromResume(GameId.ARCHOLOS.persistedName, 6, finalQuest.id))
        assertEquals(6, GameId.ARCHOLOS.sectionCount)
    }
}
