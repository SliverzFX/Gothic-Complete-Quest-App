package com.sliverzfx.gothicquest

/** Curated offline Risen 3 references; no unverified item-spawn IDs. */
internal object Risen3ToolsData {
    const val sourceNote = "Risen 3 • item references / PC testmode shortcuts"
    val codes: List<ToolReferenceEntry> = Risen3CommandData.entries + Risen3ItemData.entries
    val items: List<ToolReferenceEntry> = emptyList()
    val tips: List<ToolReferenceEntry> = Risen3TipsData.entries
    fun entries(section: ToolSection): List<ToolReferenceEntry> = when (section) {
        ToolSection.MARVIN_CODES -> codes
        ToolSection.ITEMS -> items
        ToolSection.USEFUL_TIPS -> tips
    }
}
