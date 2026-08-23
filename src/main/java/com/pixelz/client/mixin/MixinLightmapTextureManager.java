package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.modules.FullBrightModule;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LightmapTextureManager.class)
public class MixinLightmapTextureManager {

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setColor(IIII)V"), index = 3)
    private int modifyGamma(int color) {
        if (PixelzClient.INSTANCE == null) return color;
        var mm = PixelzClient.INSTANCE.getModuleManager();
        if (mm == null) return color;
        var fb = mm.get(FullBrightModule.class);
        if (fb != null && fb.isEnabled()) {
            // force max brightness by returning white-ish?
            // We keep original but gamma option already does most
            return color | 0xFF;
        }
        return color;
    }
}
