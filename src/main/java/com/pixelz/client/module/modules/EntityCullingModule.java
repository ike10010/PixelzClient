package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;

/**
 * Performance - EntityCulling (like Sodium/EntityCulling mod).
 * Skips rendering entities far away / behind walls.
 */
public class EntityCullingModule extends Module {
    public int maxDistance = 64;
    public boolean cullBehindWall = true;
    public boolean cullInvisible = true;

    public EntityCullingModule() {
        super("EntityCulling", "Performance EntityCulling - skip far/invisible entities", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        chat("EntityCulling enabled - max " + maxDistance + " blocks");
    }

    // Actual culling handled via MixinWorldRenderer + MixinEntity would be ideal.
    // This placeholder keeps the module structure; renderer mixins check isEnabled().
}
