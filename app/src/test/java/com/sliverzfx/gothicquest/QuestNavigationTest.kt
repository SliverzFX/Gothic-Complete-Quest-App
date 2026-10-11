package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class QuestNavigationTest {
    private fun quest(id: String, chapter: Int, order: Int) = Quest(
        id, chapter, order, id, category = "", giver = "", location = "",
        prerequisites = "", summary = "", walkthroughSteps = emptyList(),
        reward = "", warnings = "", searchTags = emptyList())

    private val first = quest("first", 1, 1)
    private val second = quest("second", 1, 2)
    private val third = quest("third", 2, 1)
    private val unordered = listOf(third, second, first)

    @Test fun followsPlayOrderAndCrossesChapterBoundary() {
        val neighbors = adjacentQuests(unordered, "second")
        assertEquals(first, neighbors.previous)
        assertEquals(third, neighbors.next)
    }

    @Test fun firstAndLastDoNotWrapAround() {
        assertNull(adjacentQuests(unordered, "first").previous)
        assertEquals(second, adjacentQuests(unordered, "first").next)
        assertEquals(second, adjacentQuests(unordered, "third").previous)
        assertNull(adjacentQuests(unordered, "third").next)
    }

    @Test fun hiddenQuestsAreSkippedEvenWhenCurrentQuestWasJustCompleted() {
        assertEquals(third, adjacentQuests(unordered, "first") { it.id != "second" }.next)
        val neighbors = adjacentQuests(unordered, "second") { it.id != "second" }
        assertEquals(first, neighbors.previous)
        assertEquals(third, neighbors.next)
    }

    @Test fun singleQuestAndMissingQuestHaveNoDestinations() {
        assertNull(adjacentQuests(listOf(first), "first").previous)
        assertNull(adjacentQuests(listOf(first), "first").next)
        assertNull(adjacentQuests(unordered, "missing").previous)
        assertNull(adjacentQuests(unordered, "missing").next)
    }
}
