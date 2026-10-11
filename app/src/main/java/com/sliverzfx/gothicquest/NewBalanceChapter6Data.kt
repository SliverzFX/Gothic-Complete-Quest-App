package com.sliverzfx.gothicquest

object NewBalanceChapter6Data {
    val quests = listOf(
        Quest(
            id = "CH6-001",
            chapter = 6,
            playOrder = 1,
            title = "Halls of Irdorath",
            category = "Main Story — Halls of Irdorath",
            giver = "None — automatically begins on arrival in Chapter 6",
            location = "Halls of Irdorath — landing, orc throne room, dragon caves and Inner Sanctuary",
            prerequisites = "Sail aboard the Esmeralda after recruiting a captain and crew; Chapter 6 begins when you arrive at Irdorath.",
            summary = "Fight through the orc stronghold, rescue Pedro if desired, defeat Trakanon and the Shadow Lords, open the Inner Sanctuary, and confront the Undead Dragon.",
            walkthroughSteps = listOf(
                "On arriving at Irdorath, speak to every available crew member, train, trade and refill supplies. The Esmeralda remains accessible during the assault, so return to it when necessary.",
                "Fight the elite orcs, crossbowmen and shamans guarding the landing and cave. Defeat named shaman Ur'Trax among the elders, then turn LEFT into the orc throne hall.",
                "Kill horde commander Ur-Vatah and take his key. Advance far enough past his throne to trigger the journal update. Before opening the next section, use his key to search all three prison cells: two contain loot and the third holds Pedro.",
                "Choose whether to kill Pedro or spare him. If spared, escort him back to the Esmeralda. Once the throne-hall journal trigger fires, orc warriors attack the ship; help the crew defeat them and speak with Pedro aboard the ship.",
                "Return to Ur-Vatah's throne hall. Face the sealed passage and activate the LEFT wall torch to open it; the 2026 New Balance guide specifies the left torch, not a left-then-right sequence.",
                "Enter the dragon-breeding caves. Fight the Seeker and powerful dragon lizards, collect the dragon eggs for the optional Embarla Firgasto potion, then speak with dragon Trakanon before defeating it.",
                "At the next great gate, defeat the Seekers. Shoot the switches visible through the windows of the two towers across the chasm with a bow or crossbow; a bridge extends so you can cross.",
                "Cross the gorge populated by Shadow Lords and enter the temple. Defeat converted knights and Shadow Lord Ar'Hol. Take his key, unlock the neighboring room, and collect its journal and dragon-egg potion recipe.",
                "Continue through the halls; if you recruited Mario, his confrontation can occur here. Defeat Shadow Lord Argol and continue through the burial halls into the large chamber occupied by Dark Masters.",
                "Defeat the Dark Masters carefully, then confront Dementor. Without the Call of Soul amulet from Dark Wanderers or relevant class protection, his dialogue can remove 50% of your health. Kill Dementor and take his key.",
                "Use the key to enter the far-right room and open the chest containing the Eye of Power. At the sealed doorway, kneel and read the spell obtained from the defeated foe's corpse to open the Inner Sanctuary.",
                "Fight the strengthened corrupted paladins inside the Inner Sanctuary. Two optional sacrificial altars can summon the dragon-lizard bosses Ish'Tar and Aru'Tar; these fights are not required to finish the main quest.",
                "Approach the Undead Dragon, complete its dialogue and defeat it. Back to the Ship starts automatically after the dragon dies."
            ),
            reward = "Defeating the Undead Dragon completes Irdorath's principal battle and automatically begins Back to the Ship. Optional loot includes dragon eggs, the Embarla Firgasto recipe, the Eye of Power and treasures from the temple bosses; exact staged XP not independently verified.",
            warnings = "QUEST STARTS AUTOMATICALLY: there is no speaking quest giver. After Ur-Vatah, use the LEFT torch only. Save Pedro before advancing if you want his peaceful ending. Keep dragon eggs for Embarla Firgasto; prepare for Dementor's 50% health penalty unless mentally protected. Mario's encounter depends on recruiting him.",
            searchTags = listOf("halls of irdorath", "main story", "automatic")
        ),
        Quest(
            id = "CH6-002",
            chapter = 6,
            playOrder = 2,
            title = "Back to the Ship",
            category = "Main Story — Halls of Irdorath",
            giver = "None — automatically begins after defeating the Undead Dragon",
            location = "Inner Sanctuary → temple corridors → Esmeralda",
            prerequisites = "Chapter 6; defeat the Undead Dragon at the end of Halls of Irdorath.",
            summary = "Return to the Esmeralda after defeating the Undead Dragon, speak with your scattered companions and tell the captain to sail.",
            walkthroughSteps = listOf(
                "Once the Undead Dragon is dead, leave the Inner Sanctuary by the route you entered. The quest begins automatically; you do not need to talk to a quest giver.",
                "Make your way back through the temple and speak to the crew members who have wandered away from the ship and into Irdorath.",
                "Return to the Esmeralda and speak with the remaining companions about your victory. Finish any last trading, inventory management or exploration you still want.",
                "Speak to your chosen captain aboard the Esmeralda and confirm departure to complete the normal Gothic II: New Balance story."
            ),
            reward = "Completes the normal Chapter 6 ending and the default New Balance main story. No separate material reward is specified in the April 2026 walkthrough.",
            warnings = "The captain's departure dialogue finishes the playthrough. Complete optional Irdorath boss encounters, Bennet's armor collection and any final crew dialogue BEFORE confirming.",
            searchTags = listOf("back to the ship", "main story", "automatic")
        ),
        Quest(
            id = "CH6-003",
            chapter = 6,
            playOrder = 3,
            title = "The Best Armor in the World",
            category = "Irdorath — Bennet's Crafting Quest",
            giver = "Bennet",
            location = "Esmeralda, Irdorath (Bennet's onboard smithing services)",
            prerequisites = "Chapter 6; recruit Bennet for the Esmeralda. The older detailed Returning 2.0 guide limits this armor to warrior guilds and higher difficulties; check whether your New Balance build offers his dialogue.",
            summary = "Supply Bennet with dragon scales, skulls and other rare materials to commission the powerful Dragon Slayer armor.",
            walkthroughSteps = listOf(
                "Before leaving Khorinis, recruit Bennet for the Irdorath voyage. If you intend to craft his special armor, bring your saved dragon trophies and other rare materials; they can be hard to replace after sailing.",
                "Speak with Bennet aboard the Esmeralda. In older documented versions the crafting option requires a warrior class (paladin, Dragon Hunter or Temple Guard) and Hard or Legendary difficulty; current New Balance availability may differ.",
                "Prepare the documented recipe: 50 dragon scales, 20 pieces of magic ore, 10 sulfur, 5 black pearls, 2 containers of resin/pitch and 4 dragon skulls. Dragon-scale harvesting should be learned before hunting the earlier dragons.",
                "Hand the materials to Bennet and have him begin forging the armor. The older quest walkthrough states he finishes when the Dementor encounter in the Irdorath temple is resolved.",
                "After defeating Dementor, return to the Esmeralda and ask Bennet for the completed Dragon Slayer armor. Claim it before choosing the final departure."
            ),
            reward = "Dragon Slayer armor, a high-tier strength-oriented endgame armor set, forged by Bennet from the supplied ingredients. The older quest walkthrough describes no additional gold crafting fee; precise contemporary stats are build-dependent.",
            warnings = "The 50 scales, 20 magic ore, 10 sulfur, 5 black pearls, 2 pitch/resin containers and 4 dragon skulls come from an older detailed Returning 2.0 recipe; the 2026 New Balance master index confirms this quest but does not independently restate its recipe or eligibility. The four dragon skulls compete with Ragnar's dragon-summoning staff requirements. Bennet must be recruited BEFORE leaving Khorinis, and armor collection occurs late in Irdorath.",
            searchTags = listOf("the best armor in the world", "halls of irdorath", "bennet")
        )
    )
}
