# Gothic II New Balance — Chapters 1–2 walkthrough description cleanup

**Checked:** 2026-10-10  
**Repository:** [SliverzFX/Questbound](https://github.com/SliverzFX/Questbound)  
**Branch:** `dev/android-foundation-v0.1`  
**Scope:** Walkthrough summaries, instruction steps, quest-start prerequisites, and related NPC/trigger corrections in Chapter 1 and 2 Kotlin files.

## Task 1: identified generic templates cleared

| Code-scan metric | Chapter 1 | Chapter 2 | Combined |
| --- | ---: | ---: | ---: |
| Quest entries | 283 | 211 | 494 |
| Remaining known generic summary templates | 0 | 0 | 0 |
| Remaining known generic opening walkthrough steps | 0 | 0 | 0 |
| `giver = "Trigger quest"` placeholders | 0 | 0 | 0 |
| Rewards still showing `Reward varies by quest/build` | 119 | 192 | 311 |

**Cleanup count:** 97 summaries from the original October 10 quest-giver audit, plus **51 additional generic entries** discovered by broadening the scan to patterns such as `Core objective:` and `Use the castle/known survivor camps`. All **148 identified** summary templates were replaced with quest-specific information. Where these entries had generic walkthrough steps, those steps were replaced as well.

**Validation:** read back all 7 Chapter 1 + 5 Chapter 2 Kotlin files from GitHub. Verified 494 quest entries, no duplicated quest IDs in the catalogue, no remaining patterns from the targeted placeholder dictionary, and balanced parentheses/brackets and string literals on quest source lines. **This is not a full Kotlin/Gradle compile and is not a game-script certification.**

## Additional discovered corrections

- **CH1-041 Jack's Pipe:** available in Chapter 2 after Jack's lighthouse prerequisites and Greg's return, though currently catalogued in Chapter 1 by its original assignment.
- **CH1-049 Lucia:** Bromor is the quest's initiating NPC; Nadja and Elvrich are related investigative contacts.
- **CH2-024 Saturas's Notes:** the quest is actually initiated by Lee at Onar's farm, not Saturas.
- **CH2-081 The Tower:** Henry or Greg may start/close it depending on pirate camp progress.
- **CH2-084 Meat for Morgan:** starts with Alligator Jack, not Morgan.
- **CH2-197 Distract the Orc Dabar-Shak:** Hildur gives the quest; Dabar-Shak is the target.
- **CH2-132 Hunting Glorks:** initiated by **Fajeth** at his mining camp, involving eight snapper-like glorks at three sites: three by the lake, two along the road toward Xardas's tower, three on the ledge above camp.
- **CH2-124 Kervo's Lurkers:** **distinct from Fajeth's Hunting Glorks**. The 2026 Russian quest is *Шныги Керво* (Kervo's Snappers), concerning the cave near Marcos's ore group; the precise English translated title `Kervo's Lurkers` requires a version 8.0 in-game comparison.

## Reference sources

- [New Balance April 2026 complete quest index](https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/)
- [Fajeth's Hunting Glorks](https://rpgrussia.com/threads/oxota-na-glorxov.67088/)
- [Kervo's Snappers](https://rpgrussia.com/threads/shnygi-kervo.66932/)
- [Bilgot's Escape](https://rpgrussia.com/threads/pobeg-bilgota.67090/)
- [Angar's Amulet](https://rpgrussia.com/threads/amulet-angara.66937/)
- [Oric's Pleas](https://rpgrussia.com/threads/molby-orika.66947/)
- [Ur-Shak](https://rpgrussia.com/threads/ur-shak.67295/)
- [Ur-Hash-Nar's Book](https://rpgrussia.com/threads/kniga-dlja-xash-nara.66972/)
- [Legendary Hunter Weapon](https://rpgrussia.com/threads/oruzhie-velikogo-oxotnika.67198/)
- [Ice Wind Clan Elder Trophy](https://rpgrussia.com/threads/trofej-dlja-grum-loka.67155/)
- Further per-quest source links are recorded in the corrected Kotlin `warnings` fields.

## Important limitations

1. The source audit removed **known formulaic templates**, not every possible inaccuracy. Another review should still compare the 494 quest texts against the specific Gothic II New Balance 8.0 scripts and exact English translation, especially conditions that depend on the current patch.
2. Some entries start in one chapter but finish in another, some are hidden achievements rather than normal journal quests, and Path of the Damned requires an optional plugin. **Categorization** remains a separate workstream.
3. **Task 2 — Rewards remains pending.** There are 311 unchanged `Reward varies by quest/build` placeholders (Chapter 1: 119; Chapter 2: 192). The task-1 edits did not systematically validate rewards.
4. **Task 3 — full factual correctness / translation validation**, Task 4 — categorization, Task 5 — Gradle build and APK smoke-test remain separately pending.
5. No Android build or APK device test has been run in this audit.

**Task 1 result:** All **148 identified** generic descriptions now contain specific quest guidance. Further quality checks are advisable, but the known template backlog is cleared.
