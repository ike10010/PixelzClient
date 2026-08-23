package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

public class JesusModule extends Module {
    public JesusModule() {
        super("Jesus", "Walk on water", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isTouchingWater() && !mc.player.isSneaking() && !mc.player.isSwimming()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
            mc.player.setOnGround(true);
        }
    }
}
