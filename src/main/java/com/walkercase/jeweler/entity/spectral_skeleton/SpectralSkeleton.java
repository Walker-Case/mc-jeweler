package com.walkercase.jeweler.entity.spectral_skeleton;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.UUID;

public class SpectralSkeleton extends TamableAnimal implements NeutralMob, RangedAttackMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(SpectralSkeleton.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    private final RangedBowAttackGoal<SpectralSkeleton> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        public void stop() {
            super.stop();
            SpectralSkeleton.this.setAggressive(false);
        }

        public void start() {
            super.start();
            SpectralSkeleton.this.setAggressive(true);
        }
    };

    public SpectralSkeleton(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setTame(false);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);

        this.reassessWeaponGoal();
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
    }

    public void reassessWeaponGoal() {
        if (this.level != null && !this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    i = 40;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }
        }
    }

    public void readAdditionalSaveData(CompoundTag p_32152_) {
        super.readAdditionalSaveData(p_32152_);
        this.reassessWeaponGoal();
    }

    public void setItemSlot(EquipmentSlot p_32138_, ItemStack p_32139_) {
        super.setItemSlot(p_32138_, p_32139_);
        if (!this.level.isClientSide) {
            this.reassessWeaponGoal();
        }
    }

    public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, p_32142_);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractarrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrow);
        double d0 = p_32141_.getX() - this.getX();
        double d1 = p_32141_.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = p_32141_.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(abstractarrow);
    }

    protected AbstractArrow getArrow(ItemStack p_32156_, float p_32157_) {
        return ProjectileUtil.getMobArrow(this, p_32156_, p_32157_);
    }

    public boolean canFireProjectileWeapon(ProjectileWeaponItem p_32144_) {
        return p_32144_ == Items.BOW;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    protected SoundEvent getHurtSound(DamageSource p_30424_) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }
    }

    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
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

    //Disable drops.
    @Override
    public java.util.Collection<ItemEntity> captureDrops() {
        return null;
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
}
