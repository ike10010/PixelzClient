package com.pixelz.client.module.modules;

import com.pixelz.client.gui.AltManagerScreen;
import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Client - AltManager: Wurst-style alt manager (cracked + premium via Prism hint).
 * Opens AltManager GUI to add/star/login alts.
 */
public class AltManagerModule extends Module {
    public AltManagerModule() {
        super("AltManager", "AltManager - Wurst-style alt list (cracked)", Category.CLIENT);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null) {
            mc.setScreen(new AltManagerScreen(null));
        }
        // Keep enabled false like ClickGUI - it's a screen opener
        setEnabled(false);
    }
}
