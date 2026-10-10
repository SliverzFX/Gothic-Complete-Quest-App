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

## Second pass: Bandit Camp correctness (2026-10-10)

- `R1-C1-017`, **Gold Fever**: corrected giver from Don Esteban to **Rachel** and replaced the vague Don digger conversation with the three required camp quest lines: **The workers are to work again**, **The hunters are to go hunt**, and **Power struggle**. No numeric reward was fabricated.
- `R1-C1-018`, **Beer for the gang**: corrected giver to **Rhobart**, delivery direction to **Rhobart → Rachel**, and the return/payment step. Classic walkthrough references award **200 XP for the delivery** and **100 XP for reporting back**; the gold outcome varies with dialogue.
- Added explicit JUnit regression assertions for these quests and Severin's Chapter 4 `R1-C4-007` invasion entry.

Sources:
- https://www.gamebanshee.com/risen/walkthrough/ch1banditcampquestspart1.php
- https://gamefaqs.gamespot.com/pc/952152-risen/faqs/58833
- https://gamefaqs.gamespot.com/xbox360/959034-risen/faqs/58853

These corrections make the two earlier Bandit Camp walkthroughs materially more accurate; this is not a declaration that the remaining source fields are verified.

## Consolidated 169-entry reconciliation (October 10, 2026)

This pass updated the eight Risen 1 Kotlin data files in one sustained sweep:

- **Chapter 1 Bandit Camp:** fixed Rachel's Gold Fever objective; Rhobart's Beer for the Gang delivery; Hawkins/Dwight's workers chain; Brogar's protection money; Craig's four arena challengers; Doug's Rotworm hunt; Scordo's package delivery.
- **Chapter 1 Harbour Town:** replaced incorrect Leonardo/Costa attributions with Master Belschwur's five medicine deliveries and Flavio/Baxter's skins-for-meat chain; documented Rodriguez/Doyle's burglar investigation, correct Carasco/Scordo golden bowl hand-ins, Cid's uncertain butcher quest, Romanov/Sergio rival chest hand-ins, the four Order-aligned cases versus Scordo's Don-aligned objectives, and Tilda's three sons.
- **Chapter 1 Volcano Keep:** wrote explicit prerequisites for all 45 entries, clarifying tests, teacher relationships, Mage-only Wisdom requirements, and mutual prerequisites. Corrected selected exact rewards against original-game walkthroughs.
- **Chapter 2 (both data files):** reconciled all 42 prerequisites and 22 previously empty rewards, including all five disk sources, the Severin gnome encounter, Gyrger, Cyrus/Eldric chain, eastern temple expedition, Patty/Romanov choices and important scroll/teleport outcomes.
- **Chapter 3:** reconciled all 21 prerequisites, volcano temple gate/drawbridge/crypt order, and the distinction between the world lizard encounters and faction dialogue.
- **Chapter 4:** reconciled all 17 prerequisites, the five Titan equipment pieces, Illumar's barrier scrolls, Eldric's reforging reward, Severin's invasion, and Mendoza's Ocular.
- **Progress safety:** no existing quest IDs or play orders removed or changed. The six explicitly labeled guide/cross-reference records are now excluded from the trackable-completion count; their IDs and existing saved keys are retained.

### Source-level results (across current 169 app entries)

- **169 unique IDs** in 4 narrative chapters with no removed quest IDs.
- **0 empty giver, 0 empty prerequisite, 0 empty reward** fields after this pass.
- **163 tracked entries + 6 guide-only/cross-reference entries** after classifying duplicate first/second runic seals, Magic Bullet, Frost Crystal, the Chapter 2 crystal disk guide tracker, and the Harbour Town route guide.
- Exact XP amounts have been added only where documented; other reward fields clearly state uncertainty or conditional outcomes. **Nonblank fields must not be mistaken for individually game-tested accuracy.**
- Added tests for metadata completeness, original quest counts, known corrected NPCs and reference-only IDs. Final CI/Android emulator outcome must be checked separately.

### Significant catalog coverage caveat

The external Myrtana.net Risen wiki currently lists **263 total quests**. The app's **169 legacy entries are a curated/combined set**, not proof of all possible journal quests. Some of the 263 are individual subtasks folded into a combined app walkthrough; others are real missions not represented as standalone records (especially the Bandit Camp and Harbour Town side quests). Therefore **do not call this Risen 1 catalog fully comprehensive** until a name-by-name 263-vs-169 coverage comparison is completed and missing standalone quest entries are added with reliable walkthroughs.

Examples of individual source-confirmed side quests **not currently standalone app entries** include: *Golden Fragments*, *Branon Needs Help*, *To the Temple Ruins with Lorenzo*, *Scurrying Rats*, *Rhobart's Bog Bodies*, *Dorgan's List*, *The Farmers Sick Wife*, *Mental Arithmetic*, *Nelson's Ring*, *Pearls for a Lady*, *Good Things Come in Threes*, *Anything That Heals*, *The Cursed Lords*, and *Everything That Isn't Nailed Down*. This list is illustrative, not exhaustive. Their sources provide concrete locations and XP, so they can be added without fabricating data.

Primary source comparisons:
- https://gamefaqs.gamespot.com/xbox360/959034-risen/faqs/58853
- https://gamefaqs.gamespot.com/pc/952152-risen/faqs/58833
- https://www.gamebanshee.com/risen/walkthrough.php
- https://www.myrtana.net/en/wiki/risen

**Verdict:** completed source-field and duplicate reconciliation for the **existing** 169 app records; full **game-quest coverage and in-game accuracy verification remain open**, as does release CI/real-device testing.

### Saved-progress migration warning

Stable IDs protect the storage key format, but a few previously vague records now identify different concrete journal objectives (for example `R1-C1-020`, `R1-C1-026`, `R1-C1-027`, and `R1-C1-040`). If anyone installed an earlier Questbound build and completed one of these IDs, the saved checkmark may incorrectly apply to the newly documented objective. The data correction pass deliberately did **not** delete or silently rewrite user progress. Before public release, determine whether a targeted one-time migration or a user-visible reconciliation notice is appropriate.
