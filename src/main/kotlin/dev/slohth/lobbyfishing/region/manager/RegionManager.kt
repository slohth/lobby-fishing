package dev.slohth.lobbyfishing.region.manager

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.region.Region
import dev.slohth.lobbyfishing.utils.Config
import dev.slohth.lobbyfishing.utils.list.ScrollableList
import org.bukkit.entity.Player
import java.util.*

class RegionManager(private val core: LobbyFishing) {

    val regions: ScrollableList<Region> = ScrollableList()

    fun new(name: String): Boolean {
        val region = Region(name)
        if (fromName(name) != null) return false
        if (fromID(region.id) != null) return false
        regions.linkedList.add(region)
        return true
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

        val rKey = c.getString("$path.items.region-item.key")!![0]

        for (item in c.getConfig().getConfigurationSection("$path.items")!!.getKeys(false)) {
            when (item) {

            }
        }
    }

}