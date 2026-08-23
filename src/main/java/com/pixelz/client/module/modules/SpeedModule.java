package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import org.lwjgl.glfw.GLFW;

public class SpeedModule extends Module {
    public Mode mode = Mode.STRAFE;
    public double multiplier = 1.6;

    public enum Mode { STRAFE, BHOP, YPORT }

    public SpeedModule() {
        super("Speed", "Makes you faster", Category.MOVEMENT, GLFW.GLFW_KEY_G);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!mc.player.isOnGround() && !(mode == Mode.BHOP)) return;
        if (mc.player.isSneaking() || mc.player.isInLava() || mc.player.isTouchingWater()) return;

        float yaw = mc.player.getYaw();
        switch (mode) {
            case STRAFE -> {
                if (mc.player.forwardSpeed == 0 && mc.player.sidewaysSpeed == 0) return;
                mc.player.setVelocity(
                        Math.cos(Math.toRadians(yaw + 90)) * 0.3 * multiplier,
                        mc.player.getVelocity().y,
                        Math.sin(Math.toRadians(yaw + 90)) * 0.3 * multiplier
                );
                if (mc.player.isOnGround()) mc.player.jump();
            }
            case BHOP -> {
                if (mc.player.isOnGround()) {
                    mc.player.jump();
                    mc.player.setVelocity(mc.player.getVelocity().x * multiplier, mc.player.getVelocity().y, mc.player.getVelocity().z * multiplier);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x * 0.99, mc.player.getVelocity().y, mc.player.getVelocity().z * 0.99);
                }
            }
            case YPORT -> {
                if (mc.player.isOnGround()) {
                    mc.player.setVelocity(mc.player.getVelocity().x * 1.4, 0.42, mc.player.getVelocity().z * 1.4);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x, -1.0, mc.player.getVelocity().z);
                }
            }
        }
    }
}
