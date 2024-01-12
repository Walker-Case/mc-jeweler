package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    @Override
    public void onHurtEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        int value = EffectAPI.getEffectValue(this, stack);

        IJewelryEffect.damageStack((Player)event.getEntity(), stack, RANDOM, EffectAPI.getEffectValue(this, stack));

        Objects.requireNonNull(event.getSource().getEntity()).setSecondsOnFire(4 * value);
    }
}
