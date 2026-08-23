# Pixelz Client — 1.21.11 Fabric Hack Client

**Website:** https://ike10010.github.io/PixelzClient/ — Download, docs & 52 modules

A free, open-source **Fabric** utility mod for **Minecraft 1.21.11** built with **Fabric Loader 0.19.3**, **Yarn 1.21.11+build.6**, **Loom 1.14.10** and **Fabric API 0.141.6**. Structure and philosophy inspired by Pixelz Client (mixin-based injection, modular design, ClickGUI).

> **Note:** This is the **last obfuscated Minecraft version** before 1.26.1 / 26.1 unobfuscation (see [Fabric blog](https://fabricmc.net/2025/12/05/12111.html)). Yarn + Intermediary are used here and are still correct for 1.21.11. For 26.1+ you must migrate to Mojang Mappings.

## Features

Inspired by Pixelz Client's module system:

- **Combat**: KillAura, Velocity, Criticals, AutoClicker
- **Movement**: Fly (Vanilla/Creative), Speed (Strafe/BHop), Sprint, NoFall, Jesus, Step
- **Player**: NoSlow, AutoArmor
- **World**: Scaffold, ChestStealer (Container steal)
- **Render**: ESP, BlockESP, Tracers, FullBright (Gamma/Potion), HUD (ArrayList + Watermark), Lightmap tweaks
- **Client**: ClickGUI (Pixelz Client-style panels), keybind system, Config save/load (`config/pixelz/config.json`), Commands

### Controls
- **Right Shift** — open ClickGUI
- **Click** module in GUI to toggle (sound feedback like Pixelz Client)
- **Right-click** header to expand/collapse category

### Commands (client, `/pixelz`)
- `/pixelz help` — help
- `/pixelz list` — list modules
- `/pixelz toggle <module>` — toggle e.g. `/pixelz toggle fly`

## Requirements
- Java 21 (Loom 1.14 requires Java 21)
- Minecraft 1.21.11
- Fabric Loader ≥0.19.3
- Fabric API ≥0.141.6+1.21.11

## Building

```bash
# from project root
./gradlew build          # → build/libs/pixelz-client-1.1.2.jar
./gradlew runClient      # launch test client (downloads MC 1.21.11)
./gradlew genSources     # decompile for IDE
```

Import as Gradle project in IntelliJ IDEA (2025.3+ recommended). Set Gradle JVM to 21.

## Project layout
```
src/main/java/com/pixelz/client/
  PixelzClient.java         # ClientModInitializer
  module/ {Module, Category, ModuleManager}
  module/modules/           # 20 modules (Combat/Movement/Render/World/Player)
  mixin/                    # Keyboard, ClientPlayer, InGameHud, WorldRenderer, Entity, Lightmap...
  gui/ClickGuiScreen.java   # Pixelz Client-style ClickGUI
  command/CommandManager.java
  config/ConfigManager.java # JSON persist (FabricLoader.getConfigDir()/pixelz)
  util/{ChatUtil, RotationUtil}
src/main/resources/
  fabric.mod.json
  pixelz.mixins.json
  assets/pixelz/icon.png
```

## How it mirrors Pixelz Client
- **Mixin injection** instead of shipping Mojang code (GPL-3.0 compliant)
- **Event-forwarding**: `ClientTickEvents.END_CLIENT_TICK` + `MixinKeyboard` → `ModuleManager.onTick()` / `onKey()` (Pixelz Client's EventManager)
- **ModuleManager** + **Category** enum + **Module** abstract (Pixelz Client's `module.Module`)
- **HUD ArrayList + Watermark** via `MixinInGameHud` (Pixelz Client's HUD)
- **ConfigManager** JSON (Pixelz Client's `config` system)
- **Velocity/FullBright/ESP** via targeted mixins (Pixelz Client's mixin package)

## Porting notes (1.21.11)
- Fabric changes for 1.21.11: Loom 1.14, Loader 0.18.1–0.19.3, WorldRenderEvents reworked, BiomeModificationAttributes, PacketSplitter (Fabric blog)
- You can later run `./gradlew migrateMappings --mappings net.fabricmc:yarn:1.21.11+build.6` or Loom's `migrateMappings` to move to Mojang mappings for 26.1

## License
GPL-3.0 (same as Pixelz Client source in this repository). You must disclose source of any distributed modified work under GPL-3.0.

## Disclaimer
Utility mod for education/testing. Use only where allowed. Not affiliated with Mojang/MS.



