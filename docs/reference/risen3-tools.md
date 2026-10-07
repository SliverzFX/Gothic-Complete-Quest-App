# Risen 3 offline tools

Risen 3 now uses the shared compact Codes and Useful Tips screens. Search includes full descriptions; tapping a card opens its offline details. A Copy button appears only for documented keyboard shortcuts. Item references include no speculative inventory commands, and no reference opens a browser.

## Scope

- 30 legendary collectibles: permanent bonuses, finding clues, chapter restrictions, and Fog Island DLC requirement.
- All eight permanent-attribute potion formulas and their rare plants, with recipe/training/workstation prerequisites.
- Five re-forged unique weapons: damage, category, both-part locations, Blacksmith requirement and Chapter 3/Monkey Trainer/Parrot Flight restrictions where relevant.
- Guardian and Demon Hunter armor acquisition.
- Nine original-PC debug shortcuts, plus a separate compatibility/setup card.
- 19 gameplay tips covering Glory, training, healing, teleporters, crafting, thievery, crew and planning.

This is a curated reference, not a complete engine item/NPC/weapon/armor database. No reliable Risen 3 spawn-ID export was obtained. Widely circulated lists containing Risen 1 IDs and Minsky instructions were excluded. Quest guides already present in the app are unaffected. Maps remain deferred.

## Compatibility and sources

The original 2014 PC testmode report by alex lists DebugKeys and describes OC Burner's WMVCore.dll method. An earlier Cheat Engine table targets 1.0.90.0. The DLL source was inspected as text and never executed. This is not evidence of an unrestricted built-in retail console or Enhanced Edition compatibility. No third-party binary, trainer or save modifier is bundled.

Facts were independently summarized from the official PC manual, kris.aalst's firsthand crafting guide, Andrej Eperješi's ABCgames walkthrough, Gessie's Enhanced Edition guide, and the specific Gamepressure collectible/weapon/armor pages. Source names appear as plain offline credits. URLs remain internal provenance metadata, without browser actions.

Source attribution is granular: potion formulas derive from kris.aalst; the +5/+2 attribute values derive from ABCgames. Weapon assembly training derives from the introduction page, while each part's location/damage derives from its own quest page. These are compact factual references rather than copies of source walkthrough prose.

## Validation

79 pure Kotlin JUnit data regressions pass, including seven new Risen 3 tests. Shared route/header/offline-action checks pass. Three Compose instrumentation cases cover body search, null-command item details, Copy for a debug shortcut, and gameplay tips. Android SDK/device execution is unavailable here; instrumentation and an Android build still require Android Studio.
