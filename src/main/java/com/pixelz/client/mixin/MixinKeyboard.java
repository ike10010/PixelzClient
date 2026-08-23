package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.Keyboard;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
        if (PixelzClient.INSTANCE != null && PixelzClient.INSTANCE.getModuleManager() != null) {
            int key = input.key();
            if (key == -1) return;
            PixelzClient.INSTANCE.getModuleManager().onKey(key, action);
        }
    }
}
