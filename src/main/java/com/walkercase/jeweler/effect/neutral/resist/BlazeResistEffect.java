package com.walkercase.jeweler.effect.neutral.resist;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.effect.INeutralResistEffect;
import net.minecraft.resources.ResourceLocation;

/**
 * Blaze do less damage BUT Ghasts do more.
 */
public class BlazeResistEffect implements INeutralResistEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEUTRAL;
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "blaze_resist");
    }

    @Override
    public ResourceLocation getResistEntity() {
        return new ResourceLocation("minecraft", "blaze");
    }

    @Override
    public ResourceLocation getLessResistEntity() {
        return new ResourceLocation("minecraft", "Ghast");
    }

}
