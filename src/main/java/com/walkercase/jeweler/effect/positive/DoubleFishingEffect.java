package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Doubles fishing sometimes.
 */
public class DoubleFishingEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "double_fishing");
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::onItemFished);
    }

    public void onItemFished(ItemFishedEvent e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            float value = EffectAPI.getEffectValue(this, is);

            if(value > 0){
                float percent = Math.min(10,(value/10));
                if(RANDOM.nextFloat() < percent){
                    IJewelryEffect.damageStack(e.getEntity(), is, RANDOM, (int) (100 * value));
                    e.getDrops().forEach(x->{
                        e.getEntity().getInventory().add(x.copy());
                    });
                    MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.jeweler.chat.double_fishing");

                    e.getEntity().sendSystemMessage(mutablecomponent);
                }
            }
        });
    }

}
