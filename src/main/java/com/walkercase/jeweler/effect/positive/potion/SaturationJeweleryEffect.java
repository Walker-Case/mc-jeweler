package com.walkercase.jeweler.effect.positive.potion;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IPotionJeweleryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

/**
 * Provides the saturation potion effect.
 */
public class SaturationJeweleryEffect implements IPotionJeweleryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "potion_saturation");
    }


    @Override
    public MobEffect getPotionEffect() {
        return MobEffects.SATURATION;
    }
}
