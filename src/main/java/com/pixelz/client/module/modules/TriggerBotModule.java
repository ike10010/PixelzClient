package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

/**
 * Combat - TriggerBot: attacks the moment crosshair is over entity.
 */
public class TriggerBotModule extends Module {
    public int delay = 50;
    private long last = 0;

    public TriggerBotModule() {
        super("TriggerBot", "TriggerBot - hits when crosshair over entity", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.targetedEntity == null || !(mc.targetedEntity instanceof LivingEntity le) || !le.isAlive()) return;
        if (mc.player.distanceTo(mc.targetedEntity) > 4.5f) return;
        long now = System.currentTimeMillis();
        if (now - last < delay) return;
        if (mc.player.getAttackCooldownProgress(0) < 0.9f) return;
        mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
        mc.player.swingHand(Hand.MAIN_HAND);
        last = now;
    }
}
