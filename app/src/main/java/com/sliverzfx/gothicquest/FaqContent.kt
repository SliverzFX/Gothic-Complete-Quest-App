package com.sliverzfx.gothicquest

internal data class FaqEntry(val id: String, val question: String, val answer: String)
internal data class FaqGroup(val title: String, val entries: List<FaqEntry>)

internal object FaqContent {
    val groups: List<FaqGroup> = listOf(
        FaqGroup("ABOUT QUESTBOUND", listOf(
            FaqEntry("what_is_questbound", "What is Questbound?",
                "Questbound is an RPG companion by SliverzFX. It brings quest walkthroughs for supported games and mods together so you can look up a quest when you get stuck."),
            FaqEntry("offline", "Does Questbound work offline?",
                "Yes. The included quest guides are stored in the app and can be read without an internet connection. Opening community links, such as YouTube or Discord, requires internet access."),
            FaqEntry("account", "Do I need an account?",
                "No Questbound account is required to read the included guides. External services such as Discord may require their own account."),
            FaqEntry("play_games", "Can I play the games inside Questbound?",
                "No. Questbound is a companion guide. It does not include the games, install mods or run them. You play your own copy separately."),
            FaqEntry("official", "Is this an official game guide?",
                "Questbound is an unofficial fan-made project by SliverzFX. It is not affiliated with or endorsed by the games’ developers or publishers.")
        )),
        FaqGroup("GAMES & GUIDE COVERAGE", listOf(
            FaqEntry("supported_games", "Which games and mods are included?",
                "The current game library includes:\n\n" + GameId.entries.joinToString("\n") { "• ${it.persistedName}" } +
                    "\n\nCoverage belongs to the included game or mod; a guide for one edition may not match another."),
            FaqEntry("night_of_raven", "Does Gothic II Gold include Night of the Raven?",
                "Yes. The Gothic II Gold guide includes Night of the Raven. Its expansion content may differ from a playthrough of the original Gothic II without the expansion."),
            FaqEntry("mod_guides", "Can I use a base-game guide for a mod?",
                "Use the separate mod guide when one is available. Mods such as New Balance and Archolos change quests, characters, locations and progression, so the base-game solution may not apply."),
            FaqEntry("versions", "Why is my quest different from the guide?",
                "Game editions, patches and mod versions can change a quest. Your faction, earlier decisions and current chapter can also affect the steps. Check that you are reading the guide for the correct game or mod, then compare the entry with your journal."),
            FaqEntry("missing_quest", "Why can’t I find a quest?",
                "Try another spelling or a distinctive word from the title, and check other chapters or All Quests. Some entries are grouped under a larger quest or a separate stage. If you still cannot find it, report the quest name and your game or mod version through Support."),
            FaqEntry("quest_names", "Why is a quest name different in my game?",
                "Translations and different game or mod releases can use different names for the same quest. The quest giver, location and events described in the walkthrough can help you identify the matching entry."),
            FaqEntry("guide_updates", "How do new guides and corrections reach the app?",
                "The guides are included with the app. New guides and corrections become available when you install an app version that contains them; the app does not currently download guide updates on its own.")
        )),
        FaqGroup("QUESTS & PLAYTHROUGHS", listOf(
            FaqEntry("spoilers", "Do the guides contain spoilers?",
                "Yes. Quest titles, solutions, warnings and outcomes can reveal story events. Open only the quest or stage you need and avoid reading ahead if you want to discover the story yourself."),
            FaqEntry("every_quest", "Can I complete every listed quest in one playthrough?",
                "Not always. Some quests belong to mutually exclusive factions or depend on choices that prevent other quests from appearing. The guide includes alternative routes for different playthroughs; you are not expected to complete every entry in one run."),
            FaqEntry("quest_order", "Do I have to follow the quests in the listed order?",
                "The order helps you browse progression, but it is not a strict route through every quest. Side quests and faction branches can be completed at different times. Follow the prerequisites and warnings in each entry, especially before advancing the story."),
            FaqEntry("chapter_placement", "Why is a side quest listed under a particular chapter?",
                "Chapter placement is a browsing aid. Some side quests can begin earlier, remain available later or finish across several chapters. An entry’s availability and warnings take priority over its position in the chapter list."),
            FaqEntry("quest_stages", "Why does one quest have several entries or parts?",
                "Long quests can return at different points in the story or split into distinct objectives and routes. Separate entries keep the relevant steps easy to find. The part or route named in the title tells you which stage you are reading."),
            FaqEntry("choices", "Will the guide tell me which choice to make?",
                "Entries may explain the options and their consequences, but the choice is yours. Where a decision affects later quests or closes a route, read the warning and consider making a separate game save first."),
            FaqEntry("rewards", "Why did I receive a different reward?",
                "Rewards can depend on dialogue choices, faction, earlier actions and game or mod version. Some entries describe the completion result rather than a fixed amount of experience or gold. Compare your journal and quest outcome with the entry."),
            FaqEntry("stuck", "What if the solution does not work in my playthrough?",
                "Read the full entry, check its prerequisites and compare your journal with the current objective. You may need an earlier conversation, item or quest stage. If it still differs, use Support and include the quest name, game or mod version, and what you have already tried."),
            FaqEntry("automatic_progress", "Does Questbound know what I have completed in the game?",
                "No. Questbound does not connect to your game or read its save files. Completion marks in the app are your own record and do not change your in-game progress."),
            FaqEntry("game_saves", "Can Questbound fix a failed quest or restore a game save?",
                "No. The app provides guidance and does not edit game saves, restore lost items or reset an in-game quest. Keep separate saves in the game before important decisions."),
            FaqEntry("cheats_required", "Do I need cheats to use the walkthroughs?",
                "No. Reading a quest guide does not require cheat mode. Choose a game, then use Codes for Marvin or console commands. Quests, Tips and Items are separate tabs on that same game page. Check what a code changes before using it in your game.")
        )),
        FaqGroup("COMMUNITY & WHAT’S NEXT", listOf(
            FaqEntry("corrections", "How can I report an incorrect or missing quest step?",
                "Open Support and share the game or mod name, its version, the chapter and quest title, and the step that needs correcting. A journal screenshot or a clear explanation of the correct solution is helpful."),
            FaqEntry("suggest_games", "Can I suggest another game or mod?",
                "Yes. Share suggestions with SliverzFX through the community links in About or Support. Suggestions help guide future coverage, but they do not guarantee a release date."),
            FaqEntry("more_games", "Will more games and mods be added?",
                "Questbound is intended to grow beyond its current library. Additional guides can be added as they are prepared and checked. There is no fixed release schedule for individual games."),
            FaqEntry("translations", "Will Questbound be available in other languages?",
                "The current focus is finishing the app and its guides in English. Translations are planned for a later stage, with no language release dates confirmed yet."),
            FaqEntry("external_links", "Why do some buttons open another app or a browser?",
                "Community destinations such as YouTube and Discord are external services. Questbound opens their links using an available app or browser; those services require an internet connection and have their own sign-in requirements.")
        ))
    )
}
