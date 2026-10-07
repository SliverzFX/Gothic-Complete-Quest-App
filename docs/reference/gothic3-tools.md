# Gothic 3 game tools

Search and category filters use the existing shared tools screen. Gothic 3 uses the CONSOLE CODES title, TestMode setup, give for inventory items, spawn for actors, and teach for perks / spells. No Gothic 1/2 insert command or 42 exit code is used.

## Reference scope

1916 code/setup entries, 853 item/equipment cards and 15 tips. The cleaned ID catalog contains 1898 distinct IDs. Duplicate IDs across source categories are merged. Unlabelled exports are omitted; a valid item whose name matches its ID, such as Arrow, is retained. 738 raw rows were excluded: internal meshes/world objects, obvious tests/dummies and unnamed entries. This is a curated historical reference, not an exhaustive current game-script export.

The World of Gothic index has no patch stamp. Names and IDs are factual reference identifiers; source prose is not reproduced. Descriptions are original category guidance, with brief effects only where supported. Acquisition is not inferred from a spawn code. Character variants remain templates, not necessarily distinct story characters. Body_/Fat_Body_ meshes are omitted from armor inventory entries.

The publisher-hosted Community Patch team 1.70 manual supplies 112 weapon stat cards. Outside-parentheses values are Alternative Balancing; parentheses are non-AB. Single values apply to both. A dash is retained rather than invented into a numeric requirement. These are expressly CP 1.70 figures, not claimed CP 1.75 values. Historical armor and shield tables remain separately labelled with unspecified patch version. Gold table values do not guarantee a merchant's buy/sell price.

No Quest Pack, Content Mod, Update Pack, Forsaken Gods or map assets are included. Spawning actors cannot be assumed to restore quests.

## Sources

- https://ds.thqnordic.com/community/Gothic3/Advertise/CP_1_70_Manual.pdf
- https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/39500/manuals/G3_Manual_UK.pdf
- https://www.worldofgothic.com/gothic3/?go=g3armors
- https://www.worldofgothic.com/gothic3/?go=g3cheats
- https://www.worldofgothic.com/gothic3/?go=g3insert_amulets
- https://www.worldofgothic.com/gothic3/?go=g3insert_animals
- https://www.worldofgothic.com/gothic3/?go=g3insert_armor
- https://www.worldofgothic.com/gothic3/?go=g3insert_food
- https://www.worldofgothic.com/gothic3/?go=g3insert_keys
- https://www.worldofgothic.com/gothic3/?go=g3insert_magic
- https://www.worldofgothic.com/gothic3/?go=g3insert_npc
- https://www.worldofgothic.com/gothic3/?go=g3insert_packages
- https://www.worldofgothic.com/gothic3/?go=g3insert_perks
- https://www.worldofgothic.com/gothic3/?go=g3insert_plants
- https://www.worldofgothic.com/gothic3/?go=g3insert_potions
- https://www.worldofgothic.com/gothic3/?go=g3insert_recipes
- https://www.worldofgothic.com/gothic3/?go=g3insert_rings
- https://www.worldofgothic.com/gothic3/?go=g3insert_shields
- https://www.worldofgothic.com/gothic3/?go=g3insert_teleports
- https://www.worldofgothic.com/gothic3/?go=g3insert_tools
- https://www.worldofgothic.com/gothic3/?go=g3insert_trinkets
- https://www.worldofgothic.com/gothic3/?go=g3insert_trophies
- https://www.worldofgothic.com/gothic3/?go=g3insert_weapons
- https://www.worldofgothic.com/gothic3/?go=g3insert_writings
- https://www.worldofgothic.com/gothic3/?go=g3shields

## Validation

Compile actual Kotlin data with shared data types; run Gothic3ToolsDataTest. Check unique IDs, all sections, character spawn commands, teach for skills/spells, actual armor item IDs, teleport destinations, CP numeric pairs, Gothic 3 setup, source links and absence of copy commands in normal item/tip references. Full Android/Compose build and on-phone visual tests require Android Studio / SDK and remain pending.
