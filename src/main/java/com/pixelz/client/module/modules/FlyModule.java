package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FlyModule extends Module {
    public double speed = 1.0;
    public Mode mode = Mode.VANILLA;

    public enum Mode { VANILLA, CREATIVE }

    public FlyModule() {
        super("Fly", "Allows you to fly", Category.MOVEMENT, GLFW.GLFW_KEY_F);
    }

    @Override
    protected void onEnable() {
        if (mc.player == null) return;
        chat("Fly enabled - mode: " + mode);
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().allowFlying = false;
        if (!mc.player.isCreative()) {
            // reset velocity
            Vec3d v = mc.player.getVelocity();
            mc.player.setVelocity(v.x, Math.min(v.y, 0), v.z);
        }
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        switch (mode) {
            case VANILLA -> {
                mc.player.getAbilities().allowFlying = true;
                // vanilla fly speed
                float flySpeed = (float)(0.05f * speed);
                mc.player.getAbilities().setFlySpeed(flySpeed);
                if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(mc.player.getVelocity().x, 0.5 * speed, mc.player.getVelocity().z);
                if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(mc.player.getVelocity().x, -0.5 * speed, mc.player.getVelocity().z);
            }
            case CREATIVE -> {
                mc.player.getAbilities().flying = true;
                mc.player.getAbilities().allowFlying = true;
            }
        }
    }
}
