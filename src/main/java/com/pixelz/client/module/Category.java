package com.pixelz.client.module;

import java.awt.Color;

public enum Category {
    COMBAT("Combat", new Color(220, 50, 50).getRGB()),
    MOVEMENT("Movement", new Color(50, 180, 220).getRGB()),
    PLAYER("Player", new Color(50, 220, 120).getRGB()),
    WORLD("World", new Color(220, 180, 50).getRGB()),
    RENDER("Render", new Color(180, 50, 220).getRGB()),
    CLIENT("Client", new Color(255, 255, 255).getRGB());

    private final String displayName;
    private final int color;

    Category(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }
}
