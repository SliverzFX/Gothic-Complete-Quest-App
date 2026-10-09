package com.sliverzfx.gothicquest

internal object ArcholosChapter6Part1Data {
    val quests: List<Quest> = listOf(
        Quest(
            id = "AR-C6-001",
            chapter = 6,
            playOrder = 1,
            title = "Black Hour",
            category = "MAIN STORY — Guild/story route",
            giver = "Ingolf",
            location = "Silbach / signal tower",
            prerequisites = "Route: Guild/story route",
            summary = "Respond to the emergency, clear the signal tower and push toward the burning city.",
            walkthroughSteps = listOf("Speak with Ingolf at the Chapter 6 opening and pick up/read The Might of Balance book lying nearby.", "Use the available teleport/travel route to the burning signal tower south of Silbach.", "Clear the Wolf Sons/hostile force around the tower and search the site for any quest-marked evidence.", "Move through Silbach and help survivors/defenders as the journal updates toward Archolos.", "Before climbing the final hill/approach to the city, make a permanent save and restock."),
            reward = "The emergency route reaches the final defence of Archolos and A City On Fire continues.",
            warnings = "",
            searchTags = listOf("CHAPTER 6 — BLOOD ON HANDS", "", "Guild/story route", "Canonical quest"),
        ),
        Quest(
            id = "AR-C6-002",
            chapter = 6,
            playOrder = 2,
            title = "A City On Fire",
            category = "MAIN STORY — All routes",
            giver = "Trigger quest",
            location = "Burning Archolos",
            prerequisites = "Route: All routes",
            summary = "Fight through the ruined city, help the surviving factions, defeat Ulryk in the town hall, then enter the Sewers and confront Volker.",
            walkthroughSteps = listOf("You arrive in a special burning version of Archolos; your old city map no longer tracks your position reliably.", "Head toward the Merchants' Guild courtyard through the gate near Frida's shop. Help Adelard clear Volker's men, then go upstairs inside the Guild HQ and defeat Bradlock and his two lackeys.", "Continue around the district and meet Sall. Agree to help the Fire Mages so his squad joins you for the next fights.", "Enter the Fire Mages' Quarters, rescue Runar if he survived earlier events, then reach Tengral upstairs. Follow him to the Temple of Innos.", "Use Tengral's key to enter the church from the graveyard side. Stop Trimegisto from burning the people inside: kill him or put him to sleep.", "The normal route onward is blocked. Shoot the boards covering the opening, climb through the building and drop into the Artisans' District.", "Push toward the City Guard barracks. You can run past some enemies and operate the far-gate winch so Tengral and the others can enter and help.", "After the square is secure, find Lennart in the sleeping-quarters back room. Roderich has moved to Old Town.", "Go to Volker's mansion. Fight Yezegan and the guards outside, kick the door open, then search upstairs for the burned documents and Volker's Overburnt Note. It confirms the showdown is in Old Town.", "Meet Riordian outside and take him toward Old Town. Help the City Guard hold the square until the Fire Mages arrive.", "Enter the town hall and immediately run inside before the invisible boss barrier forms. Defeat Ulryk and recover the Peacemaker.", "After the cutscene, head down the ramp to the Sewers. Take the low-corner entrance and descend the elevator.", "Meet Ivy. Depending on your earlier relationship choices, you may have to fight her or can convince her to join you for the last battle.", "Continue to the final chamber and fight Volker. Keep pressure on him until his regeneration effect wears off; ranged attacks, Dust Devil before his final phase, summons and regeneration potions all help.", "When Volker drops below roughly one-quarter health he becomes crippled and easier to finish. Decide his final fate after the fight."),
            reward = "Marvin's story ends and the game proceeds to its final epilogue.",
            warnings = "Make an archive save before entering the town hall. If the Ulryk boss barrier forms while you are still in the doorway, you can become locked outside the fight.",
            searchTags = listOf("CHAPTER 6 — BLOOD ON HANDS", "", "All routes", "Final main quest"),
        ),
    )
}
