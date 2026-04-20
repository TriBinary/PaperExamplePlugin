package com.example.exampleplugin.commands

import com.example.exampleplugin.registration.PluginCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

/**
 * Reloads the plugin configuration from disk.
 *
 * Registered as `/exampleplugin reload` and requires the
 * `exampleplugin.reload` permission.
 */
class ReloadCommand(private val plugin: JavaPlugin) : PluginCommand(
    name = "reload",
    description = "Reload the plugin configuration",
    permission = "exampleplugin.reload"
) {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        val main = plugin as? com.example.exampleplugin.Main
        if (main == null) {
            sender.sendMessage("Error: Plugin instance type mismatch. Unable to reload configuration.")
            return true
        }
        main.pluginConfig.reload()
        main.pluginConfig.migrate()
        sender.sendMessage("Configuration reloaded!")
        return true
    }
}
