# Questbound — Risen 2 quest-content reconciliation

**Started:** 2026-10-10  
**Working branch:** `dev/android-foundation-v0.1`  
**Game:** *Risen 2: Dark Waters*, original game (note edition/DLC/patch differences separately).

## Initial source inventory and first correction pass

The inherited catalog contained **204 entries** grouped into **four Questbound story chapters** (46 / 79 / 75 / 4). These sections are an app navigation structure, **not the nine geographic regions of the original guide**.

| Kotlin file | Entries now | Blank giver | Blank prerequisites | Blank rewards |
| --- | ---: | ---: | ---: | ---: |
| Risen2GuidePart1Data.kt | 20 | 0 | 0 | 0 |
| Risen2GuidePart2Data.kt | 26 | 0 | 0 | 0 |
| Risen2GuidePart3Data.kt | 35 | 0 | 35 | 0 |
| Risen2GuidePart4Data.kt | 44 | 0 | 44 | 0 |
| Risen2GuidePart5Data.kt | 31 | 0 | 31 | 0 |
| Risen2GuidePart6Data.kt | 25 | 0 | 25 | 0 |
| Risen2GuidePart7Data.kt | 19 | 0 | 19 | 0 |
| Risen2GuidePart8Data.kt | 4 | 0 | 4 | 0 |
| Risen2GuidePart9Data.kt (new) | 3 | 0 | 0 | 0 |
| **Total** | **207** | **0** | **158** | **0** |

The original 204 IDs and their playOrder values remain unchanged. **207 unique quest IDs**, in consecutive four-chapter lists of **47 / 80 / 76 / 4** after adding the missing entries. All three new IDs were appended to their respective sections rather than renumbering existing quests.

### Quest-content corrections already committed

1. **Caldera + early Tacarigua (Part 1):** completed all 20 prerequisite fields; corrected the core Caldera glory rewards:
   - Meet the Commandant! — **50 Glory**
   - Search the Beach — **50 Glory**
   - Rescue Patty — **100 Glory**, in addition to glory for personally defeated Sand Devils
   - Talk to Carlos — **50 Glory**
   - Provisions (`R2-C1-010`) — **ask Osorio whether he sells food** to finish. This is **not** an item hand-in. 50 Glory and trading access.
   - Flotsam (`R2-C1-004`) — start through Storehouse Master after Patty's rescue and return report to Carlos; complete before leaving.
2. **Tacarigua/Pirates' Den (Part 2):** completed all 26 prerequisite fields. Rewrote:
   - The Sugar Trade (`R2-C1-043`) — Booze's return letter to Di Fuego, financing from Pedro, negotiated settlement, Roquefort courier reward, and return to Booze. **100 Glory**, dialogue-dependent gold and five rum.
   - Distract Alister (`R2-C1-044`) — **Morris** provides the plan, **Lola** diverts Alister at night, with a low-cost option carrying relationship consequences. **50 Glory**.
   - 10 Bloody Roots (`R2-C1-045`) — **Elia** gives the task and awards **100 Glory plus the Bloody Mary recipe**.
3. **New source-confirmed journal entries in Part 9:**
   - `R2-C1-047` — **Flotsam on the Beach Collected**: collecting all six pieces completes an independent **50 Glory** milestone, separate from the Storehouse Master's Flotsam hand-in.
   - `R2-C2-080` — **Got Anything to Eat?**: Sancho's Sword Coast food quest, **50 Glory** and an optional additional **25 Glory** on a second helping.
   - `R2-C3-076` — **The Treasure on Fortress Beach**: Caldera II cave treasure requiring a map from a **Lockpicking 60** chest near Carlos, **100 Glory** and the legendary Comb with One Tooth.
4. Regression coverage updated in `Risen2QuestDataTest.kt`: new section counts, unique IDs, early-giver and reward fixes, and populated Chapter 1 prerequisites.

### Remaining backlog (do not mark Risen 2 complete yet)

- **158 remaining blank prerequisites** in Parts 3–8; especially distinguish Native vs Inquisition choices on the Sword Coast and Maracai Bay, mutually exclusive outcomes, and island-access gates.
- **Quest-title coverage expansion:** Gamepressure's complete Risen 2 guide lists considerably more independently named quests across Sword Coast, Antigua, Isle of Thieves, Caldera II, Maracai Bay, Isle of the Dead and Water Temple than our **207 app entries**. Examples requiring reconciliation: *Blades for Cooper*, *Dance with Tito*, *Firebird Hunt*, *Hidden Lookouts*, *Jim's Treasure Map*, *Gibson's Grave*, *Where Is Rick?*, *The Thief's Curse*, *Black Lotus*, *Follow Hakeke*, and faction-exclusive Maracai tribal subquests. Add only documented actual journal objectives, with exact giver/trigger, three useful walkthrough steps, verified reward or a clearly labelled uncertainty, and stable new IDs.
- **Rewards:** many existing nonempty rewards are *generic text*, not verified Glory figures. Check against quest-specific source pages instead of inventing exact values.
- **Reference-only records:** distinguish any quest-chain summaries or repeated guides from actual journal entries before calculating completion percent; preserve all existing progress keys.
- **Android release check:** source integrity review passed (207 IDs, consecutive order, no blank giver/reward), but latest CI JVM tests and emulator UI tests are not yet confirmed green.

### Principal sources

- [Gamepressure Risen 2 quest walkthrough index](https://www.gamepressure.com/risen2/game-guide/zf8cc1)
- [Gamepressure Flotsam](https://www.gamepressure.com/risen2/flotsam/zf392a)
- [Flotsam on the Beach Collected](https://www.gamepressure.com/risen2/flotsam-on-the-beach-collected/z0392b)
- [Provisions](https://www.gamepressure.com/risen2/provisions/z338f2)
- [Kitchen Help](https://www.gamepressure.com/risen2/kitchen-help/z238e2)
- [The Sugar Trade](https://www.gamepressure.com/risen2/the-sugar-trade/z638d7)
- [Distract Alister](https://www.gamepressure.com/risen2/distract-alister/z438e4)
- [10 Bloody Roots](https://www.gamepressure.com/risen2/10-bloody-roots/z138d2)
- [Got Anything to Eat?](https://www.gamepressure.com/risen2/got-anything-to-eat/zd390a)
- [The Treasure on Fortress Beach](https://www.gamepressure.com/risen2/the-treasure-on-fortress-beach/z43a79)

**Status:** First substantive Risen 2 correction pass done; coverage reconciliation and Chapters 2–4 verification are still outstanding.
