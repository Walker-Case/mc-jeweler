package com.walkercase.jeweler.effect.negative;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Player xp pickup reduction.
 */
public class XpReductionJeweleryEffect implements IJewelryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "xp_reduction");
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::xpGained);
    }

    public void xpGained(PlayerXpEvent.XpChange e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            int level = EffectAPI.getEffectValue(this, is);
            if(level > 0){
                e.setAmount((int)(e.getAmount() * ((float)Math.max(level, 10) / 10)));
            }
        });
    }
}
