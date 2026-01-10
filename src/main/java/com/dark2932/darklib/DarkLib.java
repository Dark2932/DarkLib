package com.dark2932.darklib;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.dark2932.darklib.test.DarkLibItems.*;

/**
 * @author Dark2932
 */
@Mod(DarkLib.MODID)
public class DarkLib {

    public static final String MODID = "darklib";

    public DarkLib(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        ITEM_REGISTER.init(bus);
        BLOCK_REGISTER.init(bus);
        TAB_REGISTER.init(bus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

}