package com.sliverzfx.gothicquest
import org.junit.Assert.*
import org.junit.Test
class Risen2ToolsDataTest {
 @Test fun activationBelongsToRisen2() {
  assertTrue(Risen2ToolsData.codes.any { it.command == "pommes" })
  assertTrue(Risen2ToolsData.codes.any { it.command == "give It_Gold 1000" })
  assertFalse(Risen2ToolsData.codes.any { it.command == "minsky" || it.command?.startsWith("insert ") == true })
 }
 @Test fun exactCaseAndTypographicCorrections() {
  assertTrue(Risen2ToolsData.codes.any { it.command == "give It_Bo_Rum 1" })
  assertTrue(Risen2ToolsData.codes.any { it.command == "give It_SilverIron_Barrel 1" })
  assertTrue(Risen2ToolsData.codes.any { it.command == "give It_Pis_Silver 1" })
  assertTrue(Risen2ToolsData.codes.any { it.command == "give It_Bandana_Black 1" })
  assertFalse(Risen2ToolsData.codes.any { it.command == "give It_SilverIron-Barrel 1" })
  assertFalse(Risen2ToolsData.codes.any { it.command?.contains('`') == true })
  assertFalse(Risen2ToolsData.codes.any { it.command?.contains("Tri̇́be") == true })
 }
 @Test fun armorKeepsProtectionAndBonuses() {
  val c=Risen2ToolsData.codes.single { it.command == "give It_Pants_White 1" }
  assertTrue(c.body.contains("Bladeproof: 3"));assertTrue(c.body.contains("Silver Tongue: +3"))
  assertTrue(Risen2ToolsData.codes.single { it.command == "give It_GreatCoat_Leather 1" }.title == "Heavy Leather Coat")
 }
 @Test fun worldObjectsAndBulkPrefixesAreNotInventoryCommands() {
  val c=Risen2ToolsData.codes.single { it.id == "r2_item_it_map_ant" }
  assertNull(c.command);assertTrue(c.body.contains("not an inventory item"))
  assertFalse(Risen2ToolsData.codes.any { it.command == "give It_Am_ 1" || it.command == "give It 1" })
 }
 @Test fun questAndDlcNotesStayOffline() {
  assertTrue(Risen2ToolsData.codes.single { it.command == "give It_Hat_DLC1 1" }.body.contains("DLC"))
  assertTrue(Risen2ToolsData.codes.single { it.command == "give It_Key_Stahlbart_TRI 1" }.body.contains("The Chest Key"))
  assertTrue(Risen2ToolsData.codes.single { it.command == "give It_Po_Perm_Cunning 1" }.body.contains("reported"))
 }
 @Test fun catalogueAndCategoriesAreStable() {
  assertEquals(572,Risen2ToolsData.codes.count { it.id.startsWith("r2_item_") })
  assertEquals(Risen2ToolsData.codes.size,Risen2ToolsData.codes.map { it.id }.toSet().size)
  assertTrue(Risen2ToolsData.codes.all { it.body.isNotBlank() && it.codeCategory != null && it.source.startsWith("https://") })
 }
 @Test fun descriptionsSurviveCompactCardsAndLegacyRouting() {
  assertEquals(Risen2ToolsData.codes,combineToolReferences(Risen2ToolsData.codes,Risen2ToolsData.items).map { it.entry })
  assertEquals(Risen2ToolsData.codes,Risen2ToolsData.entries(ToolSection.MARVIN_CODES))
  assertEquals(Risen2ToolsData.tips,Risen2ToolsData.entries(ToolSection.USEFUL_TIPS))
  assertTrue(Risen2ToolsData.tips.size >= 15)
  assertTrue(Risen2ToolsData.tips.all { it.command == null })
 }
}
