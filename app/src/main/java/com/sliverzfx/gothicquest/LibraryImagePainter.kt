package com.sliverzfx.gothicquest

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext

private data class LibraryImageKey(val resources: Resources, val resourceId: Int,
    val width: Int, val height: Int, val crop: Boolean, val configuration: String)

private object LibraryImageCache {
    private val memoryLimit = (Runtime.getRuntime().maxMemory() / 16)
        .coerceIn(4L * 1024 * 1024, 32L * 1024 * 1024).toInt()
    private val images = object : LruCache<LibraryImageKey, Bitmap>(memoryLimit) {
        override fun sizeOf(key: LibraryImageKey, value: Bitmap) = value.allocationByteCount
    }
    private val decodeSlots = Semaphore(2)

    fun get(key: LibraryImageKey): Bitmap? = images.get(key)

    suspend fun load(key: LibraryImageKey): Bitmap? = withContext(Dispatchers.IO) {
        decodeSlots.withPermit {
            images.get(key) ?: decode(key)?.also {
                // Queue the texture upload before this bitmap reaches the drawing frame.
                it.prepareToDraw()
                images.put(key, it)
            }
        }
    }

    private fun decode(key: LibraryImageKey): Bitmap? {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true; inScaled = false }
        BitmapFactory.decodeResource(key.resources, key.resourceId, bounds)
        if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null
        val plan = libraryImageDecodePlan(bounds.outWidth, bounds.outHeight, key.width, key.height, key.crop)
        val options = BitmapFactory.Options().apply {
            inSampleSize = plan.sampleSize
            inScaled = false
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }
        val decoded = BitmapFactory.decodeResource(key.resources, key.resourceId, options) ?: return null
        if (decoded.width == plan.width && decoded.height == plan.height) return decoded
        val sized = Bitmap.createScaledBitmap(decoded, plan.width, plan.height, true)
        // Only the private, never-published intermediate can be recycled. Cached images
        // can still be held by Compose after eviction and must not be recycled here.
        if (sized !== decoded) decoded.recycle()
        return sized
    }
}

/** Fixed maximum display bounds keep press animations from creating decode requests. */
@Composable
internal fun rememberLibraryImagePainter(resourceId: Int, width: Dp, height: Dp, crop: Boolean): Painter {
    val resources = LocalContext.current.resources
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current.toString()
    val widthPx = with(density) { width.roundToPx().coerceAtLeast(1) }
    val heightPx = with(density) { height.roundToPx().coerceAtLeast(1) }
    val request = remember(resources, resourceId, widthPx, heightPx, crop, configuration) {
        LibraryImageKey(resources, resourceId, widthPx, heightPx, crop, configuration)
    }
    return key(request) {
        val bitmap by produceState(initialValue = LibraryImageCache.get(request), key1 = request) {
            value = LibraryImageCache.load(request)
        }
        remember(bitmap) { bitmap?.let { BitmapPainter(it.asImageBitmap()) } ?: ColorPainter(Color.Transparent) }
    }
}
