package com.walkercase.jeweler.effect.positive.summon;


import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.ISummonEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WolfSummonEffect implements ISummonEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "wolf_summon");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    private static final SummonPotionEffect[] POTION_EFFECTS = new SummonPotionEffect[]{
            new SummonPotionEffect(2, MobEffects.DAMAGE_BOOST, 2, 1),
            new SummonPotionEffect(3, MobEffects.ABSORPTION, 2, 1),
            new SummonPotionEffect(4, MobEffects.HEALTH_BOOST, 2, 1),
            new SummonPotionEffect(5, MobEffects.DAMAGE_BOOST, 2, 2),
            new SummonPotionEffect(6, MobEffects.REGENERATION, 2, 1),
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
        entity.playSound(SoundEvents.WOLF_GROWL);
        entity.playSound(SoundEvents.PORTAL_TRAVEL);
    }

    @Override
    public int getMaxSummons(ItemStack is){
        int effLevel = EffectAPI.getEffectValue(this, is);
        return effLevel / 3;
    }

    @Override
    public LivingEntity createEntity(Level level, Player player, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);

        Wolf wolf = new Wolf(EntityType.WOLF, level);
        wolf.tame(player);
        wolf.setPos(player.getX(), player.getY() + 1.5d, player.getZ());

        wolf.setCollarColor(effLevel >= 7 ? DyeColor.RED : effLevel >= 5 ? DyeColor.BLUE : effLevel >= 2 ? DyeColor.GREEN : DyeColor.BROWN);

        wolf.setCustomName(JewelerMain.PLATFORM_UTIL.getLiteralComponent(player.getDisplayName().getString() + "'s Lvl." + effLevel + " ")
                .append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent("literal.wolf")));
        return wolf;
    }

}

