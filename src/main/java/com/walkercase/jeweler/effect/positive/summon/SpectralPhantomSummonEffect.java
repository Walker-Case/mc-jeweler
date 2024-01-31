package com.walkercase.jeweler.effect.positive.summon;


import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.ISummonEffect;
import com.walkercase.jeweler.entity.JewelerEntities;
import com.walkercase.jeweler.entity.spectral_phantom.SpectralPhantom;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpectralPhantomSummonEffect implements ISummonEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "spectral_phantom_summon");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    @Override
    public int getMaxSummons(ItemStack is){
        int effLevel = EffectAPI.getEffectValue(this, is);
        return 1 + (effLevel / 2);
    }

    private static final SummonPotionEffect[] POTION_EFFECTS = new SummonPotionEffect[]{
            new SummonPotionEffect(2, MobEffects.ABSORPTION, 2, 1),
            new SummonPotionEffect(5, MobEffects.HEALTH_BOOST, 2, 1),
            new SummonPotionEffect(8, MobEffects.DAMAGE_BOOST, 2, 1),
    };

    @Override
    public SummonPotionEffect[] getPotionEffects() {
        return POTION_EFFECTS;
    }

    @Override
    public void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack) {}

    @Override
    public void playSummonSounds(LivingEntity entity) {
        entity.playSound(SoundEvents.PHANTOM_FLAP, 1, 1);
        entity.playSound(SoundEvents.PORTAL_TRAVEL, 1, 1);
    }

    @Override
    public LivingEntity createEntity(Level level, Player player, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);
        SpectralPhantom phantom = new SpectralPhantom(JewelerEntities.SPECTRAL_PHANTOM.get(), level);
        phantom.tame(player);
        phantom.setHealth(8);
        phantom.setPos(player.getRandomX(5), player.getY() + 5d, player.getRandomZ(5));

        phantom.setCustomName(JewelerMain.PLATFORM_UTIL.getLiteralComponent(player.getDisplayName().getString() + "'s Lvl." + effLevel + " ")
                .append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent("literal.phantom")));
        return phantom;
    }

}

