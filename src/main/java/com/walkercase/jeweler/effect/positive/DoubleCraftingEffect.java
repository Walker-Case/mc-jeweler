package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Doubles crafting sometimes.
 */
public class DoubleCraftingEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "double_crafting");
    }

    @Override
    public void registerEvents(IEventBus modEventBus, IEventBus forgeEventBus){
        forgeEventBus.addListener(this::onItemCrafted);
    }

    public void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
        this.getEquippedCuriosWithEffect(e.getEntity()).forEach(is->{
            float value = EffectAPI.getEffectValue(this, is);

            float percent = Math.min(10,(value/10));
            if(RANDOM.nextFloat() < percent){
                IJewelryEffect.damageStack(e.getEntity(), is, RANDOM, (int) (100 * value));
                e.getEntity().getInventory().add(e.getCrafting().copy());
                MutableComponent mutablecomponent = JewelerMain.PLATFORM_UTIL.getTranslatedComponent("effect.jeweler.chat.double_crafting");

                e.getEntity().sendSystemMessage(mutablecomponent);
            }
        });
    }

}
