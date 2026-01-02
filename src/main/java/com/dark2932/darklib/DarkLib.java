package com.dark2932.darklib;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Dark2932
 */
@Mod(DarkLib.MODID)
public class DarkLib {

    public static final String MODID = "darklib";
    public static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public DarkLib() {}

    private void commonSetup(final FMLCommonSetupEvent event) {}

}