package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import com.pixelz.client.util.RotationUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

/**
 * Combat - Aimbot / AimAssist: smooth crosshair movement to targets.
 */
public class AimAssistModule extends Module {
    public float range = 6f;
    public float speed = 0.5f;

    public AimAssistModule() {
        super("AimAssist", "Aimbot / AimAssist - smooth aim at targets", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.targetedEntity instanceof LivingEntity) return; // already aiming
        var box = new Box(mc.player.getX()-range, mc.player.getY()-range, mc.player.getZ()-range, mc.player.getX()+range, mc.player.getY()+range, mc.player.getZ()+range);
        var entities = mc.world.getOtherEntities(mc.player, box, e -> e instanceof LivingEntity le && le.isAlive() && mc.player.distanceTo(e) < range);
        LivingEntity best = null;
        double closest = Double.MAX_VALUE;
        for (var e : entities) {
            double d = mc.player.distanceTo(e);
            if (d < closest) { closest = d; best = (LivingEntity)e; }
        }
        if (best == null) return;
        float[] rots = RotationUtil.getRotationsTo(best);
        // Smooth aim
        float yaw = mc.player.getYaw() + (rots[0] - mc.player.getYaw()) * speed;
        float pitch = mc.player.getPitch() + (rots[1] - mc.player.getPitch()) * speed;
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
    }
}
