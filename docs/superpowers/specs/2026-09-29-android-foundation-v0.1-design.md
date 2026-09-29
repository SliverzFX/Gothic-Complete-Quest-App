# Gothic Complete Quest Guide — Android Foundation v0.1 Design

**Date:** 2026-09-29

## Goal
Create the first known-good Android foundation for Gothic Complete Quest Guide. The build must install and launch on an Android phone, show Miha's supplied Gothic splash artwork with a short fade sequence, then show the approved Gothic-styled home menu.

## Scope
v0.1 contains only the app shell and home screen. It deliberately excludes quest content, chapters, search, favorites persistence, databases, networking, and settings behavior.

## Launch flow
1. App launches into a black background.
2. The supplied portrait splash artwork fades in.
3. It remains visible briefly.
4. It fades out.
5. The Home screen becomes visible.

The splash artwork is used unchanged.

## Home screen
The Home screen follows the approved Figma design direction: dark Gothic atmosphere, warm metal/gold typography and borders, ember/red accents, and three large game-selection cards.

Game labels are exactly:
- Gothic
- Gothic II — Gold Edition
- Gothic II — New Balance

Use Roman numeral **II**, never "2", in Gothic II titles.

The initial footer presents About, Settings, Favorites, and Info as visual affordances. They do not need functional destinations in v0.1.

## Architecture
Use a single Android application module written in Kotlin with Jetpack Compose. Keep the foundation deliberately small: one activity, an app-level composable controlling the splash/home state, focused splash and home composables, a reusable game-card composable, and theme files.

Do not add Navigation Compose, Room, dependency injection, networking, or a content database in v0.1.

## Visual source of truth
Figma: https://www.figma.com/design/8dCHCCjCU0nEEELGY3eRya

## Verification
v0.1 is accepted when the debug APK builds successfully; the app launches without crashing; splash transitions to Home; Home displays all three exact game labels; the portrait layout remains usable; and a clean build/test cycle succeeds.

Once verified on Miha's phone, freeze this as the known-good v0.1 baseline before adding quest-guide features.
