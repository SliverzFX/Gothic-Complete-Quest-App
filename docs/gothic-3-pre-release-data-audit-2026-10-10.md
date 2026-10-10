# Questbound — Gothic 3 pre-release quest-data reconciliation

**Audit date:** October 10, 2026  
**Branch:** `dev/android-foundation-v0.1`  
**Scope:** Two follow-ups: fill documented reward/prerequisite gaps; audit and separate duplicate/guide-only entries.

## Result at source-code checkpoint

| Regional section | Legacy records | Trackable mission entries | Reference-only records |
|---|---:|---:|---:|
| Part 1 — Ardea, Reddock, Cape Dun, Montera | 84 | 84 | 0 |
| Part 2 — Okara, Gotha, Trelis, Nemora | 68 | 68 | 0 |
| Part 3 — Geldern, Silden, Vengard, Faring | 70 | 56 | 14 |
| Part 4 — Nordmar | 72 | 72 | 0 |
| Part 5 — Northern Varant | 56 | 47 | 9 |
| Part 6 — Southern Varant | 66 | 59 | 7 |
| Part 7 — Global completionist guides | 10 | 0 | 10 |
| **Total** | **426** | **386** | **40** |

**All 426 legacy IDs remain unchanged.** No entries were deleted; this preserves deep links and stored progress keys.

- **Blank reward fields: 0**, down from 34 at the start of this pass.
- **Blank prerequisite fields: 0**, down from 29.
- No generic `Begin with ...` walkthrough templates remain.
- All entries retain at least two walkthrough steps and unique, consecutive `playOrder` in their respective existing regional sections.
- Kotlin source files and UI pass a basic syntax-delimiter/quotation check. This is not a substitute for a successful Android build and device run.

**Important interpretation:** A populated reward is **not always a verified numeric reward**. Several explicitly say “No separate reward confirmed” when a historic record is an uncertain or duplicated guide. Likewise, “386 trackable entries” is a UI count of quest-like records **not proof that all 386 correspond to unique, verified in-game journal quests in every patch**.

## 1. Reward and prerequisite reconciliation

Source-based corrections:
- Ardea's original liberation: 500 XP / +2 Rebels; Hamlar to Javier: 1,000 XP / +1 Rebels; Gorn to Reddock: 250 XP / +1 Rebels.
- Nordmar Hanson's plateau: 11 deer, 1,500 XP / +5 Wolf Clan / +1 Hunting.
- Proper prerequisites for early Okara quests, Nemora's Hengley/Farmon investigation, Harek/Bufford/Iomar, and patch/faction-gated tasks.
- Source-verified Geldern Lares golden plates (2,000 XP / +2 Thieving), Grok/Runak Orc investigation and Samuel mine-clearance chain.
- Silden's documented Inog/Anog message, the Jarock–Jaroll–Trompok arena, and Pavel's escort.
- Vengard's documented Georg escort, Keldron's **20 Weapon Bundles**, Abe's separate **30 bread** and **30 roasted meat** jobs, and Rhobar's four outside siege-leader quest.
- Faring's Ali ancestor stone, Rocko goblin cave and Zakosh stone-lifting smithing lesson.
- Braga Diego snapper hunt, and Diego's separate Mora Sul arrival milestone.

Unverifiable legacy rewards and unsupported NPCs were **not given fabricated figures**. Instead the entry is visibly labeled as a guide/unverified reference with links to the closest documented actual quest and explicit warning.

## 2. Duplicate and guide-only reconciliation

Converted repeated content to labeled **GUIDE — CROSS-REFERENCE**, **GUIDE — UNVERIFIED**, **GUIDE — REPUTATION OVERVIEW**, or **GUIDE — INTRODUCTORY DIALOGUE**:

| Reference ID(s) | Canonical quest entry / situation |
|---|---|
| G3-P3-003 | G3-P3-002 — Nemrok's Shadow Scepter |
| G3-P3-013 | G3-P3-012 — Golden plates for Lares |
| G3-P3-036 | G3-P3-029 — Silden wood to Givess |
| G3-P3-037 | G3-P3-028 — Silden fish to Givess |
| G3-P3-065 | G3-P3-040 — escort Lars to Nordmar pass |
| G3-P6-033, G3-P6-060 | G3-P6-021 — Gonzales's seven-Water-Mage assassination order |
| G3-P6-034 | G3-P6-029 — liberation of Al Shedim |

Several other legacy placeholder entries were treated as guide-only because the classic quest list did **not** corroborate a distinct giver or journal reward: Geldern's shadowbeast and minecrawler-plate tasks, Silden's unspecified Orc patrol, Ben Erai's historical “ten gold nuggets” ghost task, Ben Sala's generic Basir chest and road-clearance tasks, introductions to Fabio/Julio/Myxir/Saturas/Zuben, and general faction planning/reference notes.

**Not duplicates:** The five individually counted Nordmar Orc military camps, the five keys held by different Mora Sul characters, and separately named arena opponents are distinct goals. No IDs were merged or deleted.

## 3. Android app behavior

Changes to `Gothic3QuestData.kt`, `GothicQuestAppV2.kt` and `Gothic3QuestDataTest.kt`:

- `isGuideEntry(quest)` classifies category `GUIDE...` and all Part 7 global guides as reference-only.
- The catalog still exposes `Gothic3QuestData.quests` with all 426 IDs.
- The app can derive **386 trackable** and **40 reference-only** entries without data deletion.
- Reference cards appear after the journal list, with a **REFERENCE GUIDES — NOT JOURNAL QUESTS** heading.
- Reference detail pages remain searchable, linkable, readable and favoriteable, but **no longer offer the Completed/In Progress/Not Started quest controls or count toward chapter completion percentages**.
- The seven-part game hub distinguishes Part 7's reference-only library from an empty quest chapter.
- Existing saved completed/in-progress/notes/favorites keys are **not deleted or automatically reassigned**. A legacy guide's prior completed flag is simply excluded from the new tracked completion count.
- Regression tests cover section sizes, 426 unique IDs, 40 guide-only entries, 386 tracked entries, 0 blank prerequisites/rewards, several canonical cross-references, and no generic walkthrough steps.

## 4. Build and release caveats

- Static source check on the updated branch: no duplicate IDs or chapter-order gaps, no unclosed Kotlin brackets or quotes.
- GitHub Actions **build and unit tests passed at commit `8eda705be`** (after the duplicate classification and regression update). Subsequent UI presentation commits and their complete workflows must be checked independently before release.
- Android instrumentation tests can fail separately even when the build and JVM unit tests pass; emulator result for latest commit is not yet confirmed.
- This work used the **classic base-game guide**. Community Patch, alternative balancing and quest-package versions may change some exact XP values and triggers.
- Although existing progress keys remain stable, some earlier placeholder quest IDs were reclassified to a *different* genuine journal task during prior corrections. If a public build has already stored user progress, inspect a migration strategy **before publishing** rather than automatically marking those new tasks complete.

## Primary verification references

- [Ardea](https://www.gamepressure.com/gothic3/ardea/z95c1)
- [Geldern](https://www.gamepressure.com/gothic3/geldern/zc5c4)
- [Silden](https://www.gamepressure.com/gothic3/silden/z95ca)
- [Vengard](https://www.gamepressure.com/gothic3/vengard/zb5cc)
- [Faring](https://www.gamepressure.com/gothic3/faring/zb5c3)
- [Wolf Clan](https://www.gamepressure.com/gothic3/wolf-clan/z05d7)
- [Braga](https://www.gamepressure.com/gothic3/braga/z95d0)
- [Miscellaneous quest/Diego milestones](https://www.gamepressure.com/gothic3/other/zb5db)

**Overall:** The two requested source-level cleanup tasks are complete. Release readiness still requires verification of the final UI build/instrumentation, real-device browsing and patch-specific quest accuracy.
