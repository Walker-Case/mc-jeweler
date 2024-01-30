package com.walkercase.jeweler.effect;


import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Arrays;

/**
 * Jewelery effect that summons tamable mobs.
 */
public interface ISummonEffect extends IJewelryEffect {

    class SummonPotionEffect{
         public MobEffect mobEffect;
         public int duration;
         public int level;
         public int effectLevelRequirement;

         public SummonPotionEffect(int effectLevelRequirement, MobEffect mobEffect, int duration, int level){
             this.mobEffect = mobEffect;
             this.duration = duration;
             this.level = level;
             this.effectLevelRequirement = effectLevelRequirement;
         }
    }

    /**
     * The list of potion effects to use for this summon.
     * @return
     */
    SummonPotionEffect[] getPotionEffects();

    /**
     * If the entity is this many blocks away from the player it will teleport them closer.
     * @return
     */
    default int getFollowTeleportDistance(){
        return 40;
    }

    @Override
    default void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player){
            Level level = slotContext.entity().level;
            int effLevel = EffectAPI.getEffectValue(this, stack);
            if(!level.isClientSide){
                CompoundTag nbt = EffectAPI.getEffectsDataNBT(stack, this);
                if(nbt.contains("pet")){
                    Entity ent = level.getEntity(nbt.getInt("pet"));
                    if(ent instanceof LivingEntity living){
                        if(!living.isDeadOrDying()){
                            Arrays.stream(getPotionEffects()).forEach(eff->{
                                if(eff.effectLevelRequirement >= effLevel){
                                    living.addEffect(new MobEffectInstance(eff.mobEffect, eff.duration, eff.level));
                                }
                            });

                            if(!living.closerThan(player, getFollowTeleportDistance())){
                                living.setPos(player.getX(), player.getY(), player.getZ());
                            }
                            return;
                        }
                    }
                }


                LivingEntity livingEntity = createEntity(level, player, stack);
                this.playParticles(level, livingEntity, ParticleTypes.PORTAL, 50, 0.5d);
                EffectAPI.getEffectsDataNBT(stack, this).putInt("pet", livingEntity.getId());
                livingEntity.getPersistentData().putBoolean("jewelerSummon", true);

                level.addFreshEntity(livingEntity);
                playSummonSounds(livingEntity);
                doPostEntitySpawn(level, livingEntity, stack);

                IJewelryEffect.damageStack(player, stack, RANDOM, 100 * effLevel);
            }
        }
    }

    /**
     * Called after the entity has been added to the world.
     * @param level
     * @param entity
     */
    void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack);

    /**
     * Called to play sounds when the mob is summoned.
     * @param entity
     */
    void playSummonSounds(LivingEntity entity);

    /**
     * Called when the entity is going to be created.
     * @return
     */
    LivingEntity createEntity(Level level, Player player, ItemStack stack);

    @Override
    default void curioBreak(Player player, ItemStack stack){
        killPet(player, stack);
    }

    @Override
    default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player) {
            killPet(player, stack);
        }
    }

    /**
     * Called to kill this effects summon.
     * @param player
     * @param stack
     */
    default void killPet(Player player, ItemStack stack){
        Level level = player.level;
        if (!level.isClientSide) {
            CompoundTag nbt = EffectAPI.getEffectsDataNBT(stack, this);
            if (nbt.contains("pet")) {
                Entity ent = level.getEntity(nbt.getInt("pet"));
                if (ent instanceof LivingEntity living) {
                    if (!living.isDeadOrDying()) {
                        living.playSound(SoundEvents.PORTAL_TRIGGER);
                        this.playParticles(level, living, ParticleTypes.SMOKE, 20, 0.5d);
                        living.setHealth(0);
                    }
                }
            }
        }
    }

}

