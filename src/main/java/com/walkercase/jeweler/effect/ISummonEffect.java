package com.walkercase.jeweler.effect;


import com.walkercase.jeweler.api.EffectAPI;
import com.walkercase.jeweler.entity.SpectralEntity;
import com.walkercase.jeweler.item.ItemStackHelper;
import com.walkercase.jeweler.item.jewelry.JewelerItemBase;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
     * @return SummonPotionEffect[]
     */
    SummonPotionEffect[] getPotionEffects();

    /**
     * If the entity is this many blocks away from the player it will teleport them closer.
     * @return int
     */
    default int getFollowTeleportDistance(){
        return 40;
    }

    /**
     * Returns the number of summons to use.
     * @return int
     */
    default int getMaxSummons(ItemStack is){
        return 1;
    };

    /**
     * Called to play a sound at default volume.
     * @param entity
     * @param sound
     */
    default void playSound(LivingEntity entity, SoundEvent sound){
        entity.playSound(sound, 1, 1);
    }

    @Override
    default void curioTick(SlotContext slotContext, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player){
            Level level = slotContext.entity().level;
            int effLevel = EffectAPI.getEffectValue(this, stack);
            if(!level.isClientSide){
                CompoundTag nbt = EffectAPI.getEffectsDataNBT(stack, this);

                if(!nbt.contains("pets")){
                    nbt.putIntArray("pets", new int[0]);
                }
                List<Integer> pets = Arrays.stream( nbt.getIntArray("pets") ).filter(x->x!=0).boxed().collect( Collectors.toList() );
                List<Integer> toRemove = new ArrayList<>();
                AtomicBoolean dirty = new AtomicBoolean(false);

                pets.forEach(id->{
                    Entity ent = level.getEntity(id);

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
                        }
                    }else{
                        dirty.set(true);
                        toRemove.add(id);
                    }
                });

                int lastSummon = nbt.getInt("lastSummon");
                if(pets.size() < getMaxSummons(stack) && lastSummon <= 0){
                    nbt.putInt("lastSummon", 120);
                    LivingEntity livingEntity = createEntity(level, player, stack);
                    this.playParticles(level, livingEntity, ParticleTypes.PORTAL, 50, 0.5d);
                    livingEntity.setHealth(livingEntity.getMaxHealth());

                    livingEntity.getPersistentData().putBoolean("jewelerSummon", true);

                    if(livingEntity instanceof SpectralEntity spectral)
                        spectral.setSummonLevel(effLevel);

                    level.addFreshEntity(livingEntity);

                    pets.add(livingEntity.getId());
                    dirty.set(true);

                    playSummonSounds(livingEntity);
                    doPostEntitySpawn(level, livingEntity, stack);

                    IJewelryEffect.damageStack(player, stack, RANDOM, getSummonDamage(stack));
                }
                if(lastSummon >= 0){
                    nbt.putInt("lastSummon", lastSummon - 1);
                }

                if(dirty.get()){
                    pets.removeAll(toRemove);
                    int[] unboxed = Arrays.stream(pets.toArray(new Integer[0])).mapToInt(Integer::intValue).toArray();
                    EffectAPI.getEffectsDataNBT(stack, this).putIntArray("pets", unboxed);
                }
            }
        }
    }

    /**
     * Returns the amount to damage the ItemStack when summoning this creature.
     * @param is
     * @return int
     */
    default int getSummonDamage(ItemStack is){
        return 100 * EffectAPI.getEffectValue(this, is);
    }

    /**
     * Called after the entity has been added to the world.
     * @param level Level
     * @param entity LivingEntity
     * @param stack ItemStack
     */
    void doPostEntitySpawn(Level level, LivingEntity entity, ItemStack stack);

    /**
     * Called to play sounds when the mob is summoned.
     * @param entity LivingEntity
     */
    void playSummonSounds(LivingEntity entity);

    /**
     * Called when the entity is going to be created.
     * @return LivingEntity
     */
    LivingEntity createEntity(Level level, Player player, ItemStack stack);

    @Override
    default void curioBreak(Player player, ItemStack stack){
        killPets(player, stack);
    }

    @Override
    default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack, JewelerItemBase item){
        if(slotContext.entity() instanceof Player player) {
            if(!ItemStackHelper.equalsIgnoreTransient(newStack, stack)){
                killPets(player, stack);
            }
        }
    }

    /**
     * Called to kill this effects summons.
     * @param player Player
     * @param stack ItemStack
     */
    default void killPets(Player player, ItemStack stack){
        Level level = player.level;
        if (!level.isClientSide) {
            CompoundTag nbt = EffectAPI.getEffectsDataNBT(stack, this);
            if (nbt.contains("pets")) {
                int[] ids = nbt.getIntArray("pets");
                for(int id : ids){
                    Entity ent = level.getEntity(id);
                    if (ent instanceof LivingEntity living) {
                        if (!living.isDeadOrDying()) {
                            playSound(living, SoundEvents.PORTAL_TRIGGER);
                            this.playParticles(level, living, ParticleTypes.SMOKE, 20, 0.5d);
                            living.setHealth(0);
                        }
                    }
                }
            }
        }
    }

}

