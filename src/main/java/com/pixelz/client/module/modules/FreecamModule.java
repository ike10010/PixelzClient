package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.util.math.Vec3d;

/**
 * Wurst Render/Freecam - ghost camera with noclip.
 */
public class FreecamModule extends Module {
    private Vec3d savedPos;
    private float savedYaw, savedPitch;
    public double speed = 1.0;

    public FreecamModule() {
        super("Freecam", "Wurst Freecam - out-of-body camera", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null) return;
        savedPos = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        savedYaw = mc.player.getYaw();
        savedPitch = mc.player.getPitch();
        mc.player.noClip = true;
        mc.player.getAbilities().flying = true;
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;
        mc.player.noClip = false;
        if (!mc.player.isCreative()) mc.player.getAbilities().flying = false;
        if (savedPos != null) {
            mc.player.setPos(savedPos.x, savedPos.y, savedPos.z);
            mc.player.setYaw(savedYaw);
            mc.player.setPitch(savedPitch);
        }
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        mc.player.noClip = true;
        // simple freecam movement is vanilla flying
        float flySpeed = (float)(0.05f * speed);
        mc.player.getAbilities().setFlySpeed(flySpeed);
    }
}
