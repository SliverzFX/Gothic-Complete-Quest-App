package com.sliverzfx.gothicquest

internal data class QuestNeighbors(val previous: Quest? = null, val next: Quest? = null)

internal fun adjacentQuests(
    quests: List<Quest>,
    currentId: String,
    isVisible: (Quest) -> Boolean = { true }
): QuestNeighbors {
    val ordered = quests.filter { it.id == currentId || isVisible(it) }
        .sortedWith(compareBy<Quest> { it.chapter }.thenBy { it.playOrder })
    val index = ordered.indexOfFirst { it.id == currentId }
    if (index < 0) return QuestNeighbors()
    return QuestNeighbors(ordered.getOrNull(index - 1), ordered.getOrNull(index + 1))
}
