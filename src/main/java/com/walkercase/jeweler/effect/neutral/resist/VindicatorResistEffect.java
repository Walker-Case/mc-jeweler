package com.walkercase.jeweler.effect.neutral.resist;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.INeutralResistEffect;
import net.minecraft.resources.ResourceLocation;

/**
 * Vindicators do less damage BUT Pillagers do more.
 */
public class VindicatorResistEffect implements INeutralResistEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEUTRAL;
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.COMMON;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "vindicator_resist");
    }

    @Override
    public ResourceLocation getResistEntity() {
        return new ResourceLocation("minecraft", "vindicator");
    }

    @Override
    public ResourceLocation getLessResistEntity() {
        return new ResourceLocation("minecraft", "pillager");
    }


}
