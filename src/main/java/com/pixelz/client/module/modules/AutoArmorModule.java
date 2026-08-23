package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class AutoArmorModule extends Module {
    private int delay = 0;

    public AutoArmorModule() {
        super("AutoArmor", "Automatically equips best armor", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (delay++ < 5) return;
        delay = 0;
        // Check if any armor slot empty (generic without ArmorItem class)
        boolean hasEmpty = false;
        for (int a = 0; a < 4; a++) {
            // Use generic inventory check via stack emptiness
            try {
                if (mc.player.getInventory().getStack(36 + a).isEmpty()) { hasEmpty = true; break; }
            } catch (Exception e) {
                hasEmpty = true;
                break;
            }
        }
        if (!hasEmpty) return;
        for (int i = 9; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            String name = stack.getItem().toString().toLowerCase();
            if (name.contains("helmet") || name.contains("chestplate") || name.contains("leggings") || name.contains("boots") || name.contains("armor")) {
                mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                break;
            }
        }
    }
}
