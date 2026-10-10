package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TabletLayoutSizingTest {
    @Test
    fun pixelTabletRetainsFullSizedLandscapeLayout() {
        assertTrue(tabletLandscapeLayout(1280, 800, 800))
        assertEquals(1f, compactTabletLayoutScale(1280, 800), 0.001f)
    }

    @Test
    fun sevenInchTabletGetsCompactLandscapeLayout() {
        assertTrue(tabletLandscapeLayout(1024, 552, 600))
        assertEquals(0.8f, compactTabletLayoutScale(1024, 600), 0.001f)
    }

    @Test
    fun portraitTabletsAndLandscapePhonesDoNotSwitchToTabletLayout() {
        assertFalse(tabletLandscapeLayout(600, 1024, 600))
        assertFalse(tabletLandscapeLayout(1080, 450, 393))
        assertFalse(tabletLandscapeLayout(839, 560, 600))
    }

    @Test
    fun smallTabletScalingNeverMakesControlsExtremelySmall() {
        assertEquals(0.78f, compactTabletLayoutScale(840, 600), 0.001f)
        assertEquals(1f, compactTabletLayoutScale(1440, 720), 0.001f)
    }
}
