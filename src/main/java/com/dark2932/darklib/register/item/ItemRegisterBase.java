package com.dark2932.darklib.register.item;

import com.dark2932.darklib.register.IRegister;
import com.dark2932.darklib.util.ItemEntry;
import com.dark2932.darklib.util.annotation.NameProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
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

    protected ItemEntry newItem(Class<? extends Item> c) {
        NameProvider annotation = c.getAnnotation(NameProvider.class);
        return newItem(annotation.name(), () -> {
            try {
                return c.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Collection<RegistryObject<Item>> getItems() {
        return ITEMS.getEntries();
    }

}
