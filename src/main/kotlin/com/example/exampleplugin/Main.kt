package com.example.exampleplugin

import com.example.exampleplugin.registration.CommandRegistrar
import com.example.exampleplugin.registration.GUIManager
import com.example.exampleplugin.registration.ListenerRegistrar
import com.example.exampleplugin.registration.PermissionRegistrar
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        // Register commands, listeners and GUIs
        logger.info("Registering commands...")
        CommandRegistrar.registerAll(this)
        logger.info("Registering permissions...")
        PermissionRegistrar.registerAll(this)
        logger.info("Registering listeners...")
        ListenerRegistrar.registerAll(this)
        logger.info("Registering GUIs...")
        GUIManager.registerAll(this)

        logger.info("Plugin enabled!")
    }

    override fun onDisable() {
        logger.info("Plugin disabled!")
    }
}
