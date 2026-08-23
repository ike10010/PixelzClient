package com.pixelz.client.module.modules;

import com.pixelz.client.gui.ClickGuiScreen;
import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

public class ClickGuiModule extends Module {
    public ClickGuiModule() {
        super("ClickGUI", "Opens the click gui", Category.CLIENT);
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null) mc.setScreen(new ClickGuiScreen());
        setEnabled(false);
    }
}
