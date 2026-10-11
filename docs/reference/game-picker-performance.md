# Game picker image loading

The game library already uses LazyColumn with stable game IDs. Its banners and logos previously used painterResource, decoding full PNG resources on the UI thread when cards entered composition. Several banners are 2048–2120 pixels wide and about 3 MB compressed; a 2120 × 742 ARGB bitmap occupies about 6 MiB before GPU upload. Logos are similarly oversized for their 190–210 dp display width.

## Change

GameLibraryScreen now requests a resource-only asynchronous painter for its banners and logos. Bounds inspection, subsampled decoding and final resizing run on Dispatchers.IO, with at most two simultaneous decodes. Images retain their aspect ratio and transparency. Crop requests preserve enough pixels for both dimensions; small sources are never enlarged by the loader.

A process-local LruCache holds decoded images, capped at 1/16 of the Java heap and 32 MiB. Cache hits are available immediately when a row returns to composition. Only private intermediate bitmaps are recycled; an evicted bitmap may still be drawn by Compose. All three logo/shadow layers share the same painter and bitmap. Requests use maximum press-animation bounds, so animation frames do not request new decodes. Newly decoded bitmaps call prepareToDraw before publication.

No image assets, gradients, shadow offsets, blur radii, menu layout, animations, search, favorites, click actions or navigation were changed. Loading is entirely local and introduces no dependency or internet requirement. A first-time image can appear shortly after its card, while loading continues off the UI thread.

## Validation and limits

Five pure Kotlin tests cover banner Crop sizing, landscape/portrait logo Fit sizing, small images and invalid bounds. The existing 79 reference data tests also pass. Code review found no blocking defects. A pair of simultaneously mounting callers with an identical uncached key may duplicate a decode; each card avoids this by sharing its single logo painter.

There is no Android SDK/device in this execution environment, so Android compilation, existing Compose instrumentation tests and before/after frame timing have not been run. This removes an identified main-thread bottleneck but does not establish a measured phone frame-rate improvement. On the phone, compare first opening, scrolling to the bottom and back, reopening the picker, search/favorites, and pressing a game. If rendering still stutters after images are cached, profile frame timing and the two live blur layers before making another change.

## Technical references

- Android resource loading: https://developer.android.com/develop/ui/compose/resources
- Bitmap subsampling: https://developer.android.com/reference/android/graphics/BitmapFactory.Options
- Early texture upload: https://developer.android.com/topic/performance/issues/render
