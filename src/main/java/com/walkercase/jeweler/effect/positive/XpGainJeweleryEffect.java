package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Player xp pickup gain.
 */
public class XpGainJeweleryEffect implements IJewelryEffect {

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
        return new ResourceLocation(JewelerMain.MODID, "xp_gain");
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::xpGained);
    }

    public void xpGained(PlayerXpEvent.XpChange e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            int level = EffectAPI.getEffectValue(this, is);
            if(level > 0){
                e.setAmount((int)(e.getAmount() * (1 + ((float)Math.max(level, 10) / 10))));
            }
        });
    }
}
