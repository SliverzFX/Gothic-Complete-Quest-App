# Gothic 2: New Balance — Chapter 6 final quest audit

**Date:** 2026-10-10  
**Repository:** `SliverzFX/Questbound`  
**Branch:** `dev/android-foundation-v0.1`  
**Data file:** `app/src/main/java/com/sliverzfx/gothicquest/NewBalanceChapter6Data.kt`

## Result

The last chapter's **3/3 catalog quests** were updated in one commit:

`41ed6dffe7bfaf018fa3eafb6e26e16505aeee8f`

The two main quests and one Irdorath side quest agree with the **23 April 2026 New Balance master index**. The guild/difficulty restrictions of the side quest are not fully re-documented there, so older detailed guidance is labeled as version-dependent.

### Updated quests

1. **CH6-001 — Halls of Irdorath:** 13 walkthrough steps, including crew preparations; Ur'Trax, Ur-Vatah and his cell key; Pedro's optional rescue; returning to repel the ship attack; the **left torch only** at the orc throne; Trakanon and dragon eggs; ranged bridge switches; Shadow Lords Ar'Hol and Argol and the egg-potion recipe; Dementor's mental attack; Eye of Power and the kneeling/spell mechanism; optional Ish'Tar and Aru'Tar; Undead Dragon.
2. **CH6-002 — Back to the Ship:** 4 steps for returning through the temple, speaking with scattered companions, reaching the Esmeralda and confirming departure with the chosen captain.
3. **CH6-003 — The Best Armor in the World:** 5 steps for recruiting Bennet, bringing his materials, commissioning and collecting the Dragon Slayer armor. Historical detailed recipe: **50 dragon scales, 20 magic ore, 10 sulfur, 5 black pearls, 2 resin/pitch containers and 4 dragon skulls**. The armor competes with Ragnar's staff for the skulls; Bennet is onboard only if recruited in Chapter 5.

**No quest giver** is displayed for the first two main quests because they trigger automatically; Bennet is the explicit giver for the crafting quest.

### Verification

- IDs **CH6-001, CH6-002, CH6-003**, all present exactly once.
- Every quest has a multi-step walkthrough; 13, 4 and 5 steps respectively.
- No generic `Trigger quest` giver, generic Main Story location, or PDF extraction marker remains.
- All Kotlin string quotes and brackets balanced in static inspection.
- `Gothic2QuestData.kt` includes `NewBalanceChapter6Data.quests`, confirming Chapter 6 is part of the combined New Balance quest catalog.
- Android compile/unit/UI test outcome is **not** established by these static checks; GitHub Actions must pass separately.

### Sources and accuracy boundaries

- **New Balance 2026 master quest list:** https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/
- **Halls of Irdorath, April 2026 detailed quest:** https://rpgrussia.com/threads/zaly-irdorata.67303/
- **Back to the Ship, April 2026:** https://rpgrussia.com/threads/nazad-k-korablju.67304/
- **The Best Armor in the World, older detailed walkthrough:** https://rpgrussia.com/threads/luchshie-na-svete-dospexi.15323/
- **Recent discussion of Bennet armor timing (December 2025):** https://rpgrussia.com/threads/vydavat-dospexi-ubijcy-drakonov-srazu-po-pribytiju-na-irdorat.64253/

The master index verifies that Bennet's armor quest is present in modern New Balance but does not restate its ingredient counts, eligible guilds, or difficulty thresholds. The recipe and late pickup timing are therefore explicitly marked **historical / potentially build-dependent** rather than guaranteed for every current installation.

This completes the **New Balance Chapters 1–6 quest-content audit sequence** as recorded in the repository. Any future corrections should respond to concrete gameplay evidence or failed app tests instead of another blanket rewrite.
