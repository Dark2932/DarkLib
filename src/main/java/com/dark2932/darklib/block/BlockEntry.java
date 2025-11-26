package com.dark2932.darklib.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Dark2932
 * @date 2025/11/24
 */
public record BlockEntry(RegistryObject<Block> block, RegistryObject<Item> item) {

    public Block getBlock() {
        return block.get();
    }

    public RegistryObject<Block> getBlockObj() {
        return block;
    }

    public Item getItem() {
        return item.get();
    }

    public RegistryObject<Item> getItemObj() {
        return item;
    }

}