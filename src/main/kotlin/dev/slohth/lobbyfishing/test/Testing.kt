package dev.slohth.lobbyfishing.test

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.utils.command.Command
import dev.slohth.lobbyfishing.utils.command.CommandArgs
import dev.slohth.lobbyfishing.utils.command.ICommand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Listener
import org.bukkit.util.BoundingBox
import java.util.concurrent.ThreadLocalRandom

class Testing(private val core: LobbyFishing) : ICommand, Listener {


    init {

        for (i in 0..50) {

            val r = ThreadLocalRandom.current().nextInt(4)
            val world = Bukkit.getWorld(
                when (r) {
                    1 -> { "world" }
                    2 -> { "world_nether" }
                    3 -> { "world_the_end" }
                    else -> { "world" }
                }
            )

            val region = core.regionManager.new("Region #$i")!!
            region.add(
                world!!, BoundingBox.of(
                    Location(world, 0.0, 0.0, 0.0),
                    Location(world, 0.0, 0.0, 0.0)
                ))
        }
    }

    @Command(name = "region")
    fun onRegionCommand(args: CommandArgs) {
        core.regionManager.openMainMenu(args.player, 0)
    }

}