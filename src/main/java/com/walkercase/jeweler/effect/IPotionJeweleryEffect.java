package com.walkercase.jeweler.effect;

import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Used to create a jewelry effect from a potion effect.
 */
public interface IPotionJeweleryEffect extends IJewelryEffect {

    /**
     * The potion effect this jewelry will give.
     * @return
     */
    MobEffect getPotionEffect();

    @Override
    default void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        MobEffect effect = getPotionEffect();
        int level = EffectAPI.getEffectValue(this, stack);

        MobEffectInstance effectInstance = slotContext.entity().getEffect(effect);
        int duration = 30 * level;
        if(effectInstance == null || effectInstance.getDuration() < 30){
            slotContext.entity().addEffect(new MobEffectInstance(effect, duration, level-1));

            IJewelryEffect.damageStack((Player)slotContext.entity(), stack, RANDOM, level);
        }
    }

}
