package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

/**
 * Combat - MultiAura: attacks multiple entities per tick.
 */
public class MultiAuraModule extends Module {
    public float range = 4.5f;
    public int maxTargets = 3;

    public MultiAuraModule() {
        super("MultiAura", "MultiAura - hits multiple entities at once", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        var box = new Box(mc.player.getX()-range, mc.player.getY()-range, mc.player.getZ()-range, mc.player.getX()+range, mc.player.getY()+range, mc.player.getZ()+range);
        var entities = mc.world.getOtherEntities(mc.player, box, e -> e instanceof LivingEntity le && le.isAlive() && e != mc.player && mc.player.distanceTo(e) < range);
        int count = 0;
        for (var e : entities) {
            if (count++ >= maxTargets) break;
            if (mc.player.getAttackCooldownProgress(0) < 0.9f) break;
            mc.interactionManager.attackEntity(mc.player, e);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
