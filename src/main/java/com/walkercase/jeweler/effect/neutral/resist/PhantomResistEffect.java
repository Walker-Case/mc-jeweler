package com.walkercase.jeweler.effect.neutral.resist;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.INeutralResistEffect;
import net.minecraft.resources.ResourceLocation;

/**
 * Phantoms do less damage BUT Silverfish do more.
 */
public class PhantomResistEffect implements INeutralResistEffect {
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
        return new ResourceLocation(JewelerMain.MODID, "phantom_resist");
    }


    @Override
    public ResourceLocation getResistEntity() {
        return new ResourceLocation("minecraft", "phantom");
    }

    @Override
    public ResourceLocation getLessResistEntity() {
        return new ResourceLocation("minecraft", "silverfish");
    }
}
