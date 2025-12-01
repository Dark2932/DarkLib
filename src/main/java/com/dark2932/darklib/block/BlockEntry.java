package com.dark2932.darklib.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/11/26
 */
public record BlockEntry(RegistryObject<Block> block, RegistryObject<Item> item, Supplier<? extends Block> blockSupplier, Supplier<? extends Item> itemSupplier) {

    public RegistryObject<Block> getBlockObj() {
        return block;
    }

    public RegistryObject<Item> getItemObj() {
        return item;
    }

    public Block getBlock() {
        return block.get();
    }

    public Item getItem() {
        return item.get();
    }

    public ItemStack getStack() {
        return getItem().getDefaultInstance();
    }

}