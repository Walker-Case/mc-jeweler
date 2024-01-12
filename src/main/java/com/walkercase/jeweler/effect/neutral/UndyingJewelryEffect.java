package com.walkercase.jeweler.effect.neutral;

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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

/**
 * Prevents Death BUT reduces max health
 */
public class UndyingJewelryEffect implements IJewelryEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.NEUTRAL;
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "undying");
    }

    public void onHurtEvent(LivingHurtEvent e, ItemStack is, JewelerItemBase base){
        if(e.getEntity() instanceof Player player){
            if(player.getHealth() - e.getAmount() <= 0){
                int level = EffectAPI.getEffectValue(this, is);
                if(level > 0){
                    CuriosApi.getCuriosHelper().findCurios(player, (p)->p.equals(is)).forEach(slotResult -> {
                        EffectAPI.setEffect(is, this, level -1);
                        IJewelryEffect.damageStack(player, is, RANDOM, level * 100);

                        player.setHealth(player.getMaxHealth());
                        e.setCanceled(true);
                    });
                }
            }
        }
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player){
            player.setHealth(player.getMaxHealth());
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack, JewelerItemBase item) {
        Multimap<Attribute, AttributeModifier> atts = EffectAPI.getEmptyAttributeMap();

        float value = -(((float)EffectAPI.getEffectValue(this, stack))/10);

        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "health_bonus", Math.max(value, -0.9f), AttributeModifier.Operation.MULTIPLY_TOTAL));

        return atts;
    }
}
