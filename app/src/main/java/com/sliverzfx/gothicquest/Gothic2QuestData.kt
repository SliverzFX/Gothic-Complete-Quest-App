package com.sliverzfx.gothicquest

object Gothic2QuestData {
    val quests = Gothic2Chapter1Part1.quests +
        Gothic2Chapter1Part2.quests +
        Gothic2Chapter1Part3.quests +
        Gothic2Chapter1Part4.quests +
        Gothic2Chapter1Part5.quests +
        Gothic2Chapter2Data.quests +
        Gothic2Chapter3Data.quests +
        Gothic2Chapter4Data.quests +
        Gothic2Chapter5Data.quests +
        Gothic2Chapter6Data.quests

    fun chapter(number: Int) = quests.filter { it.chapter == number }.sortedBy { it.playOrder }
}

object NewBalanceQuestData {
    val quests = NewBalanceChapter1Part1.quests +
        NewBalanceChapter1Part2.quests +
        NewBalanceChapter1Part3.quests +
        NewBalanceChapter1Part4.quests

    fun chapter(number: Int) = quests.filter { it.chapter == number }.sortedBy { it.playOrder }
}
