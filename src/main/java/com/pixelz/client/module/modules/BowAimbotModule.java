package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import com.pixelz.client.util.RotationUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;

/**
 * Wurst Combat/BowAimbot - auto-aims bow at nearest entity.
 */
public class BowAimbotModule extends Module {
    public float range = 50f;
    public float fov = 360f;

    public BowAimbotModule() {
        super("BowAimbot", "Wurst BowAimbot - auto aim bow", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!mc.player.isUsingItem() || mc.player.getActiveItem().getItem() != Items.BOW) return;

        var entities = mc.world.getOtherEntities(mc.player, new Box(mc.player.getX()-range, mc.player.getY()-range, mc.player.getZ()-range, mc.player.getX()+range, mc.player.getY()+range, mc.player.getZ()+range),
            e -> e instanceof LivingEntity le && le.isAlive() && e != mc.player && mc.player.distanceTo(e) < range);
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (var e : entities) {
            double d = mc.player.distanceTo(e);
            if (d < bestDist) { bestDist = d; best = (LivingEntity)e; }
        }
        if (best == null) return;
        float[] rots = RotationUtil.getRotationsTo(best);
        mc.player.setYaw(rots[0]);
        mc.player.setPitch(rots[1]);
    }
}
