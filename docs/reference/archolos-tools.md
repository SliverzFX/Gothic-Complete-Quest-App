# Archolos game tools reference

The existing Archolos game hub now opens the searchable Codes, Useful Tips and Items sections. Search includes names, groups, descriptions and insert IDs. Copy controls reuse the existing reference UI. Existing chapter/quest content is unchanged.

## Scope and source version

This first item-reference release uses auronen's extracted **The Chronicles of Myrtana: Archolos v1.2.2** item tables:

- Source: https://github.com/auronen/CoM-itemlist
- 2,599 unique objects with item-table records: 381 melee weapons, 107 ranged weapons, 238 armor exports, 129 accessories, 67 potions, 176 food/plants, 158 runes/scrolls, 6 ammunition objects, 674 documents/recipes and 663 miscellaneous objects.
- Codes: 5,557 total entries: 2,599 item insert commands, 2,952 NPC/creature insert commands and 6 setup/base-engine commands.
- Items: 1,252 selected equipment, consumable, spell and recipe references. This view excludes recognized debug props, quest-ID objects, legacy high-circle/paladin spells and most NPC armor exports. Inclusion does **not** verify normal acquisition.
- Useful Tips: 15 original notes explaining the reference and effects represented by the tables.
- The Characters & Creatures category includes the actor database described below. Acquisition/location catalogues, maps and a claim of complete latest-patch coverage are not included. Empty code categories are hidden rather than presented as populated.
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

Pure Kotlin reference data is compiled with Kotlin 2.2.0 targeting JVM 17. Twelve JUnit tests check section coverage, unique IDs, safe commands, category totals, conditional summoner/set effects, upgrade values, elixir vs prop separation and search fields, plus actor aliases, exact variant IDs, source attribution, exclusions and category membership. A source-table audit checks ID coverage and numeric facts. UI integration is reviewed against the current branch source.

Full Android/Compose builds and device tests require Android Studio/SDK and have not run in this workspace.

The adjacent `archolos-source-manifest.json` records SHA-256 hashes of the downloaded source tables for future comparisons.

## NPC and creature index

ID index **© CrazyRaus, 2022**. Website address: https://docs.google.com/spreadsheets/d/1LZa9KeydVJYxprMd1Qbwl09vjknEU9Jb_EU5iAX5FjA/edit

Publisher's guide: https://steamcommunity.com/sharedfiles/filedetails/?id=2749338542

The index's Introduction permits copying and distribution when its copyright notice and website address are included. Those are preserved here and linked/credited in the app's reference sources. Only factual IDs/names are used; all spawn descriptions are original.

Coverage: 499 entries from the named-character index, 678 other creature exports, 41 bounty/boss entries and 1,734 other NPC exports. These are **2,952 unique actor IDs**, not 2,952 distinct story characters. Chapter, quest and scene variants remain separate. The index does not specify a patch version, so its version is not presented as v1.2.2 or as current-patch verified.

The official English localization supplies name checks for 1,255 NPC IDs/variants. A further 696 actor IDs match official English dialogue filenames. A filename match corroborates an ID; it is not a substitute for verifying its full runtime instance behavior. Additional creature names are matched to official localized NAME constants. Unmatched records retain explicit export-ID labels rather than invented identities. Named aliases are preserved (for example, `bau_2279_nirko` is Elco and `pir_6330_captain_archolos` is Beckett).

41 helper/test/hero/debug or other unlabelled internal actor IDs are excluded. Raw entries are retained as export references, not certified safe for every patch or quest state. No health, damage or faction relationship values are invented from the IDs. Spawned copies do not restore quest state.

`archolos-character-source-manifest.json` records source hashes, excluded IDs and coverage counts. Runtime game testing remains pending.
