package com.sliverzfx.gothicquest

import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

internal data class LibraryImageDecodePlan(val width: Int, val height: Int, val sampleSize: Int)

/** Preserve aspect ratio and enough pixels for Crop; never enlarge a small source. */
internal fun libraryImageDecodePlan(width: Int, height: Int, targetWidth: Int, targetHeight: Int,
    crop: Boolean): LibraryImageDecodePlan {
    require(width > 0 && height > 0 && targetWidth > 0 && targetHeight > 0)
    val widthScale = targetWidth.toDouble() / width
    val heightScale = targetHeight.toDouble() / height
    val scale = min(1.0, if (crop) max(widthScale, heightScale) else min(widthScale, heightScale))
    val outputWidth = (width * scale).roundToInt().coerceIn(1, width)
    val outputHeight = (height * scale).roundToInt().coerceIn(1, height)
    var sample = 1
    while (sample <= Int.MAX_VALUE / 2 && width / (sample * 2) >= outputWidth &&
        height / (sample * 2) >= outputHeight) sample *= 2
    return LibraryImageDecodePlan(outputWidth, outputHeight, sample)
}
