package com.walkercase.jeweler.effect.negative.potion;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IPotionJeweleryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

/**
 * Provides the blindness potion effect.
 */
public class BlindnessJeweleryEffect implements IPotionJeweleryEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "potion_blindness");
    }


    @Override
    public MobEffect getPotionEffect() {
        return MobEffects.BLINDNESS;
    }
}
