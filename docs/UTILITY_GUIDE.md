# ExamplePlugin - Utility Guide

This guide covers the utility helpers provided in `com.example.exampleplugin.utils`. Each utility is designed to
reduce boilerplate and provide commonly needed functionality out of the box.

| Utility           | Description                                                 |
|:------------------|:------------------------------------------------------------|
| `itemStack`       | DSL builder for creating `ItemStack` instances concisely    |
| `CountdownHelper` | Per-player countdown with configurable display and sound    |

---

## ItemStack Builder DSL

Building `ItemStack` instances with custom names, lore, enchantments, and flags normally requires verbose
boilerplate. The `itemStack` DSL in `com.example.exampleplugin.utils` lets you create fully configured items in a
single expression. All text is parsed through
[MiniMessage](https://docs.advntr.dev/minimessage/index.html), so rich formatting tags like `<bold>`, `<red>`,
and `<gradient>` work out of the box.

### Before (vanilla API)

```kotlin
val item = ItemStack(Material.DIAMOND_SWORD)
val meta = item.itemMeta
meta.displayName(MiniMessage.miniMessage().deserialize("<bold><gradient:gold:yellow>Excalibur</gradient></bold>"))
meta.lore(
    listOf(
        MiniMessage.miniMessage().deserialize("<gray>A legendary blade"),
        MiniMessage.miniMessage().deserialize("<gray>Damage: <red>+20")
    )
)
meta.addEnchant(Enchantment.SHARPNESS, 5, true)
meta.isUnbreakable = true
meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
item.itemMeta = meta
```

### After (using the DSL)

```kotlin
import com.example.exampleplugin.utils.itemStack

val item = itemStack(Material.DIAMOND_SWORD) {
    name("<bold><gradient:gold:yellow>Excalibur</gradient></bold>")
    lore("<gray>A legendary blade", "<gray>Damage: <red>+20")
    enchant(Enchantment.SHARPNESS, 5)
    unbreakable(true)
    flag(ItemFlag.HIDE_ENCHANTS)
}
```

### Builder Methods

| Method            | Signature                   | Description                                     |
|:------------------|:----------------------------|:------------------------------------------------|
| `name`            | `name(String)`              | Set the display name (MiniMessage)              |
| `lore`            | `lore(vararg String)`       | Set lore lines (each parsed with MiniMessage)   |
| `enchant`         | `enchant(Enchantment, Int)` | Add an enchantment at the given level           |
| `unbreakable`     | `unbreakable(Boolean)`      | Make the item unbreakable                       |
| `amount`          | `amount(Int)`               | Set the stack size                              |
| `flag`            | `flag(vararg ItemFlag)`     | Add one or more item flags                      |
| `customModelData` | `customModelData(Int)`      | Set the custom model data value                 |
| `meta`            | `meta(ItemMeta.() -> Unit)` | Escape hatch for direct `ItemMeta` manipulation |

### Escape Hatch Example

For advanced use-cases not covered by the builder methods, the `meta` block gives you direct access to the
`ItemMeta`. Any changes made inside `meta` are applied **after** all other builder properties, so they take
precedence:

```kotlin
import com.example.exampleplugin.utils.itemStack

val head = itemStack(Material.PLAYER_HEAD) {
    name("<yellow>Custom Head")
    meta {
        // 'this' is the ItemMeta — cast and use any Paper API method
        (this as org.bukkit.inventory.meta.SkullMeta)
            .owningPlayer = org.bukkit.Bukkit.getOfflinePlayer("Notch")
    }
}
```

---

## CountdownHelper

`CountdownHelper` runs a per-player countdown and displays the progress through a configurable
`DisplayLocation` (see the [Developer Guide](DEVELOPER_GUIDE.md#displaylocation) for all values). It schedules a repeating sync task that ticks every second, shows an
optional message on each tick, and fires an optional finish message and callback when the countdown reaches zero.

### Usage

```kotlin
import com.example.exampleplugin.utils.CountdownHelper
import com.example.exampleplugin.enums.DisplayLocation
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

CountdownHelper().start(
    plugin          = plugin,
    player          = player,
    seconds         = 10,
    displayLocation = DisplayLocation.ACTION_BAR,
    message         = "<yellow>Starting in <bold>{seconds}</bold> (<gray>{time}</gray>)",
    finishMessage   = "<green>Go!",
    sound           = Sound.sound(Key.key("minecraft:ui.button.click"), Sound.Source.MASTER, 1f, 1f),
    finishSound     = Sound.sound(Key.key("minecraft:entity.player.levelup"), Sound.Source.MASTER, 1f, 1f),
    onFinish        = { p -> p.sendMessage("Started!") }
)
```

### Parameters

| Parameter         | Type                | Required | Default              | Description                                                          |
|:------------------|:--------------------|:---------|:---------------------|:---------------------------------------------------------------------|
| `plugin`          | `JavaPlugin`        | Yes      | —                    | The owning plugin, used to schedule the internal task                |
| `player`          | `Player`            | Yes      | —                    | The player to target                                                 |
| `seconds`         | `Int`               | Yes      | —                    | Total number of seconds to count down from (must be > 0)            |
| `displayLocation` | `DisplayLocation`   | Yes      | —                    | Where messages are shown; use `DisplayLocation.NONE` to suppress all |
| `message`         | `String?`           | No       | `null`               | MiniMessage string shown on every tick; omit to skip per-tick output |
| `finishMessage`   | `String?`           | No       | `null`               | MiniMessage string shown when the countdown ends; omit to skip       |
| `bossBarColor`    | `BossBar.Color`     | No       | `BossBar.Color.BLUE` | Boss bar colour; only used when `displayLocation` is `BOSS_BAR`     |
| `sound`           | `Sound?`            | No       | `null`               | Sound played on every tick; pass `null` for silence                  |
| `finishSound`     | `Sound?`            | No       | `null`               | Sound played when the countdown ends; pass `null` for silence        |
| `onFinish`        | `(Player) -> Unit`  | Yes      | —                    | Callback invoked with the player when the countdown reaches zero     |

### Message Placeholders

Both `message` and `finishMessage` support the following placeholders:

| Placeholder  | Output example      |
|:-------------|:--------------------|
| `{seconds}`  | `5s`                |
| `{time}`     | `1h 2m 3s`          |

Either or both placeholders may be omitted from the message string.

### Example (Chat Countdown)

```kotlin
import com.example.exampleplugin.utils.CountdownHelper
import com.example.exampleplugin.enums.DisplayLocation

CountdownHelper().start(
    plugin          = plugin,
    player          = player,
    seconds         = 5,
    displayLocation = DisplayLocation.CHAT,
    message         = "<gray>Game starts in <yellow>{seconds}",
    finishMessage   = "<green><bold>Game started!",
    onFinish        = { p -> p.sendMessage("Good luck!") }
)
```

### Example (Boss Bar Countdown)

```kotlin
import com.example.exampleplugin.utils.CountdownHelper
import com.example.exampleplugin.enums.DisplayLocation
import net.kyori.adventure.bossbar.BossBar

CountdownHelper().start(
    plugin          = plugin,
    player          = player,
    seconds         = 30,
    displayLocation = DisplayLocation.BOSS_BAR,
    bossBarColor    = BossBar.Color.RED,
    message         = "<red>Time remaining: {time}",
    finishMessage   = "<green>Time's up!",
    onFinish        = { p -> p.sendMessage("Round over!") }
)
```

---
