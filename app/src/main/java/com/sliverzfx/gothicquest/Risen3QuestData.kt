package com.sliverzfx.gothicquest

internal object Risen3QuestData {
    val quests: List<Quest> = buildList {
        addAll(Risen3Chapter1Data.quests)
        addAll(Risen3Chapter2Data.quests)
        addAll(Risen3Chapter3Data.quests)
        addAll(Risen3Chapter4Data.quests)
    }

    private val guideNotes: List<RisenGuideNote> = buildList {
        addAll(Risen3Chapter1Data.notes)
        addAll(Risen3Chapter2Data.notes)
        addAll(Risen3Chapter3Data.notes)
        addAll(Risen3Chapter4Data.notes)
    }

    fun chapter(number: Int): List<Quest> =
        quests.filter { it.chapter == number }.sortedBy { it.playOrder }

    fun notes(chapter: Int): List<RisenGuideNote> =
        guideNotes.filter { it.chapter == chapter }
}
