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

    // Preserve all 426 legacy IDs for old saves, deep links and search. Reference
    // entries are browsable but are NOT independent in-game journal quests.
    fun isGuideEntry(quest: Quest): Boolean =
        quest.chapter == 7 || quest.category.startsWith("GUIDE", ignoreCase = true)

    val journalQuests: List<Quest> = quests.filterNot(::isGuideEntry)
    val guideEntries: List<Quest> = quests.filter(::isGuideEntry)

    fun part(number: Int): List<Quest> =
        quests.filter { it.chapter == number }.sortedBy { it.playOrder }

    fun chapterJournalQuests(number: Int): List<Quest> = part(number).filterNot(::isGuideEntry)
}
