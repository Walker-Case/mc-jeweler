package com.walkercase.jeweler.effect.negative;

import com.google.common.collect.Multimap;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

/**
 * Negative armor bonus.
 */
public class WeakenedJewelryEffect implements IJewelryEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "weakened");
    }

    @Override
    public void onHurtEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        stack.setDamageValue((int) Math.round((event.getAmount() + stack.getDamageValue())));
        IJewelryEffect.damageStack((Player)event.getEntity(), stack, RANDOM, (int) (event.getAmount() + (event.getAmount() * (EffectAPI.getEffectValue(this, stack) / 10))));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = EffectAPI.getEmptyAttributeMap();

        int value = EffectAPI.getEffectValue(this, stack);

        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "armor_bonus", -value, AttributeModifier.Operation.ADDITION));

        return atts;
    }

}
