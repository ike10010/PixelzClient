package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Performance - FastRender (fast math, no fancy rendering).
 */
public class FastRenderModule extends Module {
    public boolean fastMath = true;
    public boolean disableEnchantGlint = false;

    public FastRenderModule() {
        super("FastRender", "Performance FastRender - fast math & no fancy effects", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        if (mc.options != null) {
            try { mc.options.getDistortionEffectScale().setValue(0.0); } catch (Exception ignored) {}
            try { mc.options.getFovEffectScale().setValue(0.0); } catch (Exception ignored) {}
        }
    }

    @Override
    protected void onDisable() {
        if (mc.options != null) {
            try { mc.options.getDistortionEffectScale().setValue(1.0); } catch (Exception ignored) {}
            try { mc.options.getFovEffectScale().setValue(1.0); } catch (Exception ignored) {}
        }
    }
}
