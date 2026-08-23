package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Inject(method = "render", at = @At("RETURN"))
    private void afterRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        if (PixelzClient.INSTANCE == null) return;
        var mm = PixelzClient.INSTANCE.getModuleManager();
        if (mm == null) return;
        float tickDelta = tickCounter.getTickProgress(true);
        mm.onWorldRender(tickDelta);
    }
}
