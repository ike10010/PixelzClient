package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Other/Timer - changes game tick speed.
 */
public class TimerModule extends Module {
    public float speed = 2.0f;

    public TimerModule() {
        super("Timer", "Wurst Timer - game speed", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        // 1.21.11: client tick speed via MinecraftClient.renderTickCounter is not directly settable.
        // Full impl would mixin into TickRateManager / RenderTickCounter.
        // Placeholder: apply speed effect via motion multiplier for demonstration
        if (speed != 1f) {
            mc.player.setVelocity(mc.player.getVelocity().multiply(speed / 1.1));
        }
    }
}
