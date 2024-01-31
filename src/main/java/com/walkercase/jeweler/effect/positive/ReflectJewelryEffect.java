package com.walkercase.jeweler.effect.positive;

import com.google.common.collect.Multimap;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ReflectJewelryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "reflect");
    }

    @Override
    public void onHurtEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        float value = event.getAmount() * ((float) Math.min(EffectAPI.getEffectValue(this, stack), 10) / 10);
        IJewelryEffect.damageStack((Player)event.getEntity(), stack, RANDOM, (int) (value * 100));

        if (event.getSource().getEntity() instanceof LivingEntity living) {
            living.hurt(DamageSource.indirectMagic(event.getEntity(), event.getEntity()), value);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = EffectAPI.getEmptyAttributeMap();
        return atts;
    }
}
