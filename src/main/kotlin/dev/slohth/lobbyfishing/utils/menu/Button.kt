package dev.slohth.lobbyfishing.utils.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Button {

    var icon: ItemStack? = null
        private set

    abstract fun clicked(player: Player)

    fun setIcon(icon: ItemStack?): Button {
        this.icon = icon
        return this
    }

}