package dev.slohth.lobbyfishing.test

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.utils.command.ICommand
import dev.slohth.lobbyfishing.utils.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class Testing(private val core: LobbyFishing) : ICommand, Listener {

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