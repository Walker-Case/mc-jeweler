package com.walkercase.jeweler.effect;

import com.walkercase.jeweler.api.EffectAPI;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Increases break speed.
 */
public interface IBreakSpeedEffect extends IJewelryEffect {


    /**
     * Return true if this effect applies to the provided block
     * @param state BlockState
     * @return true/false
     */
    boolean isTarget(BlockState state);

    @Override
    default void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::breakSpeed);
    }

    default ParticleOptions getEquipParticle(){
        return DustParticleOptions.REDSTONE;
    }

    default void breakSpeed(PlayerEvent.BreakSpeed e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            float value = EffectAPI.getEffectValue(this, is);

            if(value > 0 && isTarget(e.getState())){
                float percent = Math.min(10,(value/10));
                if(this.getEffectType() == EffectType.POSITIVE)
                    percent ++;

                if(RANDOM.nextFloat() < percent){
                    e.setNewSpeed(e.getOriginalSpeed() * percent);
                }
            }
        });
    }

}
