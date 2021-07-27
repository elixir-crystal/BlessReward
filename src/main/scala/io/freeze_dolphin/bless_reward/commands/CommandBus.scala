package io.freeze_dolphin.bless_reward.commands

import io.freeze_dolphin.bless_reward.PlugGividado
import java.io.File
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import redempt.redlib.commandmanager.CommandHook

class CommandBus {

    @CommandHook("event")
    def event(sender: CommandSender, id: String): Unit = {
        val storage = new File(PlugGividado.plug.getDataFolder.getPath + File.separator + "storage")
        if (!storage.exists()) storage.mkdirs()

        val f = new File(storage.getPath + File.separator + s"$id.yml")
        if (f.exists()) {
            val yml = YamlConfiguration.loadConfiguration(f)
            val title = yml.getString("title")
            val subtitle = yml.getString("subtitle")
            val keyword = yml.getString("keyword")
            val reward = yml.getDouble("reward")
            val duration = yml.getInt("duration")
        } else {
            sender.sendMessage(PlugGividado.cman.getConfig.getString("msg_event_not_exist"))
        }
    }

    //noinspection ComparingUnrelatedTypes
    @CommandHook("create")
    def create(sender: CommandSender, keyw: String, id: String, titl: String, subt: String, dura: Integer, rwd: Double): Unit = {
        val storage = new File(PlugGividado.plug.getDataFolder.getPath + File.separator + "storage")
        if (!storage.exists()) storage.mkdirs()

        val f = new File(storage.getPath + File.separator + s"$id.yml")
        if (f.exists()) {
            sender.sendMessage(PlugGividado.cman.getConfig.getString("msg_event_exist"))
        } else {
            val yml = YamlConfiguration.loadConfiguration(f)
            yml.set("title", titl)
            yml.set("subtitle", subt)
            yml.set("keyword", keyw)
            yml.set("reward", if (rwd == null) PlugGividado.cman.getConfig.getDouble("default_reward") else rwd)
            yml.set("duration", if (dura == null) PlugGividado.cman.getConfig.getInt("default_duration") else dura)
            yml.save(f)
        }
    }

    @CommandHook("reload")
    def reload(sender: CommandSender): Unit = {
        PlugGividado.cman.save()
        PlugGividado.cman.load()
        sender.sendMessage(PlugGividado.cman.getConfig.getString("msg_reload"))
    }

}
