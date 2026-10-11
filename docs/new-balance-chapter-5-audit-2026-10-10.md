# Gothic 2: New Balance — Chapter 5 correction audit

**Date:** 10 October 2026  
**Repository:** `SliverzFX/Questbound`  
**Branch:** `dev/android-foundation-v0.1`  
**Data file:** `app/src/main/java/com/sliverzfx/gothicquest/NewBalanceChapter5Data.kt`

## Coverage

All **nine** Chapter 5 quest records (`CH5-001` through `CH5-009`) were reviewed and rewritten as step-by-step walkthroughs, with **72** individual quest fields updated in one code commit:

`37fba85a5ecb46b3eca9eefa060e188fd4df6aac`

The audit corrected automatic/non-journal triggers, faction-specific captain and ship requirements, actual area locations and categories, reward numbers from the April 2026 New Balance guides, optional interactions, and avoidable softlocks.

| ID | Quest | Important audit result |
|---|---|---|
| CH5-001 | Lord of the Dragons | Correct monastery book → Lester's letter → reopen book → library lamp → Ilkrus → secret Irdorath map sequence |
| CH5-002 | I Need a Ship | Starts **automatically** from the secret-library book; Brotherhood uses Idol Oran, mercenaries may secure the judge's letter, other guilds use captain/crew access |
| CH5-003 | Who Will Be My Captain? | Distinguishes Jorgen, Jack, and Torlof routes; Torlof demands **2,500 gold** and has major Innos/Beliar karma consequences |
| CH5-004 | No Voyage Without a Crew | Minimum five companions; **500 XP per recruit**, with conditional availability, services, and Mario warning |
| CH5-005 | Rosie's Disappearance | Guild-based escort destinations; 1,250 XP and 200 gold |
| CH5-006 | Dark Order | Correct initial Chromanin hypothesis, Kreol explanation, three paladin tomb keys, Ginnok's crypt and forging recipe, staged XP |
| CH5-007 | Urban Bandits | **No quest giver or journal entry**; six peaceful relocated NPC conversations for up to 3,000 XP |
| CH5-008 | Return to the Tower | Pyrokar starts Jorgen's prerequisite; skeleton warriors and Demonic Guardian in Xardas's library; 3,000 XP |
| CH5-009 | Good Connections | Idol Oran, Brotherhood only, 2,000 gold ship permit, 2,500 XP |

## Sources

Primary reference: the **April 2026** New Balance quest-by-location index and its individual walkthroughs (RPG Russia):

- Index: https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/
- Lord of the Dragons: https://rpgrussia.com/threads/povelitel-drakonov.67118/
- I Need a Ship: https://rpgrussia.com/threads/mne-neobxodim-korabl.67119/
- Who Will Be My Captain?: https://rpgrussia.com/threads/kto-budet-moim-kapitanom.67121/
- No Voyage Without a Crew: https://rpgrussia.com/threads/bez-komandy-net-puteshestvija.67122/
- Rosie's Disappearance: https://rpgrussia.com/threads/ischeznovenie-rozi.66763/
- Dark Order: https://rpgrussia.com/threads/temnyj-orden.66796/
- Urban Bandits: https://rpgrussia.com/threads/urbanizirovannye-bandity.66813/
- Return to the Tower: https://rpgrussia.com/threads/vozvraschenie-k-bashne.67120/
- Good Connections: https://rpgrussia.com/threads/xoroshie-svjazi.67117/

## Validation and limits

Static verification of the saved Chapter 5 Kotlin file:

- Nine distinct quest IDs with no gaps or duplicates: **passed**.
- Multi-step walkthroughs for every entry: **passed**.
- Generic `Trigger quest` giver, generic `Chapter 5` location/category, and PDF extraction markers: **0 remaining**.
- String escaping, brackets and parentheses balance: **passed**.

**Android compilation and instrumentation tests are separate.** The GitHub Actions workflow for `37fba85a` was still running when checked. Earlier Android UI tests were failing despite successful build jobs; this audit does **not** mark the current APK as successfully built or all gameplay branches tested.

**Next:** Chapter 6. Reopen Chapter 5 only for reproducible in-game discrepancies, updated source evidence, or build-test findings.
