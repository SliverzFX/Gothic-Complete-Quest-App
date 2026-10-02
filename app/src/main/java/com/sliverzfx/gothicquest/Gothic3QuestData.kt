package com.sliverzfx.gothicquest

object Gothic3QuestData {
    val quests: List<Quest> = buildList {
        addAll(Gothic3Part1Data.quests)
        addAll(Gothic3Part2Data.quests)
        addAll(Gothic3Part3Data.quests)
        addAll(Gothic3Part4Data.quests)
        addAll(Gothic3Part5Data.quests)
        addAll(Gothic3Part6Data.quests)
        addAll(Gothic3Part7Data.quests)
    }

    fun part(number: Int): List<Quest> =
        quests.filter { it.chapter == number }.sortedBy { it.playOrder }
}
