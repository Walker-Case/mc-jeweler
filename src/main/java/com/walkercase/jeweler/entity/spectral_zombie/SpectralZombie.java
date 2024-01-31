package com.walkercase.jeweler.entity.spectral_zombie;

import com.walkercase.jeweler.entity.SpectralEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.level.Level;

public class SpectralZombie extends SpectralEntity {

    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
        private int raiseArmTicks;
        public void start() {
            super.start();
            this.raiseArmTicks = 0;
        }

        public void stop() {
            super.stop();
            SpectralZombie.this.setAggressive(false);
        }

        public void tick() {
            super.tick();
            ++this.raiseArmTicks;
            if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
                SpectralZombie.this.setAggressive(true);
            } else {
                SpectralZombie.this.setAggressive(false);
            }

        }
    };

    public SpectralZombie(EntityType<? extends SpectralEntity> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);

        this.goalSelector.addGoal(4, meleeGoal);
    }

    @Override
    protected SoundEvent soundHurt() {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent soundAmbient() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent soundDeath() {
        return SoundEvents.ZOMBIE_DEATH;
    }
}