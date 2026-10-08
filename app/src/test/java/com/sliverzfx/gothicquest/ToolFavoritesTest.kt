package com.sliverzfx.gothicquest

import org.junit.Assert.*
import org.junit.Test

class ToolFavoritesTest {
    @Test fun sharedEntryIdsStaySeparateBetweenGamesAndQuests() {
        assertNotEquals(toolFavoriteKey(GameId.GOTHIC, "enable"), toolFavoriteKey(GameId.GOTHIC_2_GOLD, "enable"))
        assertEquals("G1|tool:enable", toolFavoriteKey(GameId.GOTHIC, "enable"))
        assertNotEquals("G1|enable", toolFavoriteKey(GameId.GOTHIC, "enable"))
    }
    @Test fun savedCombinedItemKeepsStatsAndCommandInItsCategory() {
        val keys = setOf(toolFavoriteKey(GameId.GOTHIC, "rusty_sword_insert"), toolFavoriteKey(GameId.GOTHIC, "enable"), "G1|G1-C1-01")
        val favorites = buildToolFavorites(keys)
        assertEquals(2, favorites.size)
        val weapon = favorites.first { it.card.entry.id == "rusty_sword_insert" }
        assertEquals(CodeCategory.WEAPONS, weapon.category)
        assertEquals("insert itmw_1h_sword_old_01", weapon.card.entry.command)
        assertTrue(weapon.card.entry.body.isNotBlank())
        assertEquals(CodeCategory.GENERAL, favorites.first { it.card.entry.id == "enable" }.category)
    }
    @Test fun unknownRemovedReferencesAreIgnoredAndStandaloneItemsCanBeSaved() {
        assertTrue(buildToolFavorites(setOf("G1|tool:missing")).isEmpty())
        val item = GameId.GOTHIC.toolReferenceCards(ToolSection.MARVIN_CODES).first { it.entry.command == null }
        val favorites = buildToolFavorites(setOf(toolFavoriteKey(GameId.GOTHIC, item.entry.id)))
        assertEquals(item, favorites.single().card)
    }
}
