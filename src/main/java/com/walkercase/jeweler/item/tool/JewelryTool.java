package com.walkercase.jeweler.item.tool;

import com.walkercase.jeweler.api.EffectAPI;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class JewelryTool extends Item {

    public JewelryTool(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return EffectAPI.isFoil(itemStack);
    }

    @Deprecated // Use ItemStack sensitive version.
    public boolean hasCraftingRemainingItem() {
        return true;
    }

}
