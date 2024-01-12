package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.resources.ResourceLocation;

public class UnbreakingJewelryEffect implements IJewelryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "unbreaking");
    }

}
