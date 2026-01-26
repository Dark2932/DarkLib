package com.dark2932.darklib.item;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public abstract class ItemBase {

    protected final String name;
    protected final Item.Properties itemProperties;
    protected final Supplier<? extends Item> itemSupplier;

    protected ItemBase() {
        this.name = setName();
        this.itemProperties = setItemProperties();
        this.itemSupplier = setItemSupplier();
    }

    protected abstract String setName();

    protected Item.Properties setItemProperties() {
        return new Item.Properties();
    }

    protected Supplier<? extends Item> setItemSupplier() {
        return () -> new Item(itemProperties);
    }

    public String getName() {
        return name;
    }

    public Item.Properties getItemProperties() {
        return itemProperties;
    }

    public Supplier<? extends Item> getItemSupplier() {
        return itemSupplier;
    }

}
