# New Balance Chapter 1–2 quest audit — October 10, 2026

Repository: [SliverzFX/Questbound](https://github.com/SliverzFX/Questbound)  
Branch: `dev/android-foundation-v0.1`  
Previous snapshot: [October 9 audit](./newbalance-chapters-1-2-quest-audit-2026-10-09.md)

## Current status — read back from GitHub after commits

| Metric | Chapter 1 | Chapter 2 | Total |
| --- | ---: | ---: | ---: |
| Quest entries | 283 | 211 | 494 |
| `giver = "Trigger quest"` remaining | 19 | 27 | **46** |
| Placeholders corrected on October 10 | 13 | 55 | **68** |
| Generic-pattern summaries remaining | 73 | 62 | 135 |
| Generic `Reward varies by quest/build` fields remaining | 127 | 201 | 328 |

Original October 9 baseline: 54 Chapter 1 and 130 Chapter 2 placeholder givers (184 total). October 9 end: 32 Chapter 1 and 82 Chapter 2 (114 total). October 10 end: 19 and 27 (46 total). No duplicate quest IDs in either chapter based on the current Kotlin scan.

These metrics are **literal source-code pattern counts**, not a certification of 8.0 in-game accuracy. Not every quest with a named giver has been checked against game scripts. In particular, 328 template rewards need separate verification.

## Changes today

- **Chapter 1 (13):** Field Raider Nest (Fester); Harim's Cup (Kharim); Money for the Herbalist (Wolf); Jaco's Laboratory (Cipher); Free Hunters (Falk); Eliminate the Thieves' Guild (Lord Andre); Assassins' Guild in Khorinis (Lord Andre); Bandit Hideouts (Wulfgar); Drinking for Knowledge (Rod); A Package of Swampweed and A Little Swampweed (Cipher); Bullseye (Wolf); Atonement (Baltram).
- **Chapter 2 (55):** Deep walkthrough and giver corrections in Coastal Lands, West Coast, Demon Hunters, Scouts, Orc City, Orc Mine, Valley of Mines, and Path of the Damned.
- **Important availability:** Path of the Damned is an **optional plugin** (`AB_PathOfTheDamned_Mod`), not included in standard New Balance. Potion of Deliverance extends into Chapter 4. Its Chapter 2 catalogue position represents the questchain's start, not its completion.
- **Special triggers:** Some quests begin from notes, posted notices or location discovery rather than a speaking NPC. Don't blindly replace them with arbitrary character names.
- **Chapter 2 murder investigation:** Mysterious Murder is assigned by Lord Andre and can start in Chapter 2 only after Constantino/Karras prerequisites and access to the Fire Mages' library, despite appearing in the Chapter 3 guide section.

## Commit record

- CH1: [part 3](https://github.com/SliverzFX/Questbound/commit/dcab5ca9ab2d79d972735176b92193b9a9a1323b), [part 4](https://github.com/SliverzFX/Questbound/commit/a3f0bf63b3748d66d9d758ddcb4dc985f0a1d820).
- CH2 early: [Greg's Dangerous Hunt](https://github.com/SliverzFX/Questbound/commit/b392875fd2c0b62c6e4920d4f54e559c980507ab), [Valley of Mines](https://github.com/SliverzFX/Questbound/commit/5b359f3545fc998dbdf8cf8a1965eae301496d6a).
- CH2 Orc City: [7 corrections](https://github.com/SliverzFX/Questbound/commit/77785a9ce646f96967d457972fc6874b5c4748b1).
- CH2 Orc Mine: [15 corrections](https://github.com/SliverzFX/Questbound/commit/4e5187016ead0bc8a657b59d2f98417b6a7a8c74).
- CH2 coast: [entry point](https://github.com/SliverzFX/Questbound/commit/3ca135eeb0b237a45437dcea9a7081e18da2f818), [Oswald's Farm](https://github.com/SliverzFX/Questbound/commit/d52f9daa517ee8e32505fd4a652e095c415b2c91), [West Coast](https://github.com/SliverzFX/Questbound/commit/b853b9ad25143ad063bfda16da8de269065524e7).
- CH2 factions: [Scouts & Demon Hunters](https://github.com/SliverzFX/Questbound/commit/d0214e55deb68799db9a2639a3858e0364035840), [Damned](https://github.com/SliverzFX/Questbound/commit/8ed7529655699af44d865c733d75b688e7db0054).
- CH2 crime: [Mysterious Murder](https://github.com/SliverzFX/Questbound/commit/b40aad4e2ef5c063de37227c73f308e6a5fb35ba).

## Source references

- [Sefaris Chapter 1](https://nb.mody.sefaris.eu/solucja/rozdzial-i/)
- [Sefaris Chapter 2](https://nb.mody.sefaris.eu/solucja/rozdzial-ii/)
- [Sefaris Chapter 3 (Mysterious Murder availability)](https://nb.mody.sefaris.eu/solucja/rozdzial-iii/)
- [Scouts](https://nb.mody.sefaris.eu/gildie-glowne/zwiadowca/)
- [Demon Hunters](https://nb.mody.sefaris.eu/gildie-glowne/lowca-demonow/)
- [Path of the Damned](https://nb.mody.sefaris.eu/gildie-glowne/potepiony/)
- [Mercenaries](https://nb.mody.sefaris.eu/gildie-glowne/lowca-smokow/)
- [Hunters' Guild](https://nb.mody.sefaris.eu/gildie-poboczne/gildia-mysliwych/)
- [Coastal Lands](https://nb.mody.sefaris.eu/watki/farma-oswalda/)
- [West Coast](https://nb.mody.sefaris.eu/watki/zachodnie-wybrzeze/)
- [Orc City/Mine storyline](https://nb.mody.sefaris.eu/watki/orkowie/)

## Remaining quest giver placeholders (all IDs)

### Chapter 1
- **Part 1 (4):** CH1-008 Archmage; CH1-021 Old Acquaintances; CH1-030 An Unexpected Meeting; CH1-040 Master of Dual Blades
- **Part 2 (3):** CH1-062 Halvor's Fish; CH1-084 Dead or Alive; CH1-086 Highlander
- **Part 3 (9):** CH1-092 The Fountain; CH1-095 Masters' Approval; CH1-096 Becoming an Apprentice in Khorinis; CH1-117 Underground Production; CH1-118 Attack on the Merchant; CH1-120 Empty the Jug in One Go; CH1-122 Lunch; CH1-129 Ghostly Dreams; CH1-131 Onar's Golden Plate
- **Part 4 (3):** CH1-161 Model Soldier; CH1-165 Respect of the Mercenaries; CH1-168 Best Wishes
- **Part 5 (0):** none
- **Part 6 (0):** none
- **Part 7 (0):** none

### Chapter 2
- **Part 1 (5):** CH2-022 Special Swampweed Joint; CH2-023 Fistfights; CH2-040 Bandit Attack; CH2-041 Cor Kalom's Recipe; CH2-043 Proud but Defenseless
- **Part 2 (3):** CH2-049 Music of Life and Death; CH2-050 Grandmaster's Rapier; CH2-055 Unlucky Hunter
- **Part 3 (2):** CH2-110 West Coast; CH2-121 Exodus from the Coast
- **Part 4 (12):** CH2-132 Hunting Glorks; CH2-134 Forest Camp; CH2-149 Reconnaissance of the Free Mine; CH2-153 Secrets of the Temple of Adanos; CH2-154 Raven's Nightmare; CH2-155 Artifacts of Antiquity; CH2-156 Gift of a Madman; CH2-157 Claw of Beliar; CH2-158 Missing Goblin Totem; CH2-159 Missing Brother; CH2-160 Cleansing the Temple of the Sleeper; CH2-163 Secret of the Orc Language
- **Part 5 (5):** CH2-195 Impossible Dream; CH2-205 People in the Valley; CH2-209 Price of Freedom; CH2-210 Clan War; CH2-211 Oddler's Hut

## Next work

1. Investigate the remaining 46 records individually using a version-specific New Balance quest reference, journal screenshots or game scripts. Do not assign a giver on title alone.
2. Cross-check Chapter 2's Desert of Adanos / Raven's Nightmare and Ice Mountains quest lines, where the current reference has thinner coverage.
3. Check duplicated or mistranslated quest-title variants, chapter availability and mutually-exclusive quest branches.
4. Independently replace placeholder XP/item rewards with evidence; avoid fabricating values.
5. Run Gradle compile and phone smoke test after the next validation batch. **No build or APK test has been executed during these edits.**
