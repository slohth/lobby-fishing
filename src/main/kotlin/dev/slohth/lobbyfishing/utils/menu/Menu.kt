package dev.slohth.lobbyfishing.utils.menu

import dev.slohth.lobbyfishing.utils.CC.color
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

abstract class Menu(private val plugin: JavaPlugin, var title: String, var rows: Int) : Listener {

    private val items: MutableMap<Int, ItemStack> = HashMap()
    private val buttons: MutableMap<Int, Button> = HashMap()

    private var inventory: Inventory = Bukkit.createInventory(null, rows * 9, color(title))

    init {
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    fun applyMenuPattern(pattern: MenuPattern) {
        items.putAll(pattern.getItems())
        buttons.putAll(pattern.getButtons())
    }

    fun firstEmpty(): Int {
        for (i in 0 until rows * 9) {
            if (items.containsKey(i) || buttons.containsKey(i)) continue
            return i
        }
        return rows * 9
    }

    fun setInventoryType(type: InventoryType?) {
        inventory = Bukkit.createInventory(null, type!!, title)
    }

    fun setItem(slot: Int, item: ItemStack) { items[slot] = item }

    fun getItem(slot: Int): ItemStack? = inventory.getItem(slot)

    fun setButton(slot: Int, button: Button) { buttons[slot] = button }

    fun build(): Menu {
        if (items.isNotEmpty()) for (i in items.keys) inventory.setItem(i, items[i])
        if (buttons.isNotEmpty()) for (i in buttons.keys) inventory.setItem(i, buttons[i]!!.icon)
        return this
    }

    fun open(player: Player) = player.openInventory(inventory)

    fun update(player: Player) {
        player.closeInventory()
        open(player)
    }

    fun updateAll() {
        for (e in inventory.viewers) {
            e.closeInventory()
            open(e as Player)
        }
    }

    fun close() {
        InventoryCloseEvent.getHandlerList().unregister(this)
        items.clear()
        buttons.clear()
        Bukkit.getScheduler()
            .runTaskLater(plugin, Runnable { for (e in ArrayList(inventory.viewers)) e.closeInventory() }, 1)
    }

}