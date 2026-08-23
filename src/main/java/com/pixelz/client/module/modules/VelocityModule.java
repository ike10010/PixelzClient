package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import org.lwjgl.glfw.GLFW;

public class VelocityModule extends Module {
    public Mode mode = Mode.CANCEL;
    public double horizontal = 0.0;
    public double vertical = 0.0;

    public enum Mode { CANCEL, REDUCE, JUMP_RESET }

    public VelocityModule() {
        super("Velocity", "Prevents knockback", Category.COMBAT, -1);
    }

    // handled via mixin: Entity.pushAway etc, and packet handling
    public boolean shouldCancel() {
        return mode == Mode.CANCEL;
    }
}
