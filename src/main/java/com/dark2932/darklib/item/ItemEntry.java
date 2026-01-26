package com.dark2932.darklib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Dark2932
 */
public record ItemEntry(RegistryObject<Item> itemRegistry) {

    public Item item() {
        return itemRegistry.get();
    }

    public ItemStack stack() {
        return item().getDefaultInstance();
    }

}
