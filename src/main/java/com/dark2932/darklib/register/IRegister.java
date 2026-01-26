package com.dark2932.darklib.register;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

/**
 * @author Dark2932
 */
public class IRegister<T> {

    private final DeferredRegister<T> register;

    protected IRegister(DeferredRegister<T> register) {
        this.register = register;
    }

    public DeferredRegister<T> getDeferredRegister() {
        return register;
    }

    public void init(IEventBus bus) {
        register.register(bus);
    }

    public static void init(IEventBus bus, IRegister<?>... registers) {
        for (IRegister<?> register : registers) {
            register.init(bus);
        }
    }

}
