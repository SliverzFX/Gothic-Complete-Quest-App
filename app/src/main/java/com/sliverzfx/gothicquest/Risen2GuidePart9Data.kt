package com.sliverzfx.gothicquest

/** Confirmed missing Risen 2 journal quests. Appended IDs protect existing saved quest progress. */
internal object Risen2GuidePart9Data {
    val quests: List<Quest> = listOf(
        Quest(
            id = "R2-C1-047",
            chapter = 1,
            playOrder = 47,
            title = "Flotsam on the Beach Collected",
            category = "Caldera — standalone collection",
            giver = "Automatic when collecting shipwreck cargo",
            location = "Caldera beach",
            prerequisites = "Rescue Patty, then search the Caldera shoreline for six pieces of flotsam before departing for Tacarigua.",
            summary = "Find all six flotsam pieces and trigger the separate collection milestone.",
            walkthroughSteps = listOf("After rescuing Patty, return to the beach where the shipwreck debris washed ashore.", "Collect each of the six flotsam objects along the shoreline. Watch for two Sand Devils near the westernmost cargo.", "Picking up the sixth piece completes this collection quest. Report to the Storehouse Master for his separate Flotsam hand-in reward."),
            reward = "50 Glory; 50 Glory for each of the two Sand Devils killed separately",
            warnings = "Missable before To Tacarigua. This is an independent journal completion, not the Storehouse Master's 200-Gold hand-in.",
            searchTags = listOf("Flotsam on the Beach Collected", "Automatic when collecting shipwreck cargo", "Caldera beach", "Caldera — standalone collection"),
        ),
        Quest(
            id = "R2-C2-080",
            chapter = 2,
            playOrder = 80,
            title = "Got Anything to Eat?",
            category = "Sword Coast — Inquisition side quest",
            giver = "Sancho",
            location = "Northwest of Puerto Isabella",
            prerequisites = "Reach the Inquisition soldiers patrolling northwest of Puerto Isabella and speak to Sancho with at least one set of Provisions.",
            summary = "Give one set of Provisions to Sancho when he first asks for food.",
            walkthroughSteps = listOf("Find Sancho among the Inquisition patrol northwest of Puerto Isabella.", "Agree to feed him and hand over one set of Provisions when he first asks.", "Receive 50 Glory. Giving him food once more through the follow-up dialogue can grant another 25 Glory."),
            reward = "50 Glory; optional additional 25 Glory for second food hand-in",
            warnings = "Refusing the first request fails the quest; the optional second helping remains available separately.",
            searchTags = listOf("Got Anything to Eat?", "Sancho", "Northwest of Puerto Isabella", "Sword Coast — Inquisition side quest"),
        ),
        Quest(
            id = "R2-C3-076",
            chapter = 3,
            playOrder = 76,
            title = "The Treasure on Fortress Beach",
            category = "Caldera II — treasure hunt",
            giver = "Treasure map found in the Crystal Fortress",
            location = "Caldera Crystal Fortress → beach cave",
            prerequisites = "Return to Caldera and gain Lockpicking 60 to open the chest near Carlos's tower containing the treasure map.",
            summary = "Find the hidden treasure chest in the cave below the fortress using the discovered map.",
            walkthroughSteps = listOf("Locate the locked chest near Carlos in the Crystal Fortress. You need Lockpicking 60 to open it and obtain the treasure map.", "Walk down to the Caldera shore, find the cave below the fortress near the beach where Patty was rescued, and follow the marked location.", "Dig up the buried chest inside the cave. It contains valuables and the legendary Comb with One Tooth."),
            reward = "100 Glory; Comb with One Tooth and other treasure",
            warnings = "This optional Caldera II treasure may also be recovered later once you have enough thieving skill.",
            searchTags = listOf("The Treasure on Fortress Beach", "Treasure map found in the Crystal Fortress", "Caldera Crystal Fortress → beach cave", "Caldera II — treasure hunt"),
        )
    )
}
