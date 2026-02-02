package com.dark2932.darklib.register.item;

import com.dark2932.darklib.item.ItemBase;
import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class ItemRegister extends ItemRegisterBase {

    public ItemRegister(String modid) {
        super(modid, "item");
    }

    public ItemEntry newItem(ItemBase base) {
        return newItem(base.getName(), base.getItemSupplier());
    }

    public ItemEntry newItem(String name) {
        return newItem(name, new Item.Properties());
    }

    public ItemEntry newItem(String name, Item.Properties itemProperties) {
        return super.newItem(name, itemProperties);
    }

    public ItemEntry newItem(String name, Supplier<? extends Item> itemSupplier) {
        return super.newItem(name, itemSupplier);
    }

    public static ItemRegister of(String modid) {
        return new ItemRegister(modid);
    }

}
