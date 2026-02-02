package com.dark2932.darklib.register;

import java.util.*;

/**
 * @author Dark2932
 */
public class MOD_REGISTERS {

    /** ---- Preview ----

     {
         darklib: {
             "item": [ItemRegister1, ItemRegister2...],
             "block": [BlockRegister1...]
         },
         auroramagic: {
             ...
         }
     }

     ---- Preview ---- **/
    private static final Map<String, Map<String, List<IRegister<?>>>> MOD_TYPE_REGISTERS = new HashMap<>();

    public static int initLocation(String modid, String type, IRegister<?> register) {
        int index;
        if (MOD_TYPE_REGISTERS.containsKey(modid)) {
            var typeLevel = MOD_TYPE_REGISTERS.get(modid);
            if (typeLevel.containsKey(type)) {
                List<IRegister<?>> registers = typeLevel.get(type);
                index = registers.size();
                registers.add(register);
            } else {
                List<IRegister<?>> registers = new ArrayList<>();
                registers.add(register);

                typeLevel.put(type, registers);

                index = 0;
            }
        } else {
            List<IRegister<?>> registers = new ArrayList<>();
            registers.add(register);

            Map<String, List<IRegister<?>>> typeLevel = new HashMap<>();
            typeLevel.put(type, registers);

            MOD_TYPE_REGISTERS.put(modid, typeLevel);

            index = 0;
        }
        return index;
    }

    public static String printAllRegisters() {
        return "";
    }

    public static void setRegister(String modid, String type, int index, IRegister<?> newRegister) {
        MOD_TYPE_REGISTERS.get(modid).get(type).set(index, newRegister);
    }

    public static IRegister<?> getRegister(String modid, String type, int index) {
        return getSameRegisters(modid, type).get(index);
    }

    public static List<IRegister<?>> getSameRegisters(String modid, String type) {
        return getModRegistersMap(modid).get(type);
    }

    public static Map<String, List<IRegister<?>>> getModRegistersMap(String modid) {
        return getAllRegisters().get(modid);
    }

    public static Map<String, Map<String, List<IRegister<?>>>> getAllRegisters() {
        return Collections.unmodifiableMap(MOD_TYPE_REGISTERS);
    }

}
