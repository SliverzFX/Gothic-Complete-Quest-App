package com.sliverzfx.gothicquest
import org.junit.Assert.*
import org.junit.Test
class Gothic3ToolsDataTest {
 @Test fun everySectionHasSearchableContent() { ToolSection.entries.forEach { assertTrue(Gothic3ToolsData.entries(it).isNotEmpty()) } }
 @Test fun idsAreUniqueWithinEachSection() { ToolSection.entries.forEach { val a=Gothic3ToolsData.entries(it); assertEquals(a.size,a.map { e -> e.id }.toSet().size) } }
 @Test fun charactersSpawnInsteadOfEnteringInventory() { val a=Gothic3ToolsData.entries(ToolSection.MARVIN_CODES).filter { it.codeCategory==CodeCategory.CHARACTERS };assertTrue(a.size>1000);assertTrue(a.all { it.command!!.startsWith("spawn ") });assertEquals("spawn Xardas",a.single { it.command=="spawn Xardas" }.command) }
 @Test fun skillsAreTaughtInsteadOfGiven() { val a=Gothic3ToolsData.entries(ToolSection.MARVIN_CODES); assertTrue(a.any { it.command=="teach It_Perk_1H_1" }); assertTrue(a.any { it.command=="teach It_Spell_Fireball" });assertFalse(a.any { it.command?.startsWith("give It_Perk_")==true || it.command?.startsWith("give It_Spell_")==true }) }
 @Test fun genuineArmorUsesItemIdNotBodyMesh() { val a=Gothic3ToolsData.entries(ToolSection.MARVIN_CODES); assertTrue(a.any { it.command=="give It_Armor_Paladin" && it.codeCategory==CodeCategory.ARMOR });assertFalse(a.any { it.command?.contains("Body_")==true || it.command?.contains("Armortest")==true }) }
 @Test fun basicAmmunitionIsNotDiscardedWhenNameMatchesId() { assertTrue(Gothic3ToolsData.entries(ToolSection.MARVIN_CODES).any { it.command=="give Arrow" });assertTrue(Gothic3ToolsData.entries(ToolSection.ITEMS).any { it.title=="Arrow" }) }
 @Test fun teleportNamesIncludeDestination() { val a=Gothic3ToolsData.entries(ToolSection.ITEMS);assertTrue(a.any { it.title.contains("Ardea") && it.body.contains("It_Teleport_Ardea") }) }
 @Test fun balancingStatsPreservePairedValuesAndVersion() { val a=Gothic3ToolsData.entries(ToolSection.ITEMS).single { it.id=="g3_stats_demon_bow" };assertTrue(a.body.contains("CP 1.70"));assertTrue(a.body.contains("Damage: 150 (120)"));assertTrue(a.body.contains("Hunting skill requirement: 290 (250)"));assertTrue(a.body.contains("parentheses")) }
 @Test fun consoleActivationAndExitAreGothic3Specific() { val a=Gothic3ToolsData.entries(ToolSection.MARVIN_CODES);assertTrue(a.any { it.body.contains("TestMode=true") && it.command==null });assertTrue(a.any { it.command=="game.bTestMode 0" });assertFalse(a.any { it.command=="42" || it.command?.startsWith("insert ")==true }) }
 @Test fun sourcesArePresentAndNormalReferencesHaveNoCheatCopyButton() { ToolSection.entries.forEach { s -> Gothic3ToolsData.entries(s).forEach { assertTrue(it.source.startsWith("https://"));assertTrue(it.body.isNotBlank());if(s!=ToolSection.MARVIN_CODES)assertNull(it.command) } } }
}
