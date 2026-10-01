package com.sliverzfx.gothicquest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppRouteTest {
    @Test
    fun resumeChapterMapsToChapterRoute() {
        val route = ResumeState("Gothic", 2, null).toRouteOrNull()
        assertEquals(AppRoute.Chapter(GameId.GOTHIC, 2), route)
    }

    @Test
    fun existingSavedQuestMapsToQuestDetailRoute() {
        val route = ResumeState("Gothic II Gold Edition", 1, "G2G-C01-070").toRouteOrNull()
        assertEquals(AppRoute.QuestDetail(GameId.GOTHIC_2_GOLD, "G2G-C01-070"), route)
    }

    @Test
    fun unknownGameFallsBackSafely() {
        assertNull(ResumeState("Unknown Game", 1, null).toRouteOrNull())
    }

    @Test
    fun missingSavedQuestFallsBackToChapter() {
        val route = ResumeState("Gothic II Gold Edition", 3, "missing-id").toRouteOrNull()
        assertEquals(AppRoute.Chapter(GameId.GOTHIC_2_GOLD, 3), route)
    }
}
