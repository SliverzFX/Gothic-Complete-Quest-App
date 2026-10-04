package com.sliverzfx.gothicquest

internal object Risen2QuestData {
    val quests: List<Quest> = buildList {
        addAll(Risen2GuidePart1Data.quests)
        addAll(Risen2GuidePart2Data.quests)
        addAll(Risen2GuidePart3Data.quests)
        addAll(Risen2GuidePart4Data.quests)
        addAll(Risen2GuidePart5Data.quests)
        addAll(Risen2GuidePart6Data.quests)
        addAll(Risen2GuidePart7Data.quests)
        addAll(Risen2GuidePart8Data.quests)
    }

    private val guideNotes: List<RisenGuideNote> = buildList {
        addAll(Risen2GuidePart1Data.notes)
        addAll(Risen2GuidePart2Data.notes)
        addAll(Risen2GuidePart3Data.notes)
        addAll(Risen2GuidePart4Data.notes)
        addAll(Risen2GuidePart5Data.notes)
        addAll(Risen2GuidePart6Data.notes)
        addAll(Risen2GuidePart7Data.notes)
        addAll(Risen2GuidePart8Data.notes)
    }

    fun chapter(number: Int): List<Quest> =
        quests.filter { it.chapter == number }.sortedBy { it.playOrder }

    fun notes(chapter: Int): List<RisenGuideNote> =
        guideNotes.filter { it.chapter == chapter }
}
