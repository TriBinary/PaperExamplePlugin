<h1 align="center">
  PaperExamplePlugin
</h1>

A template repository for creating Paper (Minecraft) plugins with Kotlin. It comes with an auto-registration system for
commands, listeners, permissions, GUIs and tasks — just extend a base class, drop it in the right package, and the
plugin
handles the rest.

## Getting Started

1. Click **"Use this template"** on GitHub to create your own repository.
2. Replace the placeholder values below with your own:

| Placeholder                 | File(s)                                                       | Description                               |
|:----------------------------|:--------------------------------------------------------------|:------------------------------------------|
| `ExamplePlugin`             | `settings.gradle.kts`, `plugin.yml`, source files, docs       | Your plugin's display name                |
| `exampleplugin`             | `plugin.yml`, source files, docs                              | Lowercase plugin name (used for commands) |
| `ep`                        | `plugin.yml`, `CommandRegistrar.kt`                           | Short command alias                       |
| `com.example`               | `build.gradle.kts`                                            | Your Maven group ID                       |
| `com.example.exampleplugin` | All source files under `src/main/kotlin/`, `plugin.yml`, docs | Your full base package path               |

3. Rename the source directory `src/main/kotlin/com/example/exampleplugin/` to match your package.
4. Update the `FUNDING.yml` with your own sponsorship links (or remove it).

## Project Structure

```
src/main/kotlin/com/example/exampleplugin/
├── Main.kt                  # Plugin entry point
├── commands/                # Auto-registered commands (extend PluginCommand)
├── config/                  # Typed configuration wrapper (PluginConfig)
├── data/                    # JSON-persisted player and server data (PlayerData, ServerData)
├── enums/                   # Plugin-wide enums (e.g. FillMode, DisplayLocation)
├── guis/                    # Auto-registered GUIs (extend PluginGUI)
├── listeners/               # Auto-registered listeners (implement Listener)
├── registration/            # Auto-registration framework
├── tasks/                   # Auto-registered tasks (extend PluginTask)
└── utils/                   # Utility helpers (e.g. ItemStack DSL)
```

See [`docs/DEVELOPER_GUIDE.md`](docs/DEVELOPER_GUIDE.md) for detailed instructions on creating commands, listeners,
GUIs, working with the configuration system, and managing player and server data.

See [`docs/UTILITY_GUIDE.md`](docs/UTILITY_GUIDE.md) for documentation on the built-in utility helpers such as
the `itemStack` DSL builder and `CountdownHelper`.

See [`docs/DEVELOPER_GUIDE.md`](docs/DEVELOPER_GUIDE.md#enums) for documentation on plugin-wide enums such as
`DisplayLocation` and `FillMode`.

