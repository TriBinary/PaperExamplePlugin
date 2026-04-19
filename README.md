<h1 align="center">
  PaperExamplePlugin
</h1>

A template repository for creating Paper (Minecraft) plugins with Kotlin. It comes with an auto-registration system for commands, listeners, permissions, and GUIs — just extend a base class, drop it in the right package, and the plugin handles the rest.

## Getting Started

1. Click **"Use this template"** on GitHub to create your own repository.
2. Replace the placeholder values below with your own:

| Placeholder            | File(s)                                                    | Description                          |
|:-----------------------|:-----------------------------------------------------------|:-------------------------------------|
| `ExamplePlugin`        | `settings.gradle.kts`, `plugin.yml`, source files, docs    | Your plugin's display name           |
| `exampleplugin`        | `plugin.yml`, source files, docs                           | Lowercase plugin name (used for commands) |
| `ep`                   | `plugin.yml`, `CommandRegistrar.kt`                        | Short command alias                  |
| `com.example`          | `build.gradle.kts`                                         | Your Maven group ID                  |
| `com.example.exampleplugin` | All source files under `src/main/kotlin/`, `plugin.yml`, docs | Your full base package path          |

3. Rename the source directory `src/main/kotlin/com/example/exampleplugin/` to match your package.
4. Update the `FUNDING.yml` with your own sponsorship links (or remove it).

## Project Structure

```
src/main/kotlin/com/example/exampleplugin/
├── Main.kt                  # Plugin entry point
├── commands/                # Auto-registered commands (extend PluginCommand)
├── listeners/               # Auto-registered listeners (implement Listener)
├── guis/                    # Auto-registered GUIs (extend PluginGUI)
└── registration/            # Auto-registration framework
```

See [`docs/DEVELOPER_GUIDE.md`](docs/DEVELOPER_GUIDE.md) for detailed instructions on creating commands, listeners, and GUIs.

