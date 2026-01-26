package com.dark2932.darklib.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dark2932
 */
public class MOD_REGISTERS {

    public static Map<String, Map<Integer, IRegister<?>>> MODS = new HashMap<>();

    public static int initLocation(String modid, IRegister<?> register) {
        int index;
        if (MODS.containsKey(modid)) {
            var REGISTERS = getModRegisters(modid);
            index = REGISTERS.size();
            REGISTERS.put(index, register);
        } else {
            var map = new HashMap<Integer, IRegister<?>>();
            index = 0;
            map.put(0, register);
            MODS.put(modid, map);
        }
        return index;
    }

    public static IRegister<?> getRegister(String modid, int index) {
        return getModRegisters(modid).get(index);
    }

    public static Map<Integer, IRegister<?>> getModRegisters(String modid) {
        return MODS.get(modid);
    }

}
