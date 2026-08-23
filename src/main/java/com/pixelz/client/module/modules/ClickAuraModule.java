package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

/**
 * Combat - ClickAura: attacks only while you click (more legit than KillAura).
 */
public class ClickAuraModule extends Module {
    public float range = 4.2f;

    public ClickAuraModule() {
        super("ClickAura", "ClickAura - attacks while clicking", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        if (!mc.options.attackKey.isPressed()) return;
        var box = new Box(mc.player.getX()-range, mc.player.getY()-range, mc.player.getZ()-range, mc.player.getX()+range, mc.player.getY()+range, mc.player.getZ()+range);
        var target = mc.world.getOtherEntities(mc.player, box, e -> e instanceof LivingEntity le && le.isAlive() && e != mc.player && mc.player.distanceTo(e) < range)
            .stream().min((a,b) -> Float.compare(a.distanceTo(mc.player), b.distanceTo(mc.player))).orElse(null);
        if (target == null) return;
        if (mc.player.getAttackCooldownProgress(0) < 0.9f) return;
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
