package com.sliverzfx.gothicquest
import org.junit.Assert.*
import org.junit.Test
class CombinedToolReferencesTest {
    private fun code(id: String, title: String, command: String, body: String = "Spawn instructions", category: CodeCategory = CodeCategory.WEAPONS) =
        ToolReferenceEntry(id, "WEAPONS", title, body, command, "https://codes.example", category)
    private fun item(id: String, title: String, body: String) =
        ToolReferenceEntry(id, "WEAPONS", title, body, source = "https://items.example", codeCategory = null)

    @Test fun matchingItemAddsStatsWithoutDuplicateCard() {
        val a = combineToolReferences(listOf(code("g1_bow", "Spawn Bow", "insert BOW")), listOf(item("g1_item_bow", "Bow", "100 damage")))
        assertEquals(1, a.size)
        assertEquals("Bow", a.single().entry.title)
        assertEquals("insert BOW", a.single().entry.command)
        assertTrue(a.single().entry.body.contains("100 damage"))
        assertTrue(a.single().entry.body.contains("Spawn instructions"))
        assertEquals(setOf("https://codes.example", "https://items.example"), a.single().sources)
    }
    @Test fun sameDescriptionIsNotRepeated() {
        val a = combineToolReferences(listOf(code("a", "Spawn Bow", "insert bow", "100 damage. Spawns a copy.")), listOf(item("g1_item_bow", "Bow", "100 damage.")))
        assertEquals(1, Regex("100 damage").findAll(a.single().entry.body).count())
    }
    @Test fun ambiguousNamesNeverMergeDifferentItems() {
        val a = combineToolReferences(listOf(code("a", "Spawn Ring", "insert ring_a"), code("b", "Spawn Ring", "insert ring_b")), listOf(item("location_ring", "Ring", "Location reference")))
        assertEquals(3, a.size)
        assertTrue(a.any { it.entry.id == "location_ring" && it.entry.command == null })
    }
    @Test fun exactIdsWinOverAmbiguousNames() {
        val a = combineToolReferences(listOf(code("a", "Spawn Ring", "insert ring_a"), code("b", "Spawn Ring", "insert ring_b")), listOf(item("g2_item_ring_b", "Ring", "+10 mana")))
        assertEquals(2, a.size)
        assertFalse(a.first().entry.body.contains("+10 mana"))
        assertTrue(a.last().entry.body.contains("+10 mana"))
    }
    @Test fun standaloneDetailsStaySearchableAndClassified() {
        val a = combineToolReferences(emptyList(), listOf(item("location_book", "Book location", "Under a bridge")))
        assertEquals(CodeCategory.WEAPONS, a.single().entry.codeCategory)
        assertTrue(a.single().entry.body.contains("bridge"))
        assertNull(a.single().entry.command)
    }
    @Test fun characterWithSameNameDoesNotAbsorbEquipment() {
        val a = combineToolReferences(listOf(code("npc", "Sword", "spawn Sword", category = CodeCategory.CHARACTERS)), listOf(item("location_sword", "Sword", "Weapon stats")))
        assertEquals(2, a.size)
    }
    @Test fun legacyStatsMergeByExactGothic3Id() {
        val a = combineToolReferences(listOf(code("a", "Paladin armor", "give It_Armor_Paladin", category = CodeCategory.ARMOR)), listOf(item("g3_legacy_It_Armor_Paladin", "Paladin armor — historical stats", "Protection: 100")))
        assertEquals(1, a.size)
        assertEquals("Paladin armor", a.single().entry.title)
        assertTrue(a.single().entry.body.contains("Protection: 100"))
    }
    @Test fun sourcesAndDescriptionsSurviveMultipleSupplements() {
        val a = combineToolReferences(listOf(code("a", "Bow", "give bow")), listOf(item("g3_item_bow", "Bow", "Base description"), item("g3_stats_bow", "Bow — equipment stats", "CP 1.70 damage: 45 (40)")))
        assertEquals(1, a.size)
        assertTrue(a.single().entry.body.contains("Base description"))
        assertTrue(a.single().entry.body.contains("CP 1.70"))
    }
    @Test fun caseVariantsReceiveTheirOwnExactItemDescriptions() {
        val a = combineToolReferences(
            listOf(code("upper", "Ring", "give It_Ring_LIGHTNING"), code("lower", "Ring", "give It_Ring_Lightning")),
            listOf(item("g3_item_It_Ring_LIGHTNING", "Ring", "Upper-case export"), item("g3_item_It_Ring_Lightning", "Ring", "Mixed-case export")))
        assertEquals(2, a.size)
        assertTrue(a.first().entry.body.contains("Upper-case export"))
        assertFalse(a.first().entry.body.contains("Mixed-case export"))
        assertTrue(a.last().entry.body.contains("Mixed-case export"))
    }
    @Test fun repeatedInventoryCommandStillAbsorbsItsItemReference() {
        val a = combineToolReferences(listOf(code("a", "Bow", "insert bow"), code("b", "Bow", "insert bow")), listOf(item("g1_item_bow", "Bow", "Bow statistics")))
        assertEquals(2, a.size)
        assertTrue(a.any { it.entry.body.contains("Bow statistics") })
    }

}
