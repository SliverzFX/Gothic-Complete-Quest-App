package com.sliverzfx.gothicquest

/** Offline PC reference; individual ID cards include equipment and item details. */
internal object Risen2ToolsData {
    const val sourceNote = "Risen 2 • PC / pommes • item source 1.0.1210.0"
    val codes: List<ToolReferenceEntry> = Risen2CommandData.entries + Risen2ItemData.entries
    val items: List<ToolReferenceEntry> = emptyList()
    val tips: List<ToolReferenceEntry> = Risen2TipsData.entries
    fun entries(section: ToolSection): List<ToolReferenceEntry> = when (section) {
        ToolSection.MARVIN_CODES -> codes
        ToolSection.ITEMS -> items
        ToolSection.USEFUL_TIPS -> tips
    }
}
