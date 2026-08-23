package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.util.Hand;

public class AutoClickerModule extends Module {
    public int cps = 10;
    private long last;

    public AutoClickerModule() {
        super("AutoClicker", "Automatically clicks", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (!mc.options.attackKey.isPressed()) return;
        long now = System.currentTimeMillis();
        if (now - last < 1000 / cps) return;
        if (mc.targetedEntity != null) {
            mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
            mc.player.swingHand(Hand.MAIN_HAND);
            last = now;
        }
    }
}
