package dev.slohth.lobbyfishing.region

import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox

class Region {

    val subregions: MutableList<BoundingBox> = ArrayList()
    val exclusions: MutableList<BoundingBox> = ArrayList()

    fun add(box: BoundingBox) = subregions.add(box)

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

}