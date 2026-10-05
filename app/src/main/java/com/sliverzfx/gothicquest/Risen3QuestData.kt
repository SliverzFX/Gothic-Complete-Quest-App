package com.sliverzfx.gothicquest

internal object Risen3QuestData {
    // Guide content will be populated from the supplied chapter notes.
    val quests: List<Quest> = emptyList()

    fun chapter(chapter: Int): List<Quest> =
        quests.filter { it.chapter == chapter }.sortedBy { it.playOrder }

    fun notes(chapter: Int): List<RisenGuideNote> = emptyList()
}
