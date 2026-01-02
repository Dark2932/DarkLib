package com.dark2932.darklib.register;

import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class CreativeTabRegister extends IRegister<CreativeModeTab> {

    private final DeferredRegister<CreativeModeTab> TABS;

    public CreativeTabRegister(String modid) {
        super(DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modid));
        this.TABS = super.getDeferredRegister();
    }

    public RegistryObject<CreativeModeTab> createTab(String name, ItemEntry icon, ItemRegister... registers) {
        return createTab(name, icon, (parameters, output) -> {
            for (ItemRegister register : registers) {
                register.getDeferredRegister().getEntries().forEach(item -> output.accept(item.get()));
            }
        });
    }

    public RegistryObject<CreativeModeTab> createTab(String name, ItemEntry icon, DisplayItemsGenerator displayItemsGenerator) {
        return createTab(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + name))
            .icon(icon::stack)
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
