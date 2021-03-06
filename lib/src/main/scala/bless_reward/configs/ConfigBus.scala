package bless_reward.configs

import redempt.redlib.config.annotations.ConfigMappable
import redempt.redlib.config.annotations.ConfigName
import redempt.redlib.misc.FormatUtils.color

@ConfigMappable
class ConfigBus {

    @ConfigName( "prefix" )
    var prefix: String = color( "&f[&e祝福&f] " )

    @ConfigName( "welcome_event_id" )
    var welcome_event_id: String = "welcome"

    @ConfigName( "msg_reward" )
    var msg_reward: String = color( "&a你获得了奖励!" )

    @ConfigName( "msg_reload" )
    var msg_reload: String = color( "&b已重载" )

    @ConfigName( "msg_event_exist" )
    var msg_event_exist: String = color( "&c此事件已被注册!" )

    @ConfigName( "msg_event_not_exist" )
    var msg_event_not_exist: String = color( "&c未找到此事件!" )

    @ConfigName( "msg_player_not_exist" )
    var msg_player_not_exist: String = color( "&c未找到此玩家!" )

    @ConfigName( "msg_event_register" )
    var msg_event_register: String = color( "&b已注册!" )

    @ConfigName( "msg_event_start" )
    var msg_event_start: String = color( "&b已开始事件!" )

    @ConfigName( "perm_participate" )
    var perm_participate: String = "bless-reward.use.participate"

    @ConfigName( "reward_chance" )
    var reward_chance: Double = 0.4d

}
