# Questbound — Gothic 3 Regional Corrections Checkpoint

**Date:** 2026-10-10  
**Branch:** `dev/android-foundation-v0.1`  
**Scope:** Follow-up to `docs/gothic-3-quest-audit-2026-10-10.md`

## What was accomplished

Following the initial Gothic 3 main-story and faction audit, this additional pass committed substantial corrections to **177 existing regional quest records**. All original quest IDs, seven app sections, and saved-progress keys were preserved.

- **Part 1 (Ardea / Reddock / Cape Dun / Montera):** 84 entries. All former generic walkthrough introductions replaced. Includes live NPC quest givers, hunting and supply amounts, opening liberation, Phil/Wenzel, 12 Fire Chalices, Montera slave rotations and Bengerd's Chalice, Sanford, Rufus, arena duels, and Orc-vs-Rebel quests.
- **Part 2 (Okara / Gotha / Trelis / Nemora):** 68 entries. All former generic walkthrough introductions replaced. Includes Rakus, Fraser, Kent, Gorn and Gotha's demon Ring of Adanos (Life), Pranck's TWO-DAY 20-potion choice, Kamak/Tempeck, Thorus, Karlen, Treslott and the Nemora recruitments.
- **Part 3 (Geldern / Silden / Vengard / Faring):** 70 entries; this part had no generic introductions in the initial checkpoint. Exact numeric rewards and prerequisites remain a larger research gap.
- **Part 4 (Nordmar):** 72 entries. All former generic walkthrough introductions replaced. Includes Graypelt, hunting deliveries, Ugluz, the White Ripper, Mort and the Hammer Clan furnace, craftsmens' requirements, all six ancestor tombs, and monastery supply quotas.
- **Parts 5–6 (Varant):** Still require detailed side-quest rewrite and reward verification.
- **Part 7:** Ten global GUIDE/reference entries, not ten actual in-game journal quests.

**Notable caution:** The historical entry `G3-P4-016`, “Hunt the white shadowbeast!”, is flagged as **unverified** because the classic walkthrough did not actually find the creature. Players must not be instructed to hunt a spawn as if it is confirmed.

## Final static status

| App section | Entries | Generic walkthrough openings | Blank reward fields | Blank prerequisite fields |
|---|---:|---:|---:|---:|
| Part 1 | 84 | 0 | 3 | 2 |
| Part 2 | 68 | 0 | 0 | 8 |
| Part 3 | 70 | 0 | 51 | 42 |
| Part 4 | 72 | 0 | 1 | 0 |
| Part 5 | 56 | 48 | 47 | 36 |
| Part 6 | 66 | 53 | 50 | 45 |
| Part 7 | 10 | 0 | 0 | 0 |
| **Total** | **426** | **101** | **152** | **133** |

Validation verified **426/426 unique IDs**, consecutive play orders per section, nonempty quest step lists, and balanced Kotlin delimiters/strings. These are static checks, **not an end-to-end build** or playtest.

Before this regional pass the audit reported **258 generic openings** and **301 missing reward values**, now reduced to **101** and **152**. Exact XP, gold, and faction reputation values are taken from historic reference guides and may vary with game version, Community Patch and Quest Pack; maintain uncertainty where independent source verification is unavailable.

## Code commits — regional pass

1. `ccd40134595c88706188a535b529a94a92df7e6f` — Reddock and Cape Dun 30-quest batch.
2. `ab01b3dbeff80776f74571ce8822ce45f33916a8` — Montera 43-quest batch.
3. `f293163d43a56bf84d6dec75f76fe448608a8b80` — Trelis and Nemora 31-quest batch.
4. `bd9653773f65911ba1d3f93b42d80b84a009fe03` — remaining seven Part 1 entries.
5. `cf9569153b5bc8602ecb7a3f6cd8db31f2ae31bc` — remaining eighteen Part 2 entries.
6. `9e6234620f72284120ecb5e89001934eafa2536f` — Wolf/Hammer Clan 18-quest batch.
7. `ca39f69812dd48837440afde4a659346885d4fda` — Fire Clan and ancestor tomb 18-quest batch.
8. `31cb7f860b0ef497e22f5db6bdcf887cffa7dcf1` — Hammer Clan remaining 10-quest batch.
9. `48f4f8d6113e16f3c05ed28195f4c9e2a95e5b82` — monastery's final two Nordmar entries.

Added an explicit regression test in `Gothic3QuestDataTest.kt` to prevent reintroducing template introductory lines in Parts 1–4; the existing test verifies all section sizes, ordering and critical artifact linkages.

## Follow-up priorities

1. **Northern Varant Part 5 — 48 generic entries**: Braga, Ben Erai, Lago and Ben Sala. Verify actual journal quest names, NPCs, unlock conditions and exact XP before filling values.
2. **Southern Varant Part 6 — 53 generic entries**: Mora Sul, Al Shedim, Bakaresh and Ishtar. Preserve the existing already-corrected artifact and temple key chains.
3. Part 3 Silden/Faring concrete rewards and prerequisites; review for placeholder/non-journal quest names.
4. Complete the residual empty reward and prerequisite values only with reliable documentation.
5. GitHub Actions: verify current build, unit tests and Android emulator UI test statuses independently; treat emulator failures separately from the data review.

## Reference

Classic Gothic 3 walkthrough (may reflect an older game build): https://www.gamepressure.com/gothic3/

Detailed regional sources:
- Ardea: https://www.gamepressure.com/gothic3/ardea/z95c1
- Reddock: https://www.gamepressure.com/gothic3/reddock/z15c9
- Cape Dun: https://www.gamepressure.com/gothic3/cape-dun/za5c2
- Montera: https://www.gamepressure.com/gothic3/montera/ze5c6
- Okara: https://www.gamepressure.com/gothic3/okara/z05c8
- Gotha: https://www.gamepressure.com/gothic3/gotha/zd5c5
- Trelis: https://www.gamepressure.com/gothic3/trelis/za5cb
- Nemora: https://www.gamepressure.com/gothic3/nemora/zf5c7
- Wolf Clan: https://www.gamepressure.com/gothic3/wolf-clan/z05d7
- Hammer Clan: https://www.gamepressure.com/gothic3/hammer-clan/ze5d5
- Fire Clan: https://www.gamepressure.com/gothic3/fire-clan/zd5d4
- Monastery: https://www.gamepressure.com/gothic3/monastery/zf5d6
