package dev.slohth.lobbyfishing.utils.menu

import org.bukkit.inventory.ItemStack
import java.util.*

class MenuPattern {

    private val icons = LinkedList<Char>()
    private val items: MutableMap<Int, ItemStack> = HashMap()
    private val buttons: MutableMap<Int, Button> = HashMap()

    constructor(rows: Array<CharArray>) { this.setPattern(rows) }

    constructor(rows: Array<String>) { this.setPattern(rows) }

    constructor(rows: List<String>) { this.setPattern(rows) }

    private fun setPattern(rows: Array<CharArray>) { for (row in rows) for (c in row) icons.add(c) }

    private fun setPattern(rows: Array<String>) { for (row in rows) for (c in row.toCharArray()) icons.add(c) }

    private fun setPattern(rows: List<String>) { for (row in rows) for (c in row.toCharArray()) icons.add(c) }

    operator fun set(c: Char, item: ItemStack) {
        for ((index, icon) in icons.withIndex()) if (icon == c) items[index] = item
    }

    operator fun set(c: Char, button: Button) {
        for ((index, icon) in icons.withIndex()) if (icon == c) buttons[index] = button
    }

    fun getCountOf(icon: Char): Int {
        var amount: Int = 0
        for (c: Char in icons) if (c == icon) amount++
        return amount
    }

    fun getItems(): Map<Int, ItemStack> = items

    fun getButtons(): Map<Int, Button> = buttons

    fun clear() {
        icons.clear()
        items.clear()
        buttons.clear()
    }

}