package com.walkercase.jeweler.effect.positive;

import com.google.common.collect.Multimap;
import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class LuckJeweleryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.UNCOMMON;
    }
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "luck");
    }

    public static final DustParticleOptions PARTICLE = new DustParticleOptions(Vec3.fromRGB24(0xBD9026).toVector3f(), 1.0F);

    public ParticleOptions getEquipParticle(){
        return PARTICLE;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = EffectAPI.getEmptyAttributeMap();

        int value = EffectAPI.getEffectValue(this, stack);

        atts.put(Attributes.LUCK, new AttributeModifier(uuid, "luck_bonus", 0.1 * (value), AttributeModifier.Operation.MULTIPLY_TOTAL));

        return atts;
    }
}
