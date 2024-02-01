package com.walkercase.jeweler.api.record;

import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.world.item.ItemStack;

public record Roll(float chance, int maxLevel, IJewelryEffect roll) {
}
