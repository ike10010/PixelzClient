package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

/**
 * Player - AutoEat: consumes food when hunger low.
 */
public class AutoEatModule extends Module {
    public int hungerThreshold = 16;
    public int healthThreshold = 16;

    public AutoEatModule() {
        super("AutoEat", "AutoEat - eats when hungry", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.player.getHungerManager().getFoodLevel() > hungerThreshold && mc.player.getHealth() > healthThreshold) return;
        if (mc.player.isUsingItem()) return;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.contains(DataComponentTypes.FOOD)) {
                int prev = mc.player.getInventory().getSelectedSlot();
                mc.player.getInventory().setSelectedSlot(i);
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                // will start using item automatically
                break;
            }
        }
    }
}
