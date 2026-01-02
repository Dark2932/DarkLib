package com.dark2932.darklib.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public record BlockEntry(RegistryObject<Block> blockRegistry, RegistryObject<Item> itemRegistry, Supplier<? extends Block> blockSupplier, Supplier<? extends Item> itemSupplier) {

    public Block block() {
        return blockRegistry.get();
    }

    public Item item() {
        return itemRegistry.get();
    }

    public ItemStack stack() {
        return item().getDefaultInstance();
    }

}