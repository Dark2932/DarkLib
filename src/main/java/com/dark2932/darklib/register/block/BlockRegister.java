package com.dark2932.darklib.register.block;

import com.dark2932.darklib.util.BlockEntry;
import com.dark2932.darklib.util.ItemEntry;
import com.dark2932.darklib.register.IRegister;
import com.dark2932.darklib.register.item.BlockItemRegister;
import com.dark2932.darklib.util.annotation.NameProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class BlockRegister extends IRegister<Block> {

    private final DeferredRegister<Block> BLOCKS;
    private @Nullable BlockItemRegister blockItemRegister;

    public BlockRegister(String modid) {
        super(modid, "block", DeferredRegister.create(ForgeRegistries.BLOCKS, modid));
        this.BLOCKS = super.getDeferredRegister();
        this.blockItemRegister = null;
    }

    public BlockEntry newBlock(String name) {
        return newBlock(name, BlockBehaviour.Properties.of());
    }

    public BlockEntry newBlock(String name, BlockBehaviour.Properties blockProperties) {
        return newBlock(name, blockProperties, new Item.Properties());
    }

    public BlockEntry newBlock(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        return newBlock(name, () -> new Block(blockProperties), itemProperties);
    }

    public BlockEntry newBlock(String name, Supplier<? extends Block> blockSupplier) {
        return newBlock(name, blockSupplier, new Item.Properties());
    }

    public BlockEntry newBlock(String name, Supplier<? extends Block> blockSupplier, Item.Properties itemProperties) {
        RegistryObject<Block> blockRegistry = BLOCKS.register(name, blockSupplier);
        if (blockItemRegister == null) blockItemRegister = BlockItemRegister.of(super.getModId());
        ItemEntry itemEntry = blockItemRegister.newBlockItem(blockRegistry, itemProperties);
        return new BlockEntry(blockRegistry, itemEntry.itemRegistry());
    }

    public BlockEntry newBlock(Class<? extends Block> c) {
        return newBlock(c, new Item.Properties());
    }

    public BlockEntry newBlock(Class<? extends Block> c, Item.Properties itemProperties) {
        NameProvider annotation = c.getAnnotation(NameProvider.class);
        return newBlock(annotation.name(), () -> {
            try {
                return c.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }, itemProperties);
    }

    public BlockEntry newBlockWithoutItem(String name) {
        return newBlockWithoutItem(name, BlockBehaviour.Properties.of());
    }

    public BlockEntry newBlockWithoutItem(String name, BlockBehaviour.Properties blockProperties) {
        return newBlockWithoutItem(name, () -> new Block(blockProperties));
    }

    public BlockEntry newBlockWithoutItem(String name, Supplier<? extends Block> blockSupplier) {
        RegistryObject<Block> blockRegistry = BLOCKS.register(name, blockSupplier);
        return new BlockEntry(blockRegistry, null);
    }

    public BlockEntry newBlockWithoutItem(Class<? extends Block> c) {
        NameProvider annotation = c.getAnnotation(NameProvider.class);
        return newBlockWithoutItem(annotation.name(), () -> {
            try {
                return c.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Collection<RegistryObject<Block>> getBlocks() {
        return BLOCKS.getEntries();
    }

    @Nullable
    public Collection<RegistryObject<Item>> getBlockItems() {
        return (blockItemRegister != null) ? blockItemRegister.getItems() : null;
    }

    @Nullable
    public BlockItemRegister getBlockItemRegister() {
        return blockItemRegister;
    }

    @Override
    public void init(IEventBus bus) {
        super.init(bus);
        if (blockItemRegister != null) blockItemRegister.init(bus);
    }

    public static BlockRegister of(String modid) {
        return new BlockRegister(modid);
    }

}
