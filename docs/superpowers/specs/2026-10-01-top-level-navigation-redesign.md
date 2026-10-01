# Gothic Complete Quest Guide — Top-Level Navigation Redesign

Date: 2026-10-01
Branch: `dev/android-foundation-v0.1`

## Goal

Restructure the app so the opening screen is a true Gothic-style main menu rather than a game selector. The existing Khorinis/Nameless Hero background becomes the visual focus. Content types are chosen first; game/mod selection happens only inside sections that need it.

The app must remain scalable as more games, mods, quest guides, cheats, FAQs, reference material, maps, databases, and support content are added.

## Navigation hierarchy

The top-level flow becomes:

```text
Splash
  ↓
Home
  ├─ Continue                     (only when resume data exists)
  ├─ Quest Guides
  │    └─ Game / Mod Library
  │         ├─ Gothic
  │         ├─ Gothic II Gold Edition
  │         ├─ Gothic II New Balance
  │         └─ future games / mods
  ├─ Marvin Codes / Cheats
  │    └─ Game / Mod Library      (same layout system, separate artwork/data)
  ├─ FAQs
  ├─ Info / About
  ├─ Support / Bugs
  ├─ Donations
  └─ Settings                     (preserves existing music control)
```

`Favorites` remains part of the quest-guide system and must not be deleted. For this milestone it is reachable from the Quest Guides library as a small Gothic-styled text action in the upper-right, opposite the upper-left `BACK` action. Its later visual redesign is out of scope.

## Home screen

### Background

Use the existing `home_background.png` as a full-screen background with `ContentScale.Crop`. It should no longer be covered by large game cards on the opening screen.

The existing smoke asset may be used only as a subtle readability treatment if needed. It must not become a large opaque panel or recreate a boxed mobile-dashboard look.

### Menu placement

The main menu is vertically centered on the right side of the screen. The left side remains largely unobstructed so the background scenery and Nameless Hero remain visible.

The menu contains no rectangular button boxes, cards, filled containers, or modern Material-style buttons.

Initial order:

1. Continue — only when resume data exists
2. Quest Guides
3. Marvin Codes / Cheats
4. FAQs
5. Info / About
6. Support / Bugs
7. Donations
8. Settings

### Visual language

Default menu text uses a subdued aged-gold / parchment tone.

When an item is pressed/active:

- the text becomes brighter warm gold;
- a soft glow appears around the text;
- a small Gothic ornament/rune appears immediately to the left;
- the item may use a very small scale/brightness response, but the motion must remain subtle.

On touch devices the highlighted/active state is the press state and the short transition into the selected destination; there is no permanent hover state.

The ornament must be implemented as a replaceable UI element so a final custom rune asset can be substituted later without rewriting menu behavior.

On selection, the current global crossfade behavior is retained so the app transitions smoothly into the destination screen.

## Quest Guides screen

`Quest Guides` opens the scrolling game/mod selector that has already been developed.

The screen uses:

- vertically scrollable full-width game/mod artwork panels;
- thin aged-gold separators between entries;
- smaller game logos positioned toward the lower-right of each panel;
- smoke framing where useful;
- an upper-left Gothic-styled `BACK` action;
- an upper-right Gothic-styled `FAVORITES` text action;
- enough bottom inset/padding that content never conflicts with Android system navigation.

The current three entries are:

- Gothic
- Gothic II Gold Edition
- Gothic II New Balance

The list must be data-driven/reusable so additional games and mods can be appended without duplicating the whole screen implementation.

Selecting a game continues into the existing chapter/quest flow. Existing chapter hubs, quest lists, quest details, search, completion tracking, resume tracking, and favorite tracking remain functional. Their visual redesign is explicitly deferred to later work.

## Marvin Codes / Cheats screen

Marvin Codes / Cheats uses the same reusable game/mod-library component and interaction model as Quest Guides, but it has its own dataset and its own artwork references.

The architecture must not hard-code quest-specific behavior into the reusable game selector.

Final cheat-specific game artwork has not yet been supplied. Therefore this milestone creates the real Marvin Codes / Cheats route and reusable library structure, but does not invent or silently substitute final cheat artwork. Until those assets/data are provided, the section presents an intentionally minimal/empty library state with the Gothic `BACK` action.

Later flow:

```text
Marvin Codes / Cheats
  ↓
Choose Game / Mod
  ↓
Cheat Categories
  ↓
Code / Detail
```

Cheat categories and code data are outside this milestone.

## Placeholder top-level sections

The following entries must navigate to real screens now, even though their content is intentionally deferred:

- FAQs
- Info / About
- Support / Bugs
- Donations

Each placeholder screen should:

- preserve the app's dark Gothic visual language;
- show the section title;
- provide the same Gothic-styled upper-left `BACK` action;
- contain no fabricated content.

These are structural routes, not finished features.

## Back navigation

Inner top-level/category screens use a visible Gothic-styled `BACK` action in the upper-left.

Android system Back must perform the same logical action as the visible Back control.

Expected behavior:

- placeholder section → Home
- Quest Guides library → Home
- Marvin Codes / Cheats library → Home
- game chapter hub → Quest Guides library
- deeper quest screens continue using their existing logical parent behavior until those screens are redesigned

The redesign must avoid a situation where a game hub's Back button jumps straight to Home when the user entered it through Quest Guides.

## Continue behavior

Continue remains conditional and appears as the first main-menu entry only when a valid saved resume state exists.

Existing SharedPreferences resume data remains compatible:

- game
- chapter
- optional quest ID

Selecting Continue restores the same destination that the current implementation restores: the saved game/chapter, and the saved quest detail when a valid quest ID exists.

The navigation redesign must not reset or invalidate completion, favorites, resume, or music preferences.

## Settings and Favorites preservation

The existing music setting must remain reachable. `Settings` therefore remains a top-level text menu item for this milestone.

Favorites remain a quest-guide feature. For this milestone the Quest Guides library exposes `FAVORITES` in the upper-right as a text-only Gothic utility action. It opens the existing Favorites screen. The exact final Favorites presentation will be redesigned later.

## Architecture

### Route model

Replace the growing collection of unrelated top-level destination strings with a small explicit route model for the new top-level structure. This may be a sealed class/interface or enum-backed state, but it must represent at least:

- Home
- Quest Guides library
- Marvin Codes / Cheats library
- FAQs
- Info / About
- Support / Bugs
- Donations
- Settings
- existing game/chapter/quest destinations

Do not add a new navigation framework dependency solely for this change. The current Compose state + Crossfade model can remain, provided the route state is explicit and predictable.

### Reusable game-library component

Extract the artwork-panel selector into a reusable composable whose behavior is independent of whether the caller is Quest Guides, Cheats, or a future database section.

A library entry should provide, at minimum:

- stable ID
- display title/content description
- panel drawable reference
- logo drawable reference when available
- destination/action callback

The component owns presentation and scrolling. The calling section owns the dataset and destination behavior.

### Home menu component

The home menu should be defined from a list of menu entries rather than one hard-coded block per action. An entry needs:

- label
- optional visibility condition
- action
- optional test tag

This keeps future top-level additions inexpensive.

## Existing assets

This milestone reuses existing assets where appropriate:

- `home_background.png`
- `menu_smoke.png`
- `gothic_button_1.png`
- `gothic_button_2.png`
- `gothic_button_nb.png`
- existing Gothic / Gothic II / New Balance logos

The obsolete `gothic_home_menu_v1.webp` may remain in resources as a fallback/archive asset but must not drive the new Home UI.

## Animation and interaction

- Keep the existing approximately 350 ms screen crossfade unless testing shows it causes a regression.
- Menu highlight animation should be brief and restrained.
- No looping decorative animation is required for this milestone.
- The game/mod list must scroll naturally before an entry is pressed and must remain usable with many future entries.

## Safe areas and device behavior

All persistent controls must respect status/navigation insets.

No interactive text may overlap Android gesture/navigation controls at the bottom of the device.

The Home menu must remain usable on the current Pixel 7a emulator dimensions and should use responsive alignment/padding rather than fixed absolute hitbox coordinates.

## Testing / verification

Implementation should add or update Compose UI tests around the new structure where practical.

At minimum verify:

1. Home renders the main category menu instead of game cards.
2. Continue is hidden with no resume state and shown with a valid resume state.
3. Quest Guides opens the game/mod library.
4. Quest Guides game entries navigate to the correct existing game hub.
5. Back from a game hub returns to Quest Guides, not directly to Home.
6. Marvin Codes / Cheats opens its own route without fabricated cheat content.
7. FAQs, Info / About, Support / Bugs, and Donations each open their own placeholder route and return Home with Back.
8. Settings remains reachable and music preference behavior still works.
9. Existing Favorites data remains intact and `FAVORITES` in the Quest Guides library opens the existing Favorites screen.
10. Android system Back mirrors the visible Back behavior.
11. Existing quest completion, favorites, and resume state are not cleared by the navigation refactor.
12. `:app:assembleDebug` succeeds.

Manual emulator verification should additionally check:

- visual balance of the right-centered Home menu;
- background remains meaningfully visible;
- pressed gold glow/rune feedback is subtle and readable;
- game library scroll behavior;
- safe-area spacing around Android navigation controls.

## Out of scope for this milestone

- redesigning chapter hubs;
- redesigning quest lists or quest details;
- entering Marvin code data;
- creating cheat-specific artwork;
- writing FAQ content;
- writing About, Support, Bugs, or Donations content;
- adding item/NPC/map databases;
- changing quest datasets;
- replacing the existing splash screen;
- changing persistence formats unless required for route compatibility.

## Success criteria

The app opens into a cinematic Gothic-style category menu over the Khorinis background. Quest Guides leads to the scalable scrolling game/mod library. Other top-level categories have clear real routes, with unfinished sections intentionally left empty rather than filled with invented content. Existing quest-guide functionality and saved user state remain intact, and the structure can grow without turning the opening screen into a wall of game cards.