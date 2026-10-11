# Gothic II New Balance — Chapters 1–2 reward audit

**Checkpoint:** 2026-10-10  
**Repository:** [SliverzFX/Questbound](https://github.com/SliverzFX/Questbound), branch `dev/android-foundation-v0.1`  
**Scope:** Only the rewards workstream (Task 2). Walkthrough summary audit is documented separately in `newbalance-chapters-1-2-walkthrough-summary-audit-2026-10-10.md`.

## Live GitHub status after commits

| Metric | Chapter 1 | Chapter 2 | Combined |
| --- | ---: | ---: | ---: |
| Total quest entries | 283 | 211 | 494 |
| Placeholder reward fields at start | 119 | 192 | 311 |
| Placeholder reward fields replaced this session | **57** | **6** | **63** |
| Still showing `Reward varies by quest/build` | **62** | **186** | **248** |
| Remaining placeholder quest-giver fields | 0 | 0 | 0 |

These figures are a fresh scan of all 12 Kotlin quest files on the development branch. The 63 replaced fields contain documented rewards, **including conditional, cumulative, continuing, and in some cases only partially documented rewards**, not guesses at missing values.

### Resolved reward IDs

- **Chapter 1 Part 1 (3):** CH1-033, 036, 041.
- **Chapter 1 Part 2 (28):** CH1-048, 049, 050, 055, 057, 058, 059, 061, 066, 067, 068, 069, 070, 071, 072, 073, 074, 075, 076, 078, 079, 080, 081, 082, 083, 085, 087, 088.
- **Chapter 1 Part 3 (24):** CH1-089, 091, 093, 094, 097, 098, 099, 100, 101, 102, 103, 104, 105, 106, 107, 108, 110, 112, 116, 119, 123, 124, 125, 127.
- **Chapter 1 Part 4 (2):** CH1-134, 137.
- **Chapter 2 Part 1 (5):** CH2-013, 016, 017, 018, 019.
- **Chapter 2 Part 4 (1):** CH2-130.

### Remaining by chapter and file

| Part | Chapter 1 | Chapter 2 |
| --- | ---: | ---: |
| Part 1 | 2 | 29 |
| Part 2 | 3 | 42 |
| Part 3 | 17 | 43 |
| Part 4 | 40 | 36 |
| Part 5 | 0 | 36 |
| Part 6 | 0 | — |
| Part 7 | 0 | — |

## Representative documented rewards

- **CH1-033 Erol's Stinking Beast:** 1,500 XP and 750 gold.
- **CH1-041 Jack's Pipe:** 700 XP and 300 gold on delivery, 300 XP when securing the pipe from Greg.
- **CH1-055 Fish Soup for Farim:** 600 XP on final delivery, fishing book and choice of 18 mollusks or a black pearl; further XP at preparation stages.
- **CH1-059 The Cursed Lighthouse:** peaceful resolution requires 40 Rhetoric; 3,000 XP and Jack's silver rapier, with earlier XP separately obtainable.
- **CH1-073 Donations for Daron:** progressive donations give stat/learning-point rewards, XP, or low-gold rhetoric dialogue outcomes, rather than one flat sum.
- **CH1-079 Canthar's Favor:** clearly separate rewards for framing Sarah versus reporting Canthar.
- **CH1-089 Matteo's Tools:** 1,000 XP and choice of 5,000 gold, Embarla Firgasto potion, or jewelry training.
- **CH1-093 Snapper Claws:** 1,000 XP and 400 gold plus mage/warrior-specific additional reward.
- **CH1-106 Magical Diary:** includes permanent Rhetoric, Mana, and Intelligence bonuses from reading before delivery.
- **CH2-018 Message for Isgaroth:** choice of magic ore, ring or King's Sorrel, plus optional bonuses if the sealed letter stays unread.
- **CH2-130 Tengron's Ring:** 500 XP and +2 Innos karma.

## Source references

Primary reference: April–May 2026 New Balance walkthrough records indexed at [RPG Russia](https://rpgrussia.com/threads/spisok-vsex-kvestov-po-lokacijam.67402/). Many individual source links already appear in the corresponding Kotlin `warnings` fields from the previous walkthrough audit. Examples:

- [Erol's Stinking Beast](https://rpgrussia.com/threads/vonjuchaja-tvar-ehrola.67167/)
- [Jack's Pipe](https://rpgrussia.com/threads/trubka-dzheka.66760/)
- [Canthar's Favor](https://rpgrussia.com/threads/ljubeznost-kantara.67162/)
- [Cursed Lighthouse](https://rpgrussia.com/threads/prokljatyj-majak.66900/)
- [Matteo's Tools](https://rpgrussia.com/threads/instrumenty-matteo.67235/)
- [Snapper Claws](https://rpgrussia.com/threads/kogti-sneppera.67093/)
- [Magical Diary](https://rpgrussia.com/threads/magicheskij-dnevnik.67269/)
- [Message for Isgaroth](https://rpgrussia.com/threads/poslanie-dlja-isgarota.66797/)
- [Tengron's Ring](https://rpgrussia.com/threads/kolco-tengrona.67091/)

## Exceptions and follow-up flags

1. **CH1-049 Lucia:** the walkthrough documents optional 100 XP milestones with Nadja and Elvrich but does not give a distinct final completion reward. The new field explicitly says this; do not assume it is exhaustive.
2. **Reward amount on character-specific choices:** recorded as conditional rather than a flat total. Avoid summing XP from dialogue, combat and completion if not all are guaranteed.
3. **CH1-071 Edda's Statuette:** a discussion comment corrects an earlier guide claim about Constantino's home; the Kotlin instruction was refined to prefer Harad's house.
4. **Some tasks catalogued in Chapter 1 complete in Chapter 2 or later.** This checkpoint does not fix chapter placement or optional-addon filtering.
5. **Exact New Balance 8.0 English game-script verification remains separate.** RPG Russia is a strong, current community walkthrough, but it is not the exact installed game script.
6. **Gradle compile/APK smoke test not run** in this reward-only batch.

## Next targeted batches

Finish remaining Chapter 1 Part 1 (2) and Part 2 (3), then resolve the 17 remaining Chapter 1 Part 3 rewards, Chapter 1 Part 4 (40), and the larger Chapter 2 set. If the source gives no reliable XP/items, say `Reward not confirmed in the available New Balance reference` rather than inventing amounts.

**Task 2 status: IN PROGRESS — 63 / 311 reward placeholders replaced, 248 remain.**
