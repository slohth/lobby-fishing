package dev.slohth.lobbyfishing.command

import dev.slohth.lobbyfishing.LobbyFishing
import dev.slohth.lobbyfishing.utils.command.Command
import dev.slohth.lobbyfishing.utils.command.CommandArgs
import dev.slohth.lobbyfishing.utils.command.Completer
import dev.slohth.lobbyfishing.utils.command.ICommand
import org.bukkit.event.Listener

class FishingCommand(private val core: LobbyFishing) : ICommand, Listener {

    @Command(name = "fishing.top")
    fun onFishingTopCommand(args: CommandArgs) {

    }

    @Command(name = "fishing.admin", inGameOnly = true)
    fun onFishingAdminCommand(args: CommandArgs) {

    }

    @Completer(name = "fishing")
    fun onFishingComplete(args: CommandArgs): List<String> = listOf("top", "admin")

}