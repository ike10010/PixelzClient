package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

public class HudModule extends Module {
    public boolean watermark = true;
    public boolean arrayList = true;
    public boolean notifications = true;

    public HudModule() {
        super("HUD", "In-game overlay", Category.RENDER);
        setEnabled(true);
    }
}
