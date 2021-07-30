package io.freeze_dolphin.bless_reward.configs

import redempt.redlib.configmanager.annotations.ConfigValue
import redempt.redlib.misc.FormatUtils.color

class ConfigBus {

    @ConfigValue("prefix")
    var prefix: String = color("&f[&e祝福&f] ")

    @ConfigValue("welcome_event_id")
    var welcome_event_id: String = "welcome"

    @ConfigValue("msg_reward")
    var msg_reward: String = color("&a你获得了奖励!")

    @ConfigValue("msg_reload")
    var msg_reload: String = color("&b已重载")

    @ConfigValue("msg_event_exist")
    var msg_event_exist: String = color("&c此事件已被注册!")

    @ConfigValue("msg_event_not_exist")
    var msg_event_not_exist: String = color("&c未找到此事件!")

    @ConfigValue("msg_player_not_exist")
    var msg_player_not_exist: String = color("&c未找到此玩家!")

    @ConfigValue("msg_event_register")
    var msg_event_register: String = color("&b已注册!")

    @ConfigValue("msg_event_start")
    var msg_event_start: String = color("&b已开始事件!")

    @ConfigValue("title_fade_in")
    var title_fade_in: Integer = 20

    @ConfigValue("title_stay")
    var title_stay: Integer = 60

    @ConfigValue("title_fade_out")
    var title_fade_out: Integer = 20

    @ConfigValue("perm_participate")
    var perm_participate: String = "bless-reward.use.participate"

    @ConfigValue("reward_chance")
    var reward_chance: Double = 0.4D

}
