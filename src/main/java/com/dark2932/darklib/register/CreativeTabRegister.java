package com.dark2932.darklib.register;

import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/12/23
 */
public class CreativeTabRegister extends IRegister<CreativeModeTab> {

    public final DeferredRegister<CreativeModeTab> TABS;

    public CreativeTabRegister(String modid) {
        super(DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modid));
        this.TABS = super.getDeferredRegister();
    }

    public RegistryObject<CreativeModeTab> createTab(String name, ItemEntry entry, ItemRegister itemRegister) {
        return createTab(name, entry, itemRegister.ITEMS.getEntries());
    }

    public RegistryObject<CreativeModeTab> createTab(String name, ItemEntry entry, Collection<RegistryObject<Item>> items) {
        return createTab(name, entry, (parameters, output) -> items.forEach((item) -> output.accept(item.get())));
    }

    public RegistryObject<CreativeModeTab> createTab(String name, ItemEntry entry, CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
        return createTab(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + name))
            .icon(entry::getStack)
            .displayItems(displayItemsGenerator)
            .build()
        );
    }

    public RegistryObject<CreativeModeTab> createTab(String name, Supplier<CreativeModeTab> tab) {
        return TABS.register(name, tab);
    }

    public static CreativeTabRegister of(String modid) {
        return new CreativeTabRegister(modid);
    }

}
