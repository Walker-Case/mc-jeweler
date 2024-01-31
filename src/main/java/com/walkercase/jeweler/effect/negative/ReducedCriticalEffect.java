package com.walkercase.jeweler.effect.negative;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Reduced critical hit damage.
 */
public class ReducedCriticalEffect implements IJewelryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "reduced_critical");
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::onCritical);
    }

    public void onCritical(CriticalHitEvent e) {
        this.getEquippedCuriosWithEffect(e.getPlayer()).forEach(is->{
            int level = EffectAPI.getEffectValue(this, is);
            if(level > 0){
                e.setDamageModifier((int)(e.getDamageModifier() * ((float)Math.max(level, 10) / 10)));
            }
        });
    }
}
