package dev.slohth.lobbyfishing.region.manager

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.region.Region
import java.util.*

class RegionManager(private val core: LobbyFishing) {

    val regions: MutableMap<UUID, Region> = HashMap()

    fun new(name: String): Boolean {
        val region = Region(name)
        if (fromName(name) != null) return false
        if (fromID(region.id) != null) return false
        regions[region.id] = region
        return true
    }

    fun fromName(name: String): Region? {
        for (region in regions.values) if (region.name.lowercase() == name.lowercase()) return region
        return null
    }

    fun fromID(id: UUID): Region? {
        for (region in regions.values) if (region.id == id) return region
        return null
    }

}