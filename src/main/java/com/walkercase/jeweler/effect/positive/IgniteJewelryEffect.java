package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Objects;

public class IgniteJewelryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "ignite");
    }

    public static final DustParticleOptions PARTICLE = new DustParticleOptions(Vec3.fromRGB24(0xBD2626).toVector3f(), 1.0F);

    public ParticleOptions getEquipParticle(){
        return PARTICLE;
    }

    @Override
    public void onHurtEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        int value = EffectAPI.getEffectValue(this, stack);

        IJewelryEffect.damageStack((Player)event.getEntity(), stack, RANDOM, EffectAPI.getEffectValue(this, stack));

        Objects.requireNonNull(event.getSource().getEntity()).setSecondsOnFire(4 * value);

        Level level = event.getEntity().level;
        if(event.getSource().getEntity() instanceof LivingEntity entity)
            this.playParticles(level, entity, ParticleTypes.SMOKE, 20, 0.5d);
        event.getEntity().playSound(SoundEvents.FIRE_AMBIENT);
    }
}
