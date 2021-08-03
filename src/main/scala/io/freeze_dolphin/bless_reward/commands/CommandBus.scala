package io.freeze_dolphin.bless_reward.commands

import io.freeze_dolphin.bless_reward.PlugGividado
import io.freeze_dolphin.bless_reward.miscs.BlessEvent
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import redempt.redlib.commandmanager.CommandHook
import redempt.redlib.misc.FormatUtils.color

import java.io.File

class CommandBus {

    @CommandHook( "event" )
    def event( sender: CommandSender, id: String ): Unit = {
        val storage = new File(
            PlugGividado.plug.getDataFolder.getPath + File.separator + "storage"
        )
        if (!storage.exists()) storage.mkdirs()

        val f = new File( storage.getPath + File.separator + s"$id.yml" )
        if (f.exists()) {
            val yml      = YamlConfiguration.loadConfiguration( f )
            val title    = yml.getString( "title" )
            val subtitle = yml.getString( "subtitle" )
            val keyword  = yml.getString( "keyword" )
            val reward   = yml.getString( "reward" )
            val duration = yml.getInt( "duration" )
            val msg      = yml.getString( "message" )
            val be = new BlessEvent(
                PlugGividado.plug,
                id,
                sender match {
                    case plr: Player => plr.getUniqueId
                    case _           => null
                },
                title,
                subtitle,
                duration,
                keyword,
                reward,
                msg
            )
            be.start()
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString( "msg_event_start" )
            )
        } else {
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString(
                    "msg_event_not_exist"
                )
            )
        }
    }

    @CommandHook( "welcome" )
    def welcome( sender: CommandSender, name: String ): Unit = {
        val id = "welcome"

        var plr: Player = null
        try {
            plr = Bukkit.getPlayer( name )
            if (plr == null) throw new IllegalArgumentException()
        } catch {
            case _: Exception =>
                sender.sendMessage(
                    PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString(
                        "msg_player_not_exist"
                    )
                )
                return
        }

        val storage = new File(
            PlugGividado.plug.getDataFolder.getPath + File.separator + "storage"
        )
        if (!storage.exists()) storage.mkdirs()

        val f = new File( storage.getPath + File.separator + s"$id.yml" )
        if (f.exists()) {
            val yml      = YamlConfiguration.loadConfiguration( f )
            val title    = yml.getString( "title" )
            val subtitle = yml.getString( "subtitle" )
            val keyword  = yml.getString( "keyword" )
            val reward   = yml.getString( "reward" )
            val duration = yml.getInt( "duration" )
            val msg      = yml.getString( "message" )
            val be = new BlessEvent(
                PlugGividado.plug,
                id,
                plr.getUniqueId,
                title,
                subtitle,
                duration,
                keyword,
                reward,
                msg
            )
            be.start()
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString( "msg_event_start" )
            )
        } else {
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString(
                    "msg_event_not_exist"
                )
            )
        }
    }

    @CommandHook( "create" )
    def create(
        sender: CommandSender,
        id: String,
        keyw: String,
        titl: String,
        subt: String,
        dura: Integer,
        rwd: String,
        msg: String
    ): Unit = {
        val storage = new File(
            PlugGividado.plug.getDataFolder.getPath + File.separator + "storage"
        )
        if (!storage.exists()) storage.mkdirs()

        val f = new File( storage.getPath + File.separator + s"$id.yml" )
        if (f.exists()) {
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString( "msg_event_exist" )
            )
        } else {
            val yml = YamlConfiguration.loadConfiguration( f )
            yml.set( "title", color( titl ) )
            yml.set( "subtitle", color( subt ) )
            yml.set( "keyword", color( keyw ) )
            yml.set( "reward", rwd )
            yml.set( "duration", dura )
            yml.set( "message", color( msg ) )
            yml.save( f )
            sender.sendMessage(
                PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString(
                    "msg_event_register"
                )
            )
        }
    }

    @CommandHook( "reload" )
    def reload( sender: CommandSender ): Unit = {
        PlugGividado.cman.save()
        PlugGividado.cman.load()
        sender.sendMessage(
            PlugGividado.getPrefix + PlugGividado.cman.getConfig.getString( "msg_reload" )
        )
    }

}
