package com.dark2932.darklib.register;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dark2932
 * @date 2025/12/23
 */
public class IRegister<T> {

    private final DeferredRegister<T> register;

    protected IRegister(DeferredRegister<T> register) {
        this.register = register;
    }

    protected DeferredRegister<T> getDeferredRegister() {
        return register;
    }

    public void init(IEventBus bus) {
        register.register(bus);
    }

    public static void init(IEventBus bus, IRegister<?>... registers) {
        init(bus, Arrays.asList(registers));
    }

    public static void init(IEventBus bus, List<IRegister<?>> list) {
        list.forEach(register -> register.init(bus));
    }

}
