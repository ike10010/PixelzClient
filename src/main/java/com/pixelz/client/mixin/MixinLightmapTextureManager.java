package com.pixelz.client.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {
    // 1.21.11: LightmapTextureManager.update no longer uses NativeImage.setColor(IIII) - FullBright now handled via gamma in FullBrightModule.
    // Kept as placeholder to avoid injection failure (was InvalidInjectionException on 1.21.11).
}
