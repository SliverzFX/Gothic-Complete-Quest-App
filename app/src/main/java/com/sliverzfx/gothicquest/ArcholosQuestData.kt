package com.sliverzfx.gothicquest

internal object ArcholosQuestData {
    val quests: List<Quest> = buildList {
        addAll(ArcholosChapter1Part1Data.quests)
        addAll(ArcholosChapter2Part1Data.quests)
        addAll(ArcholosChapter2Part2Data.quests)
        addAll(ArcholosChapter2Part3Data.quests)
        addAll(ArcholosChapter3Part1Data.quests)
        addAll(ArcholosChapter3Part2Data.quests)
        addAll(ArcholosChapter4Part1Data.quests)
        addAll(ArcholosChapter5Part1Data.quests)
        addAll(ArcholosChapter6Part1Data.quests)
    }

    private val guideNotes: List<RisenGuideNote> = listOf(
        RisenGuideNote(1, 1, "Chapter guide", listOf("This edition replaces the earlier compact summary. Every main/guild quest is separated into actionable steps, and regional side quests are treated as real walkthrough entries instead of checklist bullets. Mutually exclusive City Guard and Merchants’ Guild routes are both included for app coverage.", "Recommended method: make a permanent save before joining a guild and before choosing an apprenticeship. Finish chapter-sensitive side quests before advancing the main story when a warning appears.", "The regional side quests in the supplied guide span chapters 1–3. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(1, 1, "CHAPTER 1 — WELCOME TO ARCHOLOS", listOf()),
        RisenGuideNote(1, 10, "SILBACH & LURKER’S COAST", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 1, "Chapter guide", listOf("This edition replaces the earlier compact summary. Every main/guild quest is separated into actionable steps, and regional side quests are treated as real walkthrough entries instead of checklist bullets. Mutually exclusive City Guard and Merchants’ Guild routes are both included for app coverage.", "Recommended method: make a permanent save before joining a guild and before choosing an apprenticeship. Finish chapter-sensitive side quests before advancing the main story when a warning appears.", "The regional side quests in the supplied guide span chapters 1–3. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(2, 1, "CHAPTER 2 — WITHOUT A TRACE", listOf()),
        RisenGuideNote(2, 21, "CHAPTER 2 — CITY GUARD ROUTE", listOf("Route note: City Guard and Merchants’ Guild story quests are mutually exclusive on one playthrough.")),
        RisenGuideNote(2, 30, "CHAPTER 2 — MERCHANTS’ GUILD ROUTE", listOf("Route note: These quests replace the City Guard route after joining Araxos.")),
        RisenGuideNote(2, 39, "CHAPTER 2 — APPRENTICESHIPS", listOf("You may complete the preliminary jobs for multiple masters, but your apprenticeship choice is permanent. Save before accepting a master.")),
        RisenGuideNote(2, 44, "SILBACH & LURKER’S COAST", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 45, "AMBER COAST / ARAXOS", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 48, "BERMAR’S FARM", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 51, "CHARCOAL / WOODCUTTERS", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 54, "MISTY MARSHES", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 55, "ARCHOLOS CITY", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 72, "RITA’S VINEYARD", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 75, "SAILOR’S RETREAT / CEMETERY", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(2, 77, "WOLF’S DEN — EARLY", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(3, 1, "Chapter guide", listOf("This edition replaces the earlier compact summary. Every main/guild quest is separated into actionable steps, and regional side quests are treated as real walkthrough entries instead of checklist bullets. Mutually exclusive City Guard and Merchants’ Guild routes are both included for app coverage.", "Recommended method: make a permanent save before joining a guild and before choosing an apprenticeship. Finish chapter-sensitive side quests before advancing the main story when a warning appears.", "The regional side quests in the supplied guide span chapters 1–3. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(3, 1, "CHAPTER 3 — AMONG THE SCOUNDRELS", listOf()),
        RisenGuideNote(3, 16, "CHAPTER 3 — CITY GUARD", listOf("Route note: City Guard and Merchants’ Guild story quests are mutually exclusive on one playthrough.")),
        RisenGuideNote(3, 20, "CHAPTER 3 — MERCHANTS’ GUILD", listOf("Route note: These quests replace the City Guard route after joining Araxos.")),
        RisenGuideNote(3, 27, "APPRENTICESHIP FOLLOW-UPS", listOf()),
        RisenGuideNote(3, 29, "ROYAL MINE", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(3, 31, "SCOUNDRELS’ HAVEN", listOf("Regional side quests — chapters 1–3. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(3, 37, "Completion checklist", listOf("Chapter 1 survivor/refugee chain complete.", "Archolos citizenship obtained.", "City Guard OR Merchants’ Guild route completed through the Chapter 2 gateway.", "Apprenticeship chosen intentionally.", "Scoundrels’ Haven reached and Tournament completed.", "Chapter 3 investigation completed through In Broad Daylight / Silver Lining.", "Regional side quests cleared as far as their chapter availability allows.")),
        RisenGuideNote(4, 1, "Chapter guide", listOf("This volume begins with the Chapter 4 Wolf’s Den investigation and follows the story through Vardhal and the Chapter 6 finale. Royal Envoy, City Guard, Merchants’ Guild and Ring of Water branches are kept visibly separate. Late regional side quests are expanded into step-by-step entries.", "Before the Vardhal expedition and again before Chapter 6, create permanent saves and clean up side quests, training, apprenticeships and recruitable companions.", "The regional side quests in the supplied guide span chapters 4–5. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(4, 1, "CHAPTER 4 — A HEART OF STONE", listOf()),
        RisenGuideNote(4, 14, "ARCHOLOS — LATE GAME", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(4, 18, "VALERIO’S VINEYARD", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(4, 20, "WOLF’S DEN — LATE", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(4, 28, "SCOUNDRELS’ HAVEN — CHAPTER 4", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(5, 1, "Chapter guide", listOf("This volume begins with the Chapter 4 Wolf’s Den investigation and follows the story through Vardhal and the Chapter 6 finale. Royal Envoy, City Guard, Merchants’ Guild and Ring of Water branches are kept visibly separate. Late regional side quests are expanded into step-by-step entries.", "Before the Vardhal expedition and again before Chapter 6, create permanent saves and clean up side quests, training, apprenticeships and recruitable companions.", "The regional side quests in the supplied guide span chapters 4–5. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(5, 1, "CHAPTER 5 — STABILITY", listOf()),
        RisenGuideNote(5, 14, "CHAPTER 5 — VARDHAL EXPEDITION", listOf()),
        RisenGuideNote(5, 24, "ARCHOLOS — LATE GAME", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(5, 25, "MONASTERY", listOf("Regional side quests — late game. Placement is for browsing, not an exclusive availability window.")),
        RisenGuideNote(6, 1, "Chapter guide", listOf("This volume begins with the Chapter 4 Wolf’s Den investigation and follows the story through Vardhal and the Chapter 6 finale. Royal Envoy, City Guard, Merchants’ Guild and Ring of Water branches are kept visibly separate. Late regional side quests are expanded into step-by-step entries.", "Before the Vardhal expedition and again before Chapter 6, create permanent saves and clean up side quests, training, apprenticeships and recruitable companions.", "The regional side quests in the supplied guide span chapters 4–5. They are grouped here for browsing; follow each entry’s availability and warnings. Finish optional quests before advancing the main story. City Guard, Merchants’ Guild and Royal Envoy entries describe alternative routes, not a checklist for one playthrough.")),
        RisenGuideNote(6, 1, "CHAPTER 6 — BLOOD ON HANDS", listOf()),
        RisenGuideNote(6, 3, "Completion checklist", listOf("Wolf’s Den / Jon investigation resolved.", "Chapter 4 faction/Envoy quests completed for your route.", "Ring of Water branch advanced if available.", "Late regional side quests completed before Vardhal lock.", "Vardhal expedition team recruited and supplied.", "Vardhal underground cleared and Rightful Heir resolved.", "Permanent save made before Chapter 6.", "Black Hour and A City On Fire completed.")),
    )

    fun chapter(number: Int): List<Quest> =
        quests.filter { it.chapter == number }.sortedBy { it.playOrder }

    fun notes(chapter: Int): List<RisenGuideNote> =
        guideNotes.filter { it.chapter == chapter }
}
