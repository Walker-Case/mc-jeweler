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
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

/**
 * Negative knockback resist
 */
public class LightenedJewelryEffect implements IJewelryEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "lightened");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = EffectAPI.getEmptyAttributeMap();

        int value = EffectAPI.getEffectValue(this, stack);

        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "knockback_resist_bonus", -0.1 * (value), AttributeModifier.Operation.MULTIPLY_TOTAL));

        return atts;
    }
}
