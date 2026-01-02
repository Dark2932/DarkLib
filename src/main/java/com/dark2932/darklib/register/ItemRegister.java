package com.dark2932.darklib.register;

import com.dark2932.darklib.item.ItemBase;
import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class ItemRegister extends IRegister<Item> {

    private final DeferredRegister<Item> ITEMS;

    public ItemRegister(String modid) {
        super(DeferredRegister.create(ForgeRegistries.ITEMS, modid));
        this.ITEMS = super.getDeferredRegister();
    }

    public ItemEntry createItem(ItemBase base) {
        return createItem(base.getName(), base.getProperties());
    }

    public ItemEntry createItem(String name) {
        return createItem(name, new Item.Properties());
    }

    public ItemEntry createItem(String name, Item.Properties properties) {
        return createItem(name, () -> new Item(properties));
    }

    public ItemEntry createItem(String name, Supplier<? extends Item> itemSupplier) {
        final RegistryObject<Item> itemObj = ITEMS.register(name, itemSupplier);
        return new ItemEntry(itemObj, itemSupplier);
    }

    public static ItemRegister of(String modid) {
        return new ItemRegister(modid);
    }

}
