package com.sliverzfx.gothicquest

internal object Risen3Chapter2Data {
    val quests: List<Quest> = buildList {
        addAll(Risen3Chapter2Part1Data.quests)
        addAll(Risen3Chapter2Part2Data.quests)
        addAll(Risen3Chapter2Part3Data.quests)
        addAll(Risen3Chapter2Part4Data.quests)
    }

    val notes: List<RisenGuideNote> = buildList {
        addAll(Risen3Chapter2Part1Data.notes)
        addAll(Risen3Chapter2Part2Data.notes)
        addAll(Risen3Chapter2Part3Data.notes)
        addAll(Risen3Chapter2Part4Data.notes)
    }
}
