package com.sliverzfx.gothicquest

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SupportReportTest {
    @Test
    fun correctionIncludesExactQuestAndLeavesGameVersionForPlayer() {
        val report = buildSupportReport("0.1.0", "HONOR phone", "16", QuestCorrectionContext(
            "Gothic II New Balance", "CHAPTER", 2, "A hero's task", "NB-C2-123"))
        assertTrue(report.contains("Game / mod: Gothic II New Balance"))
        assertTrue(report.contains("Game / mod version:"))
        assertTrue(report.contains("CHAPTER: 2"))
        assertTrue(report.contains("Quest: A hero's task"))
        assertTrue(report.contains("Quest ID: NB-C2-123"))
        assertTrue(report.contains("Incorrect step or information:"))
        assertTrue(report.contains("Suggested correction:"))
    }

    @Test
    fun gothicThreeUsesPartLabel() {
        val report = buildSupportReport("0.1.0", "Phone", "16", QuestCorrectionContext(
            "Gothic 3", "PART", 5, "A task", "G3-001"))
        assertTrue(report.contains("PART: 5"))
        assertFalse(report.contains("CHAPTER:"))
    }

    @Test
    fun genericSupportHasNoPreviousQuestDetails() {
        val report = buildSupportReport("", "Phone", "16")
        assertTrue(report.contains("Questbound — Bug report"))
        assertTrue(report.contains("App version: Unknown"))
        assertTrue(report.contains("Game / mod and version:"))
        assertFalse(report.contains("Quest ID:"))
        assertFalse(report.contains("Suggested correction:"))
    }
}
