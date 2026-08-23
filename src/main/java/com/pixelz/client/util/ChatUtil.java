package com.pixelz.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {
    public static void info(String msg) {
        var mc = MinecraftClient.getInstance();
        if (mc.player != null) mc.player.sendMessage(Text.literal("§8[§dPixelz§8] §7" + msg), false);
    }
    public static void action(String msg) {
        var mc = MinecraftClient.getInstance();
        if (mc.player != null) mc.player.sendMessage(Text.literal("§8[§dPixelz§8] §7" + msg), true);
    }
}
