package io.freeze_dolphin.bless_reward

import fr.xephi.authme.api.v3.AuthMeApi
import fr.xephi.authme.events.RegisterEvent
import io.freeze_dolphin.bless_reward.PlugGividado.eman
import io.freeze_dolphin.bless_reward.commands.CommandBus
import io.freeze_dolphin.bless_reward.configs.ConfigBus
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import redempt.redlib.commandmanager.CommandParser
import redempt.redlib.configmanager.ConfigManager
import redempt.redlib.misc.EventListener
import redempt.redlib.misc.FormatUtils.color

object PlugGividado {

    var aman: AuthMeApi     = _
    var plug: Plugin        = _
    var cman: ConfigManager = _
    var eman: Economy       = _

    def getPrefix: String = color( cman.getConfig.getString( "prefix" ) )

}

class PlugGividado extends JavaPlugin {

    override def onEnable(): Unit = {

        PlugGividado.plug = this
        PlugGividado.aman = AuthMeApi.getInstance()

        if (!setupEconomy) {
            getLogger.severe(
                String.format(
                    "[%s] - Disabled due to no Vault dependency found!",
                    getDescription.getName
                )
            )
            getServer.getPluginManager.disablePlugin( this )
            return
        }

        PlugGividado.cman = new ConfigManager( this ).register( new ConfigBus ).saveDefaults.load
        new CommandParser( getResource( "command.rdcml" ) ).parse
            .register( getDescription.getName, new CommandBus )

        getLogger.info( "Loaded successfully!" )

        new EventListener[RegisterEvent](
            this,
            classOf[RegisterEvent],
            ( evt: RegisterEvent ) => {
                if (
                    !getServer.dispatchCommand(
                        Bukkit.getConsoleSender,
                        "blessrwd welcome " + evt.getPlayer.getName
                    )
                ) getLogger.warning( "Unable to execute welcome event!" )
            }
        )

    }

    import net.milkbowl.vault.economy.Economy

    private def setupEconomy: Boolean = {
        if (getServer.getPluginManager.getPlugin( "Vault" ) == null) return false
        val rsp = getServer.getServicesManager.getRegistration( classOf[Economy] )
        if (rsp == null) return false
        eman = rsp.getProvider
        eman != null
    }

}
