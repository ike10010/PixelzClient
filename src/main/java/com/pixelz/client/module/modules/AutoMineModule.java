package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

/**
 * World - AutoMine: automatically mines nearest block.
 */
public class AutoMineModule extends Module {
    public int range = 5;

    public AutoMineModule() {
        super("AutoMine", "AutoMine - mines nearest block", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        BlockPos origin = mc.player.getBlockPos();
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.iterateOutwards(origin, range, range, range)) {
            BlockState state = mc.world.getBlockState(pos);
            if (state.isAir()) continue;
            double d = mc.player.squaredDistanceTo(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5);
            if (d < bestDist) { bestDist = d; best = new BlockPos(pos); }
        }
        if (best == null) return;
        mc.interactionManager.attackBlock(best, net.minecraft.util.math.Direction.UP);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
