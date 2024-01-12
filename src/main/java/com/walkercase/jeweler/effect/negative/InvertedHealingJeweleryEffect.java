package com.walkercase.jeweler.effect.negative;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Heals the attacker.
 */
public class InvertedHealingJeweleryEffect implements IJewelryEffect {
    @Override
    public void onHurtEvent(LivingHurtEvent e, ItemStack is, JewelerItemBase base) {
        if (e.getSource().getEntity() instanceof LivingEntity entity) {
            entity.heal((EffectAPI.getEffectValue(this, is) + 1));
        }
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "inverted_healing");
    }
}
