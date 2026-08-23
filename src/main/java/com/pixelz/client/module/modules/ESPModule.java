package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import org.lwjgl.glfw.GLFW;

public class ESPModule extends Module {
    public boolean box = true;
    public boolean healthBar = true;
    public boolean onlyPlayers = false;
    public float lineWidth = 2.0f;

    public ESPModule() {
        super("ESP", "Highlights entities through walls", Category.RENDER, GLFW.GLFW_KEY_K);
    }
}
