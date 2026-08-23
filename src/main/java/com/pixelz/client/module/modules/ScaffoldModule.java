package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class ScaffoldModule extends Module {
    public boolean tower = true;
    public boolean safeWalk = true;

    public ScaffoldModule() {
        super("Scaffold", "Automatically places blocks under you", Category.WORLD, GLFW.GLFW_KEY_N);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        Vec3d pos = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        BlockPos below = BlockPos.ofFloored(pos.add(0, -1, 0));
        if (!mc.world.getBlockState(below).isAir()) return;

        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem) {
                slot = i;
                break;
            }
        }
        if (slot == -1) return;

        int prev = mc.player.getInventory().getSelectedSlot();
        mc.player.getInventory().setSelectedSlot(slot);

        BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(below), Direction.UP, below, false);
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
        mc.player.swingHand(Hand.MAIN_HAND);

        if (tower && mc.options.jumpKey.isPressed() && mc.player.isOnGround()) {
            mc.player.jump();
        }

        mc.player.getInventory().setSelectedSlot(prev);
    }
}
