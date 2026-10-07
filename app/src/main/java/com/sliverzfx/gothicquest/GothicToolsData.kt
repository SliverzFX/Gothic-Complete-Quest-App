package com.sliverzfx.gothicquest

internal enum class CodeCategory(val title: String) {
    GENERAL("General Cheats"), CHARACTERS("Characters & Creatures"),
    ITEMS("Items"), WEAPONS("Weapons"), ARMOR("Armor")
}

internal data class ToolReferenceEntry(
    val id: String, val group: String, val title: String, val body: String,
    val command: String? = null, val source: String,
    val codeCategory: CodeCategory? = if (command == null) null else
        if (group == "ITEM INSERTS") CodeCategory.ITEMS else CodeCategory.GENERAL
)

/** Original Gothic PC reference. Descriptions are independently written summaries. */
internal object GothicToolsData {
    private const val cheats = "https://www.worldofgothic.com/gothic/?go=g1cheats"
    private const val faq = "https://www.worldofgothic.com/gothic/?go=g1faq"
    private const val weapons = "https://www.worldofgothic.com/gothic/?go=g1waffen"
    private const val scrolls = "https://www.worldofgothic.com/gothic/?go=g1spruchrollen"
    private const val inserts = "https://www.gothicz.net/marvin/g1/insert-kody/predmety/magicke-svitky/"

    private const val console = "https://gamefaqs.gamespot.com/pc/913888-gothic/cheats"
    private const val npcs = "https://www.gothicz.net/marvin/g1/insert-kody/npc/"
    private const val creatures = "https://www.gothicz.net/marvin/g1/insert-kody/bestie/"
    private const val armorCodes = "https://www.gothicz.net/marvin/g1/insert-kody/predmety/zbroje/"

    val codes = listOf(
        ToolReferenceEntry("enable", "SETUP", "Enable Marvin mode", "Open your character/status screen, type marvin, then close it. Use your configured status-screen key; bindings vary between releases.", "marvin", cheats),
        ToolReferenceEntry("console", "SHORTCUTS", "Open the console", "With Marvin active, press F2. Type a console command and press Enter.", "F2", cheats),
        ToolReferenceEntry("disable", "SETUP", "Disable Marvin mode", "Open the status screen, type 42, then close it. This ends debug mode; it does not undo items or other changes already made.", "42", cheats),
        ToolReferenceEntry("restore", "SHORTCUTS", "Restore health and mana", "Press F8 while Marvin is active to replenish health and mana. This is a debug shortcut, not normal healing.", "F8", cheats),
        ToolReferenceEntry("camera", "SHORTCUTS", "Free camera", "Press F6 in Marvin mode for free camera. Press F4 to return to the normal camera.", "F6", cheats),
        ToolReferenceEntry("light_insert", "ITEM INSERTS", "Spawn a Light scroll", "Enter this in the F2 console. The scroll is spawned into the world; pick it up.", "insert itarscrolllight", inserts),
        ToolReferenceEntry("heal_insert", "ITEM INSERTS", "Spawn a Healing scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrollheal", inserts),
        ToolReferenceEntry("fire_insert", "ITEM INSERTS", "Spawn a Fireball scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrollfireball", inserts),
        ToolReferenceEntry("telekinesis_insert", "ITEM INSERTS", "Spawn a Telekinesis scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrolltelekinesis", inserts),
        ToolReferenceEntry("god", "CONSOLE COMMANDS", "God mode", "Enter in the F2 console to enable invulnerability. Enter again to toggle it off.", "cheat god", console),
        ToolReferenceEntry("full", "CONSOLE COMMANDS", "Restore health", "Enter in the F2 console to restore your character's health.", "cheat full", console),
        ToolReferenceEntry("ore", "STATUS-SCREEN CHEATS", "Add 1,000 ore", "Open the status screen, type marin, then close it. This older status-screen cheat can depend on your game version.", "marin", cheats),
        ToolReferenceEntry("version", "CONSOLE COMMANDS", "Show game version", "Enter in the F2 console to display the game's version.", "version", console),
        ToolReferenceEntry("diego", "NPC INSERTS", "Spawn Diego", "Inserts a copy of Diego. This does not move the existing character or reliably repair his quests.", "insert pc_thief", npcs, CodeCategory.CHARACTERS),
        ToolReferenceEntry("gorn", "NPC INSERTS", "Spawn Gorn", "Inserts a copy of Gorn. Use a separate save when experimenting with story characters.", "insert pc_fighter", npcs, CodeCategory.CHARACTERS),
        ToolReferenceEntry("scavenger", "CREATURE INSERTS", "Spawn a Scavenger", "Creates a Scavenger nearby. Spawned creatures can attack.", "insert scavenger", creatures, CodeCategory.CHARACTERS),
        ToolReferenceEntry("molerat", "CREATURE INSERTS", "Spawn a Molerat", "Creates a Molerat nearby. Spawned creatures can attack.", "insert molerat", creatures, CodeCategory.CHARACTERS),
        ToolReferenceEntry("rusty_sword_insert", "WEAPON INSERTS", "Spawn a Rusty Sword", "Creates a Rusty Sword to pick up. You still need to meet its requirements to equip it.", "insert ItMw_1H_Sword_Old_01", console, CodeCategory.WEAPONS),
        ToolReferenceEntry("short_sword_insert", "WEAPON INSERTS", "Spawn a Short Sword", "Creates a Short Sword to pick up and equip if you meet its requirements.", "insert ItMw_1H_Sword_Short_01", console, CodeCategory.WEAPONS),
        ToolReferenceEntry("battle_staff_insert", "WEAPON INSERTS", "Spawn a Battle Staff", "Creates a two-handed Battle Staff to pick up. Inserting it does not grant weapon training.", "insert ItMw_2H_Staff_01", console, CodeCategory.WEAPONS),
        ToolReferenceEntry("digger_pants_insert", "ARMOR INSERTS", "Spawn Digger's Trousers", "Creates the digger clothing item to pick up and equip.", "insert vlk_armor_m", armorCodes, CodeCategory.ARMOR),
        ToolReferenceEntry("shadow_armor_insert", "ARMOR INSERTS", "Spawn Shadow's Armor", "Creates Shadow's Armor. Inserting faction armor does not make you a member of that faction.", "insert STT_ARMOR_H", console, CodeCategory.ARMOR)

    )
    val tips = listOf(
        ToolReferenceEntry("save", "STARTING OUT", "Keep more than one save", "You can save freely. Keep an earlier slot before difficult fights or major decisions so you can return if needed.", source = faq),
        ToolReferenceEntry("training", "CHARACTER", "Spend learning points with trainers", "Levelling grants learning points. Find a trainer to turn them into stronger attributes or skills; training may also cost ore.", source = faq),
        ToolReferenceEntry("danger", "EXPLORATION", "Leave dangerous areas for later", "Early on, avoid the orc territory and deep forests. Return after improving your character and equipment.", source = faq),
        ToolReferenceEntry("journal", "QUESTS", "Check the quest journal", "The journal records active and resolved quests. Read it after conversations when you are unsure what to do next.", source = faq),
        ToolReferenceEntry("knockout", "COMBAT", "Knocking someone out is not killing them", "Human enemies defeated in melee can survive unconscious. A finishing blow kills them and can provoke witnesses.", source = faq),
        ToolReferenceEntry("golems", "ENEMY WEAKNESSES", "Prepare for Xardas's golems", "Bring a blunt weapon for the stone golem, fire magic for the ice golem, and ice or lightning magic for the fire golem.", source = faq)
    )
    val items = listOf(
        ToolReferenceEntry("rusty_sword", "ONE-HANDED WEAPONS", "Rusty Sword", "10 damage • 5 strength required. A basic weapon for a weak character; replace it as your strength improves.", source = weapons),
        ToolReferenceEntry("short_sword", "ONE-HANDED WEAPONS", "Short Sword", "12 damage • 6 strength required. A modest step above a rusty sword.", source = weapons),
        ToolReferenceEntry("mace", "ONE-HANDED WEAPONS", "Mace", "23 damage • 10 strength required. A blunt weapon worth keeping for enemies that resist blades.", source = weapons),
        ToolReferenceEntry("longsword", "ONE-HANDED WEAPONS", "Longsword", "40 damage • 17 strength required. Check its requirement before equipping it.", source = weapons),
        ToolReferenceEntry("short_bow", "RANGED WEAPONS", "Short Bow", "20 damage • 10 dexterity required. An entry-level bow; ranged accuracy also depends on training.", source = weapons),
        ToolReferenceEntry("light", "SPELL SCROLLS", "Light scroll", "Costs 1 mana. Produces light for dark areas.", source = scrolls),
        ToolReferenceEntry("firebolt", "SPELL SCROLLS", "Firebolt scroll", "Costs 1 mana • 30 fire damage. A small fire spell.", source = scrolls),
        ToolReferenceEntry("telekinesis", "SPELL SCROLLS", "Telekinesis scroll", "A sustained spell with a listed mana cost of 10. Moves a targeted object from a distance.", source = scrolls)
    )

    fun entries(section: ToolSection): List<ToolReferenceEntry> = when (section) {
        ToolSection.MARVIN_CODES -> codes
        ToolSection.USEFUL_TIPS -> tips
        ToolSection.ITEMS -> items
    }
}
