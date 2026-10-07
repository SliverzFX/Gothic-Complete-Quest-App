# Risen 2: Dark Waters offline tools

Adds the original PC game's Codes and Tips to the existing shared game-tools UI. Quests are unchanged. Compact searchable cards open their complete bundled descriptions; supported commands can be copied. Reference credits are plain text, with no browser action or runtime download.

Coverage: 572 distinct template IDs, eight console/setup/help cards, 18 gameplay tips. The catalogue includes clothing, jewellery, melee weapons, firearms, ammunition, plants, potions, recipes, keys, legendary collectibles, quest materials and map/debug references. 498 item cards have individual inventory commands; 74 world or unverified reference cards deliberately do not. Equipment/herb statistics are available for 51 matching records. All 21 legendary entries have bonuses and finding clues. These are curated reference catalogues, not an exhaustive export of every engine object or patch's numeric weapon/recipe/NPC database. No unsupported NPC template names or copied Risen 1 attributes are introduced. Maps remain deferred; map IDs are text references only.

## Sources and limitations

- ZhirC's firsthand PC item catalogue on GameFAQs (2019-05-19), identified as version 1.0.1210.0 / Change 201663: exact templates, display names and quest/sellability facts. Its miscellaneous list explicitly admits omissions. Bulk prefixes are excluded. The non-inventory catalogue page was unavailable and is not represented as complete.
- GameFAQs original PC reports by panthols29, kamehakid9229 and other participants: pommes activation, case sensitivity, basic commands and Fool's Juice behaviour. The early reported potion issue is labelled patch-dependent rather than asserted for every installation.
- RISEN.cz original Risen 2 equipment and herb tables: numeric protection, talent bonuses and prices. Pages without populated numeric tables contribute no invented statistics.
- CM Boots-Faubert's SuperCheats walkthrough and Gamepressure's authored Maracai legendary guide: independently condensed collectible bonuses and finding clues. Source inconsistencies are resolved against specific quest descriptions; no maps or walkthrough prose are copied.
- Official PC manual: short independently written gameplay guidance.

The independent original PC item list by kamehakid9229 (GameFAQs FAQ 68862) cross-checks 316 entries, adds missing tools and weapons, and resolves several typographic template conflicts. An uncertain alternative Priest’s Mask ID has no Copy command.

Corrections: the catalogue's trailing backtick on It_Bo_Rum is removed, confirmed by original PC reports. The non-ASCII It_Recipe_Am_Tri̇́be is excluded pending exact-template verification. RISEN.cz's heavy cloth/leather coat English labels are swapped; the Czech labels and GameFAQs template names determine the correct mapping. Fat Olga's grip is distinguished from its duplicated barrel display name. Known bulk requests are not exposed as individual items. Quest IDs are not advertised as completing dialogue, faction permission or quest stages.

## Validation

Seven Risen 2 JUnit data tests exercise activation, exact case, armour facts, world-reference copy suppression, quest/DLC notes, uniqueness, combined-card description preservation and section routing. Existing data regressions are also run. Static integration checks cover both RISEN_2 routes, header and absence of browser actions. This environment has no Android SDK: Android/Compose build and phone visual review must happen in Android Studio.
