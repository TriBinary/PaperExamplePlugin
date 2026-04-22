package com.example.exampleplugin.utils

import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class CountdownHelper {
    private val mm = MiniMessage.miniMessage()

    fun start(
        plugin: JavaPlugin,
        player: Player,
        seconds: Int,
        message: (remaining: Int) -> String,
        sound: Sound,
        finishSound: Sound,
        onFinish: (Player) -> Unit
    ) {
        // Countdown Logic
    }
}