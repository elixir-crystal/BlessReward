package io.freeze_dolphin.bless_reward.configs

import redempt.redlib.configmanager.annotations.ConfigValue
import redempt.redlib.misc.FormatUtils.color

class ConfigBus {

    @ConfigValue("prefix")
    var prefix: String = color("&f[&e迎新&f] ")

    @ConfigValue("msg_title")
    var msg_title: String = color("&b%EVENT%")

    @ConfigValue("msg_subtitle")
    var msg_subtitle: String = color("&6发送关键词 &e欢迎 &6有几率获得奖励!")

    @ConfigValue("default_duration")
    var default_duration: Integer = 120

    @ConfigValue("default_reward")
    var default_reward: Double = 100

    @ConfigValue("msg_reward")
    var msg_reward: String = color("&a你获得了奖励!")

    @ConfigValue("msg_reload")
    var msg_reload: String = color("&b已重载")

    @ConfigValue("msg_event_exist")
    var msg_event_exist: String = color("&c此事件已被注册!")

    @ConfigValue("msg_event_not_exist")
    var msg_event_not_exist: String = color("&c未找到拥有此事件!")

    @ConfigValue("title_fade_in")
    var title_fade_in: Integer = 20

    @ConfigValue("title_stay")
    var title_stay: Integer = 60

    @ConfigValue("title_fade_out")
    var title_fade_out: Integer = 20

    @ConfigValue("perm_participate")
    var perm_participate: String = "bless-reward.use.participate"

}
