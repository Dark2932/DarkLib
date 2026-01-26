package com.dark2932.darklib.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public abstract class BlockBase {

    protected final String name;
    protected final BlockBehaviour.Properties blockProperties;
    protected final @Nullable Item.Properties itemProperties;
    protected final Supplier<? extends Block> blockSupplier;

    protected BlockBase() {
        this.name = setName();
        this.blockProperties = setBlockProperties();
        this.itemProperties = setItemProperties();
        this.blockSupplier = setBlockSupplier();
    }

    protected abstract String setName();

    protected BlockBehaviour.Properties setBlockProperties() {
        return BlockBehaviour.Properties.of();
    }

    protected Item.Properties setItemProperties() {
        return null;
    }

    protected Supplier<? extends Block> setBlockSupplier() {
        return () -> new Block(blockProperties);
    }

    public String getName() {
        return name;
    }

    public BlockBehaviour.Properties getBlockProperties() {
        return blockProperties;
    }

    @Nullable
    public Item.Properties getItemProperties() {
        return itemProperties;
    }

    public Supplier<? extends Block> getBlockSupplier() {
        return blockSupplier;
    }

}
