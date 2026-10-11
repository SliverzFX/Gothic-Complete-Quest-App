package com.sliverzfx.gothicquest
import org.junit.Assert.*
import org.junit.Test
class Risen3ToolsDataTest {
 private fun card(id: String) = Risen3ToolsData.codes.single { it.id == id }
 @Test fun setupDoesNotPromiseAnUnverifiedRetailConsole() {
  val setup=card("r3_debug_setup")
  assertNull(setup.command)
  assertTrue(setup.body.contains("Enhanced Edition"))
  assertTrue(setup.body.contains("OC Burner"))
  assertFalse(Risen3ToolsData.codes.any { it.command in listOf("minsky", "pommes", "god") || it.command?.startsWith("give ") == true })
 }
 @Test fun shortcutsAreKeyboardActionsWithPrerequisites() {
  assertEquals("Left Ctrl+H",card("r3_debug_hud").command)
  assertEquals("Left Ctrl+Insert",card("r3_debug_free_camera").command)
  assertTrue(Risen3ToolsData.codes.filter { it.command != null }.all { it.body.contains("testmode") && it.body.contains("shortcut") })
  assertFalse(Risen3ToolsData.codes.any { it.command == "Right Alt+F12" })
 }
 @Test fun legendaryBonusesAndChapterRequirementsAreCorrect() {
  assertEquals(30,Risen3ToolsData.codes.count { it.id.startsWith("r3_legendary_") })
  assertTrue(card("r3_legendary_glass_sword").body.contains("Melee +5"))
  assertTrue(card("r3_legendary_glass_eye").body.contains("Ranged +5"))
  assertTrue(card("r3_legendary_sun_crystal").body.contains("Chapter 3"))
  assertTrue(card("r3_legendary_freddies_shackles").body.contains("DLC"))
  assertNull(card("r3_legendary_damaged_cuirass").command)
 }
 @Test fun permanentRecipesUseRisen3AttributesAndIngredients() {
  assertTrue(card("r3_potion_soul_potion").body.contains("Magic"))
  assertTrue(card("r3_potion_soul_potion").body.contains("1 Soul Lichen"))
  assertTrue(card("r3_potion_shadow_elixir").body.contains("Cunning"))
  assertTrue(card("r3_potion_shadow_elixir").body.contains("Marksman"))
  assertEquals(8,Risen3ToolsData.codes.count { it.id.startsWith("r3_potion_") })
 }
 @Test fun weaponPartsAreEquipmentReferencesRatherThanInventedIds() {
  assertEquals(CodeCategory.WEAPONS,card("r3_weapon_sphere_torn").codeCategory)
  assertTrue(card("r3_weapon_sphere_torn").body.contains("Tacarigua"))
  assertTrue(card("r3_weapon_kraken_eye").body.contains("Chapter 3"))
  assertNull(card("r3_weapon_sphere_torn").command)
  assertTrue(Risen3ToolsData.codes.any { it.codeCategory == CodeCategory.ARMOR })
 }
 @Test fun everyReferenceKeepsFullSearchableDetailsAndAUniqueId() {
  assertEquals(Risen3ToolsData.codes.size,Risen3ToolsData.codes.map { it.id }.toSet().size)
  assertTrue(Risen3ToolsData.codes.all { it.body.isNotBlank() && it.source.startsWith("https://") && it.codeCategory != null })
  assertEquals(Risen3ToolsData.codes,combineToolReferences(Risen3ToolsData.codes,Risen3ToolsData.items).map { it.entry })
 }
 @Test fun tipsAndLegacyRoutesStayOffline() {
  assertEquals(Risen3ToolsData.codes,Risen3ToolsData.entries(ToolSection.MARVIN_CODES))
  assertEquals(Risen3ToolsData.tips,Risen3ToolsData.entries(ToolSection.USEFUL_TIPS))
  assertTrue(Risen3ToolsData.items.isEmpty())
  assertTrue(Risen3ToolsData.tips.size >= 15)
  assertTrue(Risen3ToolsData.tips.all { it.command == null })
  assertTrue(Risen3ToolsData.tips.any { it.body.contains("same island") })
 }
}
