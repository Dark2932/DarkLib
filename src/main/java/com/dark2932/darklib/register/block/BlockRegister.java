package com.dark2932.darklib.register.block;

import com.dark2932.darklib.block.BlockBase;
import com.dark2932.darklib.block.BlockEntry;
import com.dark2932.darklib.item.ItemEntry;
import com.dark2932.darklib.register.IRegister;
import com.dark2932.darklib.register.item.ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class BlockRegister extends IRegister<Block> {

    private final DeferredRegister<Block> BLOCKS;
    private @Nullable ItemRegister itemRegister;

    public BlockRegister(String modid) {
        super(DeferredRegister.create(ForgeRegistries.BLOCKS, modid));
        this.BLOCKS = super.getDeferredRegister();
        this.itemRegister = null;
    }

    public BlockEntry newBlock(BlockBase base) {
        return newBlock(base.getName(), base.getBlockSupplier(), (base.getItemProperties() == null ? new Item.Properties() : base.getItemProperties()));
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
        ItemEntry itemEntry = newBlockItem(blockRegistry, itemProperties);
        return new BlockEntry(blockRegistry, itemEntry.itemRegistry());
    }

    private ItemEntry newBlockItem(RegistryObject<Block> blockRegistry, Item.Properties itemProperties) {
        ResourceLocation location = Objects.requireNonNull(blockRegistry.getId());
        if (itemRegister == null) itemRegister = ItemRegister.of(location.getNamespace());
        return itemRegister.newItem(location.getPath(), () -> new BlockItem(blockRegistry.get(), itemProperties));
    }

    public BlockEntry newBlockWithoutItem(BlockBase base) {
        return newBlockWithoutItem(base.getName(), base.getBlockSupplier());
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

    public Collection<RegistryObject<Block>> getBlocks() {
        return BLOCKS.getEntries();
    }

    @Nullable
    public Collection<RegistryObject<Item>> getBlockItems() {
        if (itemRegister != null) return itemRegister.getItems();
        else return null;
    }

    @Override
    public void init(IEventBus bus) {
        BLOCKS.register(bus);
        if (itemRegister != null) itemRegister.init(bus);
    }

    public static BlockRegister of(String modid) {
        return new BlockRegister(modid);
    }

}
