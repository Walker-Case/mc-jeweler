package com.walkercase.jeweler.effect.negative;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Removes player hunger.
 */
public class FeedingJewelryEffect implements IJewelryEffect {

    @Override
    public EffectType getEffectType() {
        return EffectType.NEGATIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "feeding");
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item) {
        int value = EffectAPI.getEffectValue(this, stack);

        if (slotContext.entity() instanceof Player player) {
            if(EffectAPI.doAfterXCalls(EffectAPI.getEffectsDataNBT(stack, this), "jewelerEffectFeedingDelay", 10)){
                player.causeFoodExhaustion((float) (value) / 10);
            }
        }
    }
}
