package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Slowly repairs held tools
 */
public class ToolRepairEffect implements IJewelryEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "tool_repair");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player){
            ItemStack is = player.getMainHandItem();
            if(is.getItem() instanceof TieredItem){
                int level = EffectAPI.getEffectValue(this, is);
                int amount = level * 10;

                if(is.getDamageValue() > amount){
                    int newDmg = Math.min(is.getDamageValue() - amount, 0);
                    is.setDamageValue(newDmg);
                    IJewelryEffect.damageStack(player, stack, RANDOM, newDmg * 3);
                }
            }
        }
    }

}
