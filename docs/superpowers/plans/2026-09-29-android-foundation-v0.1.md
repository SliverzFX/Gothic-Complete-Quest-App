# Android Foundation v0.1 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build an installable Android v0.1 that fades through the supplied Gothic splash artwork and opens the approved three-game Gothic Home screen.

**Architecture:** A deliberately small single-module Kotlin/Jetpack Compose app. `MainActivity` hosts `GothicQuestApp`; that composable owns temporary splash/home state, while `SplashScreen`, `HomeScreen`, and `GameCard` remain focused UI units.

**Tech Stack:** Kotlin, Android Gradle Plugin, Jetpack Compose, Material 3, Compose BOM, JUnit, Compose UI testing.

**Spec:** `docs/superpowers/specs/2026-09-29-android-foundation-v0.1-design.md`

## Global Constraints
- Splash artwork must be used unchanged.
- Labels: `Gothic`, `Gothic II — Gold Edition`, `Gothic II — New Balance`.
- Roman numeral `II` is mandatory.
- No quest database, networking, Room, DI, or Navigation Compose in v0.1.
- Home follows the approved Gothic Figma direction.
- v0.1 must produce an installable debug APK and launch without crashing.

## Review Focus
- Cold launch must transition from splash to Home.
- Recomposition must not repeatedly restart startup.
- All three game cards must remain reachable on compact portrait screens.
- Gothic II copy must retain Roman numeral II.
- Missing splash resource must fail at build time rather than silently substitute unrelated artwork.

---

### Task 1: Android project shell and launch test
**Files:** Gradle project files, manifest, `MainActivity.kt`, and `AppLaunchTest.kt`.

- [ ] Create the minimal Gradle/Compose project using the current Compose BOM and compiler plugin pattern.
- [ ] Add an instrumentation launch test.
- [ ] Run `./gradlew connectedDebugAndroidTest`; expect PASS when a device/emulator is available.
- [ ] Run `./gradlew assembleDebug`; expect BUILD SUCCESSFUL and `app-debug.apk`.
- [ ] Commit: `build: create Android Compose foundation`.

### Task 2: Splash-to-home state and animation
**Files:** `GothicQuestApp.kt`, `SplashScreen.kt`, splash drawable, `SplashFlowTest.kt`.

- [ ] Add a UI test asserting startup shows splash and eventually Home.
- [ ] Implement startup state with a launch effect that runs once.
- [ ] Implement fade-in, brief hold, fade-out over black using the unchanged supplied artwork.
- [ ] Run instrumentation tests; expect splash-flow PASS.
- [ ] Commit: `feat: add Gothic splash transition`.

### Task 3: Gothic Home screen
**Files:** `HomeScreen.kt`, `GameCard.kt`, theme files, `HomeScreenTest.kt`.

- [ ] Add a failing UI test for the three exact game labels.
- [ ] Implement the Gothic palette/theme and Home layout from Figma.
- [ ] Implement reusable game cards and non-functional v0.1 footer affordances.
- [ ] Add compact-height coverage verifying all game entries remain reachable.
- [ ] Run instrumentation tests; expect PASS.
- [ ] Commit: `feat: add Gothic game selection home`.

### Task 4: v0.1 verification baseline
**Files:** `README.md`, `.gitignore`.

- [ ] Run `./gradlew clean testDebugUnitTest assembleDebug`; expect BUILD SUCCESSFUL.
- [ ] Run instrumentation tests when emulator/device is available; expect PASS.
- [ ] Verify `app/build/outputs/apk/debug/app-debug.apk` exists.
- [ ] Document build/install instructions and v0.1 scope.
- [ ] Commit: `docs: document v0.1 Android baseline`.
- [ ] After Miha confirms the APK launches correctly on his phone, tag/freeze v0.1.
