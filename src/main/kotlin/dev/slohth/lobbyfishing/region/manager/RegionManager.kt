package dev.slohth.lobbyfishing.region.manager

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.region.Region
import dev.slohth.lobbyfishing.utils.Config
import dev.slohth.lobbyfishing.utils.item.ItemBuilder
import dev.slohth.lobbyfishing.utils.list.ScrollableList
import dev.slohth.lobbyfishing.utils.menu.Button
import dev.slohth.lobbyfishing.utils.menu.Menu
import dev.slohth.lobbyfishing.utils.menu.MenuPattern
import org.bukkit.entity.Player
import java.util.*

class RegionManager(private val core: LobbyFishing) {

    val regions: ScrollableList<Region> = ScrollableList()

    fun new(name: String): Region? {
        val region = Region(name)
        if (fromName(name) != null) return null
        if (fromID(region.id) != null) return null
        regions.linkedList.add(region)
        return region
    }

    fun fromName(name: String): Region? {
        for (region in regions.linkedList) if (region.name.lowercase() == name.lowercase()) return region
        return null
    }

    fun fromID(id: UUID): Region? {
        for (region in regions.linkedList) if (region.id == id) return region
        return null
    }

    fun openMainMenu(player: Player, scroll: Int) {
        val c = Config.MENUS
        val path = "region-editor.main-menu"

        val indexes: LinkedList<Int> = LinkedList()
        val rKey = c.getString("$path.items.region-item.key")!![0]
        var scrollSize = 0

        var index = 0
        for (item in c.getStringList("$path.pattern")) {
            if (scrollSize == 0) {
                for (char in item.toCharArray()) if (char == rKey) scrollSize++
            }
            for (char in item.toCharArray()) {
                if (char == rKey) indexes.add(index)
                index++
            }
        }

        val menu = object: Menu(core.plugin, c.getString("$path.name")!!, c.getStringList("$path.pattern").size) {
            override fun onClosed(player: Player) {}
        }
        val pattern = MenuPattern(c.getStringList("$path.pattern"))

        for (item in c.getConfig().getConfigurationSection("$path.items")!!.getKeys(false)) {
            when (item) {
                "scroll-up" -> {
                    val available = scroll != 0
                    val ib = ItemBuilder(
                        c.getString("$path.items.scroll-up.item-" + (if (available) "" else "un") + "available")!!
                    )
                    pattern[c.getString("$path.items.scroll-up.key")!![0]] = object: Button() {
                        override fun clicked(player: Player) {
                            if (available) {
                                menu.close()
                                openMainMenu(player, scroll - 1)
                            }
                        }
                    }.setIcon(ib.build())
                }
                "scroll-down" -> {
                    val available = regions.hasNextScroll(scroll, scrollSize, indexes.size)
                    val ib = ItemBuilder(
                        c.getString("$path.items.scroll-down.item-" + (if (available) "" else "un") + "available")!!
                    )
                    pattern[c.getString("$path.items.scroll-down.key")!![0]] = object: Button() {
                        override fun clicked(player: Player) {
                            if (available) {
                                menu.close()
                                openMainMenu(player, scroll + 1)
                            }
                        }
                    }.setIcon(ib.build())
                }
                "new-region" -> {
                    val ib = ItemBuilder(c.getString("$path.items.new-region.item")!!)
                    pattern[c.getString("$path.items.new-region.key")!![0]] = object: Button() {
                        override fun clicked(player: Player) {
                            TODO("Not yet implemented")
                        }
                    }.setIcon(ib.build())
                }
                "region-item" -> {}
                else -> {
                    val ib = ItemBuilder(c.getString("$path.items.$item")!!)
                    pattern[item[0]] = ib.build()
                }
            }
        }

        menu.applyMenuPattern(pattern)

        val s = regions.getContents(scroll, scrollSize, indexes.size);
        for (i in 0 until s.size) {
            val r = s.getOrNull(i)
            menu.setButton(indexes[i],
                if (r == null) {
                    object: Button() { override fun clicked(player: Player) {} }
                } else {
                    object: Button() {
                        override fun clicked(player: Player) {
                            TODO("Not yet implemented")
                        }
                    }.setIcon(r.getIcon())
                }
            )
        }

        menu.build()
        menu.open(player)
    }

}