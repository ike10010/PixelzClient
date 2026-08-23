package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import com.pixelz.client.util.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.Comparator;

public class KillAuraModule extends Module {
    public float range = 4.2f;
    public int aps = 12; // attacks per second
    public boolean rotate = true;
    public boolean onlyPlayers = false;
    private long lastAttack = 0;

    public KillAuraModule() {
        super("KillAura", "Automatically attacks nearby entities", Category.COMBAT, GLFW.GLFW_KEY_R);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        if (mc.player.isDead()) return;

        Entity target = findTarget();
        if (target == null) return;

        if (rotate && target instanceof LivingEntity le) {
            float[] rots = RotationUtil.getRotationsTo(le);
            mc.player.setYaw(rots[0]);
            mc.player.setPitch(rots[1]);
        }

        long now = System.currentTimeMillis();
        long delay = 1000 / aps;
        if (now - lastAttack < delay) return;
        if (mc.player.getAttackCooldownProgress(0) < 0.9f) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttack = now;
    }

    private Entity findTarget() {
        Vec3d eyes = mc.player.getEyePos();
        Box search = new Box(eyes.x - range, eyes.y - range, eyes.z - range, eyes.x + range, eyes.y + range, eyes.z + range);
        return mc.world.getOtherEntities(mc.player, search, e -> {
            if (!(e instanceof LivingEntity le)) return false;
            if (!le.isAlive() || le == mc.player) return false;
            if (le.isInvisible()) return false;
            if (onlyPlayers && !(e instanceof PlayerEntity)) return false;
            if (e.distanceTo(mc.player) > range) return false;
            // anti-bot: ignore dead, invisible armor stands etc
            return true;
        }).stream().min(Comparator.comparingDouble(e -> e.distanceTo(mc.player))).orElse(null);
    }
}
