package com.walkercase.jeweler.effect.positive.summon;


import com.walkercase.jeweler.JewelerMain;
import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.effect.ISummonEffect;
import com.walkercase.jeweler.entity.JewelerEntities;
import com.walkercase.jeweler.entity.spectral_zombie.SpectralZombie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class SpectralZombieSummonEffect implements ISummonEffect {
    @Override
    public EffectType getEffectType() {
        return EffectType.POSITIVE;
    }

    @Override
    public ResourceLocation effectID() {
        return new ResourceLocation(JewelerMain.MODID, "spectral_zombie_summon");
    }

    @Override
    public EffectRarity getEffectRarity(){
        return EffectRarity.RARE;
    }

    private static final SummonPotionEffect[] POTION_EFFECTS = new SummonPotionEffect[]{
            new SummonPotionEffect(2, MobEffects.ABSORPTION, 2, 1),
            new SummonPotionEffect(3, MobEffects.REGENERATION, 2, 1),
            new SummonPotionEffect(4, MobEffects.HEALTH_BOOST, 2, 1),
            new SummonPotionEffect(5, MobEffects.DAMAGE_BOOST, 2, 2),
            new SummonPotionEffect(6, MobEffects.MOVEMENT_SPEED, 2, 1),
            new SummonPotionEffect(8, MobEffects.DAMAGE_RESISTANCE, 2, 2),
            new SummonPotionEffect(9, MobEffects.HEALTH_BOOST, 2, 2),
            new SummonPotionEffect(10, MobEffects.DAMAGE_BOOST, 2, 2)
    };

    @Override
    public SummonPotionEffect[] getPotionEffects() {
        return POTION_EFFECTS;
    }

    @Override
    public void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);
        ItemStack itemStack;

        Item item = effLevel > 8 ? Items.NETHERITE_SWORD : effLevel > 5 ? Items.DIAMOND_SWORD : effLevel > 3 ? Items.IRON_SWORD : Items.STONE_SWORD;

        itemStack = new ItemStack(item);
        if(effLevel >= 2)
            itemStack.enchant(Enchantments.SHARPNESS, 1);
        if(effLevel >= 5)
            itemStack.enchant(Enchantments.FIRE_ASPECT, 1);
        if(effLevel >= 7)
            itemStack.enchant(Enchantments.KNOCKBACK, 1);


        entity.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }

    @Override
    public void playSummonSounds(LivingEntity entity) {
        entity.playSound(SoundEvents.ZOMBIE_INFECT, 1, 1);
        entity.playSound(SoundEvents.PORTAL_TRAVEL, 1, 1);
    }

    @Override
    public int getMaxSummons(ItemStack is){
        int effLevel = EffectAPI.getEffectValue(this, is);
        return effLevel / 3;
    }

    @Override
    public LivingEntity createEntity(Level level, Player player, ItemStack stack) {
        int effLevel = EffectAPI.getEffectValue(this, stack);
        SpectralZombie cat = new SpectralZombie(JewelerEntities.SPECTRAL_ZOMBIE.get(), level);
        cat.tame(player);
        cat.setPos(player.getX(), player.getY() + 1.5d, player.getZ());

        cat.setCustomName(JewelerMain.PLATFORM_UTIL.getLiteralComponent(player.getDisplayName().getString() + "'s Lvl." + effLevel + " ")
                .append(JewelerMain.PLATFORM_UTIL.getTranslatedComponent("literal.zombie")));
        return cat;
    }

}

