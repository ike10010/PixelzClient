package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealerModule extends Module {
    public int delay = 2;
    private int ticks;

    public ChestStealerModule() {
        super("ChestStealer", "Automatically steals items from chests", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (!(mc.currentScreen instanceof GenericContainerScreen screen)) return;
        if (ticks++ < delay) return;
        ticks = 0;
        var handler = screen.getScreenHandler();
        for (int i = 0; i < handler.slots.size() - 36; i++) {
            var stack = handler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                mc.interactionManager.clickSlot(handler.syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                break;
            }
        }
    }
}
