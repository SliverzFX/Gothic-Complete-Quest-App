# Questbound — Gothic 3 quest-data audit (first substantive correction pass)

**Date:** 2026-10-10  
**Repository / branch:** `SliverzFX/Questbound` / `dev/android-foundation-v0.1`

## Coverage

The existing Gothic 3 quest catalog has **426 entries**, aggregated by `Gothic3QuestData.kt` from `Gothic3Part1Data.kt` through `Gothic3Part7Data.kt`. These are **seven geographic/global GUIDE SECTIONS**, **not seven actual Gothic 3 story chapters**.

| Section | Scope | Entries |
|---|---|---:|
| 1 | Ardea, Reddock, Cape Dun, Montera | 84 |
| 2 | Okara, Gotha, Trelis, Nemora | 68 |
| 3 | Geldern, Silden, Vengard, Faring | 70 |
| 4 | Nordmar | 72 |
| 5 | Northern Varant | 56 |
| 6 | Southern Varant | 66 |
| 7 | Global completionist/reference guide | 10 |
| **Total** | | **426** |

**152 distinct entries** received targeted changes to quest givers, prerequisites, actionable steps, rewards, or progression warnings across the seven files. Not every existing entry was independently game-tested, and this is **not** a claim that 426 entries exhaust Gothic 3's in-game quest journal.

## Major corrected material

**Global / endgame (Part 7)**
- Exact twelve Fire Chalice holders and routes, including *Kurt's CHEST*, Markus, Milten, Treslott and Rakus.
- Grok's hand-in vs the sacred-fire blessing are mutually exclusive.
- Five Artifacts of Adanos identified correctly: **Amulet—Vak/Trelis; Crown—Mora Sul; Robe—Al Shedim; Ring of Life—Gotha demon; Ring of Magic—Akascha/Nordmar**.
- Innos, Beliar and Xardas endings with point-of-no-return warnings; Xardas requires both Rhobar's Scepter of Varant and Zuben's Staff of the Eternal Wanderer.
- Graypelt, travel to Xardas's tower, druid-stone owners and Gotha's Vengard teleport rune.

**Western and central Myrtana (Parts 1–2)**
- Ardea's automatic starting liberation is distinct from later counted Orc-city uprisings.
- Reddock leadership and escorts; Cape Dun's Phil/Wenzel espionage, Harek choice and Uruk betrayal; Montera's Sanford and Marik commitments.
- Okara recruitments (Randall, Owen, Rakus, Candela, Fraser and Kent), boars, shadowbeast horns and Manning's ore needs.
- Vak's 50,000-gold **PAYMENT** yields the Amulet of Adanos on the peaceful path. Konrad and Karlen specifically need **BLESSED**, not ordinary Fire Chalices.
- Renegade paladin Kurt's Fire Chalice is in a chest; check his key and loot it before leaving.
- Orc-town liberation can trigger the global three-city hostility rule; finish neutral quests before rebelling.

**Eastern Myrtana (Part 3)**
- Grok's Orc-aligned Fire Chalice route and the competing Milten blessing.
- Vengard teleport rune location (Gotha underground), Markus's chalice to Karrypto, Rhobar and Vengard defense, and the missing paladin Thordir.
- Faring's Kan access depends on **75 global Orc faction reputation**.
- Geldern: Grimboll sulfur, Nemrok's Shadow Scepter, Lares/Jared theft quests, Runak and Torn's druid quests and rebel-vs-Orc lockouts.

**Nordmar (Part 4)**
- Ugluz Potbelly's automatic named-ogre kill quest, the wolf Graypelt to Xardas, and Bogir's White Ripper hunt to obtain the Wolf Druid Stone.
- Hunting and clan supply requirements, Tjalf's chest, smithing and Woodcutter jobs.
- Six ancestor stones/tombs (Ejnar, Angir, Snorre, Berek, Baldar, Akascha), the Magic ring from Akascha, five counted Orc camps, and Hammer Clan furnace liberation.
- **Freeing the furnace is not itself the irreversible destruction of artifacts for Xardas.**

**Northern and southern Varant (Parts 5–6)**
- Braga's tribute debtors and the Abbas *water vendors' chalice*, which is NOT a Fire Chalice; Aila's Nomad deception possibility.
- Vatras imprisoned in Lago; liberation choices affect Water Mage/Nomad progress and Hashishin access.
- Mora Sul's five key holders: **Yussuf, Ilja, Kirk, Gonzales, Kalesch**; Gonzales's peaceful key costs **10,000 gold**.
- Al Shedim temple keys: **Saturas, Lester, three ruin chests**; its artifact is the **Robe**, whereas Mora Sul holds the **Crown**.
- Ishtar peaceful access requires **75 GLOBAL Hashishin faction reputation**, not 75 local Mora Sul standing.

## Static validation and automated tests

Final source inspection after the correction commits:
- All **426 quest IDs** unique and accounted for; each section has consecutively numbered play-order values with matching section numbers.
- No detected unfinished strings, mismatched parentheses/braces/brackets, or missing walkthrough-list fields.
- All quests retained existing IDs, so saved journal progress keys were not deliberately migrated.
- Added `app/src/test/java/com/sliverzfx/gothicquest/Gothic3QuestDataTest.kt` to check section sizes, IDs, order, and several essential global/temple/endgame details. Commit `f23de637d32ed31c0e4abf4bfebe47718292636a`.
- An earlier GitHub Actions run reported **successful build and unit test jobs but failing Android emulator/instrumentation UI tests**. At checkpoint creation, the run for the new regression tests had not yet finished. Do not claim a green latest CI run until confirmed.

## Remaining research backlog (important!)

This is a **first targeted correction pass**, not a full line-by-line source verification of all 426 entries. There are still many original, shallow walkthroughs and unverified numeric rewards.

| Part | Generic opening directions left | Reward fields blank | Prerequisites blank |
|---|---:|---:|---:|
| 1 | 66 | 68 | 59 |
| 2 | 44 | 39 | 49 |
| 3 | 0 | 51 | 42 |
| 4 | 47 | 46 | 45 |
| 5 | 48 | 47 | 36 |
| 6 | 53 | 50 | 45 |
| 7 | 0 | 0 | 0 |
| **Total** | **258** | **301** | **276** |

The remaining blank reward fields should be populated **only from documented values**, not fabricated exact XP or boilerplate text. A few regional entries describe general quest chains instead of clearly identifiable journal quest names; verify these against the game's actual journal for the chosen Community Patch / quest-pack version. Focus next on:
1. Part 1's remaining Montera/Redddock/Cape Dun details and concrete rewards;
2. Part 2's Gotha/Trelis/Nemora quests;
3. Part 4 Nordmar's remaining escort, clan and monastery tasks;
4. Parts 5–6 Varant's named side quests, city reputation and temple/faction gates;
5. Audit Part 3 Silden and Faring titles against the actual in-game quest journal;
6. Treat Part 7 as a collection of **GUIDE ENTRIES**, not 10 real journal quests.

## Primary reference

The classic Gothic 3 guide on Gamepressure (quest-specific Myrtana / Nordmar / Varant pages; game version matters):
- https://www.gamepressure.com/gothic3/
- https://www.gamepressure.com/gothic3/the-fire-chalices/za7a2
- https://www.gamepressure.com/gothic3/al-shedim-mora-sul-temple-keys/zb7a3
- https://www.gamepressure.com/gothic3/adanos-artifacts/zc7a4
- https://www.gamepressure.com/gothic3/okara/z05c8
- https://www.gamepressure.com/gothic3/wolf-clan/z05d7

Numbers and quest triggers can differ in Community Patch / Quest Pack versions; leave fields uncertain if a current game version is not confirmed.
