# Questbound — Risen 1 quest-data audit (2026-10-10)

Branch: `dev/android-foundation-v0.1`  
Scope: Original Risen 1 game; eight Kotlin parts, four narrative chapters.

## Baseline structural inventory (before first change)

| File | Quest entries | Empty giver | Empty prerequisite | Empty reward |
|---|---:|---:|---:|---:|
| RisenGuidePart1Data.kt | 16 | 0 | 0 | 0 |
| RisenGuidePart2Data.kt | 12 | 0 | 0 | 12 |
| RisenGuidePart3Data.kt | 16 | 0 | 0 | 16 |
| RisenGuidePart4Data.kt | 45 | 0 | 45 | 0 |
| RisenGuidePart5Data.kt | 22 | 0 | 22 | 22 |
| RisenGuidePart6Data.kt | 20 | 0 | 20 | 0 |
| RisenGuidePart7Data.kt | 21 | 0 | 21 | 0 |
| RisenGuidePart8Data.kt | 17 | 1 | 17 | 1 |
| **Total** | **169** | **1** | **125** | **51** |

These are **source field blank counts**, not claims that rewards or dependencies are missing from the actual game. Nonblank text may also be inaccurate or imprecise. Do not fill gaps with invented experience numbers or generic text.

## First verified correction

- `R1-C4-007` — **Lizard invasion!**: Chapter 4 Severin farm encounter. Speak with Severin; defeat five lizardmen; report back. Classic walkthrough documents 500 quest XP and 100 follow-up XP. Distinguish it from the Chapter 3 invasion at Jasmin/Henrik's hut.
- Existing quest ID and play order preserved.

References:
- https://gamefaqs.gamespot.com/ps4/383727-risen/faqs/58853
- https://gamefaqs.gamespot.com/pc/952152-risen/faqs/58833

## Further verification work

1. Fill documented rewards in Parts 2, 3 and 5 only after comparing independent Risen classic walkthroughs.
2. Reconcile quest prerequisites for Parts 4 through 8, especially faction-exclusive quests, mutually exclusive choices, and chapter gates.
3. Validate quest titles and alternate journal entries; retain IDs and distinguish reference guides from trackable quests if necessary.
4. Review giver/location claims, numeric XP, walkthrough goal/completion conditions, and cross-quest dependencies.
5. Add structural regression tests (ID uniqueness, chapter order, required fields) and run the Android build, JVM tests and emulator instrumentation. Avoid representing a green compile as game-content verification.

This is an **initial inventory and one confirmed correction**, not yet a complete Risen 1 verification.
