package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.modules.NoSlowModule;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Modules like Scaffold, Speed etc already handle via PixelzClient tick
        // But we can also ensure no-slow etc here if needed
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        // NoSlow: Pixelz Client style - prevents slowdown when eating/blocking/bow
        if (PixelzClient.INSTANCE != null) {
            var mm = PixelzClient.INSTANCE.getModuleManager();
            if (mm != null) {
                var noSlow = mm.get(NoSlowModule.class);
                if (noSlow != null && noSlow.isEnabled()) {
                    ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;
                    // Reset sprint slowdown etc — handled via velocity still
                    // This is placeholder for mixin logic; actual slow removal is via input modification
                }
            }
        }
    }
}
