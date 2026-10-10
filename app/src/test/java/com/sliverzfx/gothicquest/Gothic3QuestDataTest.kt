package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Gothic3QuestDataTest {
    @Test
    fun allSevenRegionalSectionsHaveStableQuestIdsAndOrder() {
        val sectionSizes = listOf(84, 68, 70, 72, 56, 66, 10)
        assertEquals(sectionSizes, (1..7).map { Gothic3QuestData.part(it).size })

        val quests = Gothic3QuestData.quests
        assertEquals(426, quests.size)
        assertEquals(quests.size, quests.map { it.id }.toSet().size)
        for (section in 1..7) {
            val entries = Gothic3QuestData.part(section)
            assertEquals((1..entries.size).toList(), entries.map { it.playOrder })
            assertTrue(entries.all { it.chapter == section && it.walkthroughSteps.size >= 2 })
            assertTrue(entries.all { it.giver.isNotBlank() && it.summary.isNotBlank() })
        }
    }

    @Test
    fun allGothic3SectionsHaveQuestSpecificWalkthroughs() {
        for (section in 1..7) {
            for (quest in Gothic3QuestData.part(section)) {
                assertTrue(
                    "Generic walkthrough remaining: " + quest.id,
                    quest.walkthroughSteps.none {
                        it.startsWith("Begin with ") || it.startsWith("This quest belongs to")
                    }
                )
                assertTrue("Missing giver: " + quest.id, quest.giver != "Trigger quest")
            }
        }
    }

    @Test
    fun uncertainClassicGuideEntriesAreClearlyIdentified() {
        val quests = Gothic3QuestData.quests.associateBy { it.id }
        for (id in listOf(
            "G3-P5-023", "G3-P5-044", "G3-P5-050",
            "G3-P5-051", "G3-P5-053", "G3-P5-056",
            "G3-P6-034", "G3-P6-061"
        )) {
            val entry = quests.getValue(id)
            assertTrue("Expected guide-only category: " + id, entry.category.contains("GUIDE"))
            assertTrue("Missing guide warning: " + id, entry.warnings.isNotBlank())
        }
    }

    @Test
    fun referenceEntriesAreBrowseableButNotTrackedAsSeparateQuests() {
        assertEquals(426, Gothic3QuestData.quests.size)
        assertEquals(35, Gothic3QuestData.guideEntries.size)
        assertEquals(391, Gothic3QuestData.journalQuests.size)
        assertEquals(
            listOf(84, 68, 59, 72, 47, 61, 0),
            (1..7).map { Gothic3QuestData.chapterJournalQuests(it).size }
        )
        val references = Gothic3QuestData.guideEntries.map { it.id }.toSet()
        assertEquals(35, references.size)
        assertTrue(Gothic3QuestData.journalQuests.none { it.id in references })
        assertTrue(Gothic3QuestData.quests.all { it.reward.isNotBlank() && it.prerequisites.isNotBlank() })
        for (id in listOf("G3-P3-003", "G3-P3-013", "G3-P3-034", "G3-P6-034")) {
            assertTrue(Gothic3QuestData.isGuideEntry(Gothic3QuestData.quests.single { it.id == id }))
        }
    }

    @Test
    fun importantGlobalAndRegionalObjectivesStayLinked() {
        val quests = Gothic3QuestData.quests.associateBy { it.id }
        assertTrue(quests.getValue("G3-P7-001").walkthroughSteps.any { it.contains("Cruz") })
        assertTrue(quests.getValue("G3-P7-002").walkthroughSteps.any { it.contains("Akascha") })
        assertTrue(quests.getValue("G3-P6-010").walkthroughSteps.any { it.contains("Gonzales") })
        assertTrue(quests.getValue("G3-P4-001").summary.contains("Ugluz"))
        assertTrue(quests.getValue("G3-P6-050").walkthroughSteps.any { it.contains("75") })
    }

    @Test
    fun sectionsAreGuideOrganizationNotChronologicalChapters() {
        val globalGuide = Gothic3QuestData.part(7)
        assertEquals("THE 12 FIRE CHALICES — COMPLETE CHECKLIST", globalGuide.first().title)
        assertTrue(globalGuide.all { it.category.contains("GUIDE") })
    }
}
