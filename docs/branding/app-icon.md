Questbound app icon assets

Approved design: rugged grey stone Q with integrated curved tail and gold edges.

Google Play upload: distribution/google-play/questbound_icon.png (512 x 512 RGBA PNG, sRGB, opaque background, under 1 MB).
Android launcher: already wired in app/src/main/AndroidManifest.xml. Foreground/background adaptive layers for Android 8+, monochrome layer for Android 13+, density fallback PNGs.
Q foreground is centered inside a 66 dp circular safe area on a 108 dp layer so the curved tail survives launcher masks.

Pull dev/android-foundation-v0.1, rebuild and reinstall through Android Studio. No manual image import is needed for this icon.
The Play Store icon is uploaded separately in Google Play Console when preparing the listing.

Source and image checks were run. An Android build and physical-phone launcher verification require Android Studio.
