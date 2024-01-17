package com.walkercase.jeweler.item.jewelry;

import com.walkercase.jeweler.JewelerMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class JewelerRing extends JewelerItemBase {
    public JewelerRing(Rarity rarity, Item repairItem, int durability) {
        super(rarity, repairItem, durability, new ResourceLocation(JewelerMain.MODID, "item/ring"));
    }
}
