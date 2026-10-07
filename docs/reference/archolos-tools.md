# Archolos game tools reference

Archolos uses the shared searchable Codes, Useful Tips and Items screens. Search covers names, group labels, descriptions, acquisition facts and insert commands. Copy controls retain exact insert IDs. Existing chapter and quest guides are unchanged. Maps remain outside this reference update.

## Finished reference coverage

- **5,557 code entries:** 2,599 item IDs, 2,952 actor IDs and six setup/base-engine commands.
- **1,314 item/reference cards**, including equipment, consumables, spells, recipes and location cards.
- Acquisition information is attached to **689 exact item IDs**, including base armor, upgrades, crafting diagrams, accessory sets, runes and patch-documented trophy buyers.
- **14 distinct magic-circle volume location cards:** two volumes for I, three for II, four for III and five for IV.
- **15 named teleport references**, with rune source and destination circle listed separately.
- **31 useful tips**, including normal-play equipment routes and official maintenance-patch corrections.
- **60 item-use descriptions** replace generic wording where the extracted item table explicitly establishes a use.

## Versions and verification limits

Numerical item facts come from auronen's **v1.2.2** extracted item tables: https://github.com/auronen/CoM-itemlist

The official Steam announcements for **1.2.3 through 1.2.11** were reviewed. They document trainer limits, trophy-sale options, apple bonus counting, hunting teaching/costs, localization and quest fixes. They do not announce numerical equipment-stat changes. The UI labels the stat export and reviewed patch versions separately. Reviewing patch notes is not a byte-for-byte comparison against later compiled game scripts, and no such comparison is claimed.

Relevant verified changes are incorporated in the tips and specific trophy cards:

- 1.2.3: Odgar teaches skilled smithing for militia; Yezegan teaches master alchemy; apple/dish strength counting corrected.
- 1.2.4: special Gluttonous Bear skin sale to Frida and Seashark fang sale to Markus.
- 1.2.5: Markus teaches fang extraction.
- 1.2.6: incorrect hunting-learning prices in some dialogues corrected; historical prices are not asserted as current charges.

Official announcement URLs are obtained by resolving the URLs supplied by Steam's news API; internal news IDs are not guessed into announcement links. The adjacent final audit manifest records all nine announcements, source hashes and disagreements.

The NPC/creature publisher does not specify a patch version. Those entries remain identified as exported actor references, including chapter/quest/scene variants. No runtime safety, combat statistics or automatic quest restoration is inferred from an ID.

## Acquisition sources and matching

Acquisition index: **Tulipan / CrazyRaus**, English translation **DreXav**, contribution **HRY**.

Website: https://docs.google.com/spreadsheets/d/1Z5O00oK-OYpmjtniR5t3s__TxNq8eMuwzn8ftJPeNpQ/edit

Acquisition facts are kept distinct from numerical export stats. Matches use names, item type and applicable damage/equip requirements or armor protection tuples. Generated, summon, quest and cutscene duplicates are not silently treated as normally obtainable items. Ambiguous matches receive separate name-level acquisition cards instead of fabricated exact IDs.

Specific alias/disagreement handling:

- Rusty Sword: keep the export's 10-strength requirement, not the acquisition table's 15.
- Heavy Southerner's Armor: keep the export's 85 arrow protection, not the acquisition table's 80.
- The Peacemaker: the acquisition note identifies its story source and inability to equip it; the guide's requirement is not invented into the export stats.
- Light Beechwood Bow: correct the export's `Ligh` spelling without changing its ID or numbers.

Diagram routes stay marked as diagram sources; they are not represented as sales of finished weapons. Upgraded armor routes explicitly identify how to acquire the base piece. Matching set headings are used for ring/amulet sources rather than joining generic “Ring (findable)” rows to unrelated sets. Circle book parsing stops at the next book series, preventing ordinary volumes from being mislabelled as circle IV.

All descriptions retain separate conditional bonuses, potion durations, percentage recovery and mana-scaled healing. Base value remains labelled as distinct from a merchant price. Original item flavor paragraphs, maps and images are not bundled.

## Actor source and attribution

**ID index © CrazyRaus, 2022**. Website: https://docs.google.com/spreadsheets/d/1LZa9KeydVJYxprMd1Qbwl09vjknEU9Jb_EU5iAX5FjA/edit

Publisher's guide: https://steamcommunity.com/sharedfiles/filedetails/?id=2749338542

Its Introduction permits redistribution with copyright notice and website address; both are preserved in these references and credited/linked in the app. Only factual IDs/names are used, with original spawn descriptions.

Actor coverage: 499 named-index entries, 678 other creature exports, 41 bounty/boss entries and 1,734 other NPC exports. These are 2,952 unique actor IDs, not distinct story characters. All 2,993 raw IDs are accounted for, with 41 helper/test/hero/debug or other unlabelled internal exclusions documented in `archolos-character-source-manifest.json`.

Official English names corroborate 1,255 NPC IDs/variants; 696 IDs also match official dialogue filenames. Additional creature names are matched to localized name constants. Source: https://github.com/TheChroniclesOfMyrtana/localization

## Validation

The actual data objects compile with Kotlin 2.2.0, JVM 17. **20 JUnit tests** pass across the core, actor and acquisition suites. Tests cover uniqueness, exact commands, category counts, conditional bonuses, armor values, alias preservation, actor exclusions, crafting requirements, acquisition searches, magic-volume counts, teleport structure and official patch facts.

Source audits account for all exported IDs, check preserved numerical fields and verify that acquisition keys resolve to actual exported items. Added use descriptions are tied to explicit item-table fields. Whitespace checks and remote payload verification run before completion.

A full Android/Compose build and device visual test require Android Studio/SDK and are not claimed in this workspace.
