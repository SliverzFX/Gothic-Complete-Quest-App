package com.sliverzfx.gothicquest

internal object Risen2CommandData {
    val entries: List<ToolReferenceEntry> = listOf(part0()).flatten()

    private fun part0(): List<ToolReferenceEntry> = listOf(
        ToolReferenceEntry("r2_command_setup", "SETUP", "Enable the PC console", "During gameplay, type pommes quickly using the English keyboard layout. Open the console with the backtick/tilde key; its printed symbol depends on keyboard layout. Repeat activation if necessary. Use Tab for completion and preserve exact case. These commands are for the PC game.", command = "pommes", source = "https://gamefaqs.gamespot.com/pc/622499-risen-2-dark-waters/faqs/77368/all-item-console-commands", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_god", "GENERAL CHEATS", "God mode", "Enables invulnerability for testing combat. Keep a separate save for normal progression.", command = "God", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_invisible", "GENERAL CHEATS", "Invisibility", "Makes the hero invisible to NPCs. Attacking can end the effect.", command = "Invisible", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_gold", "GENERAL CHEATS", "Give 1,000 gold", "Adds the specified gold quantity. It_Gold is case sensitive.", command = "give It_Gold 1000", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_list", "CONSOLE HELP", "List commands", "Displays available console commands in the installed PC version.", command = "list", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_edit", "CHARACTER EDITOR", "Edit the hero", "Opens the character editor. Confirm changes with Enter. Adding combat skills does not necessarily unlock faction merchant permission.", command = "edit PC_Hero", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_take", "INVENTORY", "Remove an item", "Example removes the Earth Amulet. Some quest items cannot be removed. Removing an item does not undo quest events.", command = "take It_Am_Earth", source = "https://gamefaqs.gamespot.com/boards/622499-risen-2-dark-waters/62714857", codeCategory = CodeCategory.GENERAL),
        ToolReferenceEntry("r2_command_case", "CONSOLE HELP", "Resolve an unknown item", "Check uppercase and lowercase letters, then use Tab completion. A quantity follows the complete template ID. Category prefixes request groups rather than single items. Bulk requests can affect quests.", command = "give It_Food 5", source = "https://gamefaqs.gamespot.com/pc/622499-risen-2-dark-waters/faqs/77368/all-item-console-commands", codeCategory = CodeCategory.GENERAL),
    )
}
