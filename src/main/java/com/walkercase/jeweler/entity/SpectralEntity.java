package com.walkercase.jeweler.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Base entity class for spectral entities.
 */
public abstract class SpectralEntity extends TamableAnimal implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(SpectralEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private int summonLevel = 1;


    protected SpectralEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof TamableAnimal) {
            TamableAnimal tamable = (TamableAnimal) target;
            return !tamable.isTame() || tamable.getOwner() != owner;
        }
        if(target instanceof Monster monster){
            return monster.getTarget() == owner;
        }
        return true;
    }

    //Disable drops.
    @Override
    public java.util.Collection<ItemEntity> captureDrops() {
        return null;
    }


    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int p_21673_) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, p_21673_);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_21672_) {
        this.persistentAngerTarget = p_21672_;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    //Wrapped to ensure these are set.

    /**
     * This mobs hurt sound.
     * @return
     */
    protected abstract SoundEvent soundHurt();

    /**
     * This mobs ambient sound.
     * @return
     */
    protected abstract SoundEvent soundAmbient();

    /**
     * This mobs death sound.
     * @return
     */
    protected abstract SoundEvent soundDeath();

    @Override
    protected SoundEvent getAmbientSound() {
        return soundAmbient();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return soundHurt();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return soundDeath();
    }

    /**
     * Sets this entities jewelery effect level.
     * @param level
     */
    public void setSummonLevel(int level){
        this.summonLevel = level;
    }

    /**
     * Returns the jewelery effect level used to summon this entity.
     * @return
     */
    public int getSummonLevel(){
        return this.summonLevel;
    }

}
