package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class FullBrightModule extends Module {
    public Mode mode = Mode.GAMMA;

    public enum Mode { GAMMA, POTION }

    private double prevGamma;

    public FullBrightModule() {
        super("FullBright", "Makes everything bright", Category.RENDER, -1);
    }

    @Override
    protected void onEnable() {
        if (mc.options == null) return;
        if (mode == Mode.GAMMA) {
            prevGamma = mc.options.getGamma().getValue();
            mc.options.getGamma().setValue(16.0);
        }
    }

    @Override
    protected void onDisable() {
        if (mode == Mode.GAMMA && mc.options != null) {
            mc.options.getGamma().setValue(prevGamma);
        }
        if (mc.player != null) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    @Override
    public void onTick() {
        if (mode == Mode.GAMMA && mc.options != null) {
            if (mc.options.getGamma().getValue() < 16.0) mc.options.getGamma().setValue(16.0);
        } else if (mode == Mode.POTION && mc.player != null) {
            if (!mc.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                mc.player.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(StatusEffects.NIGHT_VISION, 10000, 0, false, false, false));
            }
        }
    }
}
