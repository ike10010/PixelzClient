package com.pixelz.client.module;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class Module {
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private int key;
    private boolean enabled;
    private boolean hidden = false;

    public Module(String name, String description, Category category, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
    }

    public Module(String name, String description, Category category) {
        this(name, description, category, -1);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            onEnable();
            // Play sound feedback like Pixelz Client
            if (mc.player != null) {
                // subtle notification in chat if desired
            }
        } else {
            onDisable();
        }
        // Save config on toggle
        try {
            PixelzClient.INSTANCE.getConfigManager().save();
        } catch (Exception ignored) {}
    }

    protected void onEnable() {}
    protected void onDisable() {}

    // Called every client tick when enabled (or via ModuleManager)
    public void onTick() {}
    public void onRender() {}
    public void onWorldRender(float tickDelta) {}

    public String getDisplayName() {
        return name;
    }

    protected void chat(String msg) {
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("§8[§dPixelz§8] §7" + msg), false);
        }
    }
}
