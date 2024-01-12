package com.walkercase.jeweler.effect.positive;

import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SkeletonSpritJewelryEffect implements IJewelryEffect {

    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "skeleton_spirit");
    }

    @Override
    public void onHurtEvent(LivingHurtEvent event, ItemStack stack, JewelerItemBase item) {
        int value = EffectAPI.getEffectValue(this, stack);

        IJewelryEffect.damageStack((Player)event.getEntity(), stack, RANDOM, value);

        if (event.getSource().getEntity() instanceof LivingEntity living){
            float chance = (float) Math.min(10, value) /10;
            if(living.getRandom().nextFloat() < chance){
                performRangedAttack(event.getEntity(), living, 2 + chance, value);
            }
        }
    }

    public static void performRangedAttack(LivingEntity source, LivingEntity target, float baseDamageModifier, int effectLevel) {
        ItemStack[] tipped = new ItemStack[]{
                new ItemStack(Items.ARROW),
                PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.POISON),
                PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.HARMING),
                PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.SLOWNESS),
                PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.WEAKNESS),
                PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.STRONG_POISON),
                new ItemStack(Items.SPECTRAL_ARROW)
        };


        AbstractArrow abstractarrow = getMobArrow(source, tipped[Math.min(tipped.length - 1, source.getRandom().nextInt(effectLevel))], baseDamageModifier);

        if (effectLevel > 6)
            abstractarrow.setRemainingFireTicks(5);

        double d0 = target.getX() - source.getX();
        double d1 = target.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = target.getZ() - source.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - source.level.getDifficulty().getId() * 4));
        source.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (source.getRandom().nextFloat() * 0.4F + 0.8F));
        source.level.addFreshEntity(abstractarrow);
    }

    public static AbstractArrow getMobArrow(LivingEntity p_37301_, ItemStack p_37302_, float p_37303_) {
        ArrowItem arrowitem = (ArrowItem) p_37302_.getItem();
        AbstractArrow abstractarrow = arrowitem.createArrow(p_37301_.level, p_37302_, p_37301_);
        abstractarrow.setEnchantmentEffectsFromEntity(p_37301_, p_37303_);
        if (p_37302_.is(Items.TIPPED_ARROW) && abstractarrow instanceof Arrow) {
            ((Arrow) abstractarrow).setEffectsFromItem(p_37302_);
        }

        return abstractarrow;
    }
}
