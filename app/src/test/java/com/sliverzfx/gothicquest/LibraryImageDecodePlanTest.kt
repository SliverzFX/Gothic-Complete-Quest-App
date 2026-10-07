package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class LibraryImageDecodePlanTest {
    @Test fun croppedBannerKeepsEnoughPixelsForItsHeight() {
        val p = libraryImageDecodePlan(2048, 716, 1080, 578, crop = true)
        assertEquals(578, p.height)
        assertTrue(p.width >= 1080)
        assertTrue(p.width < 2048)
        assertEquals(2048.0 / 716, p.width.toDouble() / p.height, 0.005)
    }
    @Test fun transparentLogoFitsItsDisplayBoundsWithoutStretching() {
        val p = libraryImageDecodePlan(2168, 725, 630, 578, crop = false)
        assertEquals(630, p.width)
        assertTrue(p.height < 578)
        assertTrue(p.sampleSize >= 2)
        assertEquals(2168.0 / 725, p.width.toDouble() / p.height, 0.01)
    }
    @Test fun portraitLogoRemainsInsideItsHeightLimit() {
        val p = libraryImageDecodePlan(800, 2400, 630, 578, crop = false)
        assertEquals(578, p.height)
        assertTrue(p.width < 630)
    }
    @Test fun smallSourceIsNotUpscaledOrSampledAway() {
        val p = libraryImageDecodePlan(64, 24, 1080, 578, crop = true)
        assertEquals(LibraryImageDecodePlan(64,24,1), p)
    }
    @Test(expected = IllegalArgumentException::class)
    fun invalidBoundsAreRejected() { libraryImageDecodePlan(0,716,1080,578,true) }
}
