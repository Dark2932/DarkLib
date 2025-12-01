package com.dark2932.darklib.util;

import com.dark2932.darklib.block.BlockEntry;
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

    public void init(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TABS.register(bus);
    }

    public RegistryObject<Item> createItem(String name) {
        return createItem(name, new Item.Properties());
    }

    public RegistryObject<Item> createItem(String name, Item.Properties properties) {
        return createItem(name, () -> new Item(properties));
    }

    public RegistryObject<Item> createItem(String name, Supplier<? extends Item> item) {
        return ITEMS.register(name, item);
    }

    public BlockEntry createBlock(String name) {
        return createBlock(name, BlockBehaviour.Properties.of());
    }

    public BlockEntry createBlock(String name, BlockBehaviour.Properties blockProperties) {
        return createBlock(name, blockProperties, new Item.Properties());
    }

    public BlockEntry createBlock(String name, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        final Supplier<Block> blockSupplier = () -> new Block(blockProperties);
        RegistryObject<Block> blockObj = BLOCKS.register(name, blockSupplier);

        final Supplier<Item> itemSupplier = () -> new BlockItem(blockObj.get(), itemProperties);
        RegistryObject<Item> itemObj = createItem(name, itemSupplier);

        return new BlockEntry(blockObj, itemObj, blockSupplier, itemSupplier);
    }

    public BlockEntry createBlock(String name, Supplier<? extends Block> block, Supplier<? extends Item> blockItem) {
        final RegistryObject<Block> blockObj = BLOCKS.register(name, block);
        final RegistryObject<Item> itemObj = createItem(name, blockItem);
        return new BlockEntry(blockObj, itemObj, block, blockItem);
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