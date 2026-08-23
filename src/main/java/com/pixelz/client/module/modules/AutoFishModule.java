package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

/**
 * Wurst Other/AutoFish - automatically reels and recasts fishing rod.
 * One of Wurst's most popular utility hacks.
 */
public class AutoFishModule extends Module {
    private int tick = 0;
    private boolean wasHooked = false;

    public AutoFishModule() {
        super("AutoFish", "Wurst AutoFish - auto fishing", Category.WORLD);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;
        if (mc.player.getMainHandStack().getItem() != Items.FISHING_ROD && mc.player.getOffHandStack().getItem() != Items.FISHING_ROD) return;

        // Simple logic: if bobber is in water and bobs, reel in and recast
        var fishHook = mc.player.fishHook;
        if (fishHook == null) {
            // not fishing, cast
            if (tick++ > 20) {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                tick = 0;
            }
            wasHooked = false;
            return;
        }

        // Detect catch via fishHook velocity change or when fishHook is biting (onGround false + motion change)
        // Yarn 1.21.11: check if hook is touching water and has caught fish via isTouchingWater + vertical motion
        if (fishHook.isTouchingWater()) {
            double vy = fishHook.getVelocity().y;
            // When fish bites, bobber dips: vy < -0.05
            if (vy < -0.05 && !wasHooked) {
                wasHooked = true;
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                tick = 0;
            } else if (vy > -0.01) {
                wasHooked = false;
            }
        }
        tick++;
        if (tick > 600) { // timeout recast every 30s
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            tick = 0;
        }
    }
}
