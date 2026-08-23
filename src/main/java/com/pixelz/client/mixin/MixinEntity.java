package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.modules.VelocityModule;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void onPushAwayFrom(Entity entity, CallbackInfo ci) {
        if (PixelzClient.INSTANCE == null) return;
        var mm = PixelzClient.INSTANCE.getModuleManager();
        if (mm == null) return;
        var vel = mm.get(VelocityModule.class);
        if (vel != null && vel.isEnabled() && vel.shouldCancel()) {
            // Only cancel for player
            Entity self = (Entity) (Object) this;
            if (self == PixelzClient.mc().player) ci.cancel();
        }
    }

    // Cancel velocity update via updateVelocity? For knockback packets more advanced handling would intercept packet
    @Inject(method = "addVelocity", at = @At("HEAD"), cancellable = true)
    private void onAddVelocity(double dx, double dy, double dz, CallbackInfo ci) {
        if (PixelzClient.INSTANCE == null) return;
        var vel = PixelzClient.INSTANCE.getModuleManager().get(VelocityModule.class);
        if (vel != null && vel.isEnabled()) {
            Entity self = (Entity) (Object) this;
            if (self == PixelzClient.mc().player) {
                if (vel.mode == VelocityModule.Mode.CANCEL) {
                    ci.cancel();
                } else if (vel.mode == VelocityModule.Mode.REDUCE) {
                    // scale down
                    Entity e = (Entity)(Object)this;
                    e.setVelocity(e.getVelocity().x * vel.horizontal, e.getVelocity().y * vel.vertical, e.getVelocity().z * vel.horizontal);
                    ci.cancel();
                }
            }
        }
    }
}
