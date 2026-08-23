package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

/**
 * World - VeinMiner: mines entire ore veins automatically.
 */
public class VeinMinerModule extends Module {
    public int range = 6;

    public VeinMinerModule() {
        super("VeinMiner", "VeinMiner - mines ore veins", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        if (mc.crosshairTarget == null || !(mc.crosshairTarget instanceof net.minecraft.util.hit.BlockHitResult bhr)) return;
        BlockPos pos = bhr.getBlockPos();
        BlockState state = mc.world.getBlockState(pos);
        if (state.isAir()) return;
        Block targetBlock = state.getBlock();
        // Search nearby same block type within range
        for (BlockPos p : BlockPos.iterateOutwards(pos, range, range, range)) {
            if (mc.world.getBlockState(p).getBlock() == targetBlock) {
                mc.interactionManager.attackBlock(p, net.minecraft.util.math.Direction.UP);
                mc.player.swingHand(Hand.MAIN_HAND);
                break;
            }
        }
    }
}
