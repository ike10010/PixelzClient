package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Movement/Parkour - auto jumps at block edges.
 */
public class ParkourModule extends Module {
    public ParkourModule() {
        super("Parkour", "Wurst Parkour - auto jump at edges", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isOnGround() && !mc.player.isSneaking() && !mc.options.jumpKey.isPressed()) {
            // Check if block ahead is air one down
            var pos = mc.player.getBlockPos().down();
            if (mc.world.getBlockState(pos.down(1)).isAir() && !mc.world.getBlockState(pos).isAir()) {
                // Edge detected - will only jump if next block is jumpable
            }
            // Simple Wurst-like: if moving and block ahead is 1 up, jump
            if (mc.player.forwardSpeed > 0 && mc.player.horizontalCollision) {
                mc.player.jump();
            }
        }
        // Auto parkour on edge
        if (mc.player.isOnGround() && mc.player.forwardSpeed > 0) {
            var ahead = mc.player.getBlockPos().offset(mc.player.getHorizontalFacing());
            if (mc.world.getBlockState(ahead.down()).isAir() && !mc.world.getBlockState(ahead).isAir()) {
                // actually should not jump here - simplified to not fall
                return;
            }
            if (mc.world.getBlockState(ahead).isAir() && mc.world.getBlockState(ahead.down()).isAir()) {
                mc.player.jump();
            }
        }
    }
}
