# Gothic II New Balance — Chapters 1–2 quest-giver audit complete

**Checked:** 2026-10-10  
**Repository:** [SliverzFX/Questbound](https://github.com/SliverzFX/Questbound)  
**Branch:** `dev/android-foundation-v0.1`  
**Earlier baselines:** [October 9](newbalance-chapters-1-2-quest-audit-2026-10-09.md), [October 10 in-progress](newbalance-chapters-1-2-quest-audit-2026-10-10.md)

## Final code-level status

| Measure | Chapter 1 | Chapter 2 | Combined |
| --- | ---: | ---: | ---: |
| Quest catalogue entries | 283 | 211 | 494 |
| Entries with `giver = "Trigger quest"` | **0** | **0** | **0** |
| Quest ID duplicates | — | — | **0** |
| Generic-pattern summaries still requiring a content review | 57 | 40 | **97** |
| `Reward varies by quest/build` placeholder reward fields | 119 | 192 | **311** |
| Extracted PDF page markers | 0 | 0 | **0** |

**This concludes the placeholder quest-giver sweep, not the full factual correctness audit.** Every entry has a non-placeholder giver or an event/object/discovery initiation, but all 494 quest texts have **not** been compared to an exact New Balance 8.0 script build. Some generic summaries and 311 reward placeholders still need a separate follow-up.

## Change summary

- At October 9 close: 32 Chapter 1 and 82 Chapter 2 outstanding (114).
- At October 10 morning checkpoint: 19 Chapter 1 and 27 Chapter 2 outstanding (46).
- This final October 10 pass replaced the final **46** `Trigger quest` values (19 Chapter 1 + 27 Chapter 2) with source-supported NPCs or honest note/location/achievement triggers.
- 28 accidental `<PARSED TEXT FOR PAGE: n / 79>` PDF-import markers were removed from Chapter 2 descriptions across five files, without deleting quest records.
- Re-scanned all seven Chapter 1 Kotlin files and five Chapter 2 files from the development branch after writes.

## Representative verified sources

Most quest names/starts were matched to individual dedicated **April–May 2026** New Balance walkthroughs in [the RPG Russia quest index](https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/). Individual references include:

- [An Unexpected Meeting (Ur-Karras)](https://rpgrussia.com/threads/neozhidannaja-vstrecha.66753/)
- [Dead or Alive (bounty board)](https://rpgrussia.com/threads/zhivym-ili-mertvym.67173/)
- [Highlander (hidden music achievement)](https://rpgrussia.com/threads/gorec.67270/)
- [Underground Production (Enim; Chapter 3 component)](https://rpgrussia.com/threads/podpolnoe-proizvodstvo.66814/)
- [Attack on the Merchant (Erol)](https://rpgrussia.com/threads/napadenie-na-torgovca.66834/)
- [Model Soldier (Wulfgar; hidden dialogue reward)](https://rpgrussia.com/threads/obrazcovyj-soldat.66859/)
- [Special Swampweed Joint (Anoy's note)](https://rpgrussia.com/threads/osobyj-kosjak-bolotnoj-travy.66839/)
- [Fistfights (Mastbo; hidden event)](https://rpgrussia.com/threads/kulachnye-boi.66840/)
- [Bandit Attack (Angel; hidden event)](https://rpgrussia.com/threads/napadenie-razbojnikov.67333/)
- [Unlucky Hunter (Grobok's note; hidden event)](https://rpgrussia.com/threads/neudachlivyj-oxotnik.67086/)
- [Kervo's Snappers (CH2-132)](https://rpgrussia.com/threads/shnygi-kervo.66932/)
- [Forest Camp (Isidro)](https://rpgrussia.com/threads/lesnoj-lager.66936/)
- [Reconnaissance of the Free Mine (Garond)](https://rpgrussia.com/threads/razvedka-svobodnoj-shaxty.66949/)
- [Cleansing the Temple of the Sleeper (Dark Stranger)](https://rpgrussia.com/threads/ochischenie-xrama-spjaschego.66962/)
- [Secret of the Orc Language (multiple initiators)](https://rpgrussia.com/threads/sekret-jazyka-orkov.66965/)
- [Impossible Dream (Nuts)](https://rpgrussia.com/threads/nesbytochnaja-mechta.66992/)
- [Secrets of the Temple of Adanos (tablet)](https://rpgrussia.com/threads/tajny-xrama-adanosa.67352/)
- [Artifacts of Antiquity (Saturas)](https://rpgrussia.com/threads/artefakty-drevnosti.67354/)
- [Gift of a Madman (Xardas/Kreol)](https://rpgrussia.com/threads/dar-bezumca.67353/)
- [Claw of Beliar (Saturas)](https://rpgrussia.com/threads/kogot-beliara.67355/)

## One translation still needs in-game checking

**CH2-132 "Hunting Glorks"**: Its associated April 2026 New Balance entry appears in Russian as `Шныги Керво`, best translated `Kervo's Snappers`. That source names **Kervo**, says the creatures are near the escaped convicts' cave close to Marcos, and lists 500 XP plus guild-dependent rewards. The match is strong by quest location and subject, but the precise 8.0 English title **"Hunting Glorks"** cannot be independently certified from the source. The existing title has intentionally been retained, and the individual quest's warning field carries this discrepancy. Confirm its actual on-screen journal title before renaming.

## Additional caveats

- **Hidden activities vs real journal quests:** `Highlander`, `The Fountain`, `Fistfights`, `Model Soldier`, `Unlucky Hunter` and other entries can be achievements, encounters or dialogue rewards rather than journal quests. They should be labeled clearly in a later catalogue/category polish.
- **Optional content:** Path of the Damned requires an optional plugin; some of its arcs continue beyond Chapter 2. Availability needs filtering by installed addon/version.
- **Earlier named quest-givers remain unchecked as a population:** This sweep focused on the outstanding placeholders and corrected specific detected mismatches. A named giver in the code is not proof of game accuracy.
- **Guide versions:** The April–May 2026 reference walkthroughs are much more precise than generic guides, but an exact New Balance 8.0 English-localization check is still desirable for titles, item names, prerequisites and rewards.
- **Compilation:** The GitHub changes have been saved and reread. No Gradle build, full automated Kotlin compilation or APK/device smoke test was run in this pass.

## Next distinct audit workstreams

1. Review the remaining **97 template-like summaries** and replace them with source-specific walkthroughs.
2. Review **311 placeholder reward fields** and supply evidence-based rewards (or explicitly say unknown).
3. Validate localization and chapter availability/optional plugin categorization, especially the CH2-132 title.
4. Run Android Gradle compilation and app smoke tests before distributing a release.

**Quest-giver placeholder audit: COMPLETE. End-user guide content audit: NOT YET COMPLETE.**
