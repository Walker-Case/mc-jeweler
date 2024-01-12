package com.walkercase.jeweler.effect.neutral.resist;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.INeutralResistEffect;
import net.minecraft.resources.ResourceLocation;

/**
 * Evokers do less damage BUT Vex do more.
 */
public class EvokerResistEffect implements INeutralResistEffect {
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
        return new ResourceLocation(JewelerMain.MODID, "evoker_resist");
    }

    @Override
    public ResourceLocation getResistEntity() {
        return new ResourceLocation("minecraft", "evoker");
    }

    @Override
    public ResourceLocation getLessResistEntity() {
        return new ResourceLocation("minecraft", "vex");
    }

}
