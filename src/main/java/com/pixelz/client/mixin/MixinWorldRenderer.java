package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    // 1.21.11 WorldRenderer.render signature changed drastically (11 params, FrameGraph etc)
    // Use generic RETURN inject with only CallbackInfo to stay compatible across versions.
    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(CallbackInfo ci) {
        if (PixelzClient.INSTANCE == null) return;
        var mm = PixelzClient.INSTANCE.getModuleManager();
        if (mm == null) return;
        float tickDelta = 0f;
        try {
            var mc = PixelzClient.mc();
            if (mc != null && mc.getRenderTickCounter() != null) {
                tickDelta = mc.getRenderTickCounter().getTickProgress(true);
            }
        } catch (Exception ignored) {}
        mm.onWorldRender(tickDelta);
    }
}
