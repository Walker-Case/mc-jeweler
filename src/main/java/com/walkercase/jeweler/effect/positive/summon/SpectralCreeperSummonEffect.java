package com.walkercase.jeweler.effect.positive.summon;


import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.ISummonEffect;
import com.walkercase.jeweler.entity.JewelerEntities;
import com.walkercase.jeweler.entity.spectral_creeper.SpectralCreeper;
import com.walkercase.jeweler.entity.spectral_spider.SpectralSpider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpectralCreeperSummonEffect implements ISummonEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "spectral_creeper_summon");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.SUPER_RARE;
    }

    private static final SummonPotionEffect[] POTION_EFFECTS = new SummonPotionEffect[]{
            new SummonPotionEffect(2, MobEffects.ABSORPTION, 2, 1),
            new SummonPotionEffect(3, MobEffects.MOVEMENT_SPEED, 2, 1),
            new SummonPotionEffect(4, MobEffects.ABSORPTION, 2, 2),
            new SummonPotionEffect(5, MobEffects.MOVEMENT_SPEED, 2, 2),
            new SummonPotionEffect(8, MobEffects.MOVEMENT_SPEED, 2, 3),
            new SummonPotionEffect(9, MobEffects.ABSORPTION, 2, 4),
            new SummonPotionEffect(10, MobEffects.ABSORPTION, 2, 5)
    };

    @Override
    public SummonPotionEffect[] getPotionEffects() {
        return POTION_EFFECTS;
    }

    @Override
    public void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack) {}

    @Override
    public void playSummonSounds(LivingEntity entity) {
        entity.playSound(SoundEvents.CREEPER_PRIMED);
        entity.playSound(SoundEvents.PORTAL_TRAVEL);
    }

    public int getSummonDamage(ItemStack is){
        return 1000 * EffectAPI.getEffectValue(this, is);
    }

    @Override
    public LivingEntity createEntity(Level level, Player player, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);
        SpectralCreeper creeper = new SpectralCreeper(JewelerEntities.SPECTRAL_CREEPER.get(), level);
        creeper.tame(player);
        creeper.setPos(player.getX(), player.getY() + 1.5d, player.getZ());

        if(effLevel > 6)
            creeper.setPowered();

        creeper.setCustomName(JewelerMain.PLATFORM_UTIL.getLiteralComponent(player.getDisplayName().getString() + "'s Lvl." + effLevel + " ")
                .append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent("literal.creeper")));
        return creeper;
    }

}

