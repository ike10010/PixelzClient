package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

/**
 * Wurst Blocks/AutoTool - auto switches to best tool when mining.
 */
public class AutoToolModule extends Module {
    public AutoToolModule() {
        super("AutoTool", "Wurst AutoTool - auto best tool", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.crosshairTarget == null) return;
        if (!(mc.crosshairTarget instanceof net.minecraft.util.hit.BlockHitResult bhr)) return;
        BlockState state = mc.world.getBlockState(bhr.getBlockPos());
        if (state.isAir()) return;
        if (!mc.options.attackKey.isPressed()) return;

        int bestSlot = -1;
        float bestSpeed = 1f;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }
        if (bestSlot != -1) mc.player.getInventory().setSelectedSlot(bestSlot);
    }
}
