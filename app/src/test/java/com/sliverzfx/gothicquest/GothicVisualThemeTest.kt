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

    @Test
    fun bloodThemeIsScopedToNewBalance() {
        assertFalse(GameId.GOTHIC.usesBloodTheme)
        assertFalse(GameId.GOTHIC_2_GOLD.usesBloodTheme)
        assertTrue(GameId.NEW_BALANCE.usesBloodTheme)
        assertFalse(GameId.GOTHIC_3.usesBloodTheme)
    }
}
