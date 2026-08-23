package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.item.Items;

/**
 * Combat - FastBow: enhances bow shooting speed.
 */
public class FastBowModule extends Module {
    public int pullTicks = 3; // vanilla 20

    public FastBowModule() {
        super("FastBow", "FastBow - fast bow shooting", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.player.getActiveItem().getItem() != Items.BOW) return;
        if (mc.player.getItemUseTime() >= pullTicks) {
            mc.interactionManager.stopUsingItem(mc.player);
        }
    }
}
