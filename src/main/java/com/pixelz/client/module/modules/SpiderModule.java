package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Movement - Spider: climbs vertical walls like a spider.
 */
public class SpiderModule extends Module {
    public double climbSpeed = 0.2;

    public SpiderModule() {
        super("Spider", "Spider - climb walls", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.horizontalCollision && mc.player.forwardSpeed > 0) {
            mc.player.setVelocity(mc.player.getVelocity().x, climbSpeed, mc.player.getVelocity().z);
        }
    }
}
