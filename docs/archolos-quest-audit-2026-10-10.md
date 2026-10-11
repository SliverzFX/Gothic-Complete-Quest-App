# Questbound — The Chronicles of Myrtana: Archolos quest audit

**Date:** 2026-10-10  
**Repository:** `SliverzFX/Questbound`  
**Branch:** `dev/android-foundation-v0.1`

## Scope and outcome

Reviewed the structure and available walkthrough material of **all 192 existing Archolos records** in the nine `ArcholosChapter*Data.kt` files. Applied targeted content corrections to **91 distinct quest records** (other entries already contained actionable steps and were not blindly rewritten). These include quest-giver/trigger, location, actual chapter availability, walkthrough steps, choice-specific rewards and game-breaking warnings. Corrected `ArcholosQuestData.kt` chapter notes and `ArcholosQuestDataTest.kt` to reflect newly verified chapter placements.

**Counts after correction:**

| Chapter | Quest entries |
|---|---:|
| 1 — Welcome to Archolos | 18 |
| 2 — Without a Trace | 74 |
| 3 — Among the Scoundrels | 41 |
| 4 — A Heart of Stone | 28 |
| 5 — Stability | 29 |
| 6 — Blood on Hands | 2 |
| **Total** | **192** |

The counts represent app quest **entries** (including quest parts and separate route variants), not a claim of exactly 192 distinct, simultaneously available journal missions. Archolos has alternate City Guard, Araxos, Royal Envoy and Ring of Water routes.

## Important findings by chapter

**Chapter 1**
- **Time to Walk It Off:** fixed a misleading generic recovery description; the actual stages involve Jorn's duel, Kurt's recipe, lockpicking lessons and a drinking trip.
- **Beloved Daughter:** explained Riordian's cemetery-script interaction, grave preparation and escort guards.
- **The Art of Writing:** warned that it is missable when Riordian leaves Silbach.
- Replaced vague side-quest rewards with documented XP/item/cost outcomes where sources were sufficient.
- **Last Batch of Stuff** (`AR-C1-019`) and **Merchant Ezekiel** (`AR-C1-020`) belong in **Chapter 2**; IDs preserved but `chapter` updated to 2.

**Chapter 2**
- Corrected City Guard training/duel/quest benefits, alternative sword acquisition and night patrol requirements.
- Corrected Helga's **The Troublesome Three** (avoid drinking Black Troll personally), Araxos initiation and the night-smuggling follow-ups.
- Added useful rewards and route consequences for Drunken Guide, Pearls of Water, Fight Club, A Northern Breeze, and Green Lighthouse.
- **New to Archolos?** through **Golden Innoses** (`AR-C2-067` to `AR-C2-071`) actually begin in **Chapter 3** and now display there, while IDs remain unchanged to preserve saved progress.
- Renumbered Chapter 2 `playOrder` to consecutive 1–74 and updated the note positions without changing any quest IDs.

**Chapter 3**
- Fixed entering Scoundrels' Haven: speak to **Thiago** before Cortez (and Slasher for City Guard).
- Corrected **Who Killed Stan?** evidence locations and the danger of asking Cortez too early.
- Documented the tournament's point of no return, Big Ben's poison/antidote routes and possible future recruitment, and the temporarily disabled city fast-travel.
- **Food Shortages:** five packets must be recovered despite an earlier journal update.
- Corrected Haven side-quest rewards and their relationships with tournament registration and later recruitments.

**Chapter 4**
- Corrected the misleading **The Ring of Water** record. This Chapter 4 story quest is **Kessel's ambushed meeting and escape over the waterfall**, NOT the Chapter 5 Ring initiation/purification ritual.
- Detailed the Wolf's Den entry, Jon evidence investigation, Volker's hidden prison and the chapter's irreversible story choices.
- Updated Royal Envoy route conditions and auction timing.

**Chapter 5**
- Added the complete **From Bad to Worse** monastery puzzle and the exact correct Dagobert's Potion order (milk, King's Sorrel, three Healing Roots, Earth Aloe, Dragonroot, honey) for +50 max HP and XP.
- Corrected recovery of confiscated inventory and the Vardhal Key Fragments, including the museum purchase.
- Corrected recruitment/provisions rewards (including Kessel's 2,500 XP and 7,500 XP tasks), plus the optional Kessel training (+3 STR, +3 DEX, +10 HP).
- **Critical point of no return:** opening Vardhal's rebuilt gate fails nearly all unfinished outside quests. Two guild-quest exceptions are documented. Reminded players to leave a pet dog with Viktor beforehand.
- Made Ulryk's forced Peacemaker surrender explicit rather than implying his refusal was a normal combat choice.

**Chapter 6**
- **Black Hour:** documented an optional **early ending** before pursuing Ulryk and Volker.
- **A City on Fire:** documented Javad's effect on Lorenzo's survival, the route through the burning city, Ulryk's invisible-doorway barrier, the conditional Ivy alliance and Volker's final showdown.

## Validation

Latest structural audit against committed branch:
- **192 records**, all IDs unique.
- Chapter `playOrder` values are consecutive from 1 in **all six chapters**.
- All records contain at least two walkthrough steps, and all ten Archolos Kotlin source files (nine chapter files plus guide aggregation) pass a balanced quotation/bracket/parenthesis check.
- **40 guide notes** are within each chapter's valid placement range.
- Remaining generic `giver = "Trigger quest"` labels: **0**.
- `ArcholosQuestDataTest.kt` updated to new expected counts and to verify the seven reclassified quest IDs.

### Limitations

This is a code/content source-reference review, **not** full playtesting of every branch, NPC or reward in every Archolos version. Some older rewards remain outcome-oriented where their exact value was not verified. The 192 catalog entries are **not a proof of exhaustive coverage of every in-game quest**.

GitHub Actions runs after the initial chapter reclassification failed because an old unit test expected chapter counts `[20,77,36,28,29,2]`. That test is now updated to `[18,74,41,28,29,2]`. A successfully completed latest Android build, full unit suite and emulator UI tests must be verified independently.

## Primary reference

GooBall's walkthrough for The Chronicles of Myrtana: Archolos, updated **2026-08-05**, especially:
- https://gamefaqs.gamespot.com/pc/353946-the-chronicles-of-myrtana-archolos/faqs/82500/table-of-contents
- https://gamefaqs.gamespot.com/pc/353946-the-chronicles-of-myrtana-archolos/faqs/82500/chapter-4-a-heart-of-stone
- https://gamefaqs.gamespot.com/pc/353946-the-chronicles-of-myrtana-archolos/faqs/82500/chapter-5-stability-part-i-archolos
- https://gamefaqs.gamespot.com/pc/353946-the-chronicles-of-myrtana-archolos/faqs/82500/chapter-5-stability-part-ii-vardhal
- https://gamefaqs.gamespot.com/pc/353946-the-chronicles-of-myrtana-archolos/faqs/82500/chapter-6-blood-on-hands

**Next:** Treat reproducible quest discrepancies, omitted quests, or failing tests as focused follow-ups rather than undoing the source-verified quest placements.
