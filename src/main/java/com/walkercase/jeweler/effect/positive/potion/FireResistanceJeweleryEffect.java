package com.walkercase.jeweler.effect.positive.potion;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IPotionJeweleryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

/**
 * Provides the fire resistance potion effect.
 */
public class FireResistanceJeweleryEffect implements IPotionJeweleryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "potion_fire_resistance");
    }


    @Override
    public MobEffect getPotionEffect() {
        return MobEffects.FIRE_RESISTANCE;
    }
}
