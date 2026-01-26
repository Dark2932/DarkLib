package com.dark2932.darklib.register;

import com.dark2932.darklib.item.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.*;
import net.minecraft.world.item.Item;
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

    public RegistryObject<CreativeModeTab> newTab(String name, ItemEntry icon, String modid) {
        return newTab(name, icon, (parameters, output) -> {
            for (IRegister<?> register : MOD_REGISTERS.getModRegisters(modid).values()) {
                register.getDeferredRegister().getEntries().forEach(item -> {
                    output.accept((Item) item.get());
                });
            }
        });
    }

//    public RegistryObject<CreativeModeTab> newTab(String name, ItemEntry icon, ItemRegister... registers) {
//        return newTab(name, icon, (parameters, output) -> {
//            for (ItemRegister register : registers) {
//                register.getDeferredRegister().getEntries().forEach(item -> output.accept(item.get()));
//            }
//        });
//    }

    public RegistryObject<CreativeModeTab> newTab(String name, ItemEntry icon, DisplayItemsGenerator generator) {
        return newTab(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + name))
            .icon(icon::stack)
            .displayItems(generator)
            .build()
        );
    }

    public RegistryObject<CreativeModeTab> newTab(String name, Supplier<CreativeModeTab> tab) {
        return TABS.register(name, tab);
    }

    public RegistryObject<CreativeModeTab> newTabWithoutItems(String name, ItemEntry icon) {
        return newTab(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + name))
            .icon(icon::stack)
            .build()
        );
    }

    public static CreativeTabRegister of(String modid) {
        return new CreativeTabRegister(modid);
    }

}
