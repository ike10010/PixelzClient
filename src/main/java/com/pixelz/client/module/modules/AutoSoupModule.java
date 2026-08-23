package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

/**
 * Player - AutoSoup: drinks mushroom stew when health low.
 */
public class AutoSoupModule extends Module {
    public int healthThreshold = 12;

    public AutoSoupModule() {
        super("AutoSoup", "AutoSoup - heals with stew", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.player.getHealth() > healthThreshold) return;
        if (mc.player.isUsingItem()) return;
        for (int i = 0; i < 36; i++) {
            var stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() == Items.MUSHROOM_STEW || stack.getItem() == Items.BEETROOT_SOUP) {
                int slot = i < 9 ? i : i - 9;
                // move to hotbar if needed and use
                if (i >= 9) {
                    mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, i, 0, net.minecraft.screen.slot.SlotActionType.QUICK_MOVE, mc.player);
                    return;
                }
                mc.player.getInventory().setSelectedSlot(slot);
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                break;
            }
        }
    }
}
