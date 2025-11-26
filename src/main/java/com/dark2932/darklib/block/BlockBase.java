package com.dark2932.darklib.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

/**
 * @author Dark2932
 * @date 2025/11/18
 */
public class BlockBase extends Block {

    public BlockBase() {
        super(BlockBehaviour.Properties.of());
    }

    public BlockBase(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static class item extends BlockItem {

        public item(Supplier<Block> block) {
            super(block.get(), new Item.Properties());
        }

        public item(Supplier<Block> block, Item.Properties properties) {
            super(block.get(), properties);
        }

    }

}
