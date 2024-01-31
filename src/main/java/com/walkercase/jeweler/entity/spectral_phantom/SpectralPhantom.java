package com.walkercase.jeweler.entity.spectral_phantom;

import com.walkercase.jeweler.entity.FlyingSpectralEntity;
import com.walkercase.jeweler.entity.SpectralEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class SpectralPhantom extends FlyingSpectralEntity {

    public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
    public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(SpectralPhantom.class, EntityDataSerializers.INT);
    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    SpectralPhantom.AttackPhase attackPhase = SpectralPhantom.AttackPhase.CIRCLE;

    public SpectralPhantom(EntityType<? extends SpectralEntity> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.moveControl = new SpectralPhantom.PhantomMoveControl(this);
        this.lookControl = new SpectralPhantom.PhantomLookControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    protected @NotNull BodyRotationControl createBodyControl() {
        return new SpectralPhantom.PhantomBodyRotationControl(this);
    }

    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new SpectralPhantom.PhantomAttackStrategyGoal());
        this.goalSelector.addGoal(2, new SpectralPhantom.PhantomSweepAttackGoal());
        this.goalSelector.addGoal(3, new SpectralPhantom.PhantomCircleAroundAnchorGoal());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 0);
    }

    public void setPhantomSize(int p_33109_) {
        this.entityData.set(ID_SIZE, Mth.clamp(p_33109_, 0, 64));
    }

    private void updatePhantomSizeInfo() {
        this.refreshDimensions();
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue((double)(6 + this.getPhantomSize()));
    }

    public int getPhantomSize() {
        return this.entityData.get(ID_SIZE);
    }

    protected float getStandingEyeHeight(@NotNull Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.35F;
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> p_33134_) {
        if (ID_SIZE.equals(p_33134_)) {
            this.updatePhantomSizeInfo();
        }

        super.onSyncedDataUpdated(p_33134_);
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            float f = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            float f1 = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }

            int i = this.getPhantomSize();
            float f2 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f3 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
            this.level().addParticle(ParticleTypes.MYCELIUM, this.getX() + (double)f2, this.getY() + (double)f4, this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.MYCELIUM, this.getX() - (double)f2, this.getY() + (double)f4, this.getZ() - (double)f3, 0.0D, 0.0D, 0.0D);
        }

    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setPhantomSize(0);
        return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    public void readAdditionalSaveData(CompoundTag p_33132_) {
        super.readAdditionalSaveData(p_33132_);
        if (p_33132_.contains("AX")) {
            this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
        }

        this.setPhantomSize(p_33132_.getInt("Size"));
    }

    public void addAdditionalSaveData(CompoundTag p_33141_) {
        super.addAdditionalSaveData(p_33141_);
        p_33141_.putInt("AX", this.anchorPoint.getX());
        p_33141_.putInt("AY", this.anchorPoint.getY());
        p_33141_.putInt("AZ", this.anchorPoint.getZ());
        p_33141_.putInt("Size", this.getPhantomSize());
    }

    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    public boolean canAttackType(EntityType<?> p_33111_) {
        return true;
    }

    public EntityDimensions getDimensions(Pose p_33113_) {
        int i = this.getPhantomSize();
        EntityDimensions entitydimensions = super.getDimensions(p_33113_);
        float f = (entitydimensions.width + 0.2F * (float)i) / entitydimensions.width;
        return entitydimensions.scale(f);
    }

    public double getPassengersRidingOffset() {
        return (double)this.getEyeHeight();
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    class PhantomAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        public boolean canUse() {
            LivingEntity livingentity = SpectralPhantom.this.getTarget();
            return livingentity != null ? SpectralPhantom.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            SpectralPhantom.this.attackPhase = SpectralPhantom.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            SpectralPhantom.this.anchorPoint = SpectralPhantom.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, SpectralPhantom.this.anchorPoint).above(10 + SpectralPhantom.this.random.nextInt(20));
        }

        public void tick() {
            if (SpectralPhantom.this.attackPhase == SpectralPhantom.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    SpectralPhantom.this.attackPhase = SpectralPhantom.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + SpectralPhantom.this.random.nextInt(4)) * 20);
                    SpectralPhantom.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + SpectralPhantom.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            SpectralPhantom.this.anchorPoint = SpectralPhantom.this.getTarget().blockPosition().above(20 + SpectralPhantom.this.random.nextInt(20));
            if (SpectralPhantom.this.anchorPoint.getY() < SpectralPhantom.this.level().getSeaLevel()) {
                SpectralPhantom.this.anchorPoint = new BlockPos(SpectralPhantom.this.anchorPoint.getX(), SpectralPhantom.this.level().getSeaLevel() + 1, SpectralPhantom.this.anchorPoint.getZ());
            }

        }
    }

    class PhantomBodyRotationControl extends BodyRotationControl {
        public PhantomBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        public void clientTick() {
            SpectralPhantom.this.yHeadRot = SpectralPhantom.this.yBodyRot;
            SpectralPhantom.this.yBodyRot = SpectralPhantom.this.getYRot();
        }
    }

    class PhantomCircleAroundAnchorGoal extends SpectralPhantom.PhantomMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        public boolean canUse() {
            return SpectralPhantom.this.getTarget() == null || SpectralPhantom.this.attackPhase == SpectralPhantom.AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + SpectralPhantom.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + SpectralPhantom.this.random.nextFloat() * 5.0F;
            this.clockwise = SpectralPhantom.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (SpectralPhantom.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + SpectralPhantom.this.random.nextFloat() * 5.0F;
            }

            if (SpectralPhantom.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (SpectralPhantom.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = SpectralPhantom.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (SpectralPhantom.this.moveTargetPoint.y < SpectralPhantom.this.getY() && !SpectralPhantom.this.level().isEmptyBlock(SpectralPhantom.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (SpectralPhantom.this.moveTargetPoint.y > SpectralPhantom.this.getY() && !SpectralPhantom.this.level().isEmptyBlock(SpectralPhantom.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(SpectralPhantom.this.anchorPoint)) {
                SpectralPhantom.this.anchorPoint = SpectralPhantom.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            SpectralPhantom.this.moveTargetPoint = Vec3.atLowerCornerOf(SpectralPhantom.this.anchorPoint).add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle)));
        }
    }

    class PhantomLookControl extends LookControl {
        public PhantomLookControl(Mob p_33235_) {
            super(p_33235_);
        }

        public void tick() {
        }
    }

    class PhantomMoveControl extends MoveControl {
        private float speed = 0.3F;

        public PhantomMoveControl(Mob p_33241_) {
            super(p_33241_);
        }

        public void tick() {
            if (SpectralPhantom.this.horizontalCollision) {
                SpectralPhantom.this.setYRot(SpectralPhantom.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            double d0 = SpectralPhantom.this.moveTargetPoint.x - SpectralPhantom.this.getX();
            double d1 = SpectralPhantom.this.moveTargetPoint.y - SpectralPhantom.this.getY();
            double d2 = SpectralPhantom.this.moveTargetPoint.z - SpectralPhantom.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > (double)1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double)0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = SpectralPhantom.this.getYRot();
                float f1 = (float)Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(SpectralPhantom.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180F / (float)Math.PI));
                SpectralPhantom.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                SpectralPhantom.this.yBodyRot = SpectralPhantom.this.getYRot();
                if (Mth.degreesDifferenceAbs(f, SpectralPhantom.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f4 = (float)(-(Mth.atan2(-d1, d3) * (double)(180F / (float)Math.PI)));
                SpectralPhantom.this.setXRot(f4);
                float f5 = SpectralPhantom.this.getYRot() + 90.0F;
                double d6 = (double)(this.speed * Mth.cos(f5 * ((float)Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double)(this.speed * Mth.sin(f5 * ((float)Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double)(this.speed * Mth.sin(f4 * ((float)Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = SpectralPhantom.this.getDeltaMovement();
                SpectralPhantom.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }

    abstract class PhantomMoveTargetGoal extends Goal {
        public PhantomMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return SpectralPhantom.this.moveTargetPoint.distanceToSqr(SpectralPhantom.this.getX(), SpectralPhantom.this.getY(), SpectralPhantom.this.getZ()) < 4.0D;
        }
    }

    class PhantomSweepAttackGoal extends SpectralPhantom.PhantomMoveTargetGoal {
        private static final int CAT_SEARCH_TICK_DELAY = 20;
        private boolean isScaredOfCat;
        private int catSearchTick;

        public boolean canUse() {
            return SpectralPhantom.this.getTarget() != null && SpectralPhantom.this.attackPhase == SpectralPhantom.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = SpectralPhantom.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player) {
                    Player player = (Player)livingentity;
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (SpectralPhantom.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = SpectralPhantom.this.tickCount + 20;
                        List<Cat> list = SpectralPhantom.this.level().getEntitiesOfClass(Cat.class, SpectralPhantom.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);

                        for(Cat cat : list) {
                            cat.hiss();
                        }

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        public void start() {
        }

        public void stop() {
            SpectralPhantom.this.setTarget(null);
            SpectralPhantom.this.attackPhase = SpectralPhantom.AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingentity = SpectralPhantom.this.getTarget();
            if (livingentity != null) {
                SpectralPhantom.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
                if (SpectralPhantom.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                    SpectralPhantom.this.doHurtTarget(livingentity);
                    SpectralPhantom.this.attackPhase = SpectralPhantom.AttackPhase.CIRCLE;
                    if (!SpectralPhantom.this.isSilent()) {
                        SpectralPhantom.this.level().levelEvent(1039, SpectralPhantom.this.blockPosition(), 0);
                    }
                } else if (SpectralPhantom.this.horizontalCollision || SpectralPhantom.this.hurtTime > 0) {
                    SpectralPhantom.this.attackPhase = SpectralPhantom.AttackPhase.CIRCLE;
                }

            }
        }
    }

    @Override
    protected SoundEvent soundHurt() {
        return SoundEvents.PHANTOM_HURT;
    }

    @Override
    protected SoundEvent soundAmbient() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected SoundEvent soundDeath() {
        return SoundEvents.PHANTOM_DEATH;
    }
}