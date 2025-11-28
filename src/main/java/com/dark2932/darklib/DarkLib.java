package com.dark2932.darklib;

import com.dark2932.darklib.util.IRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Dark2932
 * @date 2025/11/26
 */
@Mod(DarkLib.MODID)
public class DarkLib {

    public static final String MODID = "darklib";
    public static final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
    public static final IRegister REGISTER = new IRegister(MODID);

    public DarkLib() {
        REGISTER.init(BUS);
    }

}
