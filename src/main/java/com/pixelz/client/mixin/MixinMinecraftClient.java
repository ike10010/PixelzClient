package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Ensures modules get tick even when screen open (fallback)
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        // Save config on close
        if (PixelzClient.INSTANCE != null && PixelzClient.INSTANCE.getConfigManager() != null) {
            try {
                PixelzClient.INSTANCE.getConfigManager().save();
            } catch (Exception ignored) {}
        }
    }
}
