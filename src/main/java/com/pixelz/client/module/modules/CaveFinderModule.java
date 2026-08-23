package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Render/CaveFinder - highlights caves and air pockets underground.
 */
public class CaveFinderModule extends Module {
    public int caveOpacity = 80;

    public CaveFinderModule() {
        super("CaveFinder", "Wurst CaveFinder - finds caves and holes", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    @Override
    protected void onDisable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
