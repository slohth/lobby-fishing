package dev.slohth.lobbyfishing.region.manager

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.region.Region
import java.util.*

class RegionManager(private val core: LobbyFishing) {

    val regions: MutableMap<UUID, Region> = HashMap()

    fun new(name: String): Boolean {
        if (getFromName(name) != null) return false

        return true
    }

    fun getFromName(name: String): Region? {
        for (region in regions.values) if (region.name.lowercase() == name.lowercase()) return region
        return null
    }

}