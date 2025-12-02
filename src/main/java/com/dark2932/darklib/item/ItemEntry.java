package com.dark2932.darklib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/12/3
 */
public record ItemEntry(RegistryObject<Item> item, Supplier<? extends Item> itemSupplier) {

    public RegistryObject<Item> getItemObj() {
        return item;
    }

    public Item getItem() {
        return item.get();
    }

    public ItemStack getStack() {
        return getItem().getDefaultInstance();
    }

    public Supplier<? extends Item> getItemSupplier() {
        return itemSupplier;
    }

}
