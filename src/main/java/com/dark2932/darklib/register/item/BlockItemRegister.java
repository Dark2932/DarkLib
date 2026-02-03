package com.dark2932.darklib.register.item;

import com.dark2932.darklib.util.ItemEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

/**
 * @author Dark2932
 */
public class BlockItemRegister extends ItemRegisterBase {

    public BlockItemRegister(String modid) {
        super(modid, "item_block");
    }

    public ItemEntry newBlockItem(RegistryObject<Block> blockRegistry, Item.Properties itemProperties) {
        ResourceLocation location = Objects.requireNonNull(blockRegistry.getId());
        return super.newItem(location.getPath(), () -> new BlockItem(blockRegistry.get(), itemProperties));
    }

    public static BlockItemRegister of(String modid) {
        return new BlockItemRegister(modid);
    }

}
