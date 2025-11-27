package com.dark2932.darklib.util;

import com.dark2932.darklib.block.BlockBase;
import com.dark2932.darklib.block.BlockEntry;
import com.dark2932.darklib.item.ItemBase;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/11/26
 */
public class IRegister {

    private final IRegister.IItem ItemRegister;
    private final IRegister.IBlock BlockRegister;
    private final IRegister.ICreativeTab TabRegister;
    public final DeferredRegister<Item> ITEMS;
    public final DeferredRegister<Block> BLOCKS;
    public final DeferredRegister<CreativeModeTab> TABS;

    public IRegister(String modid) {
        this.ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        this.BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        this.TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modid);
        this.ItemRegister = new IItem(ITEMS);
        this.BlockRegister = new IBlock(BLOCKS, ItemRegister);
        this.TabRegister = new ICreativeTab(TABS);
    }

    public void init(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TABS.register(bus);
    }

    public IItem item() {
        return ItemRegister;
    }

    public IBlock block() {
        return BlockRegister;
    }

    public ICreativeTab tab() {
        return TabRegister;
    }

    public static class IItem {

        private final DeferredRegister<Item> REGISTRY;

        private IItem(DeferredRegister<Item> registry) {
            this.REGISTRY = registry;
        }

        public RegistryObject<Item> of(String name) {
            return of(name, ItemBase::new);
        }

        public RegistryObject<Item> of(String name, Item.Properties properties) {
            return of(name, () -> new ItemBase(properties));
        }

        public RegistryObject<Item> of(String name, Supplier<? extends Item> item) {
            return REGISTRY.register(name, item);
        }

    }

    public static class IBlock {

        private final DeferredRegister<Block> REGISTRY;
        private final IRegister.IItem ItemRegister;

        private IBlock(DeferredRegister<Block> registry, IRegister.IItem itemRegister) {
            this.REGISTRY = registry;
            this.ItemRegister = itemRegister;
        }

        public BlockEntry of(String name) {
            return of(name, BlockBase::new);
        }

        public BlockEntry of(String name, BlockBehaviour.Properties properties) {
            return of(name, () -> new BlockBase(properties));
        }

        public BlockEntry of(String name, Supplier<? extends Block> block) {
            RegistryObject<Block> blockObj = REGISTRY.register(name, block);
            RegistryObject<Item> itemObj = ItemRegister.of(name, () -> new BlockBase.item(blockObj));
            return new BlockEntry(blockObj, itemObj);
        }

    }

    public static class ICreativeTab {

        private final DeferredRegister<CreativeModeTab> REGISTRY;

        private ICreativeTab(DeferredRegister<CreativeModeTab> registry) {
            this.REGISTRY = registry;
        }

        public RegistryObject<CreativeModeTab> of(String name, Supplier<? extends CreativeModeTab> tab) {
            return REGISTRY.register(name, tab);
        }

    }

}