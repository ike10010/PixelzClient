package com.pixelz.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    public static float[] getRotationsTo(Entity entity) {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null) return new float[]{0,0};
        Vec3d eyes = mc.player.getEyePos();
        Vec3d target = entity.getBoundingBox().getCenter();
        // aim at center slightly up
        target = new Vec3d(target.x, entity.getBoundingBox().getCenter().y + 0.4, target.z);
        double dx = target.x - eyes.x;
        double dy = target.y - eyes.y;
        double dz = target.z - eyes.z;
        double dist = Math.sqrt(dx*dx + dz*dz);
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, dist));
        return new float[]{ MathHelper.wrapDegrees(yaw), MathHelper.clamp(pitch, -90, 90) };
    }
}
