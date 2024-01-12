package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BloodlustJeweleryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "bloodlust");
    }

    @Override
    public void onHurtEntityEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        if (event.getSource().getEntity() instanceof Player player) {
            IJewelryEffect.damageStack(player, stack, RANDOM, EffectAPI.getEffectValue(this, stack));
            player.heal((EffectAPI.getEffectValue(this, stack)));
        }
    }
}
