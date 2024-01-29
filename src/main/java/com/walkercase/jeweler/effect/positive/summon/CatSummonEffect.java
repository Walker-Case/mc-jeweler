package com.walkercase.jeweler.effect.positive.summon;


import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.IJewelryEffect;
import com.walkercase.jeweler.effect.ISummonEffect;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Objects;

public class CatSummonEffect implements ISummonEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "cat_summon");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    private static final SummonPotionEffect[] POTION_EFFECTS = new SummonPotionEffect[]{
            new SummonPotionEffect(2, MobEffects.ABSORPTION, 2, 1),
            new SummonPotionEffect(3, MobEffects.REGENERATION, 2, 1),
            new SummonPotionEffect(4, MobEffects.HEALTH_BOOST, 2, 1),
            new SummonPotionEffect(5, MobEffects.REGENERATION, 2, 2),
            new SummonPotionEffect(6, MobEffects.MOVEMENT_SPEED, 2, 1),
            new SummonPotionEffect(8, MobEffects.HEALTH_BOOST, 2, 2),
            new SummonPotionEffect(9, MobEffects.HEALTH_BOOST, 2, 3),
            new SummonPotionEffect(10, MobEffects.HEALTH_BOOST, 2, 4)
    };

    @Override
    public SummonPotionEffect[] getPotionEffects() {
        return POTION_EFFECTS;
    }

    @Override
    public void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack) {}

    @Override
    public void playSummonSounds(LivingEntity entity) {
        entity.playSound(SoundEvents.CAT_HISS);
        entity.playSound(SoundEvents.PORTAL_TRAVEL);
    }

    @Override
    public LivingEntity createEntity(Level level, Player player, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);
        Cat cat = new Cat(EntityType.CAT, level);
        cat.tame(player);
        cat.setPos(player.getX(), player.getY() + 1.5d, player.getZ());

        if(effLevel >= 7){
            cat.setVariant(Objects.requireNonNull(BuiltInRegistries.CAT_VARIANT.get(CatVariant.RED)));
            cat.setCollarColor(DyeColor.RED);
        }else if(effLevel >= 5){
            cat.setVariant(Objects.requireNonNull(BuiltInRegistries.CAT_VARIANT.get(CatVariant.PERSIAN)));
            cat.setCollarColor(DyeColor.BLUE);
        }else if(effLevel >= 2){
            cat.setVariant(Objects.requireNonNull(BuiltInRegistries.CAT_VARIANT.get(CatVariant.BRITISH_SHORTHAIR)));
            cat.setCollarColor(DyeColor.GREEN);
        }else{
            cat.setVariant(Objects.requireNonNull(BuiltInRegistries.CAT_VARIANT.get(CatVariant.CALICO)));
            cat.setCollarColor(DyeColor.BROWN);
        }

        cat.setCustomName(JewelerMain.PLATFORM_UTIL.getLiteralComponent(player.getDisplayName().getString() + "'s Lvl." + effLevel + " ")
                .append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent("literal.cat")));
        return cat;
    }

}

