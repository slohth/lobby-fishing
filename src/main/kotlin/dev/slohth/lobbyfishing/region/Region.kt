package dev.slohth.lobbyfishing.region

import dev.slohth.lobbyfishing.region.icon.RegionIcon
import dev.slohth.lobbyfishing.utils.Config
import dev.slohth.lobbyfishing.utils.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.BoundingBox
import java.io.IOException
import java.util.*

class Region(var name: String) {

    val id = UUID.randomUUID()

    val subregions: MutableList<BoundingBox> = ArrayList()
    val exclusions: MutableList<BoundingBox> = ArrayList()

    var world = Bukkit.getWorlds()[0]

    fun add(world: World, box: BoundingBox) {
        if (subregions.isEmpty()) this.world = world
        if (this.world.name != world.name) return
        subregions.add(box)
    }

    fun remove(box: BoundingBox) = exclusions.add(box)

    fun contains(player: Player): Boolean {
        for (e in exclusions) if (e.contains(player.location.toVector())) return false
        for (r in subregions) if (r.contains(player.location.toVector())) return true
        return false
    }

    fun remove() {
        subregions.clear()
        exclusions.clear()
    }

    fun getIcon(): ItemStack {
        val builder = ItemBuilder(Config.MENUS.getConfig()
            .getString("region-editor.main-menu.items.region-item.item")!!)

        builder.type(Material.PLAYER_HEAD).texture(
            when (world.environment) {
                World.Environment.NETHER -> RegionIcon.NETHER
                World.Environment.NORMAL -> RegionIcon.OVERWORLD
                World.Environment.THE_END -> RegionIcon.END
                else -> throw IOException("Invalid world type detected!")
            }.texture
        ).name {
            s -> s.replace("%region_id%", id.toString().substring(0, 6))
                .replace("%region_name%", name)
        }.lore {
            s -> s.replace("%region_id%", id.toString().substring(0, 6))
                .replace("%region_name%", name)
        }.localisedName(id.toString())

        return builder.build()
    }

    fun openEditMenu(player: Player) {

    }

}