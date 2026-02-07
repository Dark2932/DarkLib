package com.dark2932.darklib;

import com.dark2932.darklib.network.DLNetworkHandler;
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

    public DarkLib(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        bus.addListener(this::onFMLCommonSetup);
    }

    private void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(DLNetworkHandler::register);
    }

}