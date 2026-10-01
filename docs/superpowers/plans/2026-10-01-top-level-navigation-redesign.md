# Top-Level Navigation Redesign Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace the current game-card Home screen with a Gothic-style category menu, move the existing game/mod selector under Quest Guides, add real routes for the other top-level categories, and preserve all existing quest-guide state and behavior.

**Architecture:** Keep the current single-module Jetpack Compose app and 350 ms Crossfade, but replace the growing string/boolean navigation state with an explicit `AppRoute` model. Extract the current artwork-card selector into a reusable `GameLibraryScreen` so Quest Guides, Marvin Codes / Cheats, and future databases can share the same scrolling presentation without sharing destination logic.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, SharedPreferences, JUnit 4, Compose UI testing.

**Spec:** `docs/superpowers/specs/2026-10-01-top-level-navigation-redesign.md`

## Global Constraints
- Do not add Navigation Compose or another navigation dependency solely for this change.
- Keep the existing approximately 350 ms screen crossfade.
- Keep `home_background.png` as the Home background and do not cover it with game cards.
- Home menu has no rectangular button boxes/cards.
- Home menu is vertically centered on the right side.
- Continue appears first only when a valid saved resume state exists.
- Quest Guides uses the current Gothic / Gothic II Gold / New Balance artwork panels and logos.
- Marvin Codes / Cheats must not invent final cheat artwork or cheat content.
- FAQs, Info / About, Support / Bugs, and Donations are real but intentionally empty placeholder destinations.
- Visible Back actions on top-level/category screens are upper-left and Android system Back must mirror them.
- Favorites stays reachable from Quest Guides via an upper-right text action.
- Preserve existing SharedPreferences formats for resume, favorites, completion, and music.
- Do not redesign chapter hubs, quest lists, or quest details in this milestone.
- Existing splash artwork and splash behavior remain unchanged.

## Review Focus
- Saved resume data containing an unknown game string must fail safely to Home rather than crash; Task 1 tests route conversion.
- A saved quest ID that no longer exists must resume to the saved chapter instead of producing an empty screen; Task 1 tests fallback behavior.
- Repeatedly entering/leaving Quest Guides must not clear favorites/completion/resume state; Task 5 UI test covers persistence across route changes.
- The last game entry must remain tappable above Android navigation controls on compact portrait devices; Task 3 UI test checks scroll/inset behavior.
- Android system Back and visible Back must produce the same parent route from Quest Guides, placeholders, and game hubs; Tasks 4 and 5 cover both paths.

---

### Task 1: Introduce explicit route and game models

**Files:**
- Create: `app/src/main/java/com/sliverzfx/gothicquest/AppRoute.kt`
- Create: `app/src/test/java/com/sliverzfx/gothicquest/AppRouteTest.kt`
- Modify: `app/src/main/java/com/sliverzfx/gothicquest/GothicQuestApp.kt`

**Interfaces:**
- Produces: `enum class GameId`, `sealed interface AppRoute`, `fun ResumeState.toRouteOrNull(): AppRoute?`, and route-to-game helpers used by later tasks.
- Consumes: existing quest data objects and existing SharedPreferences resume fields (`resume_game`, `resume_chapter`, `resume_quest`).

- [ ] **Step 1: Write failing unit tests for route identity and resume conversion**

Create tests asserting:
- `ResumeState("Gothic", 2, null)` maps to `AppRoute.Chapter(GameId.GOTHIC, 2)`.
- `ResumeState("Gothic II Gold Edition", 3, "G2-001")` maps to `AppRoute.QuestDetail(GameId.GOTHIC_2_GOLD, "G2-001")` only when that quest exists.
- unknown game strings return `null`.
- missing saved quest IDs fall back to `AppRoute.Chapter(game, chapter)`.

- [ ] **Step 2: Run unit tests and verify RED**

Run: `./gradlew testDebugUnitTest --tests com.sliverzfx.gothicquest.AppRouteTest`
Expected: FAIL because the route model/helpers do not exist.

- [ ] **Step 3: Implement the route model**

In `AppRoute.kt`, define:
- `enum class GameId { GOTHIC, GOTHIC_2_GOLD, NEW_BALANCE }`
- `sealed interface AppRoute` with exact destinations: `Home`, `QuestGuides`, `Cheats`, `Faqs`, `About`, `Support`, `Donations`, `Settings`, `Favorites`, `GameHub(game)`, `Chapter(game, chapter)`, `QuestDetail(game, questId)`, `AllQuests(game)`, `Search(game)`.
- helpers for persisted game-name conversion and quest lookup.

Keep `ResumeState` persistence fields unchanged.

- [ ] **Step 4: Replace top-level destination booleans/strings with one `route` state in `GothicQuestApp.kt`**

Use `var route by remember { mutableStateOf<AppRoute>(AppRoute.Home) }` plus existing `showSplash`. Crossfade target key derives from `route`; quest objects are resolved from `GameId` and route payload instead of mutable `destination`/`selectedQuest` combinations.

- [ ] **Step 5: Run unit tests and build**

Run: `./gradlew testDebugUnitTest :app:assembleDebug`
Expected: route tests PASS and build succeeds.

- [ ] **Step 6: Commit**

Commit: `refactor: add explicit app route model`

---

### Task 2: Redesign Home as a Gothic category menu

**Files:**
- Replace: `app/src/main/java/com/sliverzfx/gothicquest/HomeScreen.kt`
- Create: `app/src/androidTest/java/com/sliverzfx/gothicquest/HomeMenuTest.kt`
- Modify: `app/src/androidTest/java/com/sliverzfx/gothicquest/SplashFlowTest.kt`

**Interfaces:**
- Consumes: `hasContinue: Boolean`, `onContinue: () -> Unit`, `onNavigate: (AppRoute) -> Unit`.
- Produces: test tags `home_screen`, `home_continue`, `home_quest_guides`, `home_cheats`, `home_faqs`, `home_about`, `home_support`, `home_donations`, `home_settings`.

- [ ] **Step 1: Write failing Compose tests for the new Home**

Assert after splash:
- `QUEST GUIDES`, `MARVIN CODES / CHEATS`, `FAQs`, `INFO / ABOUT`, `SUPPORT / BUGS`, `DONATIONS`, `SETTINGS` exist.
- old game-card tags are not on Home.
- `CONTINUE` is absent when no resume state exists.

Update `SplashFlowTest.homeAppearsAfterSplash()` to wait for `QUEST GUIDES` rather than `Gothic`.

- [ ] **Step 2: Run the instrumentation test and verify RED**

Run: `./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.sliverzfx.gothicquest.HomeMenuTest`
Expected: FAIL against the current game-card Home.

- [ ] **Step 3: Implement data-driven Home entries**

Define `HomeMenuEntry(label: String, testTag: String, visible: Boolean = true, action: () -> Unit)` inside `HomeScreen.kt` or a focused private model file.

Render `home_background.png` full-screen and a right-aligned, vertically centered text menu with responsive padding. No filled button backgrounds or cards.

- [ ] **Step 4: Implement press feedback and replaceable rune**

Create `@Composable private fun GothicSelectionRune(visible: Boolean)` as the single replaceable ornament boundary. Use a simple built-in text/vector ornament for now; no new art asset.

Each entry uses an `InteractionSource` press state to animate from subdued aged gold to brighter gold with a small scale/glow response and the rune visible while pressed/transitioning.

- [ ] **Step 5: Wire Home actions to explicit routes**

Map entries to `AppRoute.QuestGuides`, `Cheats`, `Faqs`, `About`, `Support`, `Donations`, and `Settings`; prepend Continue only when `hasContinue` is true.

- [ ] **Step 6: Run Home instrumentation tests**

Run: `./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.sliverzfx.gothicquest.HomeMenuTest,com.sliverzfx.gothicquest.SplashFlowTest`
Expected: PASS.

- [ ] **Step 7: Commit**

Commit: `ui: add Gothic category home menu`

---

### Task 3: Extract reusable scrolling game/mod library

**Files:**
- Create: `app/src/main/java/com/sliverzfx/gothicquest/GameLibraryScreen.kt`
- Modify: `app/src/main/java/com/sliverzfx/gothicquest/GothicQuestApp.kt`
- Create: `app/src/androidTest/java/com/sliverzfx/gothicquest/QuestGuidesLibraryTest.kt`

**Interfaces:**
- Produces: `data class GameLibraryEntry(id: String, title: String, panelRes: Int, logoRes: Int?, onClick: () -> Unit)` and `@Composable fun GameLibraryScreen(title: String, entries: List<GameLibraryEntry>, onBack: () -> Unit, topRightActionLabel: String? = null, onTopRightAction: (() -> Unit)? = null)`.
- Consumes: existing `gothic_button_1`, `gothic_button_2`, `gothic_button_nb`, logos, and `menu_smoke`.

- [ ] **Step 1: Write failing Quest Guides library tests**

From Home tap `QUEST GUIDES`, then assert:
- library screen exists;
- three game entries exist with tags `game_gothic`, `game_gothic_2`, `game_new_balance`;
- upper-left `BACK` and upper-right `FAVORITES` exist;
- the last entry can be scrolled into view and clicked on a compact portrait emulator.

- [ ] **Step 2: Run the test and verify RED**

Run the single instrumentation class; expect FAIL because `QuestGuides` route has no library screen yet.

- [ ] **Step 3: Move current panel/smoke presentation out of Home into `GameLibraryScreen.kt`**

Preserve:
- full-width 220 dp artwork panels unless emulator review requires a later visual adjustment;
- 2 dp aged-gold separators;
- lower-right smaller logos;
- top/bottom smoke framing;
- vertical scrolling and navigation-bar-safe bottom padding.

Add an upper-left Gothic `BACK` text action and optional upper-right utility action.

- [ ] **Step 4: Build the Quest Guides dataset in `GothicQuestApp.kt`**

Use three `GameLibraryEntry` items mapping to `AppRoute.GameHub(GameId.GOTHIC)`, `GOTHIC_2_GOLD`, and `NEW_BALANCE`.

Set the top-right action to `FAVORITES` → `AppRoute.Favorites`.

- [ ] **Step 5: Run library tests and build**

Run: `./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.sliverzfx.gothicquest.QuestGuidesLibraryTest`
Then: `./gradlew :app:assembleDebug`
Expected: PASS / BUILD SUCCESSFUL.

- [ ] **Step 6: Commit**

Commit: `feat: move game selection into Quest Guides library`

---

### Task 4: Add Cheats and placeholder top-level routes

**Files:**
- Create: `app/src/main/java/com/sliverzfx/gothicquest/SectionPlaceholderScreen.kt`
- Modify: `app/src/main/java/com/sliverzfx/gothicquest/GothicQuestApp.kt`
- Create: `app/src/androidTest/java/com/sliverzfx/gothicquest/TopLevelSectionsTest.kt`

**Interfaces:**
- Produces: `@Composable fun SectionPlaceholderScreen(title: String, onBack: () -> Unit)`.
- Consumes: `GameLibraryScreen` from Task 3 and `AppRoute` from Task 1.

- [ ] **Step 1: Write failing tests for top-level destinations**

Assert:
- `MARVIN CODES / CHEATS` opens a real Cheats route with `BACK` and no fabricated game entries/content.
- FAQs, Info / About, Support / Bugs, and Donations each show their exact title and `BACK`.
- visible Back returns Home for all five routes.
- Android system Back returns Home for at least one placeholder and Cheats.

- [ ] **Step 2: Run test and verify RED**

Run the single instrumentation class; expect FAIL.

- [ ] **Step 3: Implement `SectionPlaceholderScreen`**

Use dark Gothic background treatment, section title, status-bar-safe upper-left Back action, and no body copy beyond the title.

- [ ] **Step 4: Implement Cheats as an empty `GameLibraryScreen`**

Call the reusable library with `entries = emptyList()` and no invented images/data. Keep `BACK` functional.

- [ ] **Step 5: Wire placeholder routes and keep Settings functional**

Map `Faqs`, `About`, `Support`, `Donations` to `SectionPlaceholderScreen`. Map `Settings` to the existing `SettingsScreen` with Back → Home and preserve existing music SharedPreferences behavior.

- [ ] **Step 6: Run tests**

Run the TopLevelSections instrumentation class; expect PASS.

- [ ] **Step 7: Commit**

Commit: `feat: add top-level category routes`

---

### Task 5: Preserve quest flows, parent Back behavior, Continue, and Favorites

**Files:**
- Modify: `app/src/main/java/com/sliverzfx/gothicquest/GothicQuestApp.kt`
- Modify: existing hub composables in `GothicQuestApp.kt`
- Create: `app/src/androidTest/java/com/sliverzfx/gothicquest/NavigationRegressionTest.kt`

**Interfaces:**
- Consumes: route model, Home, GameLibraryScreen, existing quest/search/favorites/settings screens.
- Produces: correct parent-route transitions for all current quest flows.

- [ ] **Step 1: Write failing navigation regression tests**

Cover:
- Quest Guides → Gothic → visible hub Back returns Quest Guides.
- system Back from a game hub returns Quest Guides.
- chapter Back returns its game hub.
- quest detail Back returns its chapter/list context as currently expected.
- Quest Guides → Favorites opens the existing Favorites screen and Back returns Quest Guides.
- route changes do not clear a seeded favorite/completed key in `quest_prefs`.

- [ ] **Step 2: Run tests and verify RED where parent behavior is still wrong**

Run the single instrumentation class.

- [ ] **Step 3: Update game hub callbacks to use route parents**

All three game hubs receive Back callbacks that set `AppRoute.QuestGuides`, not Home.

Chapter, Search, All Quests, Quest Detail, and Favorites routes return to their logical parent without relying on stale top-level booleans.

- [ ] **Step 4: Preserve Continue semantics**

On Home Continue, convert the existing `ResumeState` through `toRouteOrNull()`:
- valid quest → `QuestDetail`;
- missing quest → saved `Chapter`;
- invalid game/chapter → remain Home.

Do not change stored key names or clear resume data.

- [ ] **Step 5: Preserve favorite/completion/music persistence**

Keep existing `quest_prefs` keys and toggle helpers unchanged except for replacing string-game arguments with a stable `GameId`→persisted-name adapter where needed.

- [ ] **Step 6: Run navigation regression tests**

Run the class; expect PASS.

- [ ] **Step 7: Commit**

Commit: `fix: preserve quest navigation through new category hierarchy`

---

### Task 6: Repair test baseline and perform full verification

**Files:**
- Modify: `app/src/androidTest/java/com/sliverzfx/gothicquest/AppLaunchTest.kt`
- Modify: `app/src/androidTest/java/com/sliverzfx/gothicquest/SplashFlowTest.kt` if needed after integration
- No production feature changes unless a failing test exposes a real regression.

**Interfaces:**
- Consumes: completed navigation redesign.
- Produces: a clean build/test baseline for this milestone.

- [ ] **Step 1: Fix the stale launch assertion**

Replace the obsolete `app_root` expectation with a stable launch/home assertion that matches the current app root/splash structure; do not add fake production tags solely to satisfy an obsolete test.

- [ ] **Step 2: Run unit tests**

Run: `./gradlew testDebugUnitTest`
Expected: all unit tests PASS.

- [ ] **Step 3: Run instrumentation tests on emulator/device**

Run: `./gradlew connectedDebugAndroidTest`
Expected: all instrumentation tests PASS.

- [ ] **Step 4: Build the debug APK**

Run: `./gradlew clean :app:assembleDebug`
Expected: BUILD SUCCESSFUL and `app/build/outputs/apk/debug/app-debug.apk` exists.

- [ ] **Step 5: Manual Pixel 7a review**

Verify:
- Home background is meaningfully visible;
- right-centered text menu feels balanced;
- press glow/rune feedback is subtle;
- Quest Guides scrolls naturally and the third/future entries remain tappable;
- Back/Favorites text does not collide with status/navigation bars;
- Continue resumes the last valid quest/chapter;
- existing music, favorites, and completion state still behave as before.

- [ ] **Step 6: Commit any test-only baseline adjustments**

Commit: `test: verify top-level navigation redesign`
