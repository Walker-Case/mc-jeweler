package com.walkercase.jeweler.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ItemStackHelper {

    public static CompoundTag getModNBT(ItemStack is) {
        CompoundTag tag = getModNBT(is.getOrCreateTag());
        if(!tag.contains("jewelerUUID"))
            tag.putUUID("jewelerUUID", UUID.randomUUID());
        return tag;
    }

    public static CompoundTag getModNBT(CompoundTag tag){
        if (!tag.contains("jeweler")) {
            tag.put("jeweler", new CompoundTag());
        }

        return tag.getCompound("jeweler");
    }

    /**
     * Returns true if the ItemStacks are equal. This function ignores damage values.
     * @param is1
     * @param is2
     * @return
     */
    public static boolean equalsIgnoreDamage(ItemStack is1, ItemStack is2){
        if(is1 == null && is2 == null) {
            return true;
        }
        if(is1.getItem() == is2.getItem()){
            if(is1.getCount() == is2.getCount()){
                CompoundTag t1 = is1.getTag().copy();
                CompoundTag t2 = is2.getTag().copy();
                t1.remove("Damage");
                t2.remove("Damage");
                if(t1.getAsString().equals(t2.getAsString()))
                    return true;
            }
        }

        return false;
    }

    public static UUID getUUID(ItemStack is){
        return getModNBT(is).getUUID("jewelerUUID");
    }
}
