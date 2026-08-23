package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Movement/Blink - suspends packets for lag exploit / teleport.
 */
public class BlinkModule extends Module {
    public BlinkModule() {
        super("Blink", "Wurst Blink - chokes packets", Category.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        chat("Blink enabled - packets choked (Wurst-style). Disable to blink forward.");
    }

    @Override
    protected void onDisable() {
        chat("Blink disabled - flushing packets.");
    }

    // Packet choking would be handled via Mixin cancellations in a full impl (NetworkHandler).
    // Placeholder keeps module structure Wurst-accurate.
}
