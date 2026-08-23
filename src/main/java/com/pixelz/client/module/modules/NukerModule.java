package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

/**
 * Wurst Blocks/Nuker - destroys blocks around player instantly.
 * Iconic Wurst hack from Blocks category.
 */
public class NukerModule extends Module {
    public int range = 6;
    public boolean onlyLegit = false;
    public boolean autoTool = true;

    public NukerModule() {
        super("Nuker", "Wurst Nuker - breaks blocks around you", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        BlockPos origin = BlockPos.ofFloored(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        for (BlockPos pos : BlockPos.iterateOutwards(origin, range, range, range)) {
            BlockState state = mc.world.getBlockState(pos);
            if (state.isAir()) continue;
            if (mc.player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > range * range) continue;
            if (mc.interactionManager.breakBlock(pos)) {
                mc.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
                break;
            }
        }
    }
}
