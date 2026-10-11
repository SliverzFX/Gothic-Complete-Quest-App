# Gothic II New Balance — Chapters 1–2 Quest Audit

**Date:** 2026-10-09
**Repo/branch:** [SliverzFX/Questbound](https://github.com/SliverzFX/Questbound), `dev/android-foundation-v0.1`
**Scope:** `app/src/main/java/com/sliverzfx/gothicquest/NewBalanceChapter{1,2}Part*.kt`

## Code-scanned status

| Metric | Chapter 1 | Chapter 2 |
| --- | ---: | ---: |
| Total quest entries | 283 | 211 |
| Quest-giver placeholders `Trigger quest` | 32 | 82 |
| Template-style summaries | 83 | 103 |
| Placeholder rewards | 127 | 204 |

**Total entries:** 494. **Unresolved quest-giver placeholders:** 114. **Duplicate quest IDs:** 0.

These are literal code-pattern counts, NOT proof that all named entries are factually correct. Template rewards are not verified.

## Changes committed 2026-10-09

- Resolved **22** Chapter 1 `Trigger quest` fields with documented NPCs, dialogue starts, or actual found-note triggers, and replaced corresponding generic walkthroughs.
- Resolved **48** Chapter 2 `Trigger quest` fields with documented NPCs/quest triggers, and improved corresponding walkthroughs.
- Corrected already-named entries: **CH2-005 Halls of Adanos → Saturas** (previously Quarhodron) and **CH2-060 Temples of the Builders → Riordian** (previously Saturas).
- Corrected the previous mistaken Chapter 1 count: it has 283 entries, not 319. Original placeholder baseline: Chapter 1 = 54; Chapter 2 = 130.
- This audit has not executed a Gradle compile or tested an APK.

## Public reference guides

- [Sefaris Chapter 1](https://nb.mody.sefaris.eu/solucja/rozdzial-i/)
- [Sefaris Chapter 2](https://nb.mody.sefaris.eu/solucja/rozdzial-ii/)
- [Sefaris Hunters' Guild](https://nb.mody.sefaris.eu/gildie-poboczne/gildia-mysliwych/)
- [RPG Russia New Balance quests forum](https://rpgrussia.com/forums/novyj-balans-proxozhdenie-kvestov.600/)

## Remaining unresolved quest-giver entries

### Chapter 1

- **Part 1:** 4 unresolved — CH1-008 Archmage; CH1-021 Old Acquaintances; CH1-030 An Unexpected Meeting; CH1-040 Master of Dual Blades
- **Part 2:** 3 unresolved — CH1-062 Halvor's Fish; CH1-084 Dead or Alive; CH1-086 Highlander
- **Part 3:** 13 unresolved — CH1-092 The Fountain; CH1-095 Masters' Approval; CH1-096 Becoming an Apprentice in Khorinis; CH1-117 Underground Production; CH1-118 Attack on the Merchant; CH1-120 Empty the Jug in One Go; CH1-121 Field Raider Nest; CH1-122 Lunch; CH1-126 Harim's Cup; CH1-128 Money for the Herbalist; CH1-129 Ghostly Dreams; CH1-130 Jaco's Laboratory; CH1-131 Onar's Golden Plate
- **Part 4:** 12 unresolved — CH1-136 Free Hunters; CH1-155 Eliminate the Thieves' Guild; CH1-161 Model Soldier; CH1-162 Assassins' Guild in Khorinis; CH1-163 Bandit Hideouts; CH1-165 Respect of the Mercenaries; CH1-167 Drinking for Knowledge; CH1-168 Best Wishes; CH1-169 A Package of Swampweed; CH1-170 A Little Swampweed; CH1-171 Bullseye; CH1-175 Atonement
- **Part 5:** 0 unresolved
- **Part 6:** 0 unresolved
- **Part 7:** 0 unresolved

### Chapter 2

- **Part 1:** 13 unresolved — CH2-009 Oswald's Farm; CH2-012 Mysterious Murder; CH2-022 Special Swampweed Joint; CH2-023 Fistfights; CH2-034 Banner of Fire; CH2-035 Sabotage; CH2-036 Eliminating the Orc Mercenaries; CH2-037 Missing Recruit; CH2-038 Searching for Recruits; CH2-039 Challenge of the Demon; CH2-040 Bandit Attack; CH2-041 Cor Kalom's Recipe; CH2-043 Proud but Defenseless
- **Part 2:** 13 unresolved — CH2-044 Potion of Deliverance; CH2-045 Supplies for the Castle; CH2-046 Bandits in the Valley; CH2-047 Blood Diamond; CH2-048 Cleansing the Crypt; CH2-049 Music of Life and Death; CH2-050 Grandmaster's Rapier; CH2-051 Field Rats; CH2-052 A Man More Frightening Than a Monster; CH2-053 Trouble at the Arena; CH2-054 The Arena; CH2-055 Unlucky Hunter; CH2-078 Dangerous Hunt
- **Part 3:** 17 unresolved — CH2-108 Druid Cormac; CH2-109 Sailing to the Coast; CH2-110 West Coast; CH2-111 Weapons for the Pirates; CH2-112 Supply Problems; CH2-113 Threat on the Beach; CH2-114 Pirate Treasure; CH2-115 Abandoned Tower; CH2-116 Food for the Druid; CH2-117 Help with the Turnip Harvest; CH2-118 For the Meeting; CH2-119 Annoying Goblins; CH2-120 Long-Awaited Reunion; CH2-121 Exodus from the Coast; CH2-122 Search for the Druids; CH2-123 Paths of Darkness; CH2-126 Unpleasant Neighbors
- **Part 4:** 19 unresolved — CH2-132 Hunting Glorks; CH2-134 Forest Camp; CH2-149 Reconnaissance of the Free Mine; CH2-153 Secrets of the Temple of Adanos; CH2-154 Raven's Nightmare; CH2-155 Artifacts of Antiquity; CH2-156 Gift of a Madman; CH2-157 Claw of Beliar; CH2-158 Missing Goblin Totem; CH2-159 Missing Brother; CH2-160 Cleansing the Temple of the Sleeper; CH2-161 Ulu-Mulu; CH2-162 Bound by Honor; CH2-163 Secret of the Orc Language; CH2-164 Orc Mercenary; CH2-167 Flask of Grog; CH2-168 Mor Dar; CH2-169 Orc Weapons; CH2-170 Sword for the Chieftain
- **Part 5:** 20 unresolved — CH2-182 Lower Mine; CH2-185 Thirst for Battle; CH2-186 Escape of the Slaves; CH2-188 Greedy Orc; CH2-189 Trust of the Slaves; CH2-190 Key to the Orc Warehouse; CH2-191 Crow's Diary; CH2-193 Daily Ore Quota; CH2-194 A Word for Father; CH2-195 Impossible Dream; CH2-196 Old Furnace; CH2-198 Distract the Guard; CH2-199 Portal in the Orc Mine; CH2-200 Wenzel's Equipment; CH2-201 Provisions for the Slaves; CH2-202 Bundles of Weapons; CH2-205 People in the Valley; CH2-209 Price of Freedom; CH2-210 Clan War; CH2-211 Oddler's Hut

## Next work

1. For each remaining placeholder, verify who initiates the quest, including cases started by notes or objects without an NPC.
2. Check prerequisites and quest order against the current New Balance 8.0 scripts, English translation, or up-to-date guide.
3. Replace generic summaries and rewards with documented specifics where substantiated. Never invent XP, item counts or initiation NPCs.
4. Compile the Android app and smoke-test each edited list when the build environment is available.

**Caution:** The Sefaris community walkthrough may not match every quest in the exact New Balance 8.0 build. Unverified lines remain open rather than guessed.
