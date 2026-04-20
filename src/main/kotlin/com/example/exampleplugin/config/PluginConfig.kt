package com.example.exampleplugin.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

/**
 * A typed wrapper around the plugin's `config.yml`.
 *
 * On construction the default configuration is saved (if the file does not
 * yet exist) and the current values are loaded into memory. Call [reload]
 * to re-read the file at runtime without restarting the server.
 *
 * @param plugin the owning plugin instance
 */
class PluginConfig(private val plugin: JavaPlugin) {

    /** The currently loaded Bukkit [FileConfiguration]. */
    private var config: FileConfiguration

    init {
        plugin.saveDefaultConfig()
        config = plugin.config
    }

    /**
     * Re-reads `config.yml` from disk, picking up any changes made since
     * the last load.
     */
    fun reload() {
        plugin.reloadConfig()
        config = plugin.config
    }

    /**
     * Adds any keys present in the default `config.yml` (bundled inside the
     * plugin JAR) that are missing from the server's active configuration
     * file, while preserving all user-modified values.
     *
     * Call this after [reload] to ensure the on-disk config stays up-to-date
     * with newly introduced defaults.
     */
    fun migrate() {
        val defaultStream = plugin.getResource("config.yml") ?: return
        val defaultConfig = defaultStream.use { YamlConfiguration.loadConfiguration(it.reader()) }

        var changed = false
        for (key in defaultConfig.getKeys(true)) {
            if (!defaultConfig.isConfigurationSection(key) && !config.contains(key)) {
                config.set(key, defaultConfig.get(key))
                changed = true
            }
        }

        if (changed) {
            plugin.saveConfig()
        }
    }

    // ── Typed Getters ───────────────────────────────────────────────────

    /**
     * Returns the [String] value at [path], or [default] when the key is
     * absent or not a string.
     */
    fun getString(path: String, default: String = ""): String =
        config.getString(path, default) ?: default

    /**
     * Returns the [Int] value at [path], or [default] when the key is
     * absent or not an integer.
     */
    fun getInt(path: String, default: Int = 0): Int =
        config.getInt(path, default)

    /**
     * Returns the [Double] value at [path], or [default] when the key is
     * absent or not a double.
     */
    fun getDouble(path: String, default: Double = 0.0): Double =
        config.getDouble(path, default)

    /**
     * Returns the [Boolean] value at [path], or [default] when the key is
     * absent or not a boolean.
     */
    fun getBoolean(path: String, default: Boolean = false): Boolean =
        config.getBoolean(path, default)

    /**
     * Returns the [List] of [String] values at [path], or an empty list
     * when the key is absent.
     */
    fun getStringList(path: String): List<String> =
        config.getStringList(path)

    /**
     * Returns `true` when [path] exists in the loaded configuration.
     */
    fun contains(path: String): Boolean =
        config.contains(path)
}
