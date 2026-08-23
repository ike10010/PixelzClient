package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

public class StepModule extends Module {
    public float height = 1.5f;

    public StepModule() {
        super("Step", "Allows you to step up blocks", Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        if (mc.player != null) {
            try {
                // 1.21.11 attribute based - fallback to generic
                mc.player.getAttributeInstance(net.minecraft.entity.attribute.EntityAttributes.STEP_HEIGHT).setBaseValue(height);
            } catch (Exception ignored) {}
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player != null) {
            try {
                mc.player.getAttributeInstance(net.minecraft.entity.attribute.EntityAttributes.STEP_HEIGHT).setBaseValue(0.6);
            } catch (Exception ignored) {}
        }
    }
}
