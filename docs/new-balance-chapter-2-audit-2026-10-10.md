# Gothic 2: New Balance — Chapter 2 correction pass

**Date:** 2026-10-10  
**Repository:** SliverzFX/Questbound  
**Branch:** dev/android-foundation-v0.1  
**Scope:** `NewBalanceChapter2Part1.kt` through `NewBalanceChapter2Part5.kt`.

## Scope and recorded results

- **211 Chapter 2 catalog entries** spread across five Kotlin data files (43, 43, 43, 43, 39).
- Every ID from `CH2-001` to `CH2-211` is present exactly once.
- Removed the literal generic reward field `Reward varies by quest/build; exact values should be taken from the current in-game dialogue or source page.` from this chapter.
- Removed the literal generic prerequisite `Chapter 2; complete earlier quests in the same area/faction if the dialogue is not yet available.`.
- Replaced 50 repetitive warnings with quest-specific advice and conditions, alongside earlier edits.
- Corrected and detailed rewards, route-dependent outcomes, unlocks, collection requirements and costs in the Chapter 2 entries without manufacturing unknown XP amounts.
- Parenthesis, brace, bracket, and quote balance checked in all five files, with no detected mismatch.

## Limits: verified vs. not independently confirmed

The **catalog consistency and placeholder sweep is complete**. This is **not** proof that every quest-giver name, numeric reward, and instruction in all 211 entries exactly matches every New Balance build. When precise values could not be corroborated, entries now describe the documented quest outcome and mark exact amounts as unverified rather than asserting an invented number.

Several entries extend into later chapters, and the optional `AB_PathOfTheDamned_Mod` questline is not part of unmodified New Balance. These restrictions are preserved in relevant prerequisites or warnings.

### Known classification issue

`CH2-010: Ragnar's Bandits` is retained under its existing catalog ID to avoid an unreviewed migration of user journal state and numbering. The updated quest record warns that the April 2026 New Balance guide places it in **Act 3**. If the app later supports cross-chapter reclassification with stable IDs, this is a candidate to move.

- New Balance guide: https://rpgrussia.com/threads/bandity-ragnara.66778/
- Earlier Returning 2.0 guide lists Act 2: https://rpgrussia.com/threads/bandity-ragnara.15135/

### High-impact progression warnings

- Avoid losing Orc City questlines by killing friendly named orcs prematurely; acquire Orcish and the Ulu-Mulu for the intended route.
- `CH2-139`: accept Heart of the Harpy Queen before recruiting Gestath.
- `CH2-178`: killing Albert can lose his paladin quests.
- `CH2-180`: handing over Drag Nimrod means giving up the unique crafted crossbow.
- `CH2-103`: an unopened lockpick package gives the best Fisk reward.
- `CH2-156`: the Fellangor choice can fail Zigos's objective.

Additional community cross-checks:  
https://rpgrussia.com/threads/novyj-balans-orki-ne-puskajut-v-gorod-orkov.40370/  
https://rpgrussia.com/threads/novyj-balans-gorod-orkov-provaleno-proxozhdenie.32351/  
https://rpgrussia.com/threads/novyj-balans-zadanija-kotorye-legko-zavalit-sovety-po-igre.39514/page-5

## Verification

The GitHub Actions Android workflow triggers on pushes to this branch. At time of this audit, the latest workflow was **in progress**, so a successful Android build, unit test run, and UI instrumentation run have **not yet been confirmed**.

**Next content phase:** no further Chapter 2 blanket placeholder sweep is planned; future changes should be driven by concrete in-game evidence, specific guide contradictions, or failing tests.
