package com.pixelz.client;

import com.pixelz.client.command.CommandManager;
import com.pixelz.client.config.ConfigManager;
import com.pixelz.client.gui.ClickGuiScreen;
import com.pixelz.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixelzClient implements ClientModInitializer {
    public static final String MOD_ID = "pixelz";
    public static final String VERSION = "1.1.2";
    public static final Logger LOGGER = LoggerFactory.getLogger("Pixelz");

    public static PixelzClient INSTANCE;

    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private KeyBinding clickGuiKey;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Loading Pixelz Client v{} for 1.21.11", VERSION);

        this.moduleManager = new ModuleManager();
        this.moduleManager.load();

        this.configManager = new ConfigManager();
        try {
            this.configManager.load();
        } catch (Exception e) {
            LOGGER.warn("Failed to load config", e);
        }

        // Register click gui key - RSHEIF = RShift like Pixelz Client
        // 1.21.11 uses KeyBinding.Category record (Identifier based)
        KeyBinding.Category pixelzCategory = KeyBinding.Category.create(Identifier.of("pixelz", "main"));
        this.clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pixelz.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                pixelzCategory
        ));

        // Tick handler — forwards to modules
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            if (clickGuiKey.wasPressed()) {
                client.setScreen(new ClickGuiScreen());
            }

            moduleManager.onTick();
        });

        CommandManager.init();

        // Save config on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                configManager.save();
            } catch (Exception e) {
                LOGGER.error("Failed to save config on shutdown", e);
            }
        }));

        LOGGER.info("Pixelz Client loaded with {} modules", moduleManager.getModules().size());
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }
}
