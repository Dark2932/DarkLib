package com.dark2932.darklib.util;

import com.dark2932.darklib.block.BlockBase;
import com.dark2932.darklib.block.BlockEntry;
import com.dark2932.darklib.item.ItemBase;
import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/11/26
 */
public class IRegister {

    public final DeferredRegister<Item> ITEMS;
    public final DeferredRegister<Block> BLOCKS;
    public final DeferredRegister<CreativeModeTab> TABS;

    public IRegister(String modid) {
        this.ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        this.BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        this.TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modid);
    }

    public void init(IEventBus bus, DeferredRegister<?>... registers) {
        Arrays.asList(registers).forEach(register -> register.register(bus));
    }

    public void initAll(IEventBus bus) {
        init(bus, ITEMS, BLOCKS, TABS);
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
        final ItemEntry item = createItem(name, itemSupplier);
        return new BlockEntry(blockObj, item.getItemObj(), blockSupplier, itemSupplier);
    }

    public BlockEntry createBlock(String name, Supplier<? extends Block> blockSupplier, Supplier<? extends BlockItem> itemSupplier) {
        final RegistryObject<Block> blockObj = BLOCKS.register(name, blockSupplier);
        final ItemEntry item = createItem(name, itemSupplier);
        return new BlockEntry(blockObj, item.getItemObj(), blockSupplier, itemSupplier);
    }

    public RegistryObject<CreativeModeTab> createTabWithIcon(String name, Supplier<ItemStack> icon) {
        return createTab(name, icon, ITEMS.getEntries());
    }

    public RegistryObject<CreativeModeTab> createTab(String name, Supplier<ItemStack> icon, Collection<RegistryObject<Item>> items) {
        return createTab(name, icon, (parameters, output) -> items.forEach((item) -> output.accept(item.get())));
    }

    public RegistryObject<CreativeModeTab> createTab(String name, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
        return createTab(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + name))
            .icon(icon)
            .displayItems(displayItemsGenerator)
            .build()
        );
    }

    public RegistryObject<CreativeModeTab> createTab(String name, Supplier<CreativeModeTab> tab) {
        return TABS.register(name, tab);
    }

}