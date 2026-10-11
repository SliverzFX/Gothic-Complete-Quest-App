package com.sliverzfx.gothicquest

internal object ArcholosChapter6Part1Data {
    val quests: List<Quest> = listOf(
        Quest(
            id = "AR-C6-001",
            chapter = 6,
            playOrder = 1,
            title = "Black Hour",
            category = "MAIN STORY — Guild/story route",
            giver = "Ingolf (monastery); Chapter 6 transition follows Rightful Heir",
            location = "Silbach / signal tower",
            prerequisites = "Chapter 6; complete Rightful Heir and the monastery debrief after Vardhal.",
            summary = "Help Ingolf with the burning signal tower, then decide whether to pursue the final showdown or accept an early ending.",
            walkthroughSteps = listOf("At the monastery, follow Ingolf to his quarters and pick up The Might of Balance from the FLOOR BEHIND him. Read it and speak with Ingolf and Notger.", "Optional: visit Badulf in the second courtyard; he offers healing and sells late-game magic equipment and runes.", "Return to Ingolf and ask him to teleport you to the burning signal tower south of Silbach.", "Fight alongside Ingolf against the Wolf Sons attacking the tower. Let Ingolf investigate Silbach and then report what he learns about Archolos.", "MAKE A PERMANENT SAVE before climbing the hill for the final choice. You can abandon the chase against Volker and Ulryk to end the game early, or choose to continue.", "Continuing the final confrontation completes Black Hour and starts A City on Fire in the destroyed Archolos instance."),
            reward = "Opens the final battle in burning Archolos, or allows a shorter alternate ending if you decide not to pursue Volker and Ulryk.",
            warnings = "MAJOR ENDING CHOICE: Chapter 6 allows an early ending at Ingolf's hill conversation. Save beforehand if you want to play A City on Fire. Read The Might of Balance where it lies on the floor behind Ingolf.",
            searchTags = listOf("CHAPTER 6 — BLOOD ON HANDS", "", "Guild/story route", "Canonical quest"),
        ),
        Quest(
            id = "AR-C6-002",
            chapter = 6,
            playOrder = 2,
            title = "A City On Fire",
            category = "MAIN STORY — All routes",
            giver = "Automatic after choosing to continue from Black Hour",
            location = "Burning Archolos",
            prerequisites = "Chapter 6; in Black Hour, choose to pursue Volker and Ulryk rather than the early-ending dialogue.",
            summary = "Fight through the burning city, save surviving allies, defeat Ulryk and recover the Peacemaker, then confront Ivy and Volker beneath Archolos.",
            walkthroughSteps = listOf("Enter the BURNING version of Archolos, a separate location where older city maps no longer track you. Prepare for encounters determined by previous quest choices.", "Enter past Frida's shop and help Adelard clear the Merchants' Guild courtyard. Rush upstairs in the guild HQ to defeat Bradlock and two accomplices before he can reach Lorenzo; Lorenzo survives only if Javad was saved and sent to the city earlier.", "Find Sall and convince his squad to help the Fire Mages. Enter the Fire Mages' Quarters, rescue Runar if he survived Heaven's Will and find Tengral upstairs.", "Follow Tengral to the Temple of Innos. Take the church key, enter from the graveyard and stop Trimegisto from burning everyone inside, either by killing or putting him to sleep.", "The road onward is blocked. SHOOT the planks covering an upper opening, climb through the building and drop down into the Artisans' District.", "Proceed past the possible Blake encounter toward the City Guard barracks. You can operate the far gate's WINCH to let Tengral and his guards enter and help against Volker's men.", "After the fight, find Lennart in the back sleeping quarters; Roderich has moved to Old Town. Speak with Tengral and continue to Volker's mansion, fighting Yezegan at the ramp.", "Kick open Volker's mansion door. Decide whether to spare Alfred, read Eva's Smeared Letter on the stove, and investigate the burned documents upstairs; take the Overburnt Note from the FIREPLACE, which points to Old Town.", "Meet Riordian outside; escort him toward Old Town. Help the City Guard hold the town hall square until the Fire Mages arrive.", "MAKE AN ARCHIVE SAVE before entering the town hall. RUN ALL THE WAY INSIDE to fight Ulryk; his invisible boss barrier can lock you outside if you stop in the doorway. Defeat Ulryk and reclaim the Peacemaker.", "After Notger's cutscene, descend the low-corner entrance into the sewers and take the elevator. Meet Ivy: normally she fights you, but a specific relationship route allows her to join you for the final confrontation.", "For the peaceful Ivy option, you must have teamed up with her in Chapter 3 AND chosen the successful flirtation on arrival at Wolf's Den in Chapter 4. Otherwise be ready to defeat her.", "Fight Volker in the final chamber. His healing/regeneration initially makes him tough; use ranged damage, summons and status spells, although Dust Devil becomes ineffective below roughly 25% HP.", "When Volker is beaten and crippled, decide his final fate to trigger the game's epilogue."),
            reward = "Final showdown with Ulryk and Volker, recovery of the Peacemaker and completion of Marvin's Archolos story. Javad/Ivy outcomes and surviving NPCs depend on earlier decisions.",
            warnings = "DOOR SOFTLOCK: running too slowly into Ulryk's town hall arena can leave you outside an invisible barrier. SAVE before entry. The compassionate Ivy resolution requires both her Chapter 3 alliance and the right Wolf's Den flirtation. Lorenzo survives only if Javad was saved and sent to the city.",
            searchTags = listOf("CHAPTER 6 — BLOOD ON HANDS", "", "All routes", "Final main quest"),
        ),
    )
}
