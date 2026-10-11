# Gothic 2: New Balance — Chapter 3 correction pass

**Date:** October 10, 2026  
**Repository:** SliverzFX/Questbound  
**Branch:** dev/android-foundation-v0.1  
**Files:** `app/src/main/java/com/sliverzfx/gothicquest/NewBalanceChapter3Part1.kt` and `NewBalanceChapter3Part2.kt`

## Scope

Chapter 3 catalog contains **83 quests**, `CH3-001` through `CH3-083`.

- **Part 1:** 42 quests; rewrote 40 previously templated entries (main story, camp construction, hunters, monastery, faction questlines), preserving two already substantive records.
- **Part 2:** 41 quests; rewrote every record, covering the Thieves' Guild, Merchants' Guild and **Masyaf** questline.
- All 83 IDs are present **exactly once**.
- No instances remain of the previous `Reward varies by quest/build`, `giver = "Trigger quest"`, or boilerplate `Start this quest in`, `Core objective:`, `This is faction progression` text.
- Static check of Kotlin string quoting and balanced parentheses/brackets/braces passed. **This is not a successful Gradle compilation.**

## Notable gameplay corrections

- The Masyaf story requires saving **Gonzales** and infiltrating with **novice robes and no shield**. Avoid speaking to uninvited NPCs until the disguise/promotion allows it; directly attacking costs optional quests and rewards.
- **Missing Novices** must be completed through Masyaf and Cor Kalom's remnant; entering the concealed passage temporarily prevents travel back.
- **Haniar's smoking dialogue** appears during particular daily periods (roughly 08:00–11:00, 15:00–18:00 and 21:00–23:00).
- **Nrozas's Triumph** is a major branch, with **Gift of Fate** on successful poison test versus **Duel of Truth → Offerings to Haniar → Price of Betrayal** on the failed/denunciation route.
- **Black Brandy** has a next-day noon deadline.
- **The Assistant** has different NPC choices and reputation outcomes (Gawern, Maxi, Valeran).
- **Sheepskins for the Guards** has route-dependent gold versus potion rewards.
- **Sekob's Missing Wife** is documented as **Act 5**, despite its existing CH3 catalog location. ID retained to avoid an unreviewed save-state migration.
- **Murder Near the City**, **Fateful Encounter** and **Storming the Castle** are optional Path of the Damned quests with a chapter-placement discrepancy in individual guides; the records now warn about it.
- **Undead Attack** in the Demon Hunters' storyline remains a research gap: exact trigger, encounter location and reward not independently corroborated. The guide labels uncertainty instead of inventing facts.

## Core 2026 guide sources

- Missing Novices: https://rpgrussia.com/threads/propavshie-poslushniki.67360/
- One of Them: https://rpgrussia.com/threads/svoj-sredi-chuzhix.67369/
- Intrigues of the Masyaf Brotherhood: https://rpgrussia.com/threads/intrigi-bratstva-masiaf.67382/
- The Conspiring Slave: https://rpgrussia.com/threads/rab-zagovorschik.67374/
- Payment for the Slaves: https://rpgrussia.com/threads/plata-za-rabov.67376/
- Nrozas's Triumph: https://rpgrussia.com/threads/triumf-nrozasa.67388/
- Dark Secrets: https://rpgrussia.com/threads/mrachnye-tajny.67393/
- Antiques: https://rpgrussia.com/threads/antikvariat.67143/
- Gomez's Curse: https://rpgrussia.com/threads/prokljatie-gomeza.67144/
- Black Brandy: https://rpgrussia.com/threads/chernyj-brendi.67145/
- Helping Sarah: https://rpgrussia.com/threads/pomosch-sare.67147/
- April 2026 quest index: https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/

## Verification boundary

**Content audit complete; exact build/gameplay verification is separate.** The data remains subject to differences between New Balance releases, especially numerical rewards and faction/act gates. This pass changes only the quest data and does not migrate chapter-based progress records or move quest IDs. Any later corrections should be anchored to a specific in-game journal, gameplay demonstration, or confirmed New Balance guide discrepancy.

## Next

If the GitHub Actions run for the final commit fails, inspect its logs and repair compile/test errors. Otherwise the Chapter 3 data pass is closed and work can move to Chapter 4.
