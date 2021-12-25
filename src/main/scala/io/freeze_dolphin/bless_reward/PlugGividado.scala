package io.freeze_dolphin.bless_reward

import io.freeze_dolphin.bless_reward.commands.CommandBus
import io.freeze_dolphin.bless_reward.configs.ConfigBus
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import redempt.redlib.commandmanager.CommandParser
import redempt.redlib.configmanager.ConfigManager
import redempt.redlib.misc.EventListener
import redempt.redlib.misc.FormatUtils.color

object PlugGividado {

    var plug: Plugin        = _
    var cman: ConfigManager = _

    def getPrefix: String = color( cman.getConfig.getString( "prefix" ) )

}

class PlugGividado extends JavaPlugin {

    override def onEnable(): Unit = {

        PlugGividado.plug = this

        PlugGividado.cman = new ConfigManager( this ).register( new ConfigBus ).saveDefaults.load
        new CommandParser( getResource( "command.rdcml" ) ).parse
            .register( getDescription.getName, new CommandBus )

        getLogger.info( "Loaded successfully!" )

        new EventListener[PlayerJoinEvent](
            this,
            classOf[PlayerJoinEvent],
            ( evt: PlayerJoinEvent ) => {

                if (!evt.getPlayer.hasPlayedBefore) {
                    if (
                        !getServer.dispatchCommand(
                            Bukkit.getConsoleSender,
                            "blessrwd welcome " + evt.getPlayer.getName
                        )
                    ) getLogger.warning( "Unable to execute welcome event!" )
                }
            }
        )

    }

}
