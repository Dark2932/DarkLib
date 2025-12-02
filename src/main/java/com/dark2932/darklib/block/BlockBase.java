package com.dark2932.darklib.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * @author Dark2932
 * @date 2025/12/2
 */
public abstract class BlockBase {

    private final String name;
    private final BlockBehaviour.Properties blockProperties;
    private final Item.Properties itemProperties;

    protected BlockBase() {
        this.name = setName();
        this.blockProperties = setBlockProperties();
        this.itemProperties = setItemProperties();
    }

    protected abstract String setName();
    protected abstract BlockBehaviour.Properties setBlockProperties();
    protected abstract Item.Properties setItemProperties();

    public String getName() {
        return name;
    }

    public BlockBehaviour.Properties getBlockProperties() {
        return blockProperties;
    }

    public Item.Properties getItemProperties() {
        return itemProperties;
    }

}
