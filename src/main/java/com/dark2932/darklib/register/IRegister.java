package com.dark2932.darklib.register;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

/**
 * @author Dark2932
 */
public class IRegister<T> {

    private final String modid;
    private final int index;
    private final String type;
    private final DeferredRegister<T> register;

    protected IRegister(String modid, String type, DeferredRegister<T> register) {
        this.modid = modid;
        this.index = MOD_REGISTERS.initLocation(modid, type, this);
        this.type = type;
        this.register = register;
    }

    public void init(IEventBus bus) {
        register.register(bus);
        MOD_REGISTERS.setRegister(modid, type, index, this);
    }

    public String getModId() {
        return modid;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public DeferredRegister<T> getDeferredRegister() {
        return register;
    }

}
