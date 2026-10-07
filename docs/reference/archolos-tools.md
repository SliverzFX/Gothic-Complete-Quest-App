# Archolos game tools reference

The existing Archolos game hub now opens the searchable Codes, Useful Tips and Items sections. Search includes names, groups, descriptions and insert IDs. Copy controls reuse the existing reference UI. Existing chapter/quest content is unchanged.

## Scope and source version

This first item-reference release uses auronen's extracted **The Chronicles of Myrtana: Archolos v1.2.2** item tables:

- Source: https://github.com/auronen/CoM-itemlist
- 2,599 unique objects with item-table records: 381 melee weapons, 107 ranged weapons, 238 armor exports, 129 accessories, 67 potions, 176 food/plants, 158 runes/scrolls, 6 ammunition objects, 674 documents/recipes and 663 miscellaneous objects.
- Codes: those 2,599 insert commands plus 6 setup/base-engine commands.
- Items: 1,252 selected equipment, consumable, spell and recipe references. This view excludes recognized debug props, quest-ID objects, legacy high-circle/paladin spells and most NPC armor exports. Inclusion does **not** verify normal acquisition.
- Useful Tips: 15 original notes explaining the reference and effects represented by the tables.
- No NPC/creature insert database, acquisition/location catalogue, maps or claim of complete latest-patch coverage is included. Empty code categories are hidden rather than presented as populated.
- The raw instance-name list also contains five un-tabulated container/group identifiers (`itmi_pocket`, `itgr_meatraw`, `itgr_vegetable`, `itgr_meat`, `itgr_meatfried`). They are not fabricated into item cards or insert commands.

The version is visible in the UI. The list includes quest/NPC/legacy objects and does not claim that every exported object is obtainable by the hero. Later patches may change IDs or balance. A current-version script/stat audit is still needed before removing that qualification.

## Data handling

Names, IDs and numeric facts are transformed into original summaries. Images and copied flavor paragraphs are not bundled. Non-English legacy/debug names have English labels, with their exact IDs retained. Variants remain separate by ID even where names match.

- Trained-attribute thresholds and matching-set conditions remain attached to the additional effects that follow them. Conditional bonuses are not added to unconditional bonuses.
- Armor protection types, equip requirements, durations, fixed vs percentage recovery, mana-scaled healing, crafting skill requirements and the single-summon limit are kept distinct.
- Combined armor-piercing/combat-bonus fields stay combined; no assumption about doubling their numeric value is introduced.
- Exported `Value` is labelled base value, not merchant price.
- General console commands are identified as Gothic II engine commands and link to World of Gothic; Marvin/F2/insert setup is documented in the Archolos export README.

Official English localization from https://github.com/TheChroniclesOfMyrtana/localization was consulted to interpret effect/condition labels. It is not a current item-stat script dump and was not treated as one.

## Validation

Pure Kotlin reference data is compiled with Kotlin 2.2.0 targeting JVM 17. Seven JUnit tests check section coverage, unique IDs, safe commands, category totals, conditional summoner/set effects, upgrade values, elixir vs prop separation and search fields. A source-table audit checks ID coverage and numeric facts. UI integration is reviewed against the current branch source.

Full Android/Compose builds and device tests require Android Studio/SDK and have not run in this workspace.

The adjacent `archolos-source-manifest.json` records SHA-256 hashes of the downloaded source tables for future comparisons.
