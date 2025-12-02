package com.dark2932.darklib.item;

import net.minecraft.world.item.Item;

/**
 * @author Dark2932
 * @date 2025/12/2
 */
public abstract class ItemBase {

    private final String name;
    private final Item.Properties properties;

    protected ItemBase() {
        this.name = setName();
        this.properties = setProperties();
    }

    protected abstract String setName();
    protected abstract Item.Properties setProperties();

    public String getName() {
        return name;
    }

    public Item.Properties getProperties() {
        return properties;
    }

}
