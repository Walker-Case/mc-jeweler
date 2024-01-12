package com.walkercase.jeweler.effect.positive.potion;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.IPotionJeweleryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

/**
 * Provides the absorption potion effect.
 */
public class AbsorptionJeweleryEffect implements IPotionJeweleryEffect {

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "potion_absorption");
    }


    @Override
    public MobEffect getPotionEffect() {
        return MobEffects.ABSORPTION;
    }
}
