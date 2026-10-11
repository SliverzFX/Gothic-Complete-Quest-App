# Gothic II Gold / Night of the Raven tools reference

1083 commands and insert references, 520 normal-play item/spell entries and 26 tips. Stats target the unmodified PC Gold edition with Night of the Raven. They do not describe classic Gothic II, New Balance or other mods.

## Coverage

This finishes the equipment and tools expansion agreed for Gothic II Gold. The audit checks every identifier in the published Gothic 2 insert categories listed below, plus the additional weapon/ammunition instances, all 16 belts and the Claw of Beliar weapon tiers present in the game script reference. Named NPCs retain their world/chapter variants. Zombie range notation is expanded into actual individual commands.

Normal Items includes weapon damage, attribute requirements, weapon-skill bonuses, obtainable armor protections and acquisition steps, accessory bonuses, potion/herb effects, rune/scroll mana and circle requirements, language tablets, food and relevant crafting/hunting materials. NPC-only armor and unused special runes remain in Codes with explicit labels. Maps and translations remain deferred.

This scope does not include every engine debug command, every letter/chest key, or every individual loot location. Acquisition leads identify confirmed merchants, places, rewards or carriers; inventories can depend on chapter and guild. A source lead is not a promise of chapter-1 availability.

| Code category | Entries |
|---|---:|
| General | 21 |
| Weapons | 192 |
| Armor | 47 |
| Characters | 374 |
| Items | 449 |

| Item section | Entries |
|---|---:|
| Potions | 23 |
| Plants And Alchemy | 20 |
| Spell Scrolls | 54 |
| Stone Tablets (Notr) | 38 |
| Armor And Robes | 21 |
| One-Handed Weapons | 61 |
| Two-Handed Weapons | 48 |
| Bows And Arrows | 16 |
| Crossbows And Bolts | 11 |
| Materials And Quest Items | 16 |
| Hunting Trophies | 40 |
| Rings | 35 |
| Amulets | 22 |
| Runes | 58 |
| Food And Drink | 31 |
| Belts | 16 |
| Additional Weapons And Ammunition | 9 |
| Beliar Weapon | 1 |

## Source audit

Checked on 2026-10-07. Insert identifiers were matched against their own category table, or against the declared item instance in the Gothic 2 script reference. English item names were checked against the localization reference. Descriptions and usage notes are independently written; map images, item icons and source prose are not bundled.

Weapon damage values were independently compared with 133 rows in the inventory tables. Three requirement disagreements were resolved using the game script constants:

| Item | Requirement used | Conflicting table value |
|---|---:|---:|
| Rough Hatchet (`itmw_1h_sld_axe`) | 50 strength | 40 |
| Medium Orc Axe (`itmw_2h_orcaxe_02`) | 80 strength | 60 |
| Hunting Crossbow (`itrw_crossbow_l_01`) | 20 strength | 30 |

The Hunting Crossbow requirement also matches the English Mondgesänge reference and the independent Night of the Raven walkthrough.

Key edition-specific checks: crossbows use strength to equip, while bows use dexterity; dexterity melee weapons still use strength in melee damage. Minecrawler Plate Armor is 70/70/15/0 (weapons/arrows/fire/magic) and requires 10 plates. Strength elixirs grant +3, mana elixirs +5 maximum mana and life elixirs +20 maximum health. Ordinary rune and scroll mana costs differ.

## Sources

- Game scripts and English names: https://github.com/auronen/Gothic-2-localization
- Weapon tuning: https://github.com/auronen/Gothic-2-localization/blob/master/Scripts/Content/Items/Tuning_Melee_Weapons.d
- Ranged tuning: https://github.com/auronen/Gothic-2-localization/blob/master/Scripts/Content/Items/Tuning_Ranged_Weapons.d
- Belts: https://github.com/auronen/Gothic-2-localization/blob/master/Scripts/Content/Items/IT_Addon_Belts.d
- Claw tiers: https://github.com/auronen/Gothic-2-localization/blob/master/Scripts/Content/Items/IT_Addon_BeliarWeapons.d
- Insert categories: https://www.gothicz.net/marvin/g2/insert-kody/
- Inventory statistics: https://www.gothicz.net/gothic-2/predmety/
- English cross-check: https://mondgesaenge.de/G2ADB/guide_waffe_eng.htm
- Independent walkthrough: https://gamefaqs.gamespot.com/pc/920919-gothic-ii-night-of-the-raven/faqs/45017
- Merchant, location, reward and carrier leads: https://colony-guide.com/gothic-2-notr/items/

### Armor acquisition sources

- https://www.gothicz.net/gothic-2/predmety/zbroje/hostinsky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/selsky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/selsky-sat-pansky/
- https://www.gothicz.net/gothic-2/predmety/zbroje/selsky-sat-damsky-1/
- https://www.gothicz.net/gothic-2/predmety/zbroje/selsky-sat-damsky-2/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tezka-zbroj-banditu/
- https://www.gothicz.net/gothic-2/predmety/zbroje/stredne-tezka-zbroj-banditu/
- https://www.gothicz.net/gothic-2/predmety/zbroje/bloodwynova-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/cor-angarova-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tmavy-plast/
- https://www.gothicz.net/gothic-2/predmety/zbroje/diegova-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/zbroj-z-krunyre-dulniho-cerva/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tezka-zbroj-drakobijcu/
- https://www.gothicz.net/gothic-2/predmety/zbroje/stredne-tezka-zbroj-drakobijcu/
- https://www.gothicz.net/gothic-2/predmety/zbroje/velitelsky-kabatec/
- https://www.gothicz.net/gothic-2/predmety/zbroje/soudcovske-roucho/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tezke-ohnive-roucho/
- https://www.gothicz.net/gothic-2/predmety/zbroje/roucho-magu-ohne/
- https://www.gothicz.net/gothic-2/predmety/zbroje/roucho-magu-vody/
- https://www.gothicz.net/gothic-2/predmety/zbroje/stara-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tezka-zbroj-domobrany/
- https://www.gothicz.net/gothic-2/predmety/zbroje/roucho-novicu/
- https://www.gothicz.net/gothic-2/predmety/zbroje/paladinska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/rytirska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/stara-rytirska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/kapitansky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/piratsky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/piratska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/trestanecky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/zbroj-kruhu-vody/
- https://www.gothicz.net/gothic-2/predmety/zbroje/ravenova-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/tezka-zoldnerska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/stredne-tezka-zoldnerska-zbroj/
- https://www.gothicz.net/gothic-2/predmety/zbroje/kovarsky-odev/
- https://www.gothicz.net/gothic-2/predmety/zbroje/zbroj-ravenovych-strazi/
- https://www.gothicz.net/gothic-2/predmety/zbroje/mestansky-sat-pansky-3/
- https://www.gothicz.net/gothic-2/predmety/zbroje/mestansky-sat-pansky-1/
- https://www.gothicz.net/gothic-2/predmety/zbroje/mestansky-sat-pansky-2/
- https://www.gothicz.net/gothic-2/predmety/zbroje/mestansky-sat-damsky-3/
- https://www.gothicz.net/gothic-2/predmety/zbroje/mestansky-sat-damsky-2/
- https://www.gothicz.net/gothic-2/predmety/zbroje/roucho-temnych-umeni/

## Validation

- Source coverage audit: all published insert-table identifiers and all script weapon/belt instances included; no missing English names.
- Stable IDs preserved, unique commands/keys checked, normal entries contain no command controls, and all five code categories remain present.
- Data and eight JUnit tests compile and pass with Kotlin 2.2/JVM 17 using the production entry/category definitions and an isolated ToolSection enum. This also checks initialization of the expanded catalog.
- The catalog initializes in chunks of 90 entries to stay below JVM method-size limits.
- A full Android Gradle build and Compose/device tests were not run: this environment has no Android SDK. Existing search/category/copy instrumentation tests remain available for Android Studio.
