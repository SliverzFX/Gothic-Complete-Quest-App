# Gothic 2: New Balance — Chapter 4 correction audit

**Date:** 2026-10-10  
**Repository / branch:** `SliverzFX/Questbound` / `dev/android-foundation-v0.1`  
**Quest data file:** `app/src/main/java/com/sliverzfx/gothicquest/NewBalanceChapter4Data.kt`

## Scope and result

The Chapter 4 content pass covers **all 30 existing quests**, `CH4-001` through `CH4-030`. All thirty IDs are present exactly once. Each record now has a quest-specific category and location instead of the old `Chapter 4` placeholder. The pass also removes remaining extracted-PDF page markers and updates quest givers, step-by-step guidance, prerequisites, rewards, branch consequences and warnings where corroborated.

Changes were committed in four batches:
- `239421fbd6d05023e2a7c32ce953387bee6ceb94` — main quests and first seven records;
- `6488e999b3ad6ed390adaed467d3b1a6d6dd52e4` — monastery, Scouts, Demon Hunters, dragons and Jharkendar;
- `ad2b9f5aa5722686abdbb5ea140a67586ea58d43` — remaining Valley of Mines and castle quests;
- `858fc35457b4a7c80996a88db3c46f29697f70d7` — replace the 30 generic category/location fields.

### High-impact corrections

- **Dragon Hunt:** charged Eye of Innos required in inventory for dragon dialogue, then recharge after each dragon; loot hearts/hoards and report staged rewards to Garond.
- **Orc Squads:** commander Fangarh, his patrol map, leader-ring turn-ins and a warning to save an orc ring for Dar.
- **Deprivation:** paladin-only as documented by the April 2026 New Balance guide.
- **The Braggart:** corrected the gold reward from **1,200** to **120** and separated ring-delivery and brawl routes.
- **Dragon Eggs / A Dragon Egg for Neoras:** important choice to conserve eggs for Embarla Firgasto.
- **Dragon Hunters:** this is an **automatic** Chapter 4 quest, not given by the first hunter encountered.
- **Hunger:** Gerold meets in the **chapel after 23:30**, not the mages' building.
- **Mercenaries Near the Manor:** a Demon Hunter encounter without a normal journal entry, not missing data.
- **Talbin's Escape, Feros's Sword, Hosh-Pak, Orc Warlords, No Turning Back:** corrected destinations, NPC rewards, chapter timing, faction consequences and/or time limits.
- **News for Vatras / Orcs in the Valley:** quest chain follows Saturas and clearing the six Jharkendar orc commanders.

### Explicit uncertainty

**`CH4-011: Lost?`** is listed in the 2026 New Balance master index as a Scouts quest, but a trustworthy standalone walkthrough or in-game source for its exact trigger and payout was not established. The record is clearly marked index-only; it deliberately avoids inventing an NPC or reward. Some exact XP and monetary amounts for other quests may differ across builds. The audit is a **content cleanup and reference-based review**, not a claim of perfect gameplay verification.

### Structural verification

- IDs `CH4-001` – `CH4-030`: **30/30**, no duplicates or missing entries.
- Generic quest category and location values: **zero**.
- Extracted PDF markers: **zero**.
- Generic `Trigger quest` givers: **zero**.
- Balanced Kotlin string quotes and `()[]{ }` delimiters: **passed static check**.

### Build verification

A previous GitHub Actions **build** job succeeded, including app compilation and unit tests. Earlier workflow **instrumentation** jobs failed in Android UI tests, including repeated `TopLevelSectionsTest.waitForHome` failures. At the time this audit was authored, Chapter 4 branch runs were still being processed. Neither a current green CI run nor in-game accuracy for every reward is claimed here.

### Reference guides (April 2026 New Balance collection)

- Master index: https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/
- Dragon Hunt: https://rpgrussia.com/
- Community quest walkthroughs and entries: https://rpgrussia.com/forums/gotika-2-novyj-balans.597/

**Next:** Chapter 5 after the Chapter 4 content audit; handle Android instrumentation failures as a separate application-testing task.
