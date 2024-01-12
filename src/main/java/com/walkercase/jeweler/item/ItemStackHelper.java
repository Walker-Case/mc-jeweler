package com.walkercase.jeweler.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemStackHelper {

    public static CompoundTag getModNBT(ItemStack is) {
        CompoundTag tag = is.getOrCreateTag();
        if (!tag.contains("jeweler")) {
            tag.put("jeweler", new CompoundTag());
        }

        return tag.getCompound("jeweler");
    }
}
