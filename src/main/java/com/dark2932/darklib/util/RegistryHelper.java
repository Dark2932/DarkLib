package com.dark2932.darklib.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

/**
 * @author Dark2932
 */
public class RegistryHelper {

    private static final Map<String, List<Item>> ALL_MOD_ITEMS = Collections.unmodifiableMap(items());
    //private static final Map<String, List<Item>> ALL_MOD_BIOMES = Collections.unmodifiableMap();

    private RegistryHelper() {}

    /**
     * @param modid mod的id
     * @return 包含此mod内所有物品的列表
     */
    public static List<Item> getModItems(String modid) {
        return ALL_MOD_ITEMS.get(modid);
    }

    private static Map<String, List<Item>> items() {
        Map<String, List<Item>> map = new HashMap<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                map.computeIfAbsent(id.getNamespace(), key -> new ArrayList<>()).add(item);
            }
        }
        return map;
    }

}
