package com.sliverzfx.gothicquest

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GothicVisualThemeTest {
    @Test
    fun stoneThemeIsScopedToGothicOne() {
        assertTrue(GameId.GOTHIC.usesStoneTheme)
        assertFalse(GameId.GOTHIC_2_GOLD.usesStoneTheme)
        assertFalse(GameId.NEW_BALANCE.usesStoneTheme)
        assertFalse(GameId.GOTHIC_3.usesStoneTheme)
    }
}
