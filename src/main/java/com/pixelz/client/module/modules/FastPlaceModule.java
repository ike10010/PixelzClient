package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Blocks/FastPlace - removes block place cooldown.
 */
public class FastPlaceModule extends Module {
    public FastPlaceModule() {
        super("FastPlace", "Wurst FastPlace - no place delay", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        // In 1.21.11, placement cooldown is mc.itemUseCooldown - set to 0 each tick
        try {
            java.lang.reflect.Field f = net.minecraft.client.MinecraftClient.class.getDeclaredField("itemUseCooldown");
            f.setAccessible(true);
            f.set(mc, 0);
        } catch (Exception e) {
            // fallback via mixin would be cleaner, reflection fallback for 1.21.11
        }
    }
}
