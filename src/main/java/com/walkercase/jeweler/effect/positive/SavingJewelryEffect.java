package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SavingJewelryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "saving");
    }

    @Override
    public void curioBreak(Player player, ItemStack stack){
        int level = EffectAPI.getEffectValue(this, stack);
        if(level > 0) {
            ItemStack newStack = stack.copy();

            newStack.setDamageValue(newStack.getMaxDamage() - 1);
            EffectAPI.setEffect(newStack, this, level - 1);

            player.getInventory().add(newStack);
        }
    }
}
