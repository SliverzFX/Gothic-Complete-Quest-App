package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class QuestJournalTest {
    @Test fun selectingStatusMovesQuestBetweenExclusiveSets() {
        val completed = setOf("G1|first", "R1|other")
        val progress = setOf("G1|second")
        val started = withQuestStatus(completed, progress, "G1|first", QuestStatus.IN_PROGRESS)
        assertFalse("G1|first" in started.completed)
        assertTrue("G1|first" in started.inProgress)
        assertTrue("R1|other" in started.completed)
        val finished = withQuestStatus(started.completed, started.inProgress, "G1|first", QuestStatus.COMPLETED)
        assertTrue("G1|first" in finished.completed)
        assertFalse("G1|first" in finished.inProgress)
        val reset = withQuestStatus(finished.completed, finished.inProgress, "G1|first", QuestStatus.NOT_STARTED)
        assertFalse("G1|first" in reset.completed)
        assertFalse("G1|first" in reset.inProgress)
    }
    @Test fun oldCompletionMarksStillShowCompleted() {
        assertEquals(QuestStatus.COMPLETED, questStatus(setOf("G1|q"), emptySet(), "G1|q"))
        assertEquals(QuestStatus.NOT_STARTED, questStatus(emptySet(), emptySet(), "G1|q"))
        assertEquals(QuestStatus.IN_PROGRESS, questStatus(emptySet(), setOf("G1|q"), "G1|q"))
    }
}
