package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.particle.ParticlesMode;

/**
 * Performance - FPSBoost (Wurst/Sodium-like).
 * Fast graphics, minimal particles, no clouds, reduced shadows.
 */
public class FPSBoostModule extends Module {
    private ParticlesMode prevParticles;
    private Object prevClouds;
    private Object prevGraphics;

    public FPSBoostModule() {
        super("FPSBoost", "Performance FPSBoost - fast render + minimal particles", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        if (mc.options == null) return;
        try {
            prevParticles = mc.options.getParticles().getValue();
            mc.options.getParticles().setValue(ParticlesMode.MINIMAL);
        } catch (Exception ignored) {}
        try {
            mc.options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        } catch (Exception ignored) {}
        // GraphicsMode FAST not needed - handled via FastRender
        try {
            mc.options.getEntityShadows().setValue(false);
        } catch (Exception ignored) {}
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
        chat("FPSBoost enabled - fast graphics/minimal particles");
    }

    @Override
    protected void onDisable() {
        if (mc.options == null) return;
        try {
            if (prevParticles != null) mc.options.getParticles().setValue(prevParticles);
        } catch (Exception ignored) {}
        try {
            mc.options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FANCY);
        } catch (Exception ignored) {}
        try {
            mc.options.getEntityShadows().setValue(true);
        } catch (Exception ignored) {}
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
