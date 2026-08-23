package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import com.pixelz.client.util.RotationUtil;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;

/**
 * Wurst Combat/CrystalAura - automatically detonates crystals near enemies.
 * Top Wurst crystal PvP hack.
 */
public class CrystalAuraModule extends Module {
    public float range = 12f;
    public float breakRange = 4.5f;
    public boolean rotate = true;
    private long lastAttack = 0;

    public CrystalAuraModule() {
        super("CrystalAura", "Wurst CrystalAura - auto crystal PvP", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        long now = System.currentTimeMillis();
        if (now - lastAttack < 100) return;

        var crystals = mc.world.getEntitiesByClass(EndCrystalEntity.class, new Box(mc.player.getX() - breakRange, mc.player.getY() - breakRange, mc.player.getZ() - breakRange, mc.player.getX() + breakRange, mc.player.getY() + breakRange, mc.player.getZ() + breakRange), e -> !e.isRemoved());
        if (crystals.isEmpty()) return;

        var target = crystals.stream().min((a,b) -> Float.compare(a.distanceTo(mc.player), b.distanceTo(mc.player))).orElse(null);
        if (target == null) return;

        // Find nearby enemies to confirm damage relevance, but for simplicity just break closest
        if (rotate) {
            float[] rots = RotationUtil.getRotationsTo(target);
            mc.player.setYaw(rots[0]);
            mc.player.setPitch(rots[1]);
        }
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttack = now;
    }
}
