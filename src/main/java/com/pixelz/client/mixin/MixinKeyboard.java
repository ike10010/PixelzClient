package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (PixelzClient.INSTANCE != null && PixelzClient.INSTANCE.getModuleManager() != null) {
            // Ignore unknown key
            if (key == -1) return;
            PixelzClient.INSTANCE.getModuleManager().onKey(key, action);
        }
    }
}
