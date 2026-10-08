package com.sliverzfx.gothicquest

internal val GameId.savedKeyPrefix: String
    get() = when (this) {
        GameId.GOTHIC -> "G1"
        GameId.GOTHIC_2_GOLD -> "G2"
        GameId.NEW_BALANCE -> "NB"
        GameId.GOTHIC_3 -> "G3"
        GameId.RISEN -> "R1"
        GameId.RISEN_2 -> "R2"
        GameId.RISEN_3 -> "R3"
        GameId.ARCHOLOS -> "AR"
    }

internal fun toolFavoriteKey(game: GameId, entryId: String): String = "${game.savedKeyPrefix}|tool:$entryId"

internal fun GameId.toolReferenceCards(section: ToolSection): List<ToolReferenceCard> {
    fun entries(requested: ToolSection): List<ToolReferenceEntry> = when (this) {
        GameId.GOTHIC -> GothicToolsData.entries(requested)
        GameId.GOTHIC_2_GOLD -> Gothic2ToolsData.entries(requested)
        GameId.ARCHOLOS -> ArcholosToolsData.entries(requested)
        GameId.GOTHIC_3 -> Gothic3ToolsData.entries(requested)
        GameId.RISEN -> RisenToolsData.entries(requested)
        GameId.RISEN_2 -> Risen2ToolsData.entries(requested)
        GameId.RISEN_3 -> Risen3ToolsData.entries(requested)
        GameId.NEW_BALANCE -> emptyList()
    }
    return if (section == ToolSection.USEFUL_TIPS)
        entries(section).map { ToolReferenceCard(it, setOf(it.source)) }
    else combineToolReferences(entries(ToolSection.MARVIN_CODES), entries(ToolSection.ITEMS))
}

internal data class SavedToolFavorite(val game: GameId, val card: ToolReferenceCard) {
    val category: CodeCategory get() = card.entry.codeCategory ?: CodeCategory.ITEMS
    val key: String get() = toolFavoriteKey(game, card.entry.id)
}

internal fun buildToolFavorites(keys: Set<String>): List<SavedToolFavorite> = buildList {
    GameId.entries.forEach { game ->
        if (keys.any { it.startsWith("${game.savedKeyPrefix}|tool:") }) {
            game.toolReferenceCards(ToolSection.MARVIN_CODES).forEach { card ->
                if (toolFavoriteKey(game, card.entry.id) in keys) add(SavedToolFavorite(game, card))
            }
        }
    }
}.sortedWith(compareBy<SavedToolFavorite> { it.category.ordinal }
    .thenBy { it.game.ordinal }.thenBy { it.card.entry.title.lowercase() })
