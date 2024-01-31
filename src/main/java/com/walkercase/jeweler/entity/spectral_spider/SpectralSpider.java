package com.walkercase.jeweler.entity.spectral_spider;

import com.walkercase.jeweler.entity.SpectralEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;

public class SpectralSpider extends SpectralEntity {

    static class SpiderAttackGoal extends MeleeAttackGoal {
        public SpiderAttackGoal(SpectralSpider p_33822_) {
            super(p_33822_, 1.0D, true);
        }
        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }
        protected double getAttackReachSqr(LivingEntity p_33825_) {
            return (4.0F + p_33825_.getBbWidth());
        }
    }

    public SpectralSpider(EntityType<? extends SpectralEntity> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new SpiderAttackGoal(this));
    }

    protected PathNavigation createNavigation(Level p_33802_) {
        return new WallClimberNavigation(this, p_33802_);
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    protected SoundEvent soundHurt() {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent soundAmbient() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent soundDeath() {
        return SoundEvents.SPIDER_DEATH;
    }
}
