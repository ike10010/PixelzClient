package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Client - AltManager: switch between Minecraft accounts in-game.
 */
public class AltManagerModule extends Module {
    public AltManagerModule() {
        super("AltManager", "AltManager - switch accounts", Category.CLIENT);
    }

    @Override
    protected void onEnable() {
        chat("AltManager: Use /pixelz help for account features - in full version opens AltScreen.");
        setEnabled(false);
    }
}
