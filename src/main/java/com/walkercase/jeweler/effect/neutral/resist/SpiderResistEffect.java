package com.walkercase.jeweler.effect.neutral.resist;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.INeutralResistEffect;
import net.minecraft.resources.ResourceLocation;

/**
 * Spiders do less damage BUT Creepers do more.
 */
public class SpiderResistEffect implements INeutralResistEffect {
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
        return new ResourceLocation(JewelerMain.MODID, "spider_resist");
    }

    @Override
    public ResourceLocation getResistEntity() {
        return new ResourceLocation("minecraft", "spider");
    }

    @Override
    public ResourceLocation getLessResistEntity() {
        return new ResourceLocation("minecraft", "creeper");
    }


}
