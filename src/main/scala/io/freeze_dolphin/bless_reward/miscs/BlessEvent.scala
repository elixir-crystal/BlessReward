package io.freeze_dolphin.bless_reward.miscs

import io.freeze_dolphin.bless_reward.PlugGividado
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.{Bukkit, Sound}

import java.util.UUID

class BlessEvent(
    plug: Plugin,
    id: String,
    owner: UUID,
    title: String,
    subt: String,
    dura: Integer,
    keyword: String,
    reward: String,
    msg: String
) {

    def start(): Unit = {
        plug.getLogger.info( s"开始了事件: $id" )
        for (plr <- plug.getServer.getOnlinePlayers.toArray) {
            val p = plr.asInstanceOf[Player]

            if ({
                if (owner != null) !p.getUniqueId.equals( owner ) else true
            } && p.hasPermission( PlugGividado.cman.getConfig.getString( "perm_participate" ) )) {
                p.sendMessage( PlugGividado.getPrefix + title + subt )
                p.playSound( p.getLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f )

                val lsr = new BlessListener( plug, p.getUniqueId, keyword, reward, msg )
                lsr.init()
                Bukkit.getScheduler.runTaskLater( plug, new BlessRunnable( lsr ), dura * 10L )
            }
        }
    }

}

class BlessRunnable( lsr: BlessListener ) extends Runnable {

    override def run(): Unit = {
        lsr.dispose()
    }
}

import org.bukkit.event.player.AsyncPlayerChatEvent
import redempt.redlib.misc.EventListener

private class BlessListener( plug: Plugin, uid: UUID, key: String, reward: String, msg: String ) {

    private var event: EventListener[AsyncPlayerChatEvent] = _

    def init(): Unit = {
        new EventListener[AsyncPlayerChatEvent](
            plug,
            classOf[AsyncPlayerChatEvent],
            ( lsr: EventListener[AsyncPlayerChatEvent], evt: AsyncPlayerChatEvent ) => {
                event = lsr

                val p = evt.getPlayer
                if (p.getUniqueId.equals( uid )) {
                    if (evt.getMessage.matches( ".*" + key + ".*" )) {
                        if (PlugGividado.eman.hasAccount( p )) {

                            val chance: Double =
                                PlugGividado.cman.getConfig.getDouble( "reward_chance" )
                            if (Math.random() < chance) {
                                Bukkit.dispatchCommand(
                                    Bukkit.getConsoleSender,
                                    reward
                                        .replaceAll( "%player%", p.getName )
                                        .replaceAll( "%msg%", msg )
                                        .replaceAll( "%key%", key )
                                )
                                p.sendMessage(
                                    if (msg.equals( "*" ))
                                        PlugGividado.getPrefix + PlugGividado.cman.getConfig
                                            .getString( "msg_reward" )
                                    else msg
                                )
                            }
                            this.dispose()
                        }
                    }
                }
            }
        )
    }

    def dispose(): Unit = {
        event.unregister()
    }

}
