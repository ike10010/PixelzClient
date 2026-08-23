package com.pixelz.client.module.modules;

import com.pixelz.client.module.Category;
import com.pixelz.client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import java.util.HashSet;
import java.util.Set;

public class BlockESPModule extends Module {
    public Set<Block> blocks = new HashSet<>(Set.of(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.ANCIENT_DEBRIS, Blocks.CHEST, Blocks.ENDER_CHEST));

    public BlockESPModule() {
        super("BlockESP", "Highlights selected blocks", Category.RENDER);
    }
}
