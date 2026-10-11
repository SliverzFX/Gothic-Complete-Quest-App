package com.sliverzfx.gothicquest

/** GameBanshee's four prologue journal steps previously collapsed into other walkthroughs.
 * In-game order: R1-C1-001 -> 194 -> 195 -> 196 -> 197 -> 002.
 * Source: https://www.gamebanshee.com/risen/walkthrough/prologuequests.php
 */
internal object RisenGuidePart14Data {
    val quests: List<Quest> = listOf(
        Quest(
            id = "R1-C1-194",
            chapter = 1,
            playOrder = 194,
            title = "Take Sara to Safety",
            category = "PROLOGUE — JOURNAL SUBQUEST",
            giver = "Sara",
            location = "Shipwreck beach to abandoned house",
            prerequisites = "Speak with Sara after finding her alive in R1-C1-001.",
            summary = "Escort Sara along the torch-marked trail toward the abandoned house.",
            walkthroughSteps = listOf("After finding Sara, speak with her and agree to seek shelter inland.", "Follow the torch-marked path past the cave and wolf toward the abandoned house.", "Approach the house until Sara asks you to investigate it."),
            reward = "25 XP",
            warnings = "Chronology: between R1-C1-001 and R1-C1-002. Shown in the completionist appendix to preserve existing quest order and saved progress.",
            searchTags = listOf("Take Sara to Safety", "Sara", "Prologue", "Sara", "Abandoned house"),
        ),
        Quest(
            id = "R1-C1-195",
            chapter = 1,
            playOrder = 195,
            title = "Investigate the Abandoned House",
            category = "PROLOGUE — JOURNAL SUBQUEST",
            giver = "Sara",
            location = "Abandoned house near the shipwreck beach",
            prerequisites = "Reach the house with Sara in Take Sara to Safety (R1-C1-194).",
            summary = "Enter the abandoned house to verify that nobody is inside.",
            walkthroughSteps = listOf("Listen to Sara's request to investigate the shelter.", "Enter the house and search its rooms; clearing the nearby cave is not necessary.", "Confirm the house is empty and talk to Sara to begin searching for the key."),
            reward = "25 XP",
            warnings = "Chronology: after R1-C1-194 and before R1-C1-002. Shown in the completionist appendix to preserve existing quest order and saved progress.",
            searchTags = listOf("Investigate the Abandoned House", "Sara", "Prologue", "Sara", "Abandoned house"),
        ),
        Quest(
            id = "R1-C1-196",
            chapter = 1,
            playOrder = 196,
            title = "Find the Key in the Abandoned House",
            category = "PROLOGUE — JOURNAL SUBQUEST",
            giver = "Sara",
            location = "Abandoned house near the shipwreck beach",
            prerequisites = "Investigate the Abandoned House (R1-C1-195) and speak with Sara.",
            summary = "Find the key beside the bed in the house's second room.",
            walkthroughSteps = listOf("Sara suggests that a key might be hidden nearby.", "Search the second room and pick up the key next to the bed.", "Picking up the key awards XP and opens the chest-looting objective."),
            reward = "25 XP",
            warnings = "Chronology: after R1-C1-195 and before R1-C1-002. Shown in the completionist appendix to preserve existing quest order and saved progress.",
            searchTags = listOf("Find the Key in the Abandoned House", "Sara", "Prologue", "Sara", "Abandoned house"),
        ),
        Quest(
            id = "R1-C1-197",
            chapter = 1,
            playOrder = 197,
            title = "Loot the Chest in the Abandoned House",
            category = "PROLOGUE — JOURNAL SUBQUEST",
            giver = "Sara / journal progression",
            location = "Abandoned house near the shipwreck beach",
            prerequisites = "Find the Key in the Abandoned House (R1-C1-196).",
            summary = "Open the chest and take its contents, including the frying pan.",
            walkthroughSteps = listOf("After finding the key, return to the chest inside the abandoned house.", "Unlock the chest and take its contents, especially the frying pan.", "Looting the chest completes the quest; talk with Sara to start R1-C1-002."),
            reward = "25 XP; frying pan and other chest supplies",
            warnings = "Chronology: immediately before R1-C1-002. Shown in the completionist appendix to preserve existing quest order and saved progress.",
            searchTags = listOf("Loot the Chest in the Abandoned House", "Sara / journal progression", "Prologue", "Sara", "Abandoned house"),
        )
    )
}
