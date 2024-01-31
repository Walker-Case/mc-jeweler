package com.walkercase.jeweler.effect;

import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.item.ForgeItem;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Allows for very fast entity damage resist effects.json.
 */
public interface INeutralResistEffect extends IJewelryEffect {

    /**
     * The entity that will hurt less.
     * @return
     */
    ResourceLocation getResistEntity();

    /**
     * The entity that will hurt more.
     * @return
     */
    ResourceLocation getLessResistEntity();

    default ParticleOptions getEquipParticle(){
        return ParticleTypes.SMOKE;
    }

    @Override
    default void onHurtEvent(LivingHurtEvent e, ItemStack is, JewelerItemBase base){
        if(e.getEntity() instanceof Player player){
            int level = EffectAPI.getEffectValue(this, is);

            if(e.getSource().getEntity() != null){
                ResourceLocation entKey = ForgeRegistries.ENTITIES.getKey(e.getSource().getEntity().getType());

                if(entKey != null){
                    if(entKey.toString().equals(this.getResistEntity().toString())){
                        e.setAmount(e.getAmount() * (1 - (float)Math.min(level, 10) / 10));
                    }
                    if(entKey.toString().equals(this.getLessResistEntity().toString())){
                        e.setAmount(e.getAmount() * (1 + ((float)Math.min(level, 10) / 10)));
                    }

                    IJewelryEffect.damageStack(player, is, RANDOM, (int)e.getAmount() * level);
                }
            }
        }
    }
}
