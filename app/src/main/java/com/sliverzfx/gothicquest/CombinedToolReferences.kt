package com.sliverzfx.gothicquest

internal data class ToolReferenceCard(
    val entry: ToolReferenceEntry,
    val sources: Set<String>
)

/** Keeps the original databases intact; builds one searchable view of codes and item details. */
internal fun combineToolReferences(
    codes: List<ToolReferenceEntry>,
    items: List<ToolReferenceEntry>
): List<ToolReferenceCard> {
    val cards = codes.map { entry ->
        ToolReferenceCard(entry.copy(codeCategory = entry.codeCategory ?: CodeCategory.GENERAL), setOf(entry.source))
    }.toMutableList()
    val inventoryCodes = codes.withIndex().filter { (_, entry) ->
        entry.codeCategory != CodeCategory.CHARACTERS &&
            entry.command?.substringBefore(' ')?.lowercase() in setOf("insert", "give")
    }
    val byCode = inventoryCodes.groupBy { it.value.command!!.substringAfter(' ').trim().lowercase() }
    val byTitle = inventoryCodes.groupBy { referenceTitle(it.value.title).lowercase() }
    items.forEach { item ->
        val key = when {
            "_item_" in item.id -> item.id.substringAfter("_item_")
            item.id.startsWith("g3_legacy_") -> item.id.removePrefix("g3_legacy_")
            else -> null
        }
        // An explicit but unmatched ID must not fall back to another item with the same name.
        val match = if (key != null) {
            val matches = byCode[key.lowercase()].orEmpty()
            matches.firstOrNull { it.value.command!!.substringAfter(' ').trim() == key }
                ?: matches.singleOrNull()
        } else byTitle[referenceTitle(item.title).lowercase()]?.singleOrNull()
        if (match != null) {
            val current = cards[match.index]
            cards[match.index] = current.copy(
                entry = current.entry.copy(
                    title = referenceTitle(current.entry.title),
                    body = mergeReferenceBodies(current.entry.body, item.body)
                ),
                sources = current.sources + item.source
            )
        } else {
            cards.add(ToolReferenceCard(item.copy(codeCategory = item.codeCategory ?: itemReferenceCategory(item.group)), setOf(item.source)))
        }
    }
    return cards
}

private fun referenceTitle(title: String): String = title.removePrefix("Spawn ")
    .removeSuffix(" — equipment stats").removeSuffix(" — historical stats").trim()

private fun mergeReferenceBodies(codeBody: String, itemBody: String): String {
    val code = codeBody.trim()
    val item = itemBody.trim()
    if (item.isEmpty() || code.contains(item)) return code
    if (code.isEmpty() || item.contains(code)) return item
    return item + "\n\n" + code
}

private fun itemReferenceCategory(group: String): CodeCategory {
    val label = group.uppercase()
    return when {
        listOf("ARMOR", "ARMOUR", "ROBE", "SHIELD", "HELMET").any { it in label } -> CodeCategory.ARMOR
        listOf("WEAPON", "SWORD", "BOW", "STAFF", "STAVES", "AMMUNITION").any { it in label } -> CodeCategory.WEAPONS
        else -> CodeCategory.ITEMS
    }
}
