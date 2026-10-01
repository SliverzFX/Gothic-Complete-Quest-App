package com.sliverzfx.gothicquest

object NewBalanceChapter6Data {
    val quests = listOf(
        Quest(
            id = "CH6-001",
            chapter = 6,
            playOrder = 1,
            title = "Halls of Irdorath",
            category = "Main Story",
            giver = "Automatic",
            location = "Main Story",
            prerequisites = "Sail from Khorinis on the Esmeralda; Chapter 6 begins.",
            summary = "Use the ship as your base: speak with the crew, train, trade and prepare before entering the island complex. Fight through the elite orcs, shamans and named leaders, including Ur'Trax and Ur-Vatah, and collect the keys needed to advance. Search the prison cells; Pedro can be found in the third cell and may be killed or escorted back depending on your choice. After the throne-hall progression, deal with the orcs attacking the ship, then return and use the hidden torch/lever route onward. Continue through the dragon-lizard caves, speak to and defeat Trakanon, solve the chasm by shooting the window switches to extend the bridge, and fight through the Shadow Lords and Dark Masters. Defeat Dementor and take the key, obtain the Eye of Power from the far-right room, then use the corpse/spell interaction at the sealed door to open the Inner Sanctuary. Optional altar encounters can be completed before the final battle. Defeat the Undead Dragon to finish the main objective.",
            walkthroughSteps = listOf("Use the ship as your base: speak with the crew, train, trade and prepare before entering the island complex. Fight through the elite orcs, shamans and named leaders, including Ur'Trax and Ur-Vatah, and collect the keys needed to advance. Search the prison cells; Pedro can be found in the third cell and may be killed or escorted back depending on your choice. After the throne-hall progression, deal with the orcs attacking the ship, then return and use the hidden torch/lever route onward. Continue through the dragon-lizard caves, speak to and defeat Trakanon, solve the chasm by shooting the window switches to extend the bridge, and fight through the Shadow Lords and Dark Masters. Defeat Dementor and take the key, obtain the Eye of Power from the far-right room, then use the corpse/spell interaction at the sealed door to open the Inner Sanctuary. Optional altar encounters can be completed before the final battle. Defeat the Undead Dragon to finish the main objective."),
            reward = "Endgame loot, completion of the Irdorath main objective and automatic start of Back to the Ship.",
            warnings = "The ship remains your support hub for most of the chapter. Use crew services before major fights. Dementor's dialogue can remove a large portion of your health unless you have the appropriate mental-protection effect, such as the Call of Soul amulet or relevant class protection. If Mario joined your crew, his event occurs during this chapter.",
            searchTags = listOf("halls of irdorath", "main story", "automatic")
        ),
        Quest(
            id = "CH6-002",
            chapter = 6,
            playOrder = 2,
            title = "Back to the Ship",
            category = "Main Story",
            giver = "Automatic",
            location = "Main Story",
            prerequisites = "Defeat the Undead Dragon.",
            summary = "Leave the Inner Sanctuary after the Undead Dragon is dead. Work your way back through Irdorath and speak with crew members you encounter on the return route. Finish any final conversations, trading or looting you still want, then return to the Esmeralda. Speak with your captain when you are ready to leave. That conversation completes the voyage and ends the default main story.",
            walkthroughSteps = listOf("Leave the Inner Sanctuary after the Undead Dragon is dead. Work your way back through Irdorath and speak with crew members you encounter on the return route. Finish any final conversations, trading or looting you still want, then return to the Esmeralda. Speak with your captain when you are ready to leave. That conversation completes the voyage and ends the default main story."),
            reward = "Completes the default Gothic II: New Balance main-story route.",
            warnings = "Treat the final captain conversation as the end-of-game trigger. Finish any last Irdorath exploration or crew business before selecting it.",
            searchTags = listOf("back to the ship", "main story", "automatic")
        ),
        Quest(
            id = "CH6-003",
            chapter = 6,
            playOrder = 3,
            title = "The Best Armor in the World",
            category = "Halls of Irdorath",
            giver = "Bennet",
            location = "Halls of Irdorath",
            prerequisites = "Chapter 6; Bennet must be recruited to the voyage. Exact guild/difficulty/stat restrictions can vary by New Balance build.",
            summary = "Speak with Bennet on Irdorath about creating an ultimate set of armor. The long-standing quest recipe requires a large stock of dragon materials and rare components: 50 dragon scales, 20 pieces of magic ore, 10 sulfur, 5 black pearls, 2 containers of resin/pitch, and 4 dragon skulls. Gather or bring the materials and hand them to Bennet. The older detailed walkthrough indicates that the armor is completed after the Dementor stage of Irdorath; return to Bennet after that progression and collect the finished armor.",
            walkthroughSteps = listOf("Speak with Bennet on Irdorath about creating an ultimate set of armor. The long-standing quest recipe requires a large stock of dragon materials and rare components: 50 dragon scales, 20 pieces of magic ore, 10 sulfur, 5 black pearls, 2 containers of resin/pitch, and 4 dragon skulls. Gather or bring the materials and hand them to Bennet. The older detailed walkthrough indicates that the armor is completed after the Dementor stage of Irdorath; return to Bennet after that progression and collect the finished armor."),
            reward = "Endgame Dragon Slayer-style armor / high-tier warrior armor, subject to current-build class restrictions.",
            warnings = "The current 2026 master index still lists this Chapter 6 quest, but the most detailed public recipe walkthrough is from an older Returning/New Balance lineage. Exact eligible guilds, attribute requirements and difficulty restrictions have changed across builds, so this guide deliberately does not invent a current Strength/stamina threshold. Bring Bennet and keep dragon scales/skulls if you want to attempt the quest.",
            searchTags = listOf("the best armor in the world", "halls of irdorath", "bennet")
        )
    )
}
