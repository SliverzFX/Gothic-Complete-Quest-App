package com.sliverzfx.gothicquest

internal data class ToolReferenceEntry(
    val id: String, val group: String, val title: String, val body: String,
    val command: String? = null, val source: String
)

/** Original Gothic PC reference. Descriptions are independently written summaries. */
internal object GothicToolsData {
    private const val cheats = "https://www.worldofgothic.com/gothic/?go=g1cheats"
    private const val faq = "https://www.worldofgothic.com/gothic/?go=g1faq"
    private const val weapons = "https://www.worldofgothic.com/gothic/?go=g1waffen"
    private const val scrolls = "https://www.worldofgothic.com/gothic/?go=g1spruchrollen"
    private const val inserts = "https://www.gothicz.net/marvin/g1/insert-kody/predmety/magicke-svitky/"

    val codes = listOf(
        ToolReferenceEntry("enable", "SETUP", "Enable Marvin mode", "Open your character/status screen, type marvin, then close it. Use your configured status-screen key; bindings vary between releases.", "marvin", cheats),
        ToolReferenceEntry("console", "SHORTCUTS", "Open the console", "With Marvin active, press F2. Type a console command and press Enter.", "F2", cheats),
        ToolReferenceEntry("disable", "SETUP", "Disable Marvin mode", "Open the status screen, type 42, then close it. This ends debug mode; it does not undo items or other changes already made.", "42", cheats),
        ToolReferenceEntry("restore", "SHORTCUTS", "Restore health and mana", "Press F8 while Marvin is active to replenish health and mana. This is a debug shortcut, not normal healing.", "F8", cheats),
        ToolReferenceEntry("camera", "SHORTCUTS", "Free camera", "Press F6 in Marvin mode for free camera. Press F4 to return to the normal camera.", "F6", cheats),
        ToolReferenceEntry("light_insert", "ITEM INSERTS", "Spawn a Light scroll", "Enter this in the F2 console. The scroll is spawned into the world; pick it up.", "insert itarscrolllight", inserts),
        ToolReferenceEntry("heal_insert", "ITEM INSERTS", "Spawn a Healing scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrollheal", inserts),
        ToolReferenceEntry("fire_insert", "ITEM INSERTS", "Spawn a Fireball scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrollfireball", inserts),
        ToolReferenceEntry("telekinesis_insert", "ITEM INSERTS", "Spawn a Telekinesis scroll", "Enter this in the F2 console, then pick up the spawned scroll.", "insert itarscrolltelekinesis", inserts)
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
