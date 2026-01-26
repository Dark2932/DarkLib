package com.dark2932.darklib.register;

import com.dark2932.darklib.item.ItemEntry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author Dark2932
 */
public class FoodRegister extends ItemRegisterBase {

    public FoodRegister(String modid) {
        super(modid);
    }

    public ItemEntry newFood(String name, FoodProperties foodProperties) {
        return super.newItem(name, (new Item.Properties()).food(foodProperties));
    }

    public ItemEntry newFood(String name, Item.Properties itemProperties, FoodProperties foodProperties) {
        return super.newItem(name, itemProperties.food(foodProperties));
    }

    @SafeVarargs
    public final FoodProperties newFoodProps(int nutrition, float saturation, @Nullable Pair<Supplier<MobEffectInstance>, Float>... effectPairs) {
        return newFoodProps(nutrition, saturation, false, effectPairs);
    }

    @SafeVarargs
    public final FoodProperties newFoodProps(int nutrition, float saturation, boolean isMeat, @Nullable Pair<Supplier<MobEffectInstance>, Float>... effectPairs) {
        return newFoodProps(nutrition, saturation, isMeat, false, false, effectPairs);
    }

    @SafeVarargs
    public final FoodProperties newFoodProps(int nutrition, float saturation, boolean isMeat, boolean isFast, boolean canAlwaysEat, @Nullable Pair<Supplier<MobEffectInstance>, Float>... effectPairs) {
        FoodProperties.Builder builder = (new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturation);
        builder = isMeat ? builder.meat() : builder;
        builder = isFast ? builder.fast() : builder;
        builder = canAlwaysEat ? builder.alwaysEat() : builder;
        if (effectPairs != null) {
            for (Pair<Supplier<MobEffectInstance>, Float> effectPair : effectPairs) {
                builder = builder.effect(effectPair.getFirst(), effectPair.getSecond());
            }
        }
        return builder.build();
    }

    public static FoodRegister of(String modid) {
        return new FoodRegister(modid);
    }

}
