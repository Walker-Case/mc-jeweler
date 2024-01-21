package com.walkercase.jeweler.item.tool;

import com.walkercase.jeweler.api.EffectAPI;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class JewelryTool extends Item {

    public JewelryTool(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return EffectAPI.isFoil(itemStack);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }
    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack container = stack.copy();
        if (container.hurt(1, RandomSource.create(), null))
            return ItemStack.EMPTY;
        else
            return container;
    }
}
