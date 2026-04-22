package com.example.exampleplugin.tasks

import com.example.exampleplugin.registration.PluginTask
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit

/**
 * Periodically broadcasts a message to all online players.
 *
 * The first broadcast fires 5 minutes after the plugin enables and then
 * repeats every 5 minutes (6 000 ticks = 5 minutes at 20 ticks/second).
 */
class BroadcastTask : PluginTask(
    delay = 6000L,
    period = 6000L
) {
    override fun run() {
        Bukkit.broadcast(
            Component.text("[ExamplePlugin] ", NamedTextColor.GOLD)
                .append(Component.text("The server is running smoothly!", NamedTextColor.YELLOW))
        )
    }
}
