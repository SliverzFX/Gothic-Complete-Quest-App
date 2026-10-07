# Original Gothic PC tools reference

318 commands and spawn codes, 84 normal-play item entries, and 16 gameplay tips. These are curated references for the original Gothic, not Gothic 1 Remake, Gothic 2 or mods. NPC-only armor is identified on its command card and is not presented as normal obtainable equipment. Maps remain deferred.

The existing categories, search and command-copy controls are retained. Existing card IDs are preserved where their commands remain. The earlier unlisted battle-staff insert was corrected to the source-listed Staff of Judgement instance.

Item records include source-checked weapon damage and attribute requirements, armor protections, permanent potion effects, jewelry bonuses and selected normal acquisition instructions. In original Gothic, crossbows require dexterity, and Dragonroot restores mana. These facts must not be substituted with Gothic 2 data.

All insert identifiers were checked against their linked tables on 2026-10-07. Numeric equipment facts were independently matched by item name between the normal inventory tables and insert tables. Descriptions are independently written. No map images or source prose were copied.

Lares uses `org_801_lares`, verified against the original game startup script rather than the erroneous `org_801_larest` spelling in one community table.

## Sources

- https://gamefaqs.gamespot.com/pc/913888-gothic/cheats
- https://www.gothicz.net/gothic-1/predmety/amulety/
- https://www.gothicz.net/gothic-1/predmety/herbar/
- https://www.gothicz.net/gothic-1/predmety/jednorucni-zbrane/
- https://www.gothicz.net/gothic-1/predmety/kuse/
- https://www.gothicz.net/gothic-1/predmety/lektvary/
- https://www.gothicz.net/gothic-1/predmety/luky/
- https://www.gothicz.net/gothic-1/predmety/magicke-svitky/
- https://www.gothicz.net/gothic-1/predmety/obourucni-zbrane/
- https://www.gothicz.net/gothic-1/predmety/potraviny/
- https://www.gothicz.net/gothic-1/predmety/prsteny/
- https://www.gothicz.net/gothic-1/predmety/zbroje/
- https://www.gothicz.net/marvin/g1/insert-kody/bestie/
- https://www.gothicz.net/marvin/g1/insert-kody/npc/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/amulety/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/herbar/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/jednorucni-zbrane/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/kuse/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/lektvary/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/luky/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/magicke-runy/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/magicke-svitky/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/obourucni-zbrane/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/ostatni/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/potraviny/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/prsteny/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/trofeje/
- https://www.gothicz.net/marvin/g1/insert-kody/predmety/zbroje/
- https://www.worldofgothic.com/gothic/?go=g1cheats
- https://www.worldofgothic.com/gothic/?go=g1faq
- https://www.worldofgothic.com/gothic/?go=g1spruchrollen

Armor acquisition detail references:
- https://www.gothicz.net/gothic-1/predmety/zbroje/kalhoty-kopace/
- https://www.gothicz.net/gothic-1/predmety/zbroje/zbroj-stina/
- https://www.gothicz.net/gothic-1/predmety/zbroje/zbroj-z-krunyru-dulnich-cervu/
- https://www.gothicz.net/gothic-1/predmety/zbroje/starobyla-rudna-zbroj/
- https://www.gothicz.net/gothic-1/predmety/zbroje/vylepsena-rudna-zbroj/
- https://www.gothicz.net/gothic-1/predmety/zbroje/tezka-zbroj-zoldaka/
- https://www.gothicz.net/gothic-1/predmety/zbroje/tezka-zbroj-templare/
- https://www.gothicz.net/gothic-1/predmety/zbroje/roucho-magu-ohne/
- https://www.gothicz.net/gothic-1/predmety/zbroje/roucho-vody/
- https://www.gothicz.net/gothic-1/predmety/jednorucni-zbrane/rezavy-mec/
- https://www.gothicz.net/gothic-1/predmety/jednorucni-zbrane/kratky-mec/
- https://www.gothicz.net/gothic-1/predmety/amulety/amulet-mrtveho-strazce/

- https://github.com/auronen/Gothic-1-localization/blob/master/Scripts/content/Story/Startup.d

## Validation

Source-table audit passed for included insert IDs, stat checks, unique list keys, nonempty descriptions, five code categories and separation of normal-play entries from commands. Unit and Compose tests cover original-game stats, stable keys, search, category filtering and copying. Kotlin tests and an Android build require Android Studio and were not run in this editing environment.
