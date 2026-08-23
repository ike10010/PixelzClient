package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Performance - NoLag (aggregated: particles, animations, chunk updates).
 */
public class NoLagModule extends Module {
    public boolean noParticles = true;
    public boolean noFireOverlay = true;
    public boolean noHurtCam = true;
    public boolean noWeather = true;

    public NoLagModule() {
        super("NoLag", "Performance NoLag - particles/weather/hurtcam off", Category.RENDER);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        // Apply per-tick if needed - mixins would handle heavier logic (NoWeather, NoFireOverlay etc)
        if (noWeather && mc.world != null) {
            // Clear weather rendering via world set? For performance, disable weather particles
        }
    }

    @Override
    protected void onEnable() {
        if (mc.options != null && noParticles) {
            try { mc.options.getParticles().setValue(net.minecraft.particle.ParticlesMode.MINIMAL); } catch (Exception ignored) {}
        }
        chat("NoLag enabled - anti-lag optimizations");
    }
}
