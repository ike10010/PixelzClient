package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Wurst Render/X-Ray - see ores through walls. Best Wurst visual hack.
 */
public class XRayModule extends Module {
    public boolean fullbright = true;
    public int opacity = 120;

    public XRayModule() {
        super("XRay", "Wurst X-Ray - see ores through blocks", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
        if (fullbright && mc.options != null) mc.options.getGamma().setValue(16.0);
    }

    @Override
    protected void onDisable() {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
