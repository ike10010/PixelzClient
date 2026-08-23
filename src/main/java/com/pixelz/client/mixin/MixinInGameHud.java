package com.pixelz.client.mixin;

import com.pixelz.client.PixelzClient;
import com.pixelz.client.module.modules.HudModule;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (PixelzClient.INSTANCE == null) return;
        var mm = PixelzClient.INSTANCE.getModuleManager();
        if (mm == null) return;
        var hud = mm.get(HudModule.class);
        if (hud == null || !hud.isEnabled()) return;

        var mc = PixelzClient.mc();
        if (mc.player == null) return;

        int y = 4;
        if (hud.watermark) {
            String text = "Pixelz §7- §f1.21.11 §8| §7" + mc.getCurrentFps() + " fps §8| §7LB-inspired";
            context.drawText(mc.textRenderer, text, 4, y, 0xFFDD55FF, true);
            y += 10;
        }

        if (hud.arrayList) {
            var modules = mm.getModules().stream()
                    .filter(m -> m.isEnabled() && !m.isHidden())
                    .sorted((a, b) -> mc.textRenderer.getWidth(b.getName()) - mc.textRenderer.getWidth(a.getName()))
                    .toList();
            int x = context.getScaledWindowWidth();
            int curY = 4;
            for (var m : modules) {
                String name = m.getDisplayName();
                int w = mc.textRenderer.getWidth(name);
                int bx = x - w - 4;
                // background
                context.fill(bx - 2, curY - 1, x - 2, curY + 8, 0x80000000);
                context.fill(bx - 2, curY - 1, bx, curY + 8, m.getCategory().getColor());
                context.drawText(mc.textRenderer, name, bx, curY, 0xFFFFFFFF, true);
                curY += 9;
            }
        }
    }
}
