package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Increased critical hit damage.
 */
public class IncreasedCriticalEffect implements IJewelryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "increased_critical");
    }

    public ParticleOptions getEquipParticle(){
        return ParticleTypes.CRIT;
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::onCritical);
    }

    public void onCritical(CriticalHitEvent e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            int level = EffectAPI.getEffectValue(this, is);
            if(level > 0){
                e.setDamageModifier((int)(e.getDamageModifier() * (1 + ((float)Math.max(level, 10) / 10))));
            }
        });
    }
}
