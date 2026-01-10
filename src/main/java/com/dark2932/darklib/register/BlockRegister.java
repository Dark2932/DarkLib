package com.dark2932.darklib.register;

import com.dark2932.darklib.block.BlockBase;
import com.dark2932.darklib.block.BlockEntry;
import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class BlockRegister extends IRegister<Block> {

    private final DeferredRegister<Block> BLOCKS;
    private final ItemRegister itemRegister;

    public BlockRegister(String modid, ItemRegister itemRegister) {
        super(DeferredRegister.create(ForgeRegistries.BLOCKS, modid));
        this.BLOCKS = super.getDeferredRegister();
        this.itemRegister = itemRegister;
    }

    public BlockEntry createBlock(BlockBase base) {
        return createBlock(base.getName(), base.getBlockProperties(), base.getItemProperties());
    }

    public BlockEntry createBlock(String name) {
        return createBlock(name, BlockBehaviour.Properties.of());
    }

    public BlockEntry createBlock(String name, BlockBehaviour.Properties blockProperties) {
        return createBlock(name, blockProperties, new Item.Properties());
    }

    public BlockEntry createBlock(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        return createBlock(name, () -> new Block(blockProperties), itemProperties);
    }

    public BlockEntry createBlock(String name, Supplier<? extends Block> blockSupplier) {
        return createBlock(name, blockSupplier, new Item.Properties());
    }

    public BlockEntry createBlock(String name, Supplier<? extends Block> blockSupplier, Item.Properties properties) {
        final RegistryObject<Block> blockObj = BLOCKS.register(name, blockSupplier);
        final Supplier<BlockItem> itemSupplier = () -> new BlockItem(blockObj.get(), properties);
        final ItemEntry item = itemRegister.createItem(name, itemSupplier);
        return new BlockEntry(blockObj, item.itemRegistry(), blockSupplier, itemSupplier);
    }

    public static BlockRegister of(String modid, ItemRegister itemRegister) {
        return new BlockRegister(modid, itemRegister);
    }

}
