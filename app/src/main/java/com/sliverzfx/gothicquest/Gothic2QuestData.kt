package com.sliverzfx.gothicquest

object Gothic2QuestData {
    val quests = Gothic2Chapter1Part1.quests +
        Gothic2Chapter1Part2.quests +
        Gothic2Chapter1Part3.quests +
        Gothic2Chapter1Part4.quests +
        Gothic2Chapter1Part5.quests

    fun chapter(number: Int) = quests.filter { it.chapter == number }.sortedBy { it.playOrder }
}