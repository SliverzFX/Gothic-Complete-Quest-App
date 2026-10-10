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


---

## Second regional pass: Varant corrected (2026-10-10)

The earlier checkpoint above recorded **101 remaining generic openings in Parts 5 and 6**. Those 101 have now been replaced by actionable multi-step entries or explicitly labeled historical guide/unverified records. **Do not use the stale counts in the earlier table as the current status.**

### Corrections committed this pass

- `2a744e221807329b210c0e8895db89ea2fa8c9b8` — Braga and Ben Erai; replaced incorrect generic quests with named source counterparts.
- `59a81acc19d1a62f0c67df8efe45d6fdd359bed6` — Lago and Ben Sala; named arena opponents, Vatras escort to Hurit, Miguel/Enzo/Daro/Julio/Yugul routes.
- `11cf2267e740a1bf2e428d9425fae8cb471243bb` — Mora Sul; gifts for Gonzales via Nasib, Paladin/Anktos/Oelk/Angar arena, Masil/Orknarok and Water Mage consequences.
- `0bca947a1be85dde13ab309b57ea6719703d7036` — Al Shedim; five relic collections and ally preparation; separated true quests from duplicated region guidance.
- `f52c71925962172603ea87fc32c09dba0573445f` — Bakaresh; temple access, the one-day Amul tribute limit, Hernando's arena, Nomad versus Hashishin missions.
- `e8d8c6b0a6764ab2f137e5f553d5a14d9ed37e57` — Ishtar; 75 global Hashishin reputation, Kasim's arena, Zuben's test, Karmok's weapon/potion deliveries, optional assassination/endgame choices.
- `f53c397c7bba151fe4839c6881ef364f1fc5d544` and `80921122ef0b0b434d666fbc683b1db364096f3f` — reconcile corrected titles with summaries, prerequisites, and guide-only categories.
- `d956e02c0a8a3d63bb03adad6da3fd3253f50a87` — extend Gothic 3 regression coverage to **all seven sections**, and test that clearly uncertain records remain visibly categorized as guides.

### Significant data-hygiene correction

The original Ben Erai and Ben Sala section had many placeholder tasks with no matching **independent journal quests** in the classic base-game walkthrough. Examples included `Lukar Needs Ore`, `Bring Water to the Mine`, `The Ruined Mine`, `Basir's Old Chest` and `Clear the Road to Bakaresh`. Some old catalog IDs have been reassigned to **documented** named quests, and other uncertain entries explicitly identify themselves as guides rather than asserting a fictitious NPC/reward. The original IDs were kept to preserve saved progress; reclassification means a previously ticked entry might now represent a different task. Review saved-state migration/presentation separately if this app had existing users.

The classic guide also identifies a possible **Ten Gold Nuggets ghost quest** in Ben Erai for which it could not establish a giver. This is marked unverified rather than guaranteed in every patch.

Additional warning: `G3-P6-029` and `G3-P6-034` both concern **The Liberation of Al Shedim**. The latter is labeled as a reference entry and notes that the two entries are not separate rewards. Full deduplication should be considered before a release migration.

### Final source/static audit (after Varant pass)

| Part | Quest records | Generic walkthrough openings | Blank reward | Blank prerequisite |
|---|---:|---:|---:|---:|
| 1 — Ardea to Montera | 84 | 0 | 3 | 2 |
| 2 — Okara to Nemora | 68 | 0 | 0 | 8 |
| 3 — Geldern to Faring | 70 | 0 | 51 | 42 |
| 4 — Nordmar | 72 | 0 | 1 | 0 |
| 5 — Northern Varant | 56 | 0 | 4 | 2 |
| 6 — Southern Varant | 66 | 0 | 3 | 0 |
| 7 — Global guides | 10 | 0 | 0 | 0 |
| **TOTAL** | **426** | **0** | **62** | **54** |

Source inspection found **426 unique IDs, exactly one entry per existing section/play-order position, at least two walkthrough steps per entry, no empty summaries or givers, and no mismatched Kotlin quote/bracket/parenthesis delimiters**.

**These checks are not in-game verification or a complete Android build.** For the last checked code, GitHub's Android **build** job passed in one earlier run, whereas **Android instrumentation tests** failed in another earlier run. Latest Varant CI is still in progress. Recheck the freshest commit's jobs before claiming release readiness.

### Sources

- Braga: https://www.gamepressure.com/gothic3/braga/z95d0
- Ben Erai: https://www.gamepressure.com/gothic3/ben-erai/zd5ce
- Lago: https://www.gamepressure.com/gothic3/lago/zc5d3
- Ben Sala: https://www.gamepressure.com/gothic3/ben-sala/ze5cf
- Mora Sul: https://www.gamepressure.com/gothic3/mora-sul/zb5d2
- Al Shedim and Nomad camp: https://www.gamepressure.com/gothic3/kdw-camp/zc5dc
- Bakaresh: https://www.gamepressure.com/gothic3/bakaresh/zc5cd
- Ishtar: https://www.gamepressure.com/gothic3/ishtar/za5d1

### Remaining targeted work

- **Part 3:** 51 blank reward fields and 42 blank prerequisites across Geldern, Silden, Vengard, Faring. These should be source-verified, not auto-filled with guessed numbers.
- **Parts 1–2 / Nordmar:** 4 total blank rewards and a few version-dependent issues; fix only where a reliable source or game journal corroborates details.
- Review documented guide-only and duplicate entries before release; do not present reference entries as completed individual journal quests.
- Android UI/instrumentation tests still need a clean run independent of this content audit.


---

## Third regional pass: Silden / Faring / Geldern source reconciliation

In the same correction session, continued the Part 3 audit against the classic Gothic 3 regional guides and reduced blank reward fields in that section from **51 to 23**. The latest global counts below supersede earlier snapshots in this report.

- `c17001d8ff7d8d7bc5c883924b7ab5963780b15c`: 21 Silden and Faring entries, including eight strange lurkers, wood/fish shipments, the Inog–Anog Rebel faction choice, three trolls near Tippler's hut, Wilson's hunter requirements, and the Faring liberation reward.
- `eb95cd8b9baf49d829cf53891d006c0cbff14d9a`: seven more Geldern/Faring records, including Samuel's six minecrawlers, the two separate Faring arena ladders, Spike's title fight and Wilson's **400-gold honesty check**.

Several entries in this legacy catalog overlap the same genuine journal quest. They are now described as **cross-references** rather than promises of multiple distinct XP payouts (Shadow Scepter, Silden wood/fish delivery, Al Shedim liberation, and Lars's Nordmar-pass escort). Quest IDs remain stable to avoid a silent saved-progress migration.

### Most recent static counts

| Part | Entries | Generic openings | Blank rewards | Blank prerequisites |
|---|---:|---:|---:|---:|
| 1 | 84 | 0 | 3 | 2 |
| 2 | 68 | 0 | 0 | 8 |
| 3 | 70 | 0 | 23 | 17 |
| 4 | 72 | 0 | 1 | 0 |
| 5 | 56 | 0 | 4 | 2 |
| 6 | 66 | 0 | 3 | 0 |
| 7 | 10 | 0 | 0 | 0 |
| **Total** | **426** | **0** | **34** | **29** |

**Static verification passed:** 426/426 unique IDs, consistent section order, balanced Kotlin delimiters, and no remaining template walkthrough openings. The latest GitHub Actions builds and emulator tests are still in progress at the checkpoint; no new green CI completion is claimed.

Remaining reward gaps are concentrated in the more weakly documented quests for **Geldern, Silden, Vengard and Faring**. Recheck these against in-game journal labels and patch-specific values before release, rather than inventing exact payouts.

Additional reference sources: https://www.gamepressure.com/gothic3/silden/z95ca ; https://www.gamepressure.com/gothic3/faring/zb5c3 ; https://www.gamepressure.com/gothic3/vengard/zb5cc ; https://www.gamepressure.com/gothic3/geldern/zc5c4 .
