package com.dark2932.darklib;

import com.dark2932.darklib.util.IRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Dark2932
 * @date 2025/11/26
 */
@Mod(DarkLib.MODID)
public class DarkLib {

    public static final String MODID = "darklib";
    public static final IEventBus BUS = MinecraftForge.EVENT_BUS;
    public static final IRegister REGISTER = new IRegister(MODID);

    public DarkLib() {
        REGISTER.init(BUS);
    }

}
