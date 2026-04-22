package com.example.exampleplugin.utils

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration

/**
 * Helper that runs a per-player countdown and displays the progress through
 * one of the available [DisplayLocation]s.
 *
 * ### Message placeholders
 *
 * The [start] `message` parameter is a MiniMessage string that may contain the
 * following placeholders, which are replaced on every tick:
 *
 * | Placeholder  | Output example                          |
 * |:-------------|:----------------------------------------|
 * | `{seconds}`  | `5s` (raw remaining seconds)            |
 * | `{time}`     | `1h 2m 3s` (human-readable time string) |
 *
 * Either or both placeholders may be omitted from the message string.
 *
 * ### Optional parameters
 *
 * Pass `null` for [sound] or [finishSound] to play no sound at that point.
 * Set [displayLocation] to [DisplayLocation.NONE] to suppress the message
 * entirely.
 *
 * ### Example usage
 * ```kotlin
 * CountdownHelper().start(
 *     plugin        = plugin,
 *     player        = player,
 *     seconds       = 10,
 *     message       = "<yellow>Starting in <bold>{seconds}</bold> (<gray>{time}</gray>)",
 *     displayLocation = DisplayLocation.ACTION_BAR,
 *     sound         = Sound.sound(Key.key("minecraft:ui.button.click"), Sound.Source.MASTER, 1f, 1f),
 *     finishSound   = Sound.sound(Key.key("minecraft:entity.player.levelup"), Sound.Source.MASTER, 1f, 1f),
 *     onFinish      = { p -> p.sendMessage("<green>Go!") }
 * )
 * ```
 */
class CountdownHelper {
    private val mm = MiniMessage.miniMessage()

    /**
     * Starts a countdown for [player] and ticks every second until [seconds]
     * reaches zero.
     *
     * @param plugin          the owning plugin (used to schedule tasks)
     * @param player          the player to target
     * @param seconds         total number of seconds to count down from
     * @param message         MiniMessage string shown each tick; supports
     *                        `{seconds}` and `{time}` placeholders
     * @param displayLocation where the message is shown; use
     *                        [DisplayLocation.NONE] to suppress output
     * @param sound           sound played on every tick, or `null` for silence
     * @param finishSound     sound played when the countdown ends, or `null`
     *                        for silence
     * @param onFinish        callback invoked with [player] when the countdown
     *                        reaches zero
     */
    fun start(
        plugin: JavaPlugin,
        player: Player,
        seconds: Int,
        message: String,
        displayLocation: DisplayLocation,
        sound: Sound?,
        finishSound: Sound?,
        onFinish: (Player) -> Unit
    ) {
        require(seconds > 0) { "seconds must be greater than 0" }

        var remaining = seconds

        val bossBar: BossBar? = if (displayLocation == DisplayLocation.BOSS_BAR) {
            BossBar.bossBar(
                mm.deserialize(applyPlaceholders(message, remaining, seconds)),
                1.0f,
                BossBar.Color.BLUE,
                BossBar.Overlay.PROGRESS
            ).also { player.showBossBar(it) }
        } else null

        var task: org.bukkit.scheduler.BukkitTask? = null
        task = plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            if (!player.isOnline) {
                bossBar?.let { player.hideBossBar(it) }
                task?.cancel()
                return@Runnable
            }

            if (remaining <= 0) {
                bossBar?.let { player.hideBossBar(it) }
                finishSound?.let { player.playSound(it) }
                task?.cancel()
                onFinish(player)
                return@Runnable
            }

            val formatted = applyPlaceholders(message, remaining, seconds)

            when (displayLocation) {
                DisplayLocation.NONE -> {}
                DisplayLocation.CHAT -> player.sendMessage(mm.deserialize(formatted))
                DisplayLocation.TITLE -> player.showTitle(
                    Title.title(
                        mm.deserialize(formatted),
                        mm.deserialize(""),
                        Title.Times.times(
                            Duration.ZERO,
                            Duration.ofMillis(1200),
                            Duration.ZERO
                        )
                    )
                )
                DisplayLocation.BOSS_BAR -> bossBar?.let {
                    it.name(mm.deserialize(formatted))
                    it.progress(remaining.toFloat() / seconds.toFloat())
                }
                DisplayLocation.ACTION_BAR -> player.sendActionBar(mm.deserialize(formatted))
            }

            sound?.let { player.playSound(it) }
            remaining--
        }, 0L, 20L)
    }

    /**
     * Replaces `{seconds}` and `{time}` placeholders in [message].
     *
     * @param message   the raw MiniMessage template
     * @param remaining seconds still remaining
     * @param total     the original total seconds (unused; reserved for future use)
     * @return the message with placeholders substituted
     */
    private fun applyPlaceholders(message: String, remaining: Int, @Suppress("UNUSED_PARAMETER") total: Int): String {
        return message
            .replace("{seconds}", "${remaining}s")
            .replace("{time}", formatTime(remaining))
    }

    /**
     * Formats a raw second count into a human-readable string.
     *
     * Examples:
     * - `5`    → `5s`
     * - `90`   → `1m 30s`
     * - `3665` → `1h 1m 5s`
     *
     * @param seconds the number of seconds to format (must be ≥ 0)
     * @return formatted time string
     */
    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return buildString {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            append("${secs}s")
        }.trim()
    }
}
