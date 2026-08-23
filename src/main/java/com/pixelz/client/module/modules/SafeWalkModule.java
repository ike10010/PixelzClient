package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.util.math.BlockPos;

/**
 * Movement - SafeWalk: prevents walking off edges.
 */
public class SafeWalkModule extends Module {
    public SafeWalkModule() {
        super("SafeWalk", "SafeWalk - no walking off edges", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isOnGround() && !mc.player.isSneaking()) {
            BlockPos below = mc.player.getBlockPos().down();
            BlockPos ahead = mc.player.getBlockPos().offset(mc.player.getHorizontalFacing());
            // If block ahead is air and below that is air, we are at edge -> sneak
            if (mc.world.getBlockState(ahead).isAir() && mc.world.getBlockState(ahead.down()).isAir()) {
                // Simple safe walk: reduce velocity to 0 at edge
                if (mc.player.getVelocity().x != 0 || mc.player.getVelocity().z != 0) {
                    mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
                }
            }
        }
    }
}
