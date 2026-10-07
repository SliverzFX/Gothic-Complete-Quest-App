# Risen 1 offline tools

The existing Risen 1 game hub now opens the shared compact Codes and Tips screens. Codes includes equipment and other items; there is no separate Items tab. Search covers category, title, exact command and the full offline description. Cards open the existing detail dialog, with Copy and Close controls and plain source credits.

## Coverage

- 511 historical item database records, consolidated to 510 exact item templates.
- 141 named-character templates and 95 creature variants (variants are not separate species).
- 88 console, activation, configured-shortcut and attribute/skill references.
- 21 normal-gameplay tips derived from the official PC manual.
- All 78 database recipes inspected; their requirements and ingredient/tool quantities are embedded in the related item cards.

Item cards contain recognized numeric stats, independently written spell/effect explanations, merchants with starting chapters, pickpocket requirements, carrier/loot sources, translated reward-task labels, crafting requirements, and available world coordinates. Character cards retain published attributes, inventory/merchant/pickpocket facts and coordinates.

Location prose in the English database is often German. This addition condenses it into English location clues alongside coordinates when present; these clues are not a complete translation or a replacement for the quest walkthrough. Maps remain deferred. Items without a documented normal acquisition route are explicitly unconfirmed rather than labelled obtainable.

## Version and source handling

This targets the historical original PC game. The database does not establish its exact patch, and this is not an audit of mods, the 2023 re-release or console ports. Console activation uses `minsky`; inventory examples use `give`, actor examples use `spawn`, and skill editing uses `teach`. Optional debug bindings need the documented configuration setup.

World of Risen records 154 and 156 both publish `It_1H_Mace` with incompatible damage, strength and value stats. They appear in one card with an explicit conflict notice and both records retained. No unsupported choice between them is presented as verified.

All data is packaged as Kotlin constants. No network request, browser action or online content loader is used by these screens. Source addresses remain developer provenance metadata; users see plain credits.

Sources inspected on 2026-10-07:

- World of Risen English item, NPC, monster and recipe databases: `https://www.worldofrisen.de/english/rdb_items.htm`, `rdb_npcs.htm`, `rdb_monster.htm`, `rdb_recipe.htm`.
- Foobar's original-PC console/debug reference: `https://www.worldofrisen.de/risen/article_273.htm`.
- Official PC manual distributed through Steam: `https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/40300/manuals/GfW%20Risen%20Manual%20US%20PRINT.pdf`.

The manifest records the catalogue IDs and retrieval hashes. Website images and authored page prose are not reproduced.

## Verification

Pure Kotlin/JUnit checks cover exact commands, item stats, named-character aliases, unique IDs, catalogue coverage, bundled acquisition facts, conflicting historical records, spell effects, teleport stones and shared card preservation. Instrumented UI tests cover Risen routing, search, full descriptions, Copy, dismissal and normal tips. Android build and instrumented tests require Android Studio/SDK and were not executed in the research environment.
