package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/**
 * Wurst Blocks/FastBreak - breaks blocks faster via Haste.
 */
public class FastBreakModule extends Module {
    public int amplifier = 1;

    public FastBreakModule() {
        super("FastBreak", "Wurst FastBreak - faster breaking", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        // Apply Haste II for instant-like break
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 10, amplifier, false, false, false));
        // Also set block breaking cooldown to 0 via mixin would be more direct, but haste is packet-safe
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) mc.player.removeStatusEffect(StatusEffects.HASTE);
    }
}
