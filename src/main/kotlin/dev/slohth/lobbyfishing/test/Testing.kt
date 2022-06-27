package dev.slohth.lobbyfishing.test

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.utils.command.Command
import dev.slohth.lobbyfishing.utils.command.CommandArgs
import dev.slohth.lobbyfishing.utils.command.ICommand
import dev.slohth.lobbyfishing.utils.item.ItemBuilder
import dev.slohth.lobbyfishing.utils.list.ScrollableList
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.util.BoundingBox
import java.util.*

class Testing(private val core: LobbyFishing) : ICommand, Listener {

    val list: ScrollableList<String> = ScrollableList()

    init {
        list.linkedList.add("1")
        list.linkedList.add("2")
        list.linkedList.add("3")
        list.linkedList.add("4")
        list.linkedList.add("5")
        list.linkedList.add("6")
        list.linkedList.add("7")
        list.linkedList.add("8")
        list.linkedList.add("9")
        list.linkedList.add("10")
        list.linkedList.add("11")
        list.linkedList.add("12")
        list.linkedList.add("13")
        list.linkedList.add("14")
        list.linkedList.add("15")

        for (i in 0..50) {
            core.regionManager.new("Region #$i")
            core.regionManager.fromName("Region #$i")!!.add(
                Bukkit.getWorld("world")!!, BoundingBox.of(
                    Location(Bukkit.getWorld("world")!!, 0.0, 0.0, 0.0),
                    Location(Bukkit.getWorld("world")!!, 0.0, 0.0, 0.0)
                ))
        }
    }

    @Command(name = "scroll")
    fun onScrollCommand(args: CommandArgs) {
        var linkedList: LinkedList<String> = LinkedList()
        if (args.length() == 2) {
            linkedList = list.getContents(args.getArgs(0).toInt(), args.getArgs(1).toInt())
        } else if (args.length() == 3) {
            linkedList = list.getContents(args.getArgs(0).toInt(), args.getArgs(1).toInt(), args.getArgs(2).toInt())
        }
        args.player.sendMessage(StringUtils.join(linkedList, ", "))
    }

    @Command(name = "region")
    fun onRegionCommand(args: CommandArgs) {
        core.regionManager.openMainMenu(args.player, 0)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {

        val item = ItemBuilder(Material.CRIMSON_PLANKS)
            .name("&cRedwood")
            .lore("&cThe best planks for %player%")
            .lore { line -> line.replace("%player%", e.player.name) }
            .enchant(Enchantment.DAMAGE_ALL, 10)

        e.player.inventory.addItem(item.build())
        e.player.sendMessage(item.serialize())
    }

}