package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

public class SprintModule extends Module {
    public boolean keepSprint = true;

    public SprintModule() {
        super("Sprint", "Keeps you sprinting", Category.MOVEMENT);
        setEnabled(true); // enable by default like LB
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.isSneaking() || mc.player.horizontalCollision || mc.player.isTouchingWater()) return;
        if (mc.player.forwardSpeed > 0) mc.player.setSprinting(true);
    }
}
