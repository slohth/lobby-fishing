package dev.slohth.lobbyfishing

import dev.slohth.lobbyfishing.test.Testing
import dev.slohth.lobbyfishing.utils.command.Framework
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class LobbyFishing(val plugin: JavaPlugin) {

    val framework = Framework(plugin)

    init {
        val testing = Testing(this)
        framework.registerCommands(testing)
        Bukkit.getPluginManager().registerEvents(testing, plugin)
    }

}