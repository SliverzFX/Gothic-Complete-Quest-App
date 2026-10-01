package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppRouteTest {
    @Test
    fun resumeChapterMapsToChapterRoute() {
        val route = routeFromResume("Gothic", 2, null)
        assertEquals(AppRoute.Chapter(GameId.GOTHIC, 2), route)
    }

    @Test
    fun existingSavedQuestMapsToQuestDetailRoute() {
        val route = routeFromResume("Gothic II Gold Edition", 1, "G2G-C01-070")
        assertEquals(AppRoute.QuestDetail(GameId.GOTHIC_2_GOLD, "G2G-C01-070"), route)
    }

    @Test
    fun unknownGameFallsBackSafely() {
        assertNull(routeFromResume("Unknown Game", 1, null))
    }

    @Test
    fun missingSavedQuestFallsBackToChapter() {
        val route = routeFromResume("Gothic II Gold Edition", 3, "missing-id")
        assertEquals(AppRoute.Chapter(GameId.GOTHIC_2_GOLD, 3), route)
    }
}
