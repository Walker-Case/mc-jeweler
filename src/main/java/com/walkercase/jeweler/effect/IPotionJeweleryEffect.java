package com.walkercase.jeweler.effect;

import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Used to create a jewelry effect from a potion effect.
 */
public interface IPotionJeweleryEffect extends IJewelryEffect {

    /**
     * The potion effect this jewelry will give.
     *
     * @return
     */
    MobEffect getPotionEffect();

    default ParticleOptions getEquipParticle(){
        return new DustParticleOptions(Vec3.fromRGB24(getPotionEffect().getColor()).toVector3f(), 1.0F);
    }

    @Override
    default void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        MobEffect effect = getPotionEffect();
        int level = EffectAPI.getEffectValue(this, stack);

        MobEffectInstance effectInstance = slotContext.entity().getEffect(effect);
        int duration = 600 * level;
        if(effectInstance == null || effectInstance.getDuration() < 300){
            slotContext.entity().addEffect(new MobEffectInstance(effect, duration, level-1));

            slotContext.entity().playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
            this.playParticles(slotContext.entity().level(), slotContext.entity(), getEquipParticle(), 20, 0.5d);

            IJewelryEffect.damageStack((Player)slotContext.entity(), stack, RANDOM, level * 3);
        }
    }

}
