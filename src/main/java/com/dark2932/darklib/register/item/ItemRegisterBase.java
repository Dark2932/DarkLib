package com.dark2932.darklib.register.item;

import com.dark2932.darklib.item.ItemEntry;
import com.dark2932.darklib.register.IRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class ItemRegisterBase extends IRegister<Item> {

    private final DeferredRegister<Item> ITEMS;

    protected ItemRegisterBase(String modid, String type) {
        super(modid, type, DeferredRegister.create(ForgeRegistries.ITEMS, modid));
        this.ITEMS = super.getDeferredRegister();
    }

    protected ItemEntry newItem(String name, Item.Properties itemProperties) {
        return newItem(name, () -> new Item(itemProperties));
    }

    protected ItemEntry newItem(String name, Supplier<? extends Item> itemSupplier) {
        RegistryObject<Item> itemRegistry = ITEMS.register(name, itemSupplier);
        return new ItemEntry(itemRegistry);
    }

    public Collection<RegistryObject<Item>> getItems() {
        return ITEMS.getEntries();
    }

}
