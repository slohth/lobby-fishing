package dev.slohth.lobbyfishing.plugin

import dev.slohth.lobbyfishing.LobbyFishing
import org.bukkit.plugin.java.JavaPlugin

class LobbyFishingPlugin : JavaPlugin() {

    private lateinit var core: LobbyFishing

    override fun onEnable() {
        core = LobbyFishing(this)
    }

}